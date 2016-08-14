package me.kubqoa.creativecontrol.listeners;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.listeners.block.*;
import me.kubqoa.creativecontrol.listeners.hanging.HangingBreak;
import me.kubqoa.creativecontrol.listeners.hanging.HangingBreakByEntity;
import me.kubqoa.creativecontrol.listeners.hanging.HangingPlace;
import me.kubqoa.creativecontrol.listeners.piston.PistonExtend;
import me.kubqoa.creativecontrol.listeners.piston.PistonRetract;
import me.kubqoa.creativecontrol.listeners.piston.PistonRetract_1_7;
import me.kubqoa.creativecontrol.listeners.player.*;
import me.kubqoa.creativecontrol.listeners.vehicle.VehicleCreate;
import me.kubqoa.creativecontrol.listeners.vehicle.VehicleCreate_1_9;
import me.kubqoa.creativecontrol.listeners.vehicle.VehicleDestroy;
import me.kubqoa.creativecontrol.listeners.vehicle.VehicleMove;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 2015.
 */
public class Listeners {
    private final PluginManager pm;
    private final JavaPlugin pl;

    public Listeners(PluginManager pluginManager, JavaPlugin plugin) {
        pm = pluginManager;
        pl = plugin;
    }

    public void init() {
        if (Main.enabledFeatures.contains("BlockBreak")) {
            pm.registerEvents(new BlockBreak(), pl);
        }
        if (Main.enabledFeatures.contains("BedrockBreak")) {
            pm.registerEvents(new BlockBreakBedrock(), pl);
        }
        if (Main.enabledFeatures.contains("BlockPlace")) {
            pm.registerEvents(new BlockPlace(), pl);
        }
        if (Main.enabledFeatures.contains("BlockFall")) {
            if (!Main.serverVersion.equals("1.7")) {
                pm.registerEvents(new BlockFall(), pl);
            } else {
                pm.registerEvents(new BlockFall_1_7(), pl);
            }
        }
        if (Main.enabledFeatures.contains("BlockExplode") && !Main.serverVersion.equals("1.7")) {
            pm.registerEvents(new BlockExplode(), pl);
        }

        if (Main.enabledFeatures.contains("HangingPlace")) {
            pm.registerEvents(new HangingPlace(), pl);
        }
        if (Main.enabledFeatures.contains("HangingBreak")) {
            pm.registerEvents(new HangingBreak(), pl);
            pm.registerEvents(new HangingBreakByEntity(), pl);
        }

        if (Main.enabledFeatures.contains("VehicleCreate")) {
            if (!Main.serverVersion.equals("1.9")) {
                pm.registerEvents(new VehicleCreate(), pl);
            } else {
                pm.registerEvents(new VehicleCreate_1_9(), pl);
            }
        }
        if (Main.enabledFeatures.contains("VehicleDestroy")) {
            pm.registerEvents(new VehicleDestroy(), pl);
        }
        if (Main.enabledFeatures.contains("VehicleMove")) {
            pm.registerEvents(new VehicleMove(), pl);
        }

        if (Main.enabledFeatures.contains("PlayerArmorStandManipulate") && !Main.serverVersion.equals("1.7")) {
            pm.registerEvents(new PlayerArmorStandManipulate(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerCommandRestrict")) {
            pm.registerEvents(new PlayerCommandPreprocess(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerDamageEntity")) {
            pm.registerEvents(new PlayerDamageEntity(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerDeathNoDrop")) {
            pm.registerEvents(new PlayerDeath(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerDropItem")) {
            pm.registerEvents(new PlayerDropItem(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerGamemodeChange")) {
            pm.registerEvents(new PlayerGamemodeChange(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerJoin")) {
            pm.registerEvents(new PlayerJoin(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerPickupItem")) {
            pm.registerEvents(new PlayerPickupItem(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerQuit")) {
            pm.registerEvents(new PlayerQuit(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerOpenRestrictedInventory")) {
            pm.registerEvents(new InventoryOpen(), pl);
        }
        if (Main.enabledFeatures.contains("PlayerBannedItemFromInventory")) {
            pm.registerEvents(new InventoryCreative(), pl);
        }
        if (Main.serverVersion.equals("1.9")) {
            pm.registerEvents(new PlayerInteractEntity_1_9(), pl);
        } else {
            pm.registerEvents(new PlayerInteractEntity(), pl);
        }
        if (Main.serverVersion.equals("1.9")) {
            pm.registerEvents(new PlayerInteract_1_9(), pl);
        } else {
            pm.registerEvents(new PlayerInteract(), pl);
        }

        if (Main.enabledFeatures.contains("SpawnerSpawnEntity")) {
            pm.registerEvents(new SpawnerSpawn(), pl);
        }

        if (Main.enabledFeatures.contains("PistonExtend")) {
            pm.registerEvents(new PistonExtend(), pl);
        }
        if (Main.enabledFeatures.contains("PistonRetract")) {
            if (Main.serverVersion.equals("1.7")) {
                pm.registerEvents(new PistonRetract_1_7(), pl);
            } else {
                pm.registerEvents(new PistonRetract(), pl);
            }
        }

        pm.registerEvents(new LeftClickListener(), pl);


        //EXPERIMENTAL -DEV ONLY

        //pm.registerEvents(new PlayerBucketEmpty(), pl);
    }
}