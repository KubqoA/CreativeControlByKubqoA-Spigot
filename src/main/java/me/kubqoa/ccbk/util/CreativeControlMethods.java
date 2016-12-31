package me.kubqoa.ccbk.util;

import me.kubqoa.ccbk.CreativeControl;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Enumeration;

import static me.kubqoa.ccbk.CreativeControl.*;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class CreativeControlMethods {
    public static void sendMsg(String message, Player player) {
        sendMsg(message,player, withPrefix);
    }

    public static void sendMsg(String message, CommandSender sender) {
        sendMsg(message,sender, withPrefix);
    }

    public static void sendMsg(String message, CommandSender sender, boolean withPrefix) {
        if (withPrefix) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',messagePrefix + message));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
        }
    }

    public static void sendConfMsg(String config, CommandSender sender) {
        sendCongMsg(config,sender,withPrefix);
    }

    public static void sendCongMsg(String config, CommandSender sender, boolean withPrefix) {
        sendMsg(messages.getString(config),sender,withPrefix);
    }

    public static void log(String message) { log(message, withPrefix); }

    public static void log(String message, boolean withPrefix) {
        if (withPrefix) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',messagePrefix + message));
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',message));
        }
    }

    public static void printOutCreativeBlocks() {
        Enumeration<String> playerEnumeration = CreativeControl.playerCreativeLocations.keys();
        while (playerEnumeration.hasMoreElements()) {
            String uuid = playerEnumeration.nextElement();
            log(uuid+"-CREATIVE: "+CreativeControl.playerCreativeLocations.get(uuid).toString());
        }
        playerEnumeration = CreativeControl.playerCreativeLocations.keys();
        while (playerEnumeration.hasMoreElements()) {
            String uuid = playerEnumeration.nextElement();
            log(uuid+"-CHECKED: "+CreativeControl.playerCreativeLocations.get(uuid).toString());
        }
    }
}
