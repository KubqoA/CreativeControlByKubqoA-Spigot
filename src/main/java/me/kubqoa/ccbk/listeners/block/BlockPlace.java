package me.kubqoa.ccbk.listeners.block;

import me.kubqoa.ccbk.api.CreativeControlBlock;
import me.kubqoa.ccbk.api.CreativeControlPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class BlockPlace implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        CreativeControlPlayer player = new CreativeControlPlayer(event.getPlayer());
        if (player.hasPermission("bypass.block.place")) return;
        CreativeControlBlock block = new CreativeControlBlock(event.getBlock());
        if (block.isExcluded() || !block.isTracked()) return;
        if (block.isDisabled()) {
            player.sendConfMsg("disabled-block");
            return;
        }
        block.addBlockToDB();
    }
}
