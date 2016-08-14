package me.kubqoa.creativecontrol.listeners;

import me.kubqoa.creativecontrol.api.BlockAPI;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;

/**
 * CreativeControl class
 * by KubqoA © 2015
 */
class SpawnerSpawn implements Listener {
    @EventHandler
    public void spawner(SpawnerSpawnEvent event) {
        CreatureSpawner spawner = event.getSpawner();
        if (exclude(spawner.getLocation()) || !new BlockAPI(spawner.getBlock()).isCreativeBlock()) return;
        event.setCancelled(true);
    }
}
