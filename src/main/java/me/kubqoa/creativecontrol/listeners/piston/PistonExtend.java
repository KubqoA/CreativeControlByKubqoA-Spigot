package me.kubqoa.creativecontrol.listeners.piston;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.api.BlockAPI;
import me.kubqoa.creativecontrol.helpers.Methods;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import java.util.List;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 28/04/16.
 */
public class PistonExtend implements Listener {
    private List<Material> list;

    public PistonExtend() {
        if (Main.serverVersion.equals("1.9")) {
            list = me.kubqoa.creativecontrol.utils.lists.list_1_9.list;
        } else if (Main.serverVersion.equals("1.8")) {
            list = me.kubqoa.creativecontrol.utils.lists.list_1_8.list;
        } else if (Main.serverVersion.equals("1.7")) {
            list = me.kubqoa.creativecontrol.utils.lists.list_1_7.list;
        }
    }

    @EventHandler
    public void extend(BlockPistonExtendEvent event) {
        if (Methods.exclude(event.getBlock().getLocation())) return;
        switch (event.getDirection()) {
            case SOUTH: //z+
                for (Block block : event.getBlocks()) {
                    BlockAPI blockAPI = new BlockAPI(block);
                    Location location = block.getLocation();
                    if (blockAPI.isCreativeBlock()) {
                        if (list.contains(block.getType())) {
                            blockAPI.removeBlock();
                            block.setType(Material.AIR);
                        } else {
                            blockAPI.updateBlock(location, new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() + 1));
                        }
                    }
                }
                break;
            case NORTH: //z-
                for (Block block : event.getBlocks()) {
                    BlockAPI blockAPI = new BlockAPI(block);
                    Location location = block.getLocation();
                    if (blockAPI.isCreativeBlock()) {
                        if (list.contains(block.getType())) {
                            blockAPI.removeBlock();
                            block.setType(Material.AIR);
                        } else {
                            blockAPI.updateBlock(location, new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() - 1));
                        }
                    }
                }
                break;
            case EAST:  //x+
                for (Block block : event.getBlocks()) {
                    BlockAPI blockAPI = new BlockAPI(block);
                    Location location = block.getLocation();
                    if (blockAPI.isCreativeBlock()) {
                        if (list.contains(block.getType())) {
                            blockAPI.removeBlock();
                            block.setType(Material.AIR);
                        } else {
                            blockAPI.updateBlock(location, new Location(location.getWorld(), location.getX() + 1, location.getY(), location.getZ()));
                        }
                    }
                }
                break;
            case WEST:  //x-
                for (Block block : event.getBlocks()) {
                    BlockAPI blockAPI = new BlockAPI(block);
                    Location location = block.getLocation();
                    if (blockAPI.isCreativeBlock()) {
                        if (list.contains(block.getType())) {
                            blockAPI.removeBlock();
                            block.setType(Material.AIR);
                        } else {
                            blockAPI.updateBlock(location, new Location(location.getWorld(), location.getX() - 1, location.getY(), location.getZ()));
                        }
                    }
                }
                break;
            case UP:    //y+
                for (Block block : event.getBlocks()) {
                    BlockAPI blockAPI = new BlockAPI(block);
                    Location location = block.getLocation();
                    if (blockAPI.isCreativeBlock()) {
                        if (list.contains(block.getType())) {
                            blockAPI.removeBlock();
                            block.setType(Material.AIR);
                        } else {
                            blockAPI.updateBlock(location, new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ()));
                        }
                    }
                }
                break;
            case DOWN:  //y-
                for (Block block : event.getBlocks()) {
                    BlockAPI blockAPI = new BlockAPI(block);
                    Location location = block.getLocation();
                    if (blockAPI.isCreativeBlock()) {
                        if (list.contains(block.getType())) {
                            blockAPI.removeBlock();
                            block.setType(Material.AIR);
                        } else {
                            blockAPI.updateBlock(location, new Location(location.getWorld(), location.getX(), location.getY() - 1, location.getZ()));
                        }
                    }
                }
                break;
        }
    }
}
