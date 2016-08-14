package me.kubqoa.creativecontrol.listeners.hanging;

import me.kubqoa.creativecontrol.api.HangingAPI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;
import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class HangingPlace implements Listener {
    @EventHandler
    public void placeEvent(HangingPlaceEvent event) {
        Player player = event.getPlayer();
        if (exclude(player.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "bypass.*") || perm(player, "bypass.hanging.*") || perm(player, "bypass.hanging.place"))
            return;
        Location location = event.getEntity().getLocation();
        HangingAPI HangingAPI = new HangingAPI(location);
        HangingAPI.addHanging();
    }
}
