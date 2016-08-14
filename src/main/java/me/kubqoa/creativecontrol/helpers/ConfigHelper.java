package me.kubqoa.creativecontrol.helpers;

import me.kubqoa.creativecontrol.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CreativeControl class
 * <p/>
 * by KubqoA © 2015
 */
public class ConfigHelper {
    private final JavaPlugin plugin;
    private final File folder;
    public SimpleConfig config, messages, players, disable;
    public long cooldown;
    public int logwhen;
    public String prefix, dbprefix, protectionType;
    public final List<GameMode> disabledGamemodes = new ArrayList<GameMode>();
    public final List<String> exclude = new ArrayList<String>();
    public final List<String> excludeCMD = new ArrayList<String>();
    public final List<String> addperms = new ArrayList<String>();
    public final List<String> removeperms = new ArrayList<String>();
    public final List<Material> items = new ArrayList<Material>();
    public final List<Material> noTracking = new ArrayList<Material>();
    public final List<String> enabledFeatures = new ArrayList<String>();
    public int blockCache = 0, vehicleCache=0, hangingCache=0;
    public ConfigHelper(JavaPlugin javaPlugin) {folder= Main.folder; plugin=javaPlugin;}

    public void start() {
        boolean exists;
        if (!folder.exists() || !folder.isDirectory()) {
            if(!folder.mkdirs()) {
                Methods.console("&cFailed to create plugin directory!");
            }
        }

        SimpleConfigManager manager = new SimpleConfigManager(plugin);
        exists = new File(folder+"/config.yml").exists();
        config = manager.getNewConfig("config.yml", new String[]{"CreativeControl", "by KubqoA"});
        if (exists) { //Execute check
            checkConfig();
        } else { //Generate new one
            Methods.console("&6config.yml&c not found, creating one!");
            createConfig();
        }
        exists = new File(folder+"/messages.yml").exists();
        messages = manager.getNewConfig("messages.yml", new String[]{"CreativeControl", "by KubqoA"});
        if (exists) { //Execute check
            oldMessages();
            checkMessages();
        } else { //Generate new one
            Methods.console("&6messages.yml&c not found, creating one!");
            createMessages();
        }
        players = manager.getNewConfig("players.yml", new String[]{"CreativeControl", "by KubqoA","DO NOT MODIFY THIS FILE"});
        disable = manager.getNewConfig("disable.yml", new String[]{"CreativeControl", "by KubqoA","DO NOT MODIFY THIS FILE"});

        cooldown = (long) config.getInt("message-cooldown");
        protectionType = config.getString("protection-type");
        prefix = config.getString("message-prefix");
        dbprefix = config.getString("db-prefix");
        logwhen = config.getInt("logging-interval");
        blockCache = config.getInt("block-memory-limit");
        vehicleCache = config.getInt("vehicle-memory-limit");
        hangingCache = config.getInt("hanging-memory-limit");
        for (Object o:config.getList("disabled-gamemodes")) {
            disabledGamemodes.add(GameMode.valueOf(o.toString()));
        }
        for (Object o:config.getList("disabled-worlds")) {
            exclude.add(o.toString());
        }
        for (Object o:config.getList("disabled-commands")) {
            excludeCMD.add(o.toString());
        }
        for (Object o:config.getList("disabled-items")) {
            items.add(Material.getMaterial(o.toString()));
        }
        for (Object o:config.getList("add-permissions")) {
            addperms.add(o.toString());
        }
        for (Object o:config.getList("remove-permissions")) {
            removeperms.add(o.toString());
        }
        for (Object o:config.getList("excluded-blocks")) {
            noTracking.add(Material.valueOf(o.toString()));
        }
        for (Object o:config.getList("enabled-features")) {
            enabledFeatures.add(o.toString());
        }
    }

