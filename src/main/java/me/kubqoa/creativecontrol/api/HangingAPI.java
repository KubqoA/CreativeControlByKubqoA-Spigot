package me.kubqoa.creativecontrol.api;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.HangingHelper;
import me.kubqoa.creativecontrol.integrations.Integrations;
import me.kubqoa.creativecontrol.tasks.HangingsFromDB;
import me.kubqoa.creativecontrol.tasks.HangingsToDB;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * CreativeControl class
 *
 * by KubqoA © 2015
 */
public class HangingAPI {
    private final Location loc;
    public HangingAPI(Location location) {loc=location;}

    public boolean isCreativeHanging() {
        if (Main.hangingsLocation.contains(loc)) {
            return true;
        } else if (Main.WhangingsLocation.contains(loc)) {
            return true;
        } else if (HangingHelper.isCreativeHanging(loc)) {
            return true;
        }

        return false;
    }

    public boolean canBreak(Player player) {
        return Integrations.canBreak(loc.getBlock(),player);
    }

    public void removeHanging() {
        if (Main.WhangingsLocation.contains(loc)) {
            Main.WhangingsLocation.remove(loc);
        }
        if (Main.hangingsLocation.contains(loc)) {
            Main.hangingsLocation.remove(loc);
        }
        if (HangingHelper.isCreativeHanging(loc)) {
            Main.RhangingsLocation.add(loc);
            if (Main.RhangingsLocation.size()>=Main.removingInterval) {
                new HangingsFromDB().runTaskAsynchronously(Main.thisPlugin);
            }
        }
    }

    public void addHanging() {
        if (Main.hangingsLocation.size()<Main.hangingCache) {
            Main.hangingsLocation.add(loc);
        }
        Main.WhangingsLocation.add(loc);
        if (Main.WhangingsLocation.size()>=Main.loggingInterval) {
            new HangingsToDB().runTaskAsynchronously(Main.thisPlugin);
        }
    }
}
