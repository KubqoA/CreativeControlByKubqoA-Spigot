package me.kubqoa.creativecontrol;

import me.kubqoa.creativecontrol.commands.Commands;
import me.kubqoa.creativecontrol.helpers.ConfigHelper;
import me.kubqoa.creativecontrol.helpers.DatabaseHelper;
import me.kubqoa.creativecontrol.helpers.Methods;
import me.kubqoa.creativecontrol.helpers.SimpleConfig;
import me.kubqoa.creativecontrol.integrations.Vault;
import me.kubqoa.creativecontrol.listeners.Listeners;
import me.kubqoa.creativecontrol.tasks.*;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CreativeControl class
 *
 * by KubqoA © 2015
 */
public class Main extends JavaPlugin {
    /* VARIABLES */

    public static Plugin factions,griefPrevention,residence,towny,vault,worldGuard; //supported plugins
    public static JavaPlugin thisPlugin; //This plugin
    public static PluginManager pm;

    public static SimpleConfig config, messages, players, disable; //Configs

    public static List<GameMode> disabledGamemodes = new ArrayList<GameMode>(); //disabled gamemodes
    public static List<String> exclude = new ArrayList<String>(); //stop plugin working in this worlds
    public static List<String> excludeCMD = new ArrayList<String>(); //disabled commands in creative
    public static List<Material> items = new ArrayList<Material>(); //disabled items list
    public static List<Material> noTracking = new ArrayList<Material>(); //do not track creative placement of these blocks
    public static List<String> addperms = new ArrayList<String>(); //add this permissions when gamemode changed to creative
    public static List<String> removeperms = new ArrayList<String>(); //remove this permissions when gamemode changed to creative
    public static List<String> enabledFeatures = new ArrayList<String>(); //enabled features

    public static final List<Location> blocksLocation = new ArrayList<Location>();
    public static final HashMap<Location,Material> blocksMaterial = new HashMap<Location, Material>();

    public static final List<Location> WblocksLocation = new ArrayList<Location>(); //to be written
    public static final HashMap<Location,Material> WblocksMaterial = new HashMap<Location, Material>(); //to be written

    public static final List<Location> RblocksLocation = new ArrayList<Location>(); //to be removed
    public static final HashMap<Location,Material> RblocksMaterial = new HashMap<Location, Material>(); //to be removed

    public static final List<Location> hangingsLocation = new ArrayList<Location>();

    public static final List<Location> WhangingsLocation = new ArrayList<Location>(); //to be written

    public static final List<Location> RhangingsLocation = new ArrayList<Location>(); //to be removed

    public static final List<Location> vehiclesLocation = new ArrayList<Location>();

    public static final List<Location> WvehiclesLocation = new ArrayList<Location>(); //to be written

    public static final List<Location> RvehiclesLocation = new ArrayList<Location>(); //to be removed

    public static final ConcurrentHashMap<Location,Location> UvehiclesLocation1 = new ConcurrentHashMap<Location, Location>(); //to be updated (struc. newLoc, oldLoc);
    public static final ConcurrentHashMap<Location,Location> UvehiclesLocation2 = new ConcurrentHashMap<Location, Location>(); //to be updated (struc. oldLoc, newLoc);

    public static final HashMap<String,String> cInventory = new HashMap<String, String>(); //Creative inv
    public static final HashMap<String,String> sInventory = new HashMap<String, String>(); //Survival inv
    public static final HashMap<String,String> aInventory = new HashMap<String, String>(); //Adventure inv

    public static final HashMap<String,String> wcInventory = new HashMap<String, String>(); //Creative inv (to be written)
    public static final HashMap<String,String> wsInventory = new HashMap<String, String>(); //Survival inv (to be written)
    public static final HashMap<String,String> waInventory = new HashMap<String, String>(); //Adventure inv (to be written)

    public static int loggingInterval = 0; //after how many blocks they should be logged to DB
    public static final int removingInterval = 50; //after how many blocks they should be removed from DB
    public static final int updateInterval = 50; //after how many vehicles in update memory should they be updated to DB
    public static int blockCache = 0; //how many blocks can be stored in memory
    public static int vehicleCache = 0; //how many blocks can be stored in memory
    public static int hangingCache = 0; //how many blocks can be stored in memory