    private void createConfig() {
        config.set("disabled-commands", Arrays.asList("/command1", "/command2"), new String[]{"Put here all the commands you want to ","disable in creative! If you want to", "enable these commands for specific players", "give them permission cc.cmd./command_here","(e.g. cc.cmd./command1)"});
        config.set("disabled-gamemodes", Arrays.asList("ADVENTURE", "SPECTATOR"), new String[]{"Here you can define which gamemodes you"," want to disable!","You can also create node disabled-gamemodes-worldname","and specify disabled gamemodes for each world."});
        config.set("disabled-items", Arrays.asList("TNT", "BEDROCK"), new String[]{"Here you can put blocks or items which you"," want to disable in creative.","List of blocks you can disable can be", "found on this website", "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html", "Please pay attention to put the values","correctly with uppercase letters."," otherwise this feature won't work", "and may cause errors!"});
        config.set("disabled-worlds", Arrays.asList("world1", "world2"), new String[]{"Here you can define worlds in which should","be the functions of this plugin disabled!"});
        config.set("disable-creative-spawners", true, new String[]{"Set to true if you want to prevent","creative placed spawners from spawning mobs."});
        config.set("remove-permissions", Arrays.asList("permissionnode1", "permission.node.2"), new String[]{"Here you can define which permissions will be", "taken when switched to creative mode. e.g. essentials.tpa"});
        config.set("add-permissions", Arrays.asList("permissionnode1", "permission.node.2"), new String[]{"Here you can define which permissions will be", "added when switched to creative mode. e.g. essentials.tpa"});
        config.set("db-type", "sqlite", new String[]{"Here you can set if you want to use", "sqlite DB or mysql DB"});
        config.set("db-host", "localhost", new String[]{"Set MySQL address here", "(only when db type set to mysql)"});
        config.set("db-port", "3306", new String[]{"Set MySQL server port here", "(only when db type set to mysql)"});
        config.set("db-user", "user", new String[]{"Set the user login for mysql database", "(only when db type set to mysql"});
        config.set("db-password", "password", new String[]{"Set user password", "(only when db type set to mysql)"});
        config.set("db-database", "database", new String[]{"Set MySQL database name", "(only when db type set to mysql)"});
        config.set("db-prefix", "cc_", new String[]{"Set tables prefix here", "(only when db type set to mysql)"});
        config.set("message-prefix", "&4[CC]&c", "Set message prefix here (supports colors)");
        config.set("inventory-switching", true, new String[]{"Set to true to enable inventory switching", "Set to false to disable it"});
        config.set("check-for-updates", true, new String[]{"Set to true to enable automatic update checking", "Set to false to disable it"});
        config.set("message-cooldown", 2L, new String[]{"Here you can define time in seconds for","which will be all messages in cooldown","before sending them again to the player.","Set to 0 to disable."});
        config.set("logging-interval", 10, new String[]{"Here you can define after how many creative", "placed blocks should they be written to","database. If this limit is not reached","they will be logged after 5 minutes."});
        config.set("excluded-blocks", Arrays.asList("DIRT", "SAND"), new String[]{"Place here blocks you want to be","excluded from being tracked when", "placed in creative."});
        config.set("block-memory-limit", 10000, new String[]{"Set how many blocks can be stored","in memory and the others will","be stored in database.","set to 0 to disable this memory","set to -1 to use only memory."});
        config.set("vehicle-memory-limit", 10000, new String[]{"Set how many vehicles can be stored","in memory and the others will","be stored in database.","set to 0 to disable this memory","set to -1 to use only memory."});
        config.set("hanging-memory-limit", 10000, new String[]{"Set how many hangings can be stored","in memory and the others will","be stored in database.","set to 0 to disable this memory","set to -1 to use only memory."});
        config.set("enabled-features", Arrays.asList("BlockBreak", "BlockFall", "BlockPlace", "BlockExplode", "BedrockBreak", "HangingBreak", "HangingPlace", "VehicleCreate", "VehicleDestroy", "VehicleMove", "PlayerArmorStandManipulate", "PlayerCommandRestrict", "PlayerBannedItemFromInventory", "PlayerDamageEntity", "PlayerDeathNoDrop", "PlayerDropItem", "PlayerGamemodeChange", "PlayerJoin", "PlayerOpenRestrictedInventory", "PlayerPickupItem", "PlayerQuit","SpawnerSpawnEntity","CreatureSpawn","PistonExtend","PistonRetract"), new String[]{"Remove or comment out specific", "features to disable them."});
        config.saveConfig();
    }

    private void createMessages() {
        messages.set("container-chest", "You are not allowed to open chest in creative!");
        messages.set("container-chestdouble", "You are not allowed to open chest in creative!");
        messages.set("container-furnace", "You are not allowed to open furnace in creative!");
        messages.set("container-crafting", "You are not allowed to open crafting table in creative!");
        messages.set("container-enderchest", "You are not allowed to open enderchest in creative!");
        messages.set("container-minecart", "You are not allowed to open minecart in creative!");
        messages.set("container-dispenser", "You are not allowed to open dispenser in creative!");
        messages.set("container-dropper", "You are not allowed to open dropper in creative!");
        messages.set("container-hopper", "You are not allowed to open hopper in creative!");
        messages.set("container-horse", "You are not allowed to open horse inventory in creative!");
        messages.set("container-donkey", "You are not allowed to open donkey inventory in creative!");
        messages.set("container-enchant", "You are not allowed to open enchantment table in creative!");
        messages.set("container-repair", "You are not allowed to open anvil in creative!");
        messages.set("container-villager", "You are not allowed to trade with villager in creative!");
        messages.set("container-brewing", "You are not allowed to open brewing stand in creative!");
        messages.set("damage-creeper", "You are not allowed to damage creepers while in creative!");
        messages.set("damage-skeleton", "You are not allowed to damage skeletons while in creative!");
        messages.set("damage-spider", "You are not allowed to damage spiders while in creative!");
        messages.set("damage-zombie", "You are not allowed to damage zombies while in creative!");
        messages.set("damage-slime", "You are not allowed to damage slimes while in creative!");
        messages.set("damage-ghast", "You are not allowed to damage ghasts while in creative!");
        messages.set("damage-zombie-pigman", "You are not allowed to damage zombie pigmans while in creative!");
        messages.set("damage-enderman", "You are not allowed to damage endermans while in creative!");
        messages.set("damage-cave-spider", "You are not allowed to damage cave spiderss while in creative!");
        messages.set("damage-silverfish", "You are not allowed to damage silverfish while in creative!");
        messages.set("damage-blaze", "You are not allowed to damage blazes while in creative!");
        messages.set("damage-magma-cube", "You are not allowed to damage magma cubes while in creative!");
        messages.set("damage-bat", "You are not allowed to damage bats while in creative!");
        messages.set("damage-witch", "You are not allowed to damage witches while in creative!");
        messages.set("damage-endermite", "You are not allowed to damage endermites while in creative!");
        messages.set("damage-guardian", "You are not allowed to damage guardians while in creative!");
        messages.set("damage-pig", "You are not allowed to damage pigs while in creative!");
        messages.set("damage-sheep", "You are not allowed to damage sheep while in creative!");
        messages.set("damage-cow", "You are not allowed to damage cows while in creative!");
        messages.set("damage-chicken", "You are not allowed to damage chickens while in creative!");
        messages.set("damage-squid", "You are not allowed to damage squids while in creative!");
        messages.set("damage-wolf", "You are not allowed to damage wolfs while in creative!");
        messages.set("damage-mooshroom", "You are not allowed to damage mooshrooms while in creative!");
        messages.set("damage-ocelot", "You are not allowed to damage ocelots while in creative!");
        messages.set("damage-donkey", "You are not allowed to damage donkeys while in creative!");
        messages.set("damage-horse", "You are not allowed to damage horses while in creative!");
        messages.set("damage-mule", "You are not allowed to damage mules while in creative!");
        messages.set("damage-rabbit", "You are not allowed to damage rabbits while in creative!");
        messages.set("damage-villager", "You are not allowed to damage villagers while in creative!");
        messages.set("damage-giant", "You are not allowed to damage giants while in creative!");
        messages.set("damage-iron-golem", "You are not allowed to damage iron golems while in creative!");
        messages.set("damage-ender-dragon", "You are not allowed to damage ender dragons while in creative!");
        messages.set("damage-snow-golem", "You are not allowed to damage snow golems while in creative!");
        messages.set("damage-wither", "You are not allowed to damage withers while in creative!");
        messages.set("damage-ender-crystal", "You are not allowed to destroy ender crystals while in creative!");
        messages.set("damage-player", "You are not allowed to damage players while in creative!");
        messages.set("damage-shulker", "You are not allowed to damage shulkers while in creative!");
        messages.set("disabled-gamemode", "You can't use this gamemode because it is disabled!");
        messages.set("block-break-bedrock", "You are not allowed to break bedrock while in creative!");
        messages.set("hanging-break", "This item frame was placed in creative, meaning you can't get the drops from it!");
        messages.set("block-break", "This block was placed in creative, meaning you can't get the drops from it!");
        messages.set("vehicle-break", "This minecart was place in creative, meaning you can't get the drops from it!");
        messages.set("pickup", "You can't pick up items while in creative");
        messages.set("ender_portal", "You can't activate ender portal while in creative!");
        messages.set("eye_of_ender", "You are not allowed to use eye of ender while in creative!");
        messages.set("snowball", "You are not allowed to use snowballs while in creative!");
        messages.set("monster_egg", "You can't spawn monsters in creative!");
        messages.set("ignite", "You can't ignite while in creative!");
        messages.set("exp_bottle", "You can't use xp bottles while in creative!");
        messages.set("chicken_egg", "You can't use chicken eggs while in creative!");
        messages.set("potion", "You can't use potions while in creative!");
        messages.set("jukebox", "You can't use jukeboxes while in creative!");
        messages.set("beacon", "You can't manipulate beacons while in creative!");
        messages.set("item_frame", "You can't put items to itemframes while in creative!");
        messages.set("armor_stand", "You can't edit armor stands in creative!");
        messages.set("saddle", "You can't put saddle on pigs in creative mode!");
        messages.set("command", "You are not allowed to execute command %cmd% while in creative!");
        messages.set("disabled-item", "You can't use this item while in creative!");
        messages.set("disabled-block", "You can't place this block while in creative!");
        messages.set("plant", "You can't plant while in creative!");
        messages.set("destroy-farmland", "You can't destroy farmland while in creative!");
        messages.set("breed", "You can't breed animals in creative!");
        messages.set("fish", "You are not allowed to fish while in creative!");
        messages.set("drop", "You can't drop items while in creative!");
        messages.set("mob-create", "You can't create mobs while in creative!");
        messages.set("shooting", "You can't shoot from bow while in creative!");
        messages.saveConfig();
    }

