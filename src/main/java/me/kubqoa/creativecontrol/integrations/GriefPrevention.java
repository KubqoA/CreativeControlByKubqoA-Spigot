package me.kubqoa.creativecontrol.integrations;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 2015.
 */

class GriefPrevention {
    public static boolean griefPrevention(Player player, Block block) {
        String can = me.ryanhamshire.GriefPrevention.GriefPrevention.instance.allowBuild(player, block.getLocation(), block.getType());
        return can == null;
    }
}
