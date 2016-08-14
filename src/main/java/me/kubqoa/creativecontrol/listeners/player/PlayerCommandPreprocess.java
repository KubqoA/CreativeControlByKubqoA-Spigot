package me.kubqoa.creativecontrol.listeners.player;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.exclude;
import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class PlayerCommandPreprocess implements Listener {
    @EventHandler
    public void command(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage().split(" ")[0];
        if (player.getGameMode() != GameMode.CREATIVE || exclude(player.getLocation()) || perm(player, "cmd." + cmd) || perm(player, "cmd.*") || perm(player, "*"))
            return;
        if (Main.excludeCMD.contains(cmd)) {
            event.setCancelled(true);
            Methods.sendMsg(player, ChatColor.translateAlternateColorCodes('&', Main.messages.getString("command").replaceAll("%cmd%", cmd)));
        }
    }
}
