package me.kubqoa.creativecontrol.listeners.block;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.BlockAPI;
import me.kubqoa.creativecontrol.helpers.Methods;
import me.kubqoa.creativecontrol.utils.lists.list_universal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.List;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 26/04/16.
 */
public class BlockFall implements Listener {
    private List<Material> willbreak;

    public BlockFall() {
        if (Main.serverVersion.equals("1.9")) {
            willbreak = me.kubqoa.creativecontrol.utils.lists.list_1_9.willbreak;
        } else if (Main.serverVersion.equals("1.8")) {
            willbreak = me.kubqoa.creativecontrol.utils.lists.list_1_8.willbreak;
        }
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        if ((event.getEntityType() == EntityType.FALLING_BLOCK)) {
            FallingBlock fallingBlock = (org.bukkit.entity.FallingBlock) event.getEntity();
            if (fallingBlock.getMaterial() != Material.AIR) {
                BlockAPI blockAPI = new BlockAPI(event.getBlock());
                Location original = event.getBlock().getLocation();
                if (blockAPI.isCreativeBlock()) {
                    Location under = original.clone();
                    under.setY(under.getY() - 1);
                    while (under.getBlock().getType() == Material.AIR || under.getBlock().getType() == Material.WATER || under.getBlock().getType() == Material.STATIONARY_WATER || under.getBlock().getType() == Material.LAVA || under.getBlock().getType() == Material.STATIONARY_LAVA) {
                        under.setY(under.getY() - 1);
                    }
                    if (fallingBlock.getMaterial() != Material.ANVIL && willbreak.contains(under.getBlock().getType())) {
                        fallingBlock.setDropItem(false);
                        blockAPI.removeBlock();
                        return;
                    }
                    if (list_universal.willreplace.contains(under.getBlock().getType())) {
                        if (under.getBlock().getType() == Material.DOUBLE_PLANT) {
                            under.setY(under.getY() - 1);
                        }
                    } else {
                        under.setY(under.getY() + 1);
                        Methods.console(under.toString());
                    }
                    if (!under.equals(original)) {
                        blockAPI.blocksAbove();
                        fallingBlock.teleport(under);
                        blockAPI.updateBlock(original, under);
                    }
                }
            }
        }
    }
}
