package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.DatabaseHelper;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 01/12/15.
 */
public class BlocksToDB extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.dbtype) { //mysql
            String sql = "INSERT INTO `"+Main.dbprefix+"blocks` (x,y,z,world,material) VALUES ";
            for (Location location:Main.WblocksLocation) {
                sql+="("+location.getX()+","+location.getY()+","+location.getZ()+",'"+location.getWorld().getName()+"','"+Main.WblocksMaterial.get(location)+"'), ";
            }
            sql=sql.substring(0,sql.length()-2);
            sql+=";";
            DatabaseHelper.updateSQL(sql);
        } else { //sqlite
            String sql = "INSERT INTO `"+Main.dbprefix+"blocks` (x,y,z,world,material) ";
            int i=0;
            for (Location location:Main.WblocksLocation) {
                if (i==0) {
                    sql+="SELECT '"+location.getX()+"' , '"+location.getY()+"' , '"+location.getZ()+"' , '"+location.getWorld().getName()+"' , '"+Main.WblocksMaterial.get(location)+"' ";
                } else {
                    sql+="UNION ALL SELECT '"+location.getX()+"' , '"+location.getY()+"' , '"+location.getZ()+"' , '"+location.getWorld().getName()+"' , '"+Main.WblocksMaterial.get(location).name()+"' ";
                }
                i++;
            }
            sql=sql.substring(0,sql.length()-1);
            if (i>0) {
                DatabaseHelper.updateSQL(sql);
            }
        }
        Main.WblocksLocation.clear();
        Main.WblocksMaterial.clear();
    }
}
