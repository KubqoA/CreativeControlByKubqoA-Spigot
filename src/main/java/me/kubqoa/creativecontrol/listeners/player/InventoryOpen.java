package me.kubqoa.creativecontrol.listeners.player;

import me.kubqoa.creativecontrol.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class InventoryOpen implements Listener {
    @EventHandler
    public void inventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        String name = event.getInventory().getName().replace("container.", "").replace("mob.", "").toLowerCase();
        if (Main.messages.get("container-" + name) == null) return;
        if (exclude(player.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "allow.*") || perm(player, "allow.container.*") || perm(player, "allow.container." + name))
            return;
        event.setCancelled(true);
        send(player, "container-" + name);
    }
}
