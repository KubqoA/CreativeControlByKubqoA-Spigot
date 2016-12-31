package me.kubqoa.ccbk.listeners.block;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.api.CreativeControlBlock;
import me.kubqoa.ccbk.api.CreativeControlPlayer;
import me.kubqoa.ccbk.managers.CreativeControlDatabaseManager;
import me.kubqoa.ccbk.util.AsyncBlockCheck;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class BlockBreakHelpers implements Listener
{
    public static final CreativeControl instance = CreativeControl.getInstance();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        CreativeControlPlayer player = new CreativeControlPlayer(event.getPlayer());
        if (player.hasPermission("bypass.block.break")) return;
        final CreativeControlBlock block = new CreativeControlBlock(event.getBlock());
        if (block.isExcluded() || !block.isTracked() || block.isDisabled()) return;
        if (player.isInCreative()) {
            block.removeBlockFromDB();
        } else {
            if (block.isCreativeBlock()) {
                block.removeBlock();
                block.removeBlockFromDB();
                block.removeBlockFromCache();
                player.sendConfMsg("block-break");
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(instance, new AsyncBlockCheck(event.getPlayer()));
    }

    public void packetBlockDigListener() {
        CreativeControl.protocolManager.addPacketListener(new PacketAdapter(instance,
                ListenerPriority.NORMAL,
                PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(final PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                    PacketContainer packet = event.getPacket();
                    final Location location = packet.getBlockPositionModifier().read(0).toLocation(event.getPlayer().getWorld());
                    Bukkit.getScheduler().runTaskAsynchronously(instance, new BukkitRunnable() {
                        @Override
                        public void run() {//TODO: block break for creative
                            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                                if (!CreativeControl.playerCheckedLocations.contains(location)) {
                                    if (CreativeControlDatabaseManager.executeQuery("SELECT * FROM `" + CreativeControl.databasePrefix + "blocks` " +
                                            "WHERE x=? AND y=? AND z=? AND world=?;", new Object[]{location.getX(), location.getY(), location.getZ(), location.getWorld().getUID().toString()}) > 0) {
                                        CreativeControl.playerCreativeLocations.replace(event.getPlayer().getUniqueId().toString(), location);
                                    }
                                    CreativeControl.playerCheckedLocations.replace(event.getPlayer().getUniqueId().toString(), location);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(instance, new AsyncBlockCheck(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(instance, new AsyncBlockCheck(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final CreativeControlPlayer player = new CreativeControlPlayer(event.getPlayer());
        if (player.hasPermission("bypass.block.break")) return;
        CreativeControl.playerCheckedLocations.remove(player.getUUID());
        CreativeControl.playerCreativeLocations.remove(player.getUUID());
    }
}
