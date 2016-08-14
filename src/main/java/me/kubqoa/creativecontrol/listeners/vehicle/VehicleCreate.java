package me.kubqoa.creativecontrol.listeners.vehicle;

import me.kubqoa.creativecontrol.api.VehicleAPI;
import me.kubqoa.creativecontrol.utils.lists.list_universal;
import org.bukkit.GameMode;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;
import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class VehicleCreate implements Listener {
    @EventHandler
    public void vehicleCreate(VehicleCreateEvent event) {
        Vehicle vehicle = event.getVehicle();
        Player player;
        if (exclude(vehicle.getLocation())) return;
        if (vehicle instanceof Boat) return;
        for (Entity ent : event.getVehicle().getNearbyEntities(4, 4, 4)) {
            if (ent instanceof Player) {
                player = (Player) ent;
                if (player.getGameMode() == GameMode.CREATIVE && !perm(player, "*") && !perm(player, "bypass.*") && !perm(player, "bypass.vehicles.*") && !perm(player, "bypass.vehicles.place")) {
                    if (list_universal.minecarts.contains(player.getItemInHand().getType())) {
                        new VehicleAPI(vehicle).addVehicle();
                        return;
                    }
                }
            }
        }
    }
}
