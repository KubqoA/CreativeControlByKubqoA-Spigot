package me.kubqoa.creativecontrol.listeners.player;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.PlayerAPI;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import static me.kubqoa.creativecontrol.helpers.Methods.perm;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 28/04/16.
 */
public class PlayerGamemodeChange implements Listener {
    @EventHandler
    public void gamemodeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if(perm(player,"bypass.gamemode.*") || perm(player,"bypass.*") || perm(player,"*")) { return; }
        if (Main.disabledGamemodes.contains(event.getNewGameMode()) && !Methods.perm(player,"bypass.gamemode."+event.getNewGameMode().name())) {
            event.setCancelled(true);
        } else {
            new PlayerAPI(player).changeGM(event.getNewGameMode());
        }
    }
}
