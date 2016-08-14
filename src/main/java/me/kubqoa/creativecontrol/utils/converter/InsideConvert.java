package me.kubqoa.creativecontrol.utils.converter;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.DatabaseHelper;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 05/02/16.
 */
class InsideConvert {
    private final CommandSender sender;

    public InsideConvert(CommandSender sender) {
        this.sender = sender;
    }

    private final List<Location> tempBlockLocation = new ArrayList<Location>();
    private final HashMap<Location, Material> tempBlockMaterial = new HashMap<Location, Material>();

    private final List<Location> tempBlockLocation2 = new ArrayList<Location>();

    public void mysql() {
        if (Main.dbtype) {
            try {
                Methods.sendMsg(sender, "&cStarting conversion!");
                Class.forName("org.sqlite.JDBC");
                Connection c = DriverManager.getConnection("jdbc:sqlite:" + Main.folder + "/creativecontrol.db");
                Statement stmt = c.createStatement();
                Methods.sendMsg(sender, "&cReading data!");
                ResultSet rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "blocks`");
                while (rs.next()) {
                    double x = rs.getDouble("x");
                    double y = rs.getDouble("y");
                    double z = rs.getDouble("z");
                    World world = Bukkit.getServer().getWorld(rs.getString("world"));
                    Material material = Material.valueOf(rs.getString("material"));
                    Location location = new Location(world, x, y, z);
                    tempBlockLocation.add(location);
                    tempBlockMaterial.put(location, material);
                }
                rs.close();
                stmt.close();
                c.close();
                stmt = Main.c.createStatement();
                rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "blocks`");
                while (rs.next()) {
                    double x = rs.getDouble("x");
                    double y = rs.getDouble("y");
                    double z = rs.getDouble("z");
                    World world = Bukkit.getServer().getWorld(rs.getString("world"));
                    Location location = new Location(world, x, y, z);
                    tempBlockLocation2.add(location);
                }
                rs.close();
                stmt.close();
                Methods.sendMsg(sender, "&cWriting data!");
                String sql = "INSERT INTO `" + Main.dbprefix + "blocks` (x,y,z,world,material) VALUES ";
                String sql2 = "DELETE FROM `" + Main.dbprefix + "blocks` WHERE (x,y,z,world) IN (";
                int i2 = 0;
                for (Location location : tempBlockLocation) {
                    if (tempBlockLocation2.contains(location)) {
                        sql2 += "(" + location.getX() + "," + location.getY() + "," + location.getZ() + ",'" + location.getWorld().getName() + "'),";
                        i2++;
                    }
                    sql += "(" + location.getX() + "," + location.getY() + "," + location.getZ() + ",'" + location.getWorld().getName() + "','" + tempBlockMaterial.get(location) + "'), ";
                }
                if (i2 > 0) {
                    sql2 = sql2.substring(0, sql2.length() - 1);
                    sql2 += ")";
                    DatabaseHelper.updateSQL(sql2);
                }
                sql = sql.substring(0, sql.length() - 2);
                sql += ";";
                DatabaseHelper.updateSQL(sql);
                tempBlockMaterial.clear();
                tempBlockLocation.clear();
                tempBlockLocation2.clear();
                Methods.sendMsg(sender, "&cFinished!");
            } catch (Exception e) {
                Methods.sendMsg(sender, "&cError while converting from MySQL to SQLite");
                e.printStackTrace();
            }
        } else {
            Methods.sendMsg(sender, "&cYou can convert to MySQL only when you have MySQL configured as your active connection!");
        }
    }

    public void sqlite() {
        if (Main.dbtype) {
            try {
                Methods.sendMsg(sender, "&cStarting conversion!");
                Statement stmt = Main.c.createStatement();
                Methods.sendMsg(sender, "&cReading data!");
                ResultSet rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "blocks`");
                while (rs.next()) {
                    double x = rs.getDouble("x");
                    double y = rs.getDouble("y");
                    double z = rs.getDouble("z");
                    World world = Bukkit.getServer().getWorld(rs.getString("world"));
                    Material material = Material.valueOf(rs.getString("material"));
                    Location location = new Location(world, x, y, z);
                    tempBlockLocation.add(location);
                    tempBlockMaterial.put(location, material);
                }
                rs.close();
                stmt.close();
                Class.forName("org.sqlite.JDBC");
                Connection c = DriverManager.getConnection("jdbc:sqlite:" + Main.folder + "/creativecontrol.db");
                stmt = c.createStatement();
                rs = stmt.executeQuery("SELECT * FROM `" + Main.dbprefix + "blocks`");
                while (rs.next()) {
                    double x = rs.getDouble("x");
                    double y = rs.getDouble("y");
                    double z = rs.getDouble("z");
                    World world = Bukkit.getServer().getWorld(rs.getString("world"));
                    Location location = new Location(world, x, y, z);
                    tempBlockLocation2.add(location);
                }
                rs.close();
                Methods.sendMsg(sender, "&cWriting data!");
                String sql = "INSERT INTO `" + Main.dbprefix + "blocks` (x,y,z,world,material) ";
                String sql2 = "DELETE FROM `" + Main.dbprefix + "blocks` WHERE ";
                int i = 0;
                int i2 = 0;
                for (Location location : tempBlockLocation) {
                    if (tempBlockLocation2.contains(location)) {
                        sql2 += "(x=" + location.getX() + " AND y=" + location.getY() + " AND z=" + location.getZ() + " AND world='" + location.getWorld().getName() + "') OR ";
                        i2++;
                    }
                    if (i == 0) {
                        sql += "SELECT '" + location.getX() + "' , '" + location.getY() + "' , '" + location.getZ() + "' , '" + location.getWorld().getName() + "' , '" + tempBlockMaterial.get(location) + "' ";
                    } else {
                        sql += "UNION ALL SELECT '" + location.getX() + "' , '" + location.getY() + "' , '" + location.getZ() + "' , '" + location.getWorld().getName() + "' , '" + tempBlockMaterial.get(location).name() + "' ";
                    }
                    i++;
                }
                if (i2 > 0) {
                    sql2 = sql2.substring(0, sql2.length() - 4);
                    stmt.executeUpdate(sql2);
                }
                sql = sql.substring(0, sql.length() - 1);
                stmt.executeUpdate(sql);
                c.close();
                tempBlockMaterial.clear();
                tempBlockLocation.clear();
                Methods.sendMsg(sender, "&cFinished!");
            } catch (Exception e) {
                Methods.sendMsg(sender, "&cError while converting from SQLite to MySQL!");
                e.printStackTrace();
            }
        } else {
            Methods.sendMsg(sender, "&cYou can convert to SQLite only when you have MySQL configured as your active connection!");
        }
    }
}
