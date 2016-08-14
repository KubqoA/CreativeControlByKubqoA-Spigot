package me.kubqoa.creativecontrol.integrations;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

class WorldGuard {
    public static boolean worldGuard(Player player, Location loc, PluginManager pm) {
        return getWorldGuard(pm).canBuild(player, loc);
    }

    private static WorldGuardPlugin getWorldGuard(PluginManager pm) {
        Plugin plugin = pm.getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }
}
