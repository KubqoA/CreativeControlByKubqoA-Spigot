package me.kubqoa.creativecontrol.listeners.hanging;

import me.kubqoa.creativecontrol.api.HangingAPI;
import org.bukkit.entity.Hanging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class HangingBreak implements Listener {
    @EventHandler
    public void breakEvent(HangingBreakEvent event) {
        Hanging entity = event.getEntity();
        if (exclude(entity.getLocation())) return;
        HangingAPI HangingAPI = new HangingAPI(entity.getLocation());
        if (HangingAPI.isCreativeHanging()) {
            HangingAPI.removeHanging();
            entity.remove();
        }
    }
}
