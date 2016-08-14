package me.kubqoa.creativecontrol.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 02/05/16.
 */
public class PlayerInteractEntity implements Listener {
    private final List<Material> animals = new ArrayList<Material>();

    {
        animals.add(Material.SEEDS);
        animals.add(Material.CARROT_ITEM);
        animals.add(Material.GOLDEN_APPLE);
        animals.add(Material.GOLDEN_CARROT);
        animals.add(Material.YELLOW_FLOWER);
        animals.add(Material.WHEAT);
    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (exclude(player.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "allow.*"))
            return;
        Material material = player.getItemInHand().getType();
        Entity entity = event.getRightClicked();
        if (entity instanceof ItemFrame && !perm(player, "allow.itemframe")) {
            event.setCancelled(true);
            send(player, "item_frame");
        } else if (entity instanceof org.bukkit.entity.minecart.StorageMinecart && !perm(player, "allow.container.minecart")) {
            event.setCancelled(true);
            send(player, "container-minecart");
        } else if (entity instanceof Animals) {
            if (animals.contains(material) && !perm(player, "allow.breed")) {
                event.setCancelled(true);
                send(player, "breed");
                return;
            } else if (material == Material.SADDLE && entity instanceof Pig && !perm(player, "allow.saddle")) {
                event.setCancelled(true);
                send(player, "saddle");
                return;
            }
        }

        if (material == Material.EGG && !perm(player, "allow.chickenegg")) {
            event.setCancelled(true);
            send(player, "chicken_egg");
        } else if (material == Material.EYE_OF_ENDER && !perm(player, "allow.eyeofender")) {
            event.setCancelled(true);
            send(player, "eye_of_ender");
        } else if (material == Material.MONSTER_EGG || material == Material.MONSTER_EGGS && !perm(player, "allow.monsteregg")) {
            event.setCancelled(true);
            send(player, "monster_egg");
        } else if (material == Material.FISHING_ROD && !perm(player, "allow.fish")) {
            event.setCancelled(true);
            send(player, "fish");
        } else if (material == Material.POTION && !perm(player, "allow.potion")) {
            event.setCancelled(true);
            send(player, "potion");
        } else if (material == Material.SNOW_BALL && !perm(player, "allow.snowball")) {
            event.setCancelled(true);
            send(player, "snowball");
        } else if (material == Material.BOW && !perm(player, "allow.shooting")) {
            event.setCancelled(true);
            send(player, "shooting");
        }

    }
}
