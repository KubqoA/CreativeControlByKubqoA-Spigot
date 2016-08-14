package me.kubqoa.creativecontrol.listeners;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.BlockAPI;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 10/01/16.
 */
class LeftClickListener implements Listener {
    @EventHandler
    public void leftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (Main.addMode.contains(player)) {
                Main.addMode.remove(player);
                event.setCancelled(true);
                BlockAPI blockAPI = new BlockAPI(block);
                if (blockAPI.isCreativeBlock()) {
                    Methods.sendMsg(player, "&4This block is already in database!");
                    return;
                }
                blockAPI.addBlock(block.getType());
                Methods.sendMsg(player, "&4Block added!");
            } else if (Main.removeMode.contains(player)) {
                Main.removeMode.remove(player);
                event.setCancelled(true);
                BlockAPI blockAPI = new BlockAPI(block);
                if (!blockAPI.isCreativeBlock()) {
                    Methods.sendMsg(player, "&4This block is not in database, so it couldn't have been deleted!");
                    return;
                }
                blockAPI.removeBlock();
                Methods.sendMsg(player, "&4Block removed!");
            }
        }
    }
}
