package me.kubqoa.ccbk.listeners.block;

import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.api.CreativeControlBlock;
import me.kubqoa.ccbk.api.CreativeControlPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class BlockBreakNone implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        CreativeControlPlayer player = new CreativeControlPlayer(event.getPlayer());
        if (player.hasPermission("*") || player.hasPermission("bypass.*") || player.hasPermission("bypass.block.*") || player.hasPermission("bypass.block.break")) return;
        CreativeControlBlock block = new CreativeControlBlock(event.getBlock());
        if (CreativeControl.excludedWorlds.contains(block.getWorld())) return;
        if (block.isCreativeBlock()) {
            block.removeBlockFromDB();
            block.removeBlock();
        }
    }
}
