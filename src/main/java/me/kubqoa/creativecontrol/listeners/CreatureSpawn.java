package me.kubqoa.creativecontrol.listeners;

import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 28/04/16.
 */
public class CreatureSpawn implements Listener {
    public static final List<Player> pumpkin = new ArrayList<Player>();
    public static final List<Player> wither = new ArrayList<Player>();

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
        if (reason.equals(CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) || reason.equals(CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN) && pumpkin.size()>0) {
            List<Entity> entities = event.getEntity().getNearbyEntities(7,7,7);
            for (Entity entity : entities) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (pumpkin.contains(player) && !Methods.perm(player,"*") && !Methods.perm(player,"allow.*") && !Methods.perm(player,"allow.mobspawn")) {
                        Methods.send(player,"mob-create");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            pumpkin.clear();
        } else if (reason.equals(CreatureSpawnEvent.SpawnReason.BUILD_WITHER) && wither.size()>0) {
            List<Entity> entities = event.getEntity().getNearbyEntities(7,7,7);
            for (Entity entity : entities) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (wither.contains(player) && !Methods.perm(player,"*") && !Methods.perm(player,"allow.*") && !Methods.perm(player,"allow.mobspawn")) {
                        Methods.send(player,"mob-create");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            wither.clear();
        }
    }
}
