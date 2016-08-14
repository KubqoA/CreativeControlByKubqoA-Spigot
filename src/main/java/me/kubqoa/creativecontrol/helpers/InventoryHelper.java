package me.kubqoa.creativecontrol.helpers;

import me.kubqoa.creativecontrol.Base64Coder;
import me.kubqoa.creativecontrol.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * CreativeControl class
 * by KubqoA © 2015
 */
public class InventoryHelper {
    private final Player player;

    public InventoryHelper(Player p) {
        player = p;
    }

    public String encodeInventory() {
        ItemStack[] itemStacks = player.getInventory().getContents();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(itemStacks.length);

            // Save every element in the list
            for (ItemStack itemStack : itemStacks) {
                dataOutput.writeObject(itemStack);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public ItemStack[] decodeInventory(GameMode gameMode) {
        String data;
        String uuid = player.getUniqueId().toString();
        if (gameMode == GameMode.ADVENTURE && Main.aInventory.containsKey(uuid)) data = Main.aInventory.get(uuid);
        else if (gameMode == GameMode.CREATIVE && Main.cInventory.containsKey(uuid)) data = Main.cInventory.get(uuid);
        else if (gameMode == GameMode.SURVIVAL && Main.sInventory.containsKey(uuid)) data = Main.sInventory.get(uuid);
        else return null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            int size = dataInput.readInt();
            ItemStack[] itemStacks = new ItemStack[size];

            // Read the serialized inventory
            for (int i = 0; i < size; i++) {
                itemStacks[i] = ((ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return itemStacks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

/*
    public String encodeArmor() {
        return encodeItem(player.getInventory().getBoots()) + "_-_-_" + encodeItem(player.getInventory().getLeggings()) + "_-_-_" +encodeItem(player.getInventory().getChestplate()) + "_-_-_" +encodeItem(player.getInventory().getHelmet())+ "_-_-_" +encodeItem(player.getInventory().getItemInOffHand());
    }

    public List<ItemStack> decodeArmor(GameMode gameMode) {
        String data;
        String uuid = player.getUniqueId().toString();
        List<ItemStack> armor = new ArrayList<ItemStack>();
        if (gameMode == GameMode.ADVENTURE && Main.aArmor.containsKey(uuid)) data = Main.aArmor.get(uuid);
        else if (gameMode == GameMode.CREATIVE && Main.cArmor.containsKey(uuid)) data = Main.cArmor.get(uuid);
        else if (gameMode == GameMode.SURVIVAL && Main.sArmor.containsKey(uuid)) data = Main.sArmor.get(uuid);
        else return null;
        try {
            for (String armorpart : data.split("_-_-_")) {
                armor.add(decodeItem(armorpart));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return armor;
    }

    private String encodeItem(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(item);

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    private ItemStack decodeItem(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            return (ItemStack) dataInput.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }*/
}
