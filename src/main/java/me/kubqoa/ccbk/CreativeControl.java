package me.kubqoa.ccbk;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.zaxxer.hikari.HikariDataSource;
import me.kubqoa.ccbk.managers.SimpleConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main class of CreativeControl plugin,
 * contains all the variables used throughout
 * the run of the plugin.
 */
public class CreativeControl extends JavaPlugin {
    /**
     * Version of the server
     */
    public static String serverVersion;

    /**
     * This class instance used to access JavaPlugin methods outside this class
     */
    private static CreativeControl instance;

    /**
     * Reference to the plugin description file of this plugin
     */
    public static PluginDescriptionFile pdf;

    /**
     * Reference to the plugin folder
     */
    public static File folder;

    /**
     * Reference to the main managers file
     */
    public static SimpleConfig config;

    /**
     * Reference to the messages
     */
    public static SimpleConfig messages;

    /**
     * The database connection pool.
     */
    public static HikariDataSource dataSource;

    /**
     * Configuration option
     * Wheter to send messages with messagePrefix
     */
    public static boolean withPrefix;

    /**
     * Configuration option
     * Message prefix
     */
    public static String messagePrefix;

    /**
     * Configuration option
     * Database's table's prefix
     */
    public static String databasePrefix;

    /**
     * Configuration option
     * Specifies the database type. (MySQL/MariaDB/SQLite)
     */
    public static String databaseType;

    /**
     * Configuration option
     * List of world where the functions of this plugin are disabled
     */
    public static List<World> excludedWorlds = new ArrayList<World>();
    //materials which placement won't be tracked
    public static List<Material> noTracking = new ArrayList<Material>();
    //materials which are can't be placed or even obtained
    public static List<Material> disabledMaterials = new ArrayList<Material>();
    //vault hook
    public static boolean vault=false;

    public static List<Location> creative = new ArrayList<Location>();
    public static ConcurrentHashMap<String, Location> playerCreativeLocations = new ConcurrentHashMap<String, Location>();
    public static List<Location> checked = new ArrayList<Location>();
    public static ConcurrentHashMap<String, Location> playerCheckedLocations = new ConcurrentHashMap<String, Location>();

    public static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        CreativeControlManager manager = new CreativeControlManager();
        manager.CreativeControlEnable();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        CreativeControlManager manager = new CreativeControlManager();
        manager.CreativeControlDisable();

        super.onDisable();
    }


    public static CreativeControl getInstance() {
        return instance;
    }
}
