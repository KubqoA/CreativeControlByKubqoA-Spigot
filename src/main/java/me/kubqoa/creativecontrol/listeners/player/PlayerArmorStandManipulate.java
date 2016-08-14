package me.kubqoa.creativecontrol.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;
import static me.kubqoa.creativecontrol.helpers.Methods.perm;
import static me.kubqoa.creativecontrol.helpers.Methods.send;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class PlayerArmorStandManipulate implements Listener {
    @EventHandler
    public void armorStand(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        if (exclude(player.getLocation()) || player.getGameMode()!= GameMode.CREATIVE || perm(player,"*") || perm(player,"allow.*") || perm(player,"allow.armorstand")) return;
        event.setCancelled(true);
        send(player,"armor_stand");
    }
}
