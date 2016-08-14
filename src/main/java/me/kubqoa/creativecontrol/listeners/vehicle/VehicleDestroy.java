package me.kubqoa.creativecontrol.listeners.vehicle;

import me.kubqoa.creativecontrol.api.VehicleAPI;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class VehicleDestroy implements Listener {
    @EventHandler
    public void vehicleDestroy(VehicleDestroyEvent event) {
        Vehicle vehicle = event.getVehicle();
        if (exclude(vehicle.getLocation())) return;
        VehicleAPI vehicleAPI = new VehicleAPI(vehicle);
        if (vehicleAPI.isCreativeVehicle()) {
            vehicleAPI.removeVehicle();
            if (event.getAttacker() instanceof Player) {
                Player player = (Player) event.getAttacker();
                if (player.getGameMode() != GameMode.SURVIVAL || perm(player, "*") || perm(player, "bypass.*") || perm(player, "bypass.vehicles.*") || perm(player, "bypass.vehicles.break"))
                    return;
                event.setCancelled(true);
                vehicle.remove();
                send(player, "vehicle-break");
            }
        }
    }
}