    private void checkConfig() {
        if (config.getList("disabled-commands") == null) {
            config.set("disabled-commands", Arrays.asList("/command1", "/command2"), new String[]{"Put here all the commands you want to ","disable in creative! If you want to", "enable these commands for specific players", "give them permission cc.cmd./command_here","(e.g. cc.cmd./command1)"});
        }
        if (config.get("disabled-gamemodes") == null) {
            config.set("disabled-gamemodes", Arrays.asList("ADVENTURE", "SPECTATOR"), new String[]{"Here you can define which gamemodes you"," want to disable!","You can also create node disabled-gamemodes-worldname","and specify disabled gamemodes for each world."});
        }
        if (config.getList("disabled-items") == null) {
            config.set("disabled-items", Arrays.asList("TNT", "BEDROCK"), new String[]{"Here you can put blocks or items which you"," want to disable in creative.","List of blocks you can disable can be", "found on this website", "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html", "Please pay attention to put the values","correctly with uppercase letters."," otherwise this feature won't work", "and may cause errors!"});
        }
        if (config.getList("disabled-worlds") == null) {
            config.set("disabled-worlds", Arrays.asList("world1", "world2"), new String[]{"Here you can define worlds in which should","be the functions of this plugin disabled!"});
        }
        if (config.get("disable-creative-spawners")==null) {
            config.set("disable-creative-spawners", true, new String[]{"Set to true if you want to prevent","creative placed spawners from spawning mobs."});
        }
        if (config.getList("remove-permissions") == null) {
            config.set("remove-permissions", Arrays.asList("permissionnode1", "permission.node.2"), new String[]{"Here you can define which permissions will be", "taken when switched to creative mode. e.g. essentials.tpa"});
        }
        if (config.getList("add-permissions") == null) {
            config.set("add-permissions", Arrays.asList("permissionnode1", "permission.node.2"), new String[]{"Here you can define which permissions will be", "added when switched to creative mode. e.g. essentials.tpa"});
        }
        if (config.get("db-type") == null) {
            config.set("db-type", "sqlite", new String[]{"Here you can set if you want to use", "sqlite DB or mysql DB"});
        }
        if (config.get("db-host") == null) {
            config.set("db-host", "localhost", new String[]{"Set MySQL address here", "(only when db type set to mysql)"});
        }
        if (config.get("db-port") == null) {
            config.set("db-port", "3306", new String[]{"Set MySQL server port here", "(only when db type set to mysql)"});
        }
        if (config.get("db-user") == null) {
            config.set("db-user", "user", new String[]{"Set the user login for mysql database", "(only when db type set to mysql"});
        }
        if (config.get("db-password") == null) {
            config.set("db-password", "password", new String[]{"Set user password", "(only when db type set to mysql)"});
        }
        if (config.get("db-database") == null) {
            config.set("db-database", "database", new String[]{"Set MySQL database name", "(only when db type set to mysql)"});
        }
        if (config.get("db-prefix") == null) {
            config.set("db-prefix", "cc_", new String[]{"Set tables prefix here", "(only when db type set to mysql)"});
        }
        if (config.get("message-prefix") == null) {
            config.set("message-prefix", "&4[CC]&c", "Set message prefix here (supports colors)");
        }
        if (config.get("inventory-switching") == null) {
            config.set("inventory-switching", true, new String[]{"Set to true if you want to enable inventory switching", "Set to false to disable it"});
        }
        if (config.get("check-for-updates") == null) {
            config.set("check-for-updates", true, new String[]{"Set to true to enable automatic update checking", "Set to false to disable it"});
        }
        if (config.get("logging-interval") == null) {
            config.set("logging-interval", 10, new String[]{"Here you can define after how many creative", "placed blocks should they be written to database.","If this limit is not reached they","will be logged after 5 minutes."});
        }
        if (config.get("message-cooldown") == null) {
            config.set("message-cooldown", 2L, new String[]{"Here you can define time in seconds for","which will be all messages in cooldown","before sending them again to the player."});
        }
        if (config.get("excluded-blocks") == null) {
            config.set("excluded-blocks", Arrays.asList("DIRT", "SAND"), new String[]{"Place here blocks you want to be", "excluded from being tracked when", "placed in creative."});
        }
        if (config.get("block-memory-limit") == null) {
            config.set("block-memory-limit", 10000, new String[]{"Set how many blocks can be stored","in memory and the others will","be stored in database.","set to 0 to disable this memory","set to -1 to use only memory."});
        }
        if (config.get("vehicle-memory-limit") == null) {
            config.set("vehicle-memory-limit", 10000, new String[]{"Set how many vehicles can be stored","in memory and the others will","be stored in database.","set to 0 to disable this memory","set to -1 to use only memory."});
        }
        if (config.get("hanging-memory-limit") == null) {
            config.set("hanging-memory-limit", 10000, new String[]{"Set how many hangings can be stored","in memory and the others will","be stored in database.","set to 0 to disable this memory","set to -1 to use only memory."});
        }
        if (config.get("enabled-features") == null) {
            config.set("enabled-features", Arrays.asList("BlockBreak", "BlockFall", "BlockPlace", "BlockExplode", "BedrockBreak", "HangingBreak", "HangingPlace", "VehicleCreate", "VehicleDestroy", "VehicleMove", "PlayerArmorStandManipulate", "PlayerCommandRestrict", "PlayerBannedItemFromInventory", "PlayerDamageEntity", "PlayerDeathNoDrop", "PlayerDropItem", "PlayerGamemodeChange", "PlayerJoin", "PlayerOpenRestrictedInventory", "PlayerPickupItem", "PlayerQuit","SpawnerSpawnEntity","CreatureSpawn","PistonExtend","PistonRetract"), new String[]{"Remove or comment out specific", "features to disable them."});        }
        if (config.get("track-worldedit") != null) {
            config.removeKey("track-worldedit");
        }
        config.saveConfig();
    }

