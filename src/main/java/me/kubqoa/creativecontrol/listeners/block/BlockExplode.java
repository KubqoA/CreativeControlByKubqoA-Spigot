package me.kubqoa.creativecontrol.listeners.block;

import me.kubqoa.creativecontrol.api.BlockAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class BlockExplode implements Listener {
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        Block block = event.getBlock();
        if (exclude(block.getLocation())) return;
        BlockAPI blockAPI = new BlockAPI(block);
        if (blockAPI.isCreativeBlock()) {
            event.setCancelled(true);
            block.setType(Material.AIR);
            blockAPI.removeBlock();
        }
    }
}
