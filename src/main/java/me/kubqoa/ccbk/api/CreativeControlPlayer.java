package me.kubqoa.ccbk.api;

import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.integrations.Vault;
import me.kubqoa.ccbk.util.CreativeControlMethods_1_9_above;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * CreativeControl class
 *
 * <p>
 * Custom player class for easier working with methods<br>
 * across this plugin
 *
 * @author KubqoA
 */
public class CreativeControlPlayer {
    private final Player player;

    /**
     * CreativeControlPlayer constructor
     *
     * @param player    Player which we will later use in the functions
     */
    public CreativeControlPlayer(Player player) {
        this.player=player;
    }

    /**
     * Public function to send message to player
     *
     * @param message   Message we want to send
     */
    public void sendMsg(String message) {
        sendMsg(message,CreativeControl.withPrefix);
    }

    /**
     * Public function to send message to player
     *
     * @param message   Message we want to send
     * @param prefix    Boolean specifying if message should be send with prefix
     */
    public void sendMsg(String message,boolean prefix) {
        if (prefix) player.sendMessage(ChatColor.translateAlternateColorCodes('&', CreativeControl.messagePrefix + message));
        else player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Public function to send message to player from messages.yml
     *
     * @param config    Path to message we want to send in messages.yml
     */
    public void sendConfMsg(String config) {
        sendConfMsg(config,CreativeControl.withPrefix);
    }

    /**
     * Public function to send message to player from messages.yml
     *
     * @param config    Path to message we want to send in messages.yml
     * @param prefix    Boolean specifying if message should be send with prefix
     */
    public void sendConfMsg(String config, boolean prefix) {
        sendMsg(CreativeControl.messages.getString(config),prefix);
    }

    /**
     * Public function used to obtain player's gamemode
     *
     * @return  Player's gamemode
     */
    public GameMode getGameMode() {
        return player.getGameMode();
    }

    /**
     * Public function used to check if player is in creative gamemode
     *
     * @return  boolean based on whether player is in creative gamemode
     */
    public boolean isInCreative() {
        return getGameMode().equals(GameMode.CREATIVE);
    }

    /**
     * Public function used to check if player is in survival gamemode
     *
     * @return  boolean based on whether player is in survival gamemode
     */
    public boolean isInSurvival() {
        return getGameMode().equals(GameMode.SURVIVAL);
    }

    /**
     * Public function used to obtain player's inventory
     *
     * @return  Player's inventory
     */
    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    /**
     * Public function used to obtain player's inventory's contents
     *
     * @return  Player's inventory's contents
     */
    public ItemStack[] getInventoryContents() {
        return getInventory().getContents();
    }

    /**
     * Public function used to obtain player's item in hand
     *
     * @return Player's item in hand
     */
    public ItemStack getItemInHand() {
        if (CreativeControl.serverVersion.equalsIgnoreCase("1.9") || CreativeControl.serverVersion.equalsIgnoreCase("1.10")) {
            return CreativeControlMethods_1_9_above.getItemInHand(getInventory());
        } else {
            return getInventory().getItemInHand();
        }
    }

    /**
     * Public function used to check whether player has the given permission
     *
     * @param permission    Permission we are checking fot
     * @return              Boolean based on whether player has the given permission
     */
    public boolean hasPermission(String permission) {
        return hasPermissionNode(permission) || isOP();
    }

    private boolean hasPermissionNode(String permission) {
        permission = "cc."+permission;
        if (CreativeControl.vault) {
            return Vault.hasPermission(player,permission);
        } else {
            return player.hasPermission(permission);
        }
    }

    /**
     * Public function used to check whether player is OP
     *
     * @return  Boolean based on whether player is OP
     */
    public boolean isOP() {
        return player.isOp();
    }


    /**
     * Public function used to obtain player's location
     *
     * @return  Player's location
     */
    public Location getLocation() {
        return getLocation();
    }

    public String getUUID() { return player.getUniqueId().toString(); }

    /**
     * Player getter
     *
     * @return Returns player
     */
    public Player getPlayer() {
        return player;
    }
}
