package me.kubqoa.creativecontrol.commands;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 04/12/15.
 */
public class Commands {
    private final JavaPlugin plugin;
    public Commands(JavaPlugin plugin) {
        this.plugin=plugin;
    }

    public void init() {
        plugin.getCommand("creativecontrol").setExecutor(new creativecontrolCMD());
        plugin.getCommand("ccpermissions").setExecutor(new ccpermissionsCMD());
        plugin.getCommand("cccommands").setExecutor(new cccommandsCMD());
        plugin.getCommand("ccconvert").setExecutor(new ccconvertCMD());
    }

    public static void endLine(CommandSender sender) {
        if (sender == Main.thisPlugin.getServer().getConsoleSender()) {
            Methods.sendMsg(sender,"&4[------------------------------------------------------]",false);
        } else {
            Methods.sendMsg(sender,"&4[---------------------------------------------------]",false);
        }
    }
}
