package me.kubqoa.creativecontrol.listeners.player;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.PlayerAPI;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class PlayerJoin implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (perm(player,"*") || perm(player,"bypass.*") || perm(player,"bypass.gamemode.*")) return;
        String gamemode = Main.players.getString(player.getUniqueId().toString() + "-gamemode");
        if (gamemode==null) return;
        GameMode gm = player.getGameMode();
        GameMode old = GameMode.valueOf(gamemode);
        if (gm!=old) {
            PlayerAPI playerAPI = new PlayerAPI(player);
            if (Main.vault!=null && !perm(player,"bypass.gamemode.permissions")) {
                playerAPI.perms(gm);
            }
            if (!perm(player,"bypass.gamemode.inventory")) {
                if (Main.serverVersion.equals("1.7")) {
                    playerAPI.setInv(gm);
                } else if (old != GameMode.SPECTATOR) {
                    playerAPI.setInv(gm);
                }
            }
        }
    }
}
