package me.kubqoa.creativecontrol.listeners.block;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.BlockAPI;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;
import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class BlockBreak implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void blockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (perm(player, "*") || perm(player, "bypass.*") || perm(player, "bypass.break")) return;
        if (exclude(block.getLocation())) return;
        BlockAPI blockAPI = new BlockAPI(block);
        if (!blockAPI.canBreak(player)) return;
        if (blockAPI.isCreativeBlock()) {
            blockAPI.removeBlock();
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
                block.setType(Material.AIR);
                Methods.sendMsg(player, Main.messages.getString("block-break"));
            }
        }
        blockAPI.blocksAbove();
    }
}
