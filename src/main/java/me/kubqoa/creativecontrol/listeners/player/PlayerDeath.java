package me.kubqoa.creativecontrol.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class PlayerDeath implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getGameMode() != GameMode.CREATIVE) return;
        event.setKeepInventory(true);
    }
}
