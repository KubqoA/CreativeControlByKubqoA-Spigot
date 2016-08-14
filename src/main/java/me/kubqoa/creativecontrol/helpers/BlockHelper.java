package me.kubqoa.creativecontrol.helpers;

import me.kubqoa.creativecontrol.Main;
import org.bukkit.Location;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 01/12/15.
 */
public class BlockHelper {
    public static boolean isCreativeBlock(Location location) {
        if(Main.RblocksLocation.contains(location)) {
            if (Main.RblocksMaterial.get(location)==location.getBlock().getType()) {
                return false;
            }
        }
        return DatabaseHelper.selectSQL("SELECT * FROM `" + Main.dbprefix + "blocks` WHERE x=" + location.getX() + " AND y=" + location.getY() + " AND z=" + location.getZ() + " AND world='" + location.getWorld().getName() + "'") > 0;
        /*
        try {
            Statement stmt = Main.c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `"+Main.dbprefix+"blocks` WHERE x="+location.getX()+" AND y="+location.getY()+" AND z="+location.getZ()+" AND world='"+location.getWorld().getName()+"'");
            if (rs.next()) {
                String material = rs.getString("material");
                rs.close();
                stmt.close();
                if (material==null) {
                    throw new Error("Failed to obtain data from database. (isCreativeBlock() function)");
                }
                Block block = location.getBlock();
                Material material1 = Material.valueOf(material);
                if (material1==block.getType()) {
                    return true;
                } else if (material1 == Material.GRASS && block.getType() == Material.DIRT) {
                    return true;
                } else if (material1==Material.DIRT && block.getType() == Material.GRASS) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to check whether block is listed in database.", e);
        }*/
    }

    public static void removeBlock(Location location) {
        DatabaseHelper.updateSQL("DELETE FROM `"+Main.dbprefix+"blocks` WHERE x="+location.getX()+" AND y="+location.getY()+" AND z="+location.getZ()+" AND world='"+location.getWorld().getName()+"'");
    }

    public static void updateBlock(Location oldLocation, Location newLocation) {
        if (!Main.RblocksLocation.contains(oldLocation)) {
            DatabaseHelper.updateSQL("UPDATE `" + Main.dbprefix + "blocks` SET x = " + newLocation.getX() + ", y = " + newLocation.getY() + ", z = " + newLocation.getZ() + ", world = '" + newLocation.getWorld().getName() + "' WHERE x=" + oldLocation.getX() + " AND y=" + oldLocation.getY() + " AND z=" + oldLocation.getZ() + " AND world='" + oldLocation.getWorld().getName() + "'");
        }
    }
}
