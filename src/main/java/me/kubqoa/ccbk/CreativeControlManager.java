package me.kubqoa.ccbk;

import me.kubqoa.ccbk.integrations.Vault;
import me.kubqoa.ccbk.listeners.CreativeControlListeners;
import me.kubqoa.ccbk.managers.CreativeControlConfigManager;
import me.kubqoa.ccbk.managers.CreativeControlDatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import java.io.File;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class CreativeControlManager {
    public void CreativeControlEnable() {
        // VERSION DETERMINE
        CreativeControl.serverVersion=Bukkit.getBukkitVersion().split("-")[0];
        CreativeControl.serverVersion=CreativeControl.serverVersion.substring(0,CreativeControl.serverVersion.lastIndexOf("."));

        CreativeControl.pdf = getPluginDescriptionFile();
        CreativeControl.folder = getPluginFolder();

        log("Loading configurations...");
        new CreativeControlConfigManager().CreativeControlConfig();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        Plugin vault = pm.getPlugin("Vault");
        if (vault != null) {
            log("Hooking with Vault");
            if (!vault.isEnabled()) pm.enablePlugin(vault);
            new Vault().setup();
            CreativeControl.vault=true;
        }

        log("Connecting to database...");
        new CreativeControlDatabaseManager().onEnable();

        log("Registering listeners...");
        new CreativeControlListeners().registerCreativeControlListeners();
    }

    public void CreativeControlDisable() {
        log("Disconnecting from database...");
        new CreativeControlDatabaseManager().onDisable();
    }


    private PluginDescriptionFile getPluginDescriptionFile() {
        return CreativeControl.getInstance().getDescription();
    }

    private File getPluginFolder() {
        File folder = new File(CreativeControl.getInstance().getDataFolder().getParent() + "/CreativeControl");
        if (!folder.exists() && !folder.isDirectory()) {
            log("Plugin directory not found, creating one.");
            if (!folder.mkdirs()) {
                log("Failed to create plugin directory.");
                return null;
            }
        }
        return folder;
    }


    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&3["+CreativeControl.pdf.getName()+" "+CreativeControl.pdf.getVersion()+"] &9"+message));
    }
}
