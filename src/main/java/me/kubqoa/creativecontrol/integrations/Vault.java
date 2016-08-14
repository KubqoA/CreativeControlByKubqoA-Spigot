package me.kubqoa.creativecontrol.integrations;

import me.kubqoa.creativecontrol.Main;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {
    private static Permission permission;

    public Vault() {
    }

    public void setup() {
        RegisteredServiceProvider<Permission> rsp = Main.thisPlugin.getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
    }

    public static boolean hasPermission(Player player, String permissionnode) {
        return permission.playerHas(null, player, permissionnode);
    }

    public static void addPermission(Player player, String permissionnode) {
        permission.playerAdd(null, player, permissionnode);
    }

    public static void removePermission(Player player, String permissionnode) {
        permission.playerRemove(null, player, permissionnode);
    }

    public Permission getPermission() {
        return permission;
    }
}
