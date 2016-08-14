package me.kubqoa.creativecontrol.listeners.block;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class BlockBreakBedrock implements Listener {
    @EventHandler
    public void bedrock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (exclude(block.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "bypass.*") || perm(player, "bypass.bedrock"))
            return;
        if (block.getType() == Material.BEDROCK) {
            double y = block.getLocation().getY();
            World.Environment world = block.getWorld().getEnvironment();
            if (world == World.Environment.NETHER) {
                if (y >= 122) {
                    event.setCancelled(true);
                    send(player, "block-break-bedrock");
                }
            } else if (world == World.Environment.NORMAL) {
                if (y <= 5) {
                    event.setCancelled(true);
                    send(player, "block-break-bedrock");
                }
            }
        }
    }
}
