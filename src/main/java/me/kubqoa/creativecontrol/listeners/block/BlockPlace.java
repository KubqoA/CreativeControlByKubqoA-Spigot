package me.kubqoa.creativecontrol.listeners.block;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.BlockAPI;
import me.kubqoa.creativecontrol.helpers.Methods;
import me.kubqoa.creativecontrol.listeners.CreatureSpawn;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;
import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class BlockPlace implements Listener {
    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "bypass.*") || perm(player, "bypass.place"))
            return;
        if (!event.canBuild()) return;
        Block block = event.getBlock();
        if (exclude(block.getLocation())) return;
        Material material = block.getType();
        if (Main.noTracking.contains(material)) return;
        if (Main.items.contains(material)) {
            Methods.send(player, "disabled-block");
            event.setCancelled(true);
            return;
        }
        if (material == Material.PUMPKIN) {
            CreatureSpawn.pumpkin.add(player);
        } else if (material == Material.SKULL && block.getData() == (byte) 1) {
            CreatureSpawn.wither.add(player);
        }
        BlockAPI blockAPI = new BlockAPI(block);
        if (!blockAPI.canBreak(player)) return;
        blockAPI.addBlock(material);
    }
}
