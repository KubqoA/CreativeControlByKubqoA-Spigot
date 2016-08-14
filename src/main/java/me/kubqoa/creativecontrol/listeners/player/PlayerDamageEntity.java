package me.kubqoa.creativecontrol.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;
import static me.kubqoa.creativecontrol.helpers.Methods.perm;
import static me.kubqoa.creativecontrol.helpers.Methods.send;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class PlayerDamageEntity implements Listener {
    @EventHandler
    public void hit(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if(event.isCancelled()) return;
        if (damager instanceof Player && entity instanceof Damageable) {
            String name = entity.getName().toLowerCase();
            name = name.replaceAll(" ","-");
            if (name.contains("endercrystal")) name="ender-crystal";
            Player player = (Player) damager;
            if (exclude(player.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "allow.*") || perm(player, "allow.damage.*") || perm(player, "allow.damage."+name)) return;
            event.setCancelled(true);
            send(player,"damage-"+name);
        }
    }
}
