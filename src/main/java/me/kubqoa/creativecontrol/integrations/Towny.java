package me.kubqoa.creativecontrol.integrations;

import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.palmergames.bukkit.util.BukkitTools;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 2015.
 */

class Towny {
    public static boolean towny(Player player, Block block) {
        return PlayerCacheUtil.getCachePermission(player, block.getLocation(), BukkitTools.getTypeId(block), BukkitTools.getData(block), TownyPermission.ActionType.BUILD);
    }
}
