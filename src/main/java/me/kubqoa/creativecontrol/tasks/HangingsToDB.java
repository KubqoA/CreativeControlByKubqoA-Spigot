package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.DatabaseHelper;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 03/12/15.
 */
public class HangingsToDB extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.dbtype) { //mysql
            String sql = "INSERT INTO `" + Main.dbprefix + "hangings` (x,y,z,world) VALUES ";
            for (Location location : Main.WhangingsLocation) {
                sql += "(" + location.getX() + "," + location.getY() + "," + location.getZ() + ",'" + location.getWorld().getName() + "'), ";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ";";
            DatabaseHelper.updateSQL(sql);
        } else { //sqlite
            String sql = "INSERT INTO `" + Main.dbprefix + "hangings` (x,y,z,world) ";
            int i = 0;
            for (Location location : Main.WhangingsLocation) {
                if (i == 0) {
                    sql += "SELECT '" + location.getX() + "' , '" + location.getY() + "' , '" + location.getZ() + "' , '" + location.getWorld().getName() + "' ";
                } else {
                    sql += "UNION ALL SELECT '" + location.getX() + "' , '" + location.getY() + "' , '" + location.getZ() + "' , '" + location.getWorld().getName() + "' ";
                }
                i++;
            }
            sql = sql.substring(0, sql.length() - 1);
            DatabaseHelper.updateSQL(sql);
        }
        Main.WhangingsLocation.clear();
    }
}
