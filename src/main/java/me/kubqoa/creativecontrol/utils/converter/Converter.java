package me.kubqoa.creativecontrol.utils.converter;

import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.command.CommandSender;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 07/02/16.
 */
public class Converter {
    private final String from;
    private final String to;
    private final CommandSender sender;

    public Converter(String from, String to, CommandSender sender) {
        this.from = from;
        this.to = to;
        this.sender = sender;
    }

    public void start() {
        if (from.equalsIgnoreCase("oldcc")) {
            if (to.equalsIgnoreCase("current")) {
                Methods.sendMsg(sender, "&cNow not supported. Sorry.");
            } else {
                Methods.sendMsg(sender, "&6oldcc &ccan only be used in combination with &6current&c.");
            }
            return;
        }
        InsideConvert insideConvert = new InsideConvert(sender);
        if (from.equalsIgnoreCase("mysql")) {
            if (to.equalsIgnoreCase("sqlite")) {
                insideConvert.sqlite();
            } else {
                Methods.sendMsg(sender, "&6mysql &ccan only be used in combination with &6sqlite&c.");
            }
        } else if (from.equalsIgnoreCase("sqlite")) {
            if (to.equalsIgnoreCase("mysql")) {
                insideConvert.mysql();
            } else {
                Methods.sendMsg(sender, "&6sqlite &ccan only be used in combination with &6mysql&c.");
            }
        } else {
            Methods.sendMsg(sender, "&cIncorrect attribute &6from&c.");
            Methods.sendMsg(sender, "&c<from> &6- From can be either &coldcc&6, &csqlite&6 or &cmysql");
        }
    }
}
