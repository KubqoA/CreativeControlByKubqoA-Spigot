package me.kubqoa.creativecontrol.listeners.vehicle;

import me.kubqoa.creativecontrol.api.VehicleAPI;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class VehicleMove implements Listener {
    @EventHandler
    public void vehicleMove(VehicleMoveEvent event) {
        Vehicle vehicle = event.getVehicle();
        if (exclude(vehicle.getLocation())) return;
        VehicleAPI vehicleAPI = new VehicleAPI(vehicle);
        vehicleAPI.updateVehicle(event.getFrom(),event.getTo());
    }
}
