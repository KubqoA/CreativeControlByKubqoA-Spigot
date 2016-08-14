package me.kubqoa.creativecontrol.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControl class
 * by KubqoA © 2015
 */
public class PlayerInteract implements Listener {
    private List<Material> seeds = new ArrayList<Material>();
    private List<Material> recordings = new ArrayList<Material>();

    public PlayerInteract() {
        seeds = me.kubqoa.creativecontrol.utils.lists.list_universal.seeds;
        recordings = me.kubqoa.creativecontrol.utils.lists.list_universal.recordings;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (exclude(player.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "allow.*"))
            return;
        ItemStack hand = player.getItemInHand();
        Material material = hand.getType();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Material clicked = event.getClickedBlock().getType();
            if (clicked == Material.BEACON && !perm(player, "allow.beacon")) {
                event.setCancelled(true);
                send(player, "beacon");
            } else if (material == Material.EGG && !perm(player, "allow.chickenegg")) {
                event.setCancelled(true);
                send(player, "chicken_egg");
            } else if (material == Material.EXP_BOTTLE && !perm(player, "allow.expbottle")) {
                event.setCancelled(true);
                send(player, "exp_bottle");
            } else if (material == Material.EYE_OF_ENDER && clicked == Material.ENDER_PORTAL_FRAME && !perm(player, "allow.fillenderportal")) {
                event.setCancelled(true);
                send(player, "ender_portal");
            } else if (material == Material.EYE_OF_ENDER && !perm(player, "allow.eyeofender")) {
                event.setCancelled(true);
                send(player, "eye_of_ender");
            } else if (material == Material.FISHING_ROD && !perm(player, "allow.fish")) {
                event.setCancelled(true);
                send(player, "fish");
            } else if (material == Material.FLINT_AND_STEEL && !perm(player, "allow.ignite")) {
                event.setCancelled(true);
                send(player, "ignite");
            } else if (recordings.contains(material) && clicked == Material.JUKEBOX && !perm(player, "allow.jukebox")) {
                event.setCancelled(true);
                send(player, "jukebox");
            } else if (material == Material.MONSTER_EGG || material == Material.MONSTER_EGGS && !perm(player, "allow.monsteregg")) {
                event.setCancelled(true);
                send(player, "monster_egg");
            } else if (seeds.contains(material) && clicked == Material.SOIL && !perm(player, "allow.plant")) {
                event.setCancelled(true);
                send(player, "plant");
            } else //noinspection deprecation
                if (hand.getData().toString().equals("BROWN DYE(3)") && clicked == Material.LOG && event.getClickedBlock().getData() == 3 && !perm(player, "allow.plant")) {
                    event.setCancelled(true);
                    send(player, "plant");
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
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (material == Material.EGG && !perm(player, "allow.chickenegg")) {
                event.setCancelled(true);
                send(player, "chicken_egg");
            } else if (material == Material.EXP_BOTTLE && !perm(player, "allow.expbottle")) {
                event.setCancelled(true);
                send(player, "exp_bottle");
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
        } else if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.SOIL && !perm(player, "allow.destroyfarmland")) {
                event.setCancelled(true);
                send(player, "destroy-farmland");
            }
        }
    }
}
