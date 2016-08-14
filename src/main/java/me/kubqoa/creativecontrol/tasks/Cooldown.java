package me.kubqoa.creativecontrol.tasks;

import me.kubqoa.creativecontrol.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * CreativeControl class
 * <p/>
 * by KubqoA © 2015
 */
public class Cooldown extends BukkitRunnable {
    private final Player player;
    private final String msg;
    public Cooldown (Player p, String message) {player=p;msg=message;}

    @Override
    public void run() {
        int i=0;
        for (String string : Main.cooldownsS) {
            if (string.equalsIgnoreCase(msg)) {
                if (Main.cooldownsP.get(i)==player) {
                    break;
                }
            }
            i++;
        }
        Main.cooldownsS.remove(i);
        Main.cooldownsP.remove(i);
    }
}
