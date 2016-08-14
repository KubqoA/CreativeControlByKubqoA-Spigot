package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.DatabaseHelper;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/01/16.
 */
public class HangingsFromDB extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.dbtype) { //mysql
            String sql = "DELETE FROM `" + Main.dbprefix + "hangings` WHERE (x,y,z,world) IN (";
            for (Location location : Main.RhangingsLocation) {
                sql += "(" + location.getX() + "," + location.getY() + "," + location.getZ() + ",'" + location.getWorld().getName() + "'),";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
            DatabaseHelper.updateSQL(sql);
        } else { //sqlite
            String sql = "DELETE FROM `" + Main.dbprefix + "hangings` WHERE ";
            for (Location location : Main.RhangingsLocation) {
                sql += "(x=" + location.getX() + " AND y=" + location.getY() + " AND z=" + location.getZ() + " AND world='" + location.getWorld().getName() + "') OR ";
            }
            sql = sql.substring(0, sql.length() - 4);
            DatabaseHelper.updateSQL(sql);
        }
        Main.RhangingsLocation.clear();
    }
}
