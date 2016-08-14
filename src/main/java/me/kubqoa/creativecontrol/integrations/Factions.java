package me.kubqoa.creativecontrol.integrations;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * CreativeControl class
 * <p/>
 * by KubqoA © 2015
 */
class Factions {
    public static boolean factions(Player player, Location loc) {
        return com.massivecraft.factions.engine.EngineMain.canPlayerBuildAt(player, com.massivecraft.massivecore.ps.PS.valueOf(loc), false);
    }
}