    private void checkMessages() {
        if (messages.get("container-chest") == null) {
            messages.set("container-chest", "You are not allowed to open chest in creative!");
        }
        if (messages.get("container-chestdouble") == null) {
            messages.set("container-chestdouble", "You are not allowed to open chest in creative!");
        }
        if (messages.get("container-furnace") == null) {
            messages.set("container-furnace", "You are not allowed to open furnace in creative!");
        }
        if (messages.get("container-crafting") == null) {
            messages.set("container-crafting", "You are not allowed to open crafting table in creative!");
        }
        if (messages.get("container-enderchest") == null) {
            messages.set("container-enderchest", "You are not allowed to open enderchest in creative!");
        }
        if (messages.get("container-minecart") == null) {
            messages.set("container-minecart", "You are not allowed to open minecart in creative!");
        }
        if (messages.get("container-dispenser") == null) {
            messages.set("container-dispenser", "You are not allowed to open dispenser in creative!");
        }
        if (messages.get("container-dropper") == null) {
            messages.set("container-dropper", "You are not allowed to open dropper in creative!");
        }
        if (messages.get("container-hopper") == null) {
            messages.set("container-hopper", "You are not allowed to open hopper in creative!");
        }
        if (messages.get("container-horse") == null) {
            messages.set("container-horse", "You are not allowed to open horse inventory in creative!");
        }
        if (messages.get("container-donkey") == null) {
            messages.set("container-donkey", "You are not allowed to open donkey inventory in creative!");
        }
        if (messages.get("container-enchant") == null) {
            messages.set("container-enchant", "You are not allowed to open enchantment table in creative!");
        }
        if (messages.get("container-repair") == null) {
            messages.set("container-repair", "You are not allowed to open repair in creative!");
        }
        if (messages.get("container-villager") == null) {
            messages.set("container-villager", "You are not allowed to trade with villager in creative!");
        }
        if (messages.get("container-brewing") == null) {
            messages.set("container-brewing", "You are not allowed to open brewing stand in creative!");
        }
        if (messages.get("damage-creeper") == null) {
            messages.set("damage-creeper", "You are not allowed to damage creepers while in creative!");
        }
        if (messages.get("damage-skeleton") == null) {
            messages.set("damage-skeleton", "You are not allowed to damage skeletons while in creative!");
        }
        if (messages.get("damage-spider") == null) {
            messages.set("damage-spider", "You are not allowed to damage spiders while in creative!");
        }
        if (messages.get("damage-zombie") == null) {
            messages.set("damage-zombie", "You are not allowed to damage zombies while in creative!");
        }
        if (messages.get("damage-slime") == null) {
            messages.set("damage-slime", "You are not allowed to damage slimes while in creative!");
        }
        if (messages.get("damage-ghast") == null) {
            messages.set("damage-ghast", "You are not allowed to damage ghasts while in creative!");
        }
        if (messages.get("damage-zombie-pigman") == null) {
            messages.set("damage-zombie-pigman", "You are not allowed to damage zombie pigmans while in creative!");
        }
        if (messages.get("damage-enderman") == null) {
            messages.set("damage-enderman", "You are not allowed to damage endermans while in creative!");
        }
        if (messages.get("damage-cave-spider") == null) {
            messages.set("damage-cave-spider", "You are not allowed to damage cave spiderss while in creative!");
        }
        if (messages.get("damage-silverfish") == null) {
            messages.set("damage-silverfish", "You are not allowed to damage silverfish while in creative!");
        }
        if (messages.get("damage-blaze") == null) {
            messages.set("damage-blaze", "You are not allowed to damage blazes while in creative!");
        }
        if (messages.get("damage-magma-cube") == null) {
            messages.set("damage-magma-cube", "You are not allowed to damage magma cubes while in creative!");
        }
        if (messages.get("damage-bat") == null) {
            messages.set("damage-bat", "You are not allowed to damage bats while in creative!");
        }
        if (messages.get("damage-witch") == null) {
            messages.set("damage-witch", "You are not allowed to damage witches while in creative!");
        }
        if (messages.get("damage-endermite") == null) {
            messages.set("damage-endermite", "You are not allowed to damage endermites while in creative!");
        }
        if (messages.get("damage-guardian") == null) {
            messages.set("damage-guardian", "You are not allowed to damage guardians while in creative!");
        }
        if (messages.get("damage-pig") == null) {
            messages.set("damage-pig", "You are not allowed to damage pigs while in creative!");
        }
        if (messages.get("damage-sheep") == null) {
            messages.set("damage-sheep", "You are not allowed to damage sheep while in creative!");
        }
        if (messages.get("damage-cow") == null) {
            messages.set("damage-cow", "You are not allowed to damage cows while in creative!");
        }
        if (messages.get("damage-chicken") == null) {
            messages.set("damage-chicken", "You are not allowed to damage chickens while in creative!");
        }
        if (messages.get("damage-squid") == null) {
            messages.set("damage-squid", "You are not allowed to damage squids while in creative!");
        }
        if (messages.get("damage-wolf") == null) {
            messages.set("damage-wolf", "You are not allowed to damage wolfs while in creative!");
        }
        if (messages.get("damage-mooshroom") == null) {
            messages.set("damage-mooshroom", "You are not allowed to damage mooshrooms while in creative!");
        }
        if (messages.get("damage-ocelot") == null) {
            messages.set("damage-ocelot", "You are not allowed to damage ocelots while in creative!");
        }
        if (messages.get("damage-donkey") == null) {
            messages.set("damage-donkey", "You are not allowed to damage donkeys while in creative!");
        }
        if (messages.get("damage-horse") == null) {
            messages.set("damage-horse", "You are not allowed to damage horses while in creative!");
        }
        if (messages.get("damage-mule") == null) {
            messages.set("damage-mule", "You are not allowed to damage mules while in creative!");
        }
        if (messages.get("damage-rabbit") == null) {
            messages.set("damage-rabbit", "You are not allowed to damage rabbits while in creative!");
        }
        if (messages.get("damage-villager") == null) {
            messages.set("damage-villager", "You are not allowed to damage villagers while in creative!");
        }
        if (messages.get("damage-giant") == null) {
            messages.set("damage-giant", "You are not allowed to damage giants while in creative!");
        }
        if (messages.get("damage-iron-golem") == null) {
            messages.set("damage-iron-golem", "You are not allowed to damage iron golems while in creative!");
        }
        if (messages.get("damage-ender-dragon") == null) {
            messages.set("damage-ender-dragon", "You are not allowed to damage ender dragons while in creative!");
        }
        if (messages.get("damage-snow-golem") == null) {
            messages.set("damage-snow-golem", "You are not allowed to damage snow golems while in creative!");
        }
        if (messages.get("damage-wither") == null) {
            messages.set("damage-wither", "You are not allowed to damage withers while in creative!");
        }
        if (messages.get("damage-ender-crystal") == null) {
            messages.set("damage-ender-crystal", "You are not allowed to destroy ender crystals while in creative!");
        }
        if (messages.get("damage-player") == null) {
            messages.set("damage-player", "You are not allowed to damage players while in creative!");
        }
        if (messages.get("damage-shulker") == null) {
            messages.set("damage-shulker", "You are not allowed to damage shulkers while in creative!");
        }
        if (messages.get("disabled-gamemode") == null) {
            messages.set("disabled-gamemode", "You can't use this gamemode because it is disabled!");
        }
        if (messages.get("block-break-bedrock") == null) {
            messages.set("block-break-bedrock", "You are not allowed to break bedrock while in creative!");
        }
        if (messages.get("hanging-break") == null) {
            messages.set("hanging-break", "This item frame was placed in creative, meaning you can't get the drops from it!");
        }
        if (messages.get("block-break") == null) {
            messages.set("block-break", "This block was placed in creative, meaning you can't get the drops from it!");
        }
        if (messages.get("vehicle-break") == null) {
            messages.set("vehicle-break", "This minecart was place in creative, meaning you can't get the drops from it!");
        }
        if (messages.get("drop") == null) {
            messages.set("drop", "You can't drop items in creative mode!");
        }
        if (messages.get("pickup") == null) {
            messages.set("pickup", "You can't pick up items while in creative");
        }
        if (messages.get("ender_portal") == null) {
            messages.set("ender_portal", "You can't activate ender portal while in creative!");
        }
        if (messages.get("eye_of_ender") == null) {
            messages.set("eye_of_ender", "You are not allowed to use eye of ender while in creative!");
        }
        if (messages.get("snowball") == null) {
            messages.set("snowball", "You are not allowed to use snowballs while in creative!");
        }
        if (messages.get("monster_egg") == null) {
            messages.set("monster_egg", "You can't spawn monsters in creative!");
        }
        if (messages.get("ignite") == null) {
            messages.set("ignite", "You can't ignite while in creative!");
        }
        if (messages.get("exp_bottle") == null) {
            messages.set("exp_bottle", "You can't use xp bottles while in creative!");
        }
        if (messages.get("chicken_egg") == null) {
            messages.set("chicken_egg", "You can't use chicken eggs while in creative!");
        }
        if (messages.get("potion") == null) {
            messages.set("potion", "You can't use potions while in creative!");
        }
        if (messages.get("jukebox") == null) {
            messages.set("jukebox", "You can't use jukeboxes while in creative!");
        }
        if (messages.get("beacon") == null) {
            messages.set("beacon", "You can't manipulate beacons while in creative!");
        }
        if (messages.get("item_frame") == null) {
            messages.set("item_frame", "You can't put items to itemframes while in creative!");
        }
        if (messages.get("armor_stand") == null) {
            messages.set("armor_stand", "You can't edit armor stands in creative!");
        }
        if (messages.get("saddle") == null) {
            messages.set("saddle", "You can't put saddle on pigs in creative mode!");
        }
        if (messages.get("command") == null) {
            messages.set("command", "You are not allowed to execute command %cmd% while in creative!");
        }
        if (messages.get("disabled_item") == null) {
            messages.set("disabled_item", "You can't use this item while in creative!");
        }
        if (messages.get("plant") == null) {
            messages.set("plant", "You can't plant while in creative!");
        }
        if (messages.get("destroy_farmland") == null) {
            messages.set("destroy_farmland", "You can't destroy farmland while in creative!");
        }
        if (messages.get("breed") == null) {
            messages.set("breed", "You can't breed animals in creative!");
        }
        if (messages.get("fish") == null) {
            messages.set("fish", "You are not allowed to fish while in creative!");
        }
        if (messages.get("mob-create") == null) {
            messages.set("mob-create", "You can't create mobs while in creative!");
        }
        if (messages.get("shooting") == null) {
            messages.set("shooting", "You can't shoot from bow while in creative!");
        }
        messages.saveConfig();
    }

