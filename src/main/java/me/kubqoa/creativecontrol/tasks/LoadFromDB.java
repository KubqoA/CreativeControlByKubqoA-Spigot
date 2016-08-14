package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 13/01/16.
 */
public class LoadFromDB {
    public static void load() {
        try {
            Statement stmt = Main.c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "blocks`");
            int i = 0;
            while (rs.next() && i < Main.blockCache) {
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                World world = Bukkit.getServer().getWorld(rs.getString("world"));
                Material material = Material.valueOf(rs.getString("material"));
                Location location = new Location(world, x, y, z);
                Main.blocksLocation.add(location);
                Main.blocksMaterial.put(location, material);
                i++;
            }
            rs.close();
            rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "vehicles`");
            i = 0;
            while (rs.next() && i < Main.vehicleCache) {
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                World world = Bukkit.getServer().getWorld(rs.getString("world"));
                Location location = new Location(world, x, y, z);
                Main.vehiclesLocation.add(location);
                i++;
            }
            rs.close();
            rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "hangings`");
            i = 0;
            while (rs.next() && i < Main.hangingCache) {
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                World world = Bukkit.getServer().getWorld(rs.getString("world"));
                Location location = new Location(world, x, y, z);
                Main.hangingsLocation.add(location);
                i++;
            }
            rs.close();
            rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "inventories`");
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String inventory = rs.getString("inventory");
                String s = rs.getString("gamemode");
                if (s.equals("SURVIVAL")) {
                    Main.sInventory.put(uuid, inventory);
                } else if (s.equals("CREATIVE")) {
                    Main.cInventory.put(uuid, inventory);
                } else if (s.equals("ADVENTURE")) {
                    Main.aInventory.put(uuid, inventory);
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new IllegalStateException("Error while loading from DB!", e);
        }
    }
}