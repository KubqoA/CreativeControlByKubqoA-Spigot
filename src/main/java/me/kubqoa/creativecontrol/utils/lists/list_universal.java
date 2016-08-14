package me.kubqoa.creativecontrol.utils.lists;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class list_universal {
    public static final List<Material> willreplace = new ArrayList<Material>();
    static {
        willreplace.add(Material.VINE);
        willreplace.add(Material.SNOW);
        willreplace.add(Material.DEAD_BUSH);
        willreplace.add(Material.LONG_GRASS);
        willreplace.add(Material.DOUBLE_PLANT);
    }
    public static final List<Material> minecarts = new ArrayList<Material>();
    static {
        minecarts.add(Material.MINECART);
        minecarts.add(Material.COMMAND_MINECART);
        minecarts.add(Material.EXPLOSIVE_MINECART);
        minecarts.add(Material.HOPPER_MINECART);
        minecarts.add(Material.POWERED_MINECART);
        minecarts.add(Material.STORAGE_MINECART);
    }
    public static final List<Material> recordings = new ArrayList<Material>();
    static {
        recordings.add(Material.RECORD_3);
        recordings.add(Material.RECORD_4);
        recordings.add(Material.RECORD_5);
        recordings.add(Material.RECORD_6);
        recordings.add(Material.RECORD_7);
        recordings.add(Material.RECORD_8);
        recordings.add(Material.RECORD_9);
        recordings.add(Material.RECORD_10);
        recordings.add(Material.RECORD_11);
        recordings.add(Material.RECORD_12);
        recordings.add(Material.GOLD_RECORD);
        recordings.add(Material.GREEN_RECORD);
    }
    public static final List<Material> seeds = new ArrayList<Material>();
    static {
        seeds.add(Material.PUMPKIN_SEEDS);
        seeds.add(Material.MELON_SEEDS);
        seeds.add(Material.POTATO_ITEM);
        seeds.add(Material.CARROT_ITEM);
        seeds.add(Material.SEEDS);
    }
}
