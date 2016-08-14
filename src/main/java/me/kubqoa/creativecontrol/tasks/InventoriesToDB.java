package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.DatabaseHelper;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 13/01/16.
 */
public class InventoriesToDB extends BukkitRunnable {
    private String gamemode;
    public InventoriesToDB(String gamemode) {
        this.gamemode = gamemode;
    }

    @Override
    public void run() {
        if (gamemode.equalsIgnoreCase("all")) {
            survival(); creative(); adventure();
        } else if (gamemode.equalsIgnoreCase("SURVIVAL")) {
            survival();
        } else if (gamemode.equalsIgnoreCase("CREATIVE")) {
            creative();
        } else if (gamemode.equalsIgnoreCase("ADVENTURE")) {
            adventure();
        }
    }

    private void survival() {
        //Survival inventories
        gamemode = "SURVIVAL";
        for (String uuid : Main.wsInventory.keySet()) {
            String inv = Main.wsInventory.get(uuid);
            if (DatabaseHelper.selectSQL("SELECT * FROM `" + Main.dbprefix + "inventories` WHERE uuid='" + uuid + "' AND gamemode='" + gamemode + "'") > 0) {
                DatabaseHelper.updateSQL("UPDATE `" + Main.dbprefix + "inventories` SET inventory='" + inv + "' WHERE uuid='" + uuid + "' AND gamemode='" + gamemode + "'");
            } else {
                DatabaseHelper.updateSQL("INSERT INTO `" + Main.dbprefix + "inventories` (uuid, gamemode, inventory) VALUES ('" + uuid + "','" + gamemode + "','" + inv + "')");
            }
        }
    }

    private void creative() {
        //Creative inventories
        gamemode = "CREATIVE";
        for (String uuid : Main.wcInventory.keySet()) {
            String inv = Main.wcInventory.get(uuid);
            if (DatabaseHelper.selectSQL("SELECT * FROM `" + Main.dbprefix + "inventories` WHERE uuid='" + uuid + "' AND gamemode='" + gamemode + "'") > 0) {
                DatabaseHelper.updateSQL("UPDATE `" + Main.dbprefix + "inventories` SET inventory='" + inv + "' WHERE uuid='" + uuid + "' AND gamemode='" + gamemode + "'");
            } else {
                DatabaseHelper.updateSQL("INSERT INTO `" + Main.dbprefix + "inventories` (uuid, gamemode, inventory) VALUES ('" + uuid + "','" + gamemode + "','" + inv + "')");
            }
        }
    }

    private void adventure() {
        //Adventure inventories
        gamemode = "ADVENTURE";
        for (String uuid : Main.waInventory.keySet()) {
            String inv = Main.waInventory.get(uuid);
            if (DatabaseHelper.selectSQL("SELECT * FROM `" + Main.dbprefix + "inventories` WHERE uuid='" + uuid + "' AND gamemode='" + gamemode + "'") > 0) {
                DatabaseHelper.updateSQL("UPDATE `" + Main.dbprefix + "inventories` SET inventory='" + inv + "' WHERE uuid='" + uuid + "' AND gamemode='" + gamemode + "'");
            } else {
                DatabaseHelper.updateSQL("INSERT INTO `" + Main.dbprefix + "inventories` (uuid, gamemode, inventory) VALUES ('" + uuid + "','" + gamemode + "','" + inv + "')");
            }
        }
    }
}
