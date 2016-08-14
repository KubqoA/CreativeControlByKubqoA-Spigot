package me.kubqoa.creativecontrol.listeners.player;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.PlayerAPI;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class PlayerQuit implements Listener {
    @EventHandler
    public void leave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (perm(player,"*") || perm(player,"bypass.*") || perm(player,"bypass.gamemode.*")) return;
        GameMode gameMode = Main.thisPlugin.getServer().getDefaultGameMode();
        if (player.getGameMode()!=gameMode) new PlayerAPI(player).logInv();
        Main.players.set(player.getUniqueId().toString()+"-gamemode",player.getGameMode().name());
        Main.players.saveConfig();
    }
}
