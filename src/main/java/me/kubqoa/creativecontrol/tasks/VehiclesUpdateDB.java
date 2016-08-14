package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.Methods;
import me.kubqoa.creativecontrol.helpers.VehicleHelper;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/01/16.
 */
public class VehiclesUpdateDB extends BukkitRunnable {
    @Override
    public void run() {
        List<Location> locations1 = new ArrayList<Location>(Main.UvehiclesLocation2.keySet());
        for (Location location : locations1) {
            Methods.console(location.toString());
            VehicleHelper.updateVehicle(location, Main.UvehiclesLocation2.get(location));
        }
    }
}
