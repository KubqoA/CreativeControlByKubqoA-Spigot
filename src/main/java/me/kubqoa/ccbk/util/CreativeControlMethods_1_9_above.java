package me.kubqoa.ccbk.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class CreativeControlMethods_1_9_above {
    public static ItemStack getItemInHand(PlayerInventory inventory) {
        return inventory.getItemInMainHand();
    }
}