    private void oldMessages() {
        if (messages.get("blockbreak")!=null) {
            messages.set("block-break",messages.get("blockbreak"));
            messages.removeKey("blockbreak");
        }
        if (messages.get("minecart")!=null) {
            messages.set("vehicle-break",messages.get("minecart"));
            messages.removeKey("minecart");
        }
        if (messages.get("itemframedestroy")!=null) {
            messages.set("hanging-break",messages.get("itemframedestroy"));
            messages.removeKey("itemframedestroy");
        }
        if (messages.get("bedrock")!=null) {
            messages.set("block-break-bedrock",messages.get("bedrock"));
            messages.removeKey("bedrock");
        }
        if (messages.get("monsteregg")!=null) {
            messages.set("monster_egg",messages.get("monsteregg"));
            messages.removeKey("monsteregg");
        }
        if (messages.get("expbottle")!=null) {
            messages.set("exp_bottle",messages.get("expbottle"));
            messages.removeKey("expbottle");
        }
        if (messages.get("chickenegg")!=null) {
            messages.set("chicken_egg",messages.get("chickenegg"));
            messages.removeKey("chickenegg");
        }
        if (messages.get("itemframe")!=null) {
            messages.set("item_frame",messages.get("itemframe"));
            messages.removeKey("itemframe");
        }
        if (messages.get("armorstand")!=null) {
            messages.set("armor_stand",messages.get("armorstand"));
            messages.removeKey("armorstand");
        }
        if (messages.get("disableditem")!=null) {
            messages.set("disabled_item",messages.get("disableditem"));
            messages.removeKey("disableditem");
        }
        if (messages.get("destroyfarmland")!=null) {
            messages.set("destroy_farmland",messages.get("destroyfarmland"));
            messages.removeKey("destroyfarmland");
        }
        if (messages.get("fillenderportal")!=null) {
            messages.set("ender_portal",messages.get("fillenderportal"));
            messages.removeKey("fillenderportal");
        }
        if (messages.get("eyeofender")!=null) {
            messages.set("eye_of_ender",messages.get("eyeofender"));
            messages.removeKey("eyeofender");
        }
        if (messages.get("enderpearl")!=null) {
            messages.set("ender_pearl",messages.get("enderpearl"));
            messages.removeKey("enderpearl");
        }
        if (messages.get("cantplace")!=null) {
            messages.set("disabled-block",messages.get("cantplace"));
            messages.removeKey("cantplace");
        }
        messages.saveConfig();
    }
}
