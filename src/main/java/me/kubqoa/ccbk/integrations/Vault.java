package me.kubqoa.ccbk.integrations;

import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.CreativeControlManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class Vault {
    private static Permission permission;

    public Vault () {}

    public void setup() {
        RegisteredServiceProvider<Permission> permissionProvider = CreativeControl.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        CreativeControlManager.log("OK!");
    }

    public static boolean hasPermission(Player player, String permissionnode) {
        return permission.playerHas(null,player,permissionnode);
    }
}
