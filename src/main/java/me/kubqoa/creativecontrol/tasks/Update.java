package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.UpdateChecker;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 08/12/15.
 */
public class Update extends BukkitRunnable {
    @Override
    public void run() {
        Methods.console(Main.prefix + "&cChecking for updates..");
        String latestVersion;
        String currentVersion = "v" + Main.thisPlugin.getDescription().getVersion();
        try {
            UpdateChecker updateChecker = new UpdateChecker();
            updateChecker.checkUpdate(currentVersion);
            latestVersion = updateChecker.getLatestVersion();
            if (latestVersion != null) {
                latestVersion = "v" + latestVersion;
                Methods.console(Main.prefix + "&cNew update found! Current version &6" + currentVersion + "&c latest version &6" + latestVersion + "&c! Download here:");
                Methods.console(Main.prefix + "&6https://www.spigotmc.org/resources/creativecontrol.9988/");
            } else {
                Methods.console(Main.prefix + "&cNo updates found!");
            }
        } catch (Exception ex) {
            Methods.console(Main.prefix + "&cFailed to check for update!");
        }
    }
}
