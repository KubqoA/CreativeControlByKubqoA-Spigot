package me.kubqoa.ccbk.listeners;

import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.CreativeControlManager;
import me.kubqoa.ccbk.listeners.block.BlockBreakHelpers;
import me.kubqoa.ccbk.listeners.block.BlockPlace;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class CreativeControlListeners {
    private final PluginManager pm;
    private final JavaPlugin plugin;

    public CreativeControlListeners() {
        this.pm= CreativeControl.getInstance().getServer().getPluginManager();
        this.plugin=CreativeControl.getInstance();
    }

    public void registerCreativeControlListeners() {
        //list of enabled features
        /*List<String> enabledFeatures = new ArrayList<String>();
        for (Object o : CreativeControl.config.getList("enabled-features")) {
            enabledFeatures.add(o.toString());
        }

        if (enabledFeatures.contains("BlockBreak")) {
            String blockBreakMode = CreativeControl.config.getString("block-break-mode");
            if (blockBreakMode.equalsIgnoreCase("full")) {
                pm.registerEvents(new BlockBreakFull(), plugin);
            } else if (blockBreakMode.equalsIgnoreCase("partly")) {
                pm.registerEvents(new BlockBreakPartly(), plugin);
            } else if (blockBreakMode.equalsIgnoreCase("none")) {
                pm.registerEvents(new BlockBreakNone(), plugin);
            } else {
                CreativeControlManager.log("Invalid value in config.yml for block-break-mode.");
            }
        }
        if (enabledFeatures.contains("BlockFall")) {
            if (CreativeControl.serverVersion.equalsIgnoreCase("1.7")) {
                pm.registerEvents(new BlockFall_1_7(), plugin);
            } else {
                pm.registerEvents(new BlockFall(), plugin);
            }
        }
        if (enabledFeatures.contains("BlockPlace")) {
            pm.registerEvents(new BlockPlace(), plugin);
        }*/
        pm.registerEvents(new BlockBreakHelpers(), plugin);
        new BlockBreakHelpers().packetBlockDigListener();
        pm.registerEvents(new BlockPlace(), plugin);
        CreativeControlManager.log("OK!");
    }
}
