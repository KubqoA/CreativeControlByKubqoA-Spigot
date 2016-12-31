package me.kubqoa.ccbk.util;

import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.api.CreativeControlPlayer;
import me.kubqoa.ccbk.managers.CreativeControlDatabaseManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class AsyncBlockCheck extends BukkitRunnable {
    CreativeControlPlayer player;
    public AsyncBlockCheck(Player player) {
        this.player = new CreativeControlPlayer(player);
    }

    @Override
    public void run() {
        if (player.hasPermission("bypass.block.break")) return;
        final Location location = player.getPlayer().getTargetBlock((Set<Material>) null,5).getLocation();
        if (!CreativeControl.playerCheckedLocations.contains(location)) {
            if (CreativeControlDatabaseManager.executeQuery("SELECT * FROM `" + CreativeControl.databasePrefix + "blocks` " +
                    "WHERE x=? AND y=? AND z=? AND world=?;", new Object[]{location.getX(), location.getY(), location.getZ(), location.getWorld().getUID().toString()}) > 0) {
                CreativeControl.playerCreativeLocations.put(player.getUUID(), location);
            }
            CreativeControl.playerCheckedLocations.put(player.getUUID(), location);
        }
    }
}
