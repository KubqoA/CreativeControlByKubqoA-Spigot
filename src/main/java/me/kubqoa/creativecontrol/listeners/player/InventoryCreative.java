package me.kubqoa.creativecontrol.listeners.player;

import me.kubqoa.creativecontrol.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.*;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class InventoryCreative implements Listener {
    @EventHandler
    public void inventoryItem(InventoryCreativeEvent event) {
        Player player = (Player) event.getWhoClicked();
        Material material = event.getCursor().getType();
        if (exclude(player.getLocation()) || player.getGameMode() != GameMode.CREATIVE || perm(player, "*") || perm(player, "item.*") || perm(player, "item." + material.name()) || !Main.items.contains(material))
            return;
        event.setCancelled(true);
        send(player, "disabled-item");
    }
}
