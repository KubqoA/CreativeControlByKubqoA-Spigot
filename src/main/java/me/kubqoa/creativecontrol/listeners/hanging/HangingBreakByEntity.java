package me.kubqoa.creativecontrol.listeners.hanging;

import me.kubqoa.creativecontrol.api.HangingAPI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class HangingBreakByEntity implements Listener {
    @EventHandler
    public void breakByEntityEvent(HangingBreakByEntityEvent event) {
        Entity remover = event.getRemover();
        Location location = event.getEntity().getLocation();
        if (exclude(location)) return;
        HangingAPI HangingAPI = new HangingAPI(location);
        if (HangingAPI.isCreativeHanging()) {
            HangingAPI.removeHanging();
            event.setCancelled(true);
            if (remover instanceof Player) {
                Player player = (Player) remover;
                if (player.getGameMode() == GameMode.CREATIVE || perm(player, "*") || perm(player, "bypass.*") || perm(player, "bypass.hanging.*") || perm(player, "bypass.hanging.break"))
                    return;
                if (HangingAPI.canBreak(player)) {
                    event.getEntity().remove();
                    send(player, "hanging-break");
                }
            } else {
                event.getEntity().remove();
            }
        }
    }
}
