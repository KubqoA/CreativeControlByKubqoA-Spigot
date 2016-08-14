package me.kubqoa.creativecontrol.listeners.player;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.BlockAPI;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 08/05/16.
 */
class PlayerBucketEmpty implements Listener {
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "bypass.*") || perm(player, "bypass.bucketplace"))
            return;
        Material material = event.getBucket();
        if (Main.noTracking.contains(material)) return;
        if (Main.items.contains(material)) {
            Methods.send(player, "disabled-block");
            event.setCancelled(true);
            return;
        }
        Block block = event.getBlockClicked().getRelative(event.getBlockFace());
        Methods.console(block.getLocation().toString());
        Methods.console(block.getType().name());
        BlockAPI blockAPI = new BlockAPI(block);
        if (blockAPI.canBreak(player)) {
            blockAPI.addBlock(material);
        }
    }
}