    public static boolean dbtype = false; //FALSE = SQLITE | TRUE = MYSQL
    public static Connection c = null; //SQL CONNECTION
    public static File folder; //Plugin folder

    public static final List<Player> addMode = new ArrayList<Player>(); //players in mode of adding [blocks,hangings,vehicles] to DB
    public static final List<Player> removeMode = new ArrayList<Player>(); //players in mode of removing [blocks,hangings,vehicles] to DB

    public static String protectionType; //Block protection type
    public static String prefix; //Message prefix
    public static String dbprefix; //Table prefix

    public static final List<Player> cooldownsP = new ArrayList<Player>();
    public static final List<String> cooldownsS = new ArrayList<String>();
    public static long cooldown = 0; //message cooldown

    public static String serverVersion;


    @Override
    public void onEnable() {
        super.onEnable();
        Methods.console("&4[------------------[&cCreative Control&4]------------------]");
        Methods.console("&6verison: &c"+this.getDescription().getVersion()+"&6 by &cKubqoA");
        Methods.console("&6If you have found some in-game &cbugs &6report them to the issue tracker.");
        Methods.console("&6Be sure to leave review on plugin page on SpigotMC.");
        Methods.console("");
        pm = super.getServer().getPluginManager();
        folder = new File(getDataFolder().getParent()+"/CreativeControl");
        if (!folder.exists() && !folder.isDirectory()) { //PLUGIN DIRECTORY
            Methods.console("&6Plugin directory&c not found, creating one!");
            if (!folder.mkdirs()){
                Methods.console("&cFailed to create plugin directory!");
            }
        }

        // VERSION DETERMINE
        serverVersion=this.getServer().getBukkitVersion().split("-")[0];
        serverVersion=serverVersion.substring(0,serverVersion.lastIndexOf("."));

        // CONFIG
        ConfigHelper configHelper = new ConfigHelper(this);
        configHelper.start();
        config = configHelper.config;
        messages = configHelper.messages;
        players = configHelper.players;
        disable = configHelper.disable;
        protectionType = configHelper.protectionType;
        prefix = ChatColor.translateAlternateColorCodes('&',configHelper.prefix);
        int counter = 0;
        for( int i=0; i<prefix.length(); i++ ) {
            if( prefix.charAt(i) == '§' ) {
                counter++;
            }
        }
        if (prefix.length()!=counter*2) {
            prefix+=" ";
        }
        dbprefix = configHelper.dbprefix;
        cooldown = configHelper.cooldown;
        loggingInterval = configHelper.logwhen;
        disabledGamemodes = configHelper.disabledGamemodes;
        exclude = configHelper.exclude;
        excludeCMD = configHelper.excludeCMD;
        items = configHelper.items;
        addperms = configHelper.addperms;
        removeperms = configHelper.removeperms;
        noTracking = configHelper.noTracking;
        blockCache = configHelper.blockCache;
        vehicleCache = configHelper.vehicleCache;
        hangingCache = configHelper.hangingCache;
        disabledGamemodes = configHelper.disabledGamemodes;
        enabledFeatures = configHelper.enabledFeatures;
        if (config.getString("db-type").equalsIgnoreCase("mysql")) dbtype = true;

        // DATABASE
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.start();
        databaseHelper.checkTables();

        // INTEGRATIONS
        factions = pm.getPlugin("Factions");
        griefPrevention = pm.getPlugin("GriefPrevention");
        residence = pm.getPlugin("Residence");
        towny = pm.getPlugin("Towny");
        vault = pm.getPlugin("Vault");
        worldGuard = pm.getPlugin("WorldGuard");
        thisPlugin= (JavaPlugin) pm.getPlugin("CreativeControlByKubqoA");

        if (factions!=null) {
            Plugin massivecore = pm.getPlugin("MassiveCore");
            if (massivecore!=null) {
                Methods.console("&cFound MassiveCore plugin! Implementing it!");
                if (!massivecore.isEnabled()) {
                    Methods.console("&cMassiveCore not enabled! Enabling!");
                    pm.enablePlugin(massivecore);
                }
                Methods.console("&cFound Factions plugin! Implementing it!");
                if (!factions.isEnabled()) {
                    Methods.console("&cFactions not enabled! Enabling!");
                    pm.enablePlugin(factions);
                    try {
                        Class.forName("com.massivecraft.factions.engine.EngineMain");
                        Methods.console("Factions");
                    } catch (ClassNotFoundException e) {
                        try {
                            Class.forName("com.massivecraft.factions.listeners.FactionsListenerMain");
                            Methods.console("FactionsUUID");
                        } catch (ClassNotFoundException e1) {
                            Methods.console("nor Factions or FactionsUUID");
                        }
                    }
                }
            } else {
                Methods.console("&cFound Factions but not MassiveCore! Error!");
            }
        }

        if (griefPrevention!=null) {
            Methods.console("&cFound GriefPrevention plugin! Implementing it!");
            if (!griefPrevention.isEnabled()) {
                Methods.console("&cGriefPrevention not enabled! Enabling!");
                pm.enablePlugin(griefPrevention);
            }
        }

        if (residence!=null) {
            Methods.console("&cFound Residence plugin! Implementing it!");
            if (!residence.isEnabled()) {
                Methods.console("&cResidence not enabled! Enabling!");
                pm.enablePlugin(residence);
            }
        }

        if (towny!=null) {
            Methods.console("&cFound Towny plugin! Implementing it!");
            if (!towny.isEnabled()) {
                Methods.console("&cTowny not enabled! Enabling!");
                pm.enablePlugin(towny);
            }
        }

        if (vault!=null) {
            Methods.console("&cFound Vault plugin! Implementing it!");
            if (!vault.isEnabled()) {
                Methods.console("&cVault not enabled! Enabling!");
                pm.enablePlugin(vault);
            }
            new Vault().setup();
        }

        if (worldGuard!=null) {
            Methods.console("&cFound WorldGuard plugin! Implementing it!");
            if (!worldGuard.isEnabled()) {
                Methods.console("&cWorldGuard not enabled! Enabling!");
                pm.enablePlugin(worldGuard);
            }
        }

        //LOAD TO VARIABLES
        LoadFromDB.load();

        // LISTENERS
        Methods.console("&cRegistering listeners!");
        Listeners listeners = new Listeners(pm,this);
        listeners.init();
        Methods.console("&cDone!");

        //COMMANDS
        Methods.console("&cRegistering commands!");
        Commands commands = new Commands(this);
        commands.init();
        Methods.console("&cDone!");

        Methods.console("&cInitializing Metrics!");
        try {
            Metrics metrics = new Metrics(this);
            Metrics.Graph dbtype = metrics.createGraph("Database type");
            dbtype.addPlotter(new Metrics.Plotter("MySQL") {
                @Override
                public int getValue() {
                    if (Main.dbtype) return 1;
                    return 0;
                }
            });
            dbtype.addPlotter(new Metrics.Plotter("SQLite") {
                @Override
                public int getValue() {
                    if (!Main.dbtype) return 1;
                    return 0;
                }
            });
            metrics.start();
            Methods.console("&cDone!");
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }

        Methods.console("&cPlugin init completed!");
        Methods.console("&4[------------------------------------------------------]");

        //UPDATE CHECKER
        if (config.getBoolean("check-for-updates")) {
            new Update().runTaskTimerAsynchronously(this, 0L, 864000L);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (WblocksLocation.size()>0) {
            new BlocksToDB().run();
        }
        if (WvehiclesLocation.size()>0) {
            new VehiclesToDB().run();
        }
        if (WhangingsLocation.size()>0) {
            new HangingsToDB().run();
        }
        if (RblocksLocation.size()>0) {
            new BlocksFromDB().run();
        }
        if (RvehiclesLocation.size()>0) {
            new VehiclesFromDB().run();
        }
        if (RhangingsLocation.size()>0) {
            new HangingsFromDB().run();
        }
        if (UvehiclesLocation1.size()>0) {
            new VehiclesUpdateDB().run();
        }
        if (wsInventory.size()>0 && waInventory.size()>0 && wcInventory.size()>0) {
            new InventoriesToDB("all").run();
        } else if (wsInventory.size()>0) {
            new InventoriesToDB("SURVIVAL").run();
        } else if (wcInventory.size()>0) {
            new InventoriesToDB("CREATIVE").run();
        } else if (waInventory.size()>0) {
            new InventoriesToDB("ADVENTURE").run();
        }
        disable.set("old-db-prefix",dbprefix);
        disable.saveConfig();
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
