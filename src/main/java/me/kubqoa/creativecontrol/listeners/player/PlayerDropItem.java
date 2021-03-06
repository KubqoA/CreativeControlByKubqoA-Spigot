package me.kubqoa.creativecontrol.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class PlayerDropItem implements Listener {
    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (exclude(player.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "allow.*") || perm(player, "allow.drop"))
            return;
        event.setCancelled(true);
        send(player, "drop");
    }
}
