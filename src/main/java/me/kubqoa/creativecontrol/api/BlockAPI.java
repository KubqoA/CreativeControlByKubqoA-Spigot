package me.kubqoa.creativecontrol.api;

import me.kubqoa.creativecontrol.Main;
import me.kubqoa.creativecontrol.helpers.BlockHelper;
import me.kubqoa.creativecontrol.integrations.Integrations;
import me.kubqoa.creativecontrol.tasks.BlocksFromDB;
import me.kubqoa.creativecontrol.tasks.BlocksToDB;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Rails;

import java.util.List;

/**
 * CreativeControlPaidByKubqoA class
 * Created by jacobarbet on 2015.
 */

public class BlockAPI {
    private final Block block;
    private List<Material> above;

    public BlockAPI(Block b) {
        if (Main.serverVersion.equals("1.9")) {
            above = me.kubqoa.creativecontrol.utils.lists.list_1_9.above;
        } else if (Main.serverVersion.equals("1.8")) {
            above = me.kubqoa.creativecontrol.utils.lists.list_1_8.above;
        } else if (Main.serverVersion.equals("1.7")) {
            above = me.kubqoa.creativecontrol.utils.lists.list_1_7.above;
        }
        block = b;
    }

    public void blocksAbove() {
        Block relative = block.getRelative(BlockFace.UP);
        BlockAPI relativeAPI = new BlockAPI(relative);
        if (above.contains(relative.getType()) && relativeAPI.isCreativeBlock()) {
            relativeAPI.removeBlock();
            relative.setType(Material.AIR);
        }
        relative = block.getRelative(BlockFace.WEST);
        MaterialData data = relative.getState().getData();
        relativeAPI = new BlockAPI(relative);
        if (data instanceof Attachable && relativeAPI.isCreativeBlock()) {
            BlockFace attached = ((Attachable) data).getAttachedFace();
            Location original = relative.getRelative(attached).getLocation();
            if (original.equals(block.getLocation())) {
                relativeAPI.removeBlock();
                relative.setType(Material.AIR);
            }
        } else if (data instanceof Rails) {
            Rails rails = (Rails) data;
            if (rails.isOnSlope()) {
                BlockFace blockFace = rails.getDirection();
                Location original = relative.getRelative(blockFace).getLocation();
                if (original.equals(block.getLocation())) {
                    relativeAPI.removeBlock();
                    relative.setType(Material.AIR);
                }
            }
        }
        relative = block.getRelative(BlockFace.EAST);
        data = relative.getState().getData();
        relativeAPI = new BlockAPI(relative);
        if (data instanceof Attachable && relativeAPI.isCreativeBlock()) {
            BlockFace attached = ((Attachable) data).getAttachedFace();
            Location original = relative.getRelative(attached).getLocation();
            if (original.equals(block.getLocation())) {
                relativeAPI.removeBlock();
                relative.setType(Material.AIR);
            }
        } else if (data instanceof Rails) {
            Rails rails = (Rails) data;
            if (rails.isOnSlope()) {
                BlockFace blockFace = rails.getDirection();
                Location original = relative.getRelative(blockFace).getLocation();
                if (original.equals(block.getLocation())) {
                    relativeAPI.removeBlock();
                    relative.setType(Material.AIR);
                }
            }
        }
        relative = block.getRelative(BlockFace.NORTH);
        data = relative.getState().getData();
        relativeAPI = new BlockAPI(relative);
        if (data instanceof Attachable && relativeAPI.isCreativeBlock()) {
            BlockFace attached = ((Attachable) data).getAttachedFace();
            Location original = relative.getRelative(attached).getLocation();
            if (original.equals(block.getLocation())) {
                relativeAPI.removeBlock();
                relative.setType(Material.AIR);
            }
        } else if (data instanceof Rails) {
            Rails rails = (Rails) data;
            if (rails.isOnSlope()) {
                BlockFace blockFace = rails.getDirection();
                Location original = relative.getRelative(blockFace).getLocation();
                if (original.equals(block.getLocation())) {
                    relativeAPI.removeBlock();
                    relative.setType(Material.AIR);
                }
            }
        }
        relative = block.getRelative(BlockFace.SOUTH);
        data = relative.getState().getData();
        relativeAPI = new BlockAPI(relative);
        if (data instanceof Attachable && relativeAPI.isCreativeBlock()) {
            BlockFace attached = ((Attachable) data).getAttachedFace();
            Location original = relative.getRelative(attached).getLocation();
            if (original.equals(block.getLocation())) {
                relativeAPI.removeBlock();
                relative.setType(Material.AIR);
            }
        } else if (data instanceof Rails) {
            Rails rails = (Rails) data;
            if (rails.isOnSlope()) {
                BlockFace blockFace = rails.getDirection();
                Location original = relative.getRelative(blockFace).getLocation();
                if (original.equals(block.getLocation())) {
                    relativeAPI.removeBlock();
                    relative.setType(Material.AIR);
                }
            }
        }
    }

    public boolean isCreativeBlock() {
        Location location = block.getLocation();
        if (Main.blocksLocation.contains(location)) {
            Material material = Main.blocksMaterial.get(location);
            if (material == block.getType()) {
                return true;
            } else if (material == Material.GRASS && block.getType() == Material.DIRT) {
                return true;
            } else if (material == Material.DIRT && block.getType() == Material.GRASS) {
                return true;
            }
        } else if (Main.WblocksLocation.contains(location)) {
            Material material = Main.WblocksMaterial.get(location);
            if (material == block.getType()) {
                return true;
            } else if (material == Material.GRASS && block.getType() == Material.DIRT) {
                return true;
            } else if (material == Material.DIRT && block.getType() == Material.GRASS) {
                return true;
            }
        } else if (BlockHelper.isCreativeBlock(location)) {
            return true;
        }

        return false;
    }

    public boolean canBreak(Player player) {
        return Integrations.canBreak(block, player);
    }

    public void removeBlock() {
        Location location = block.getLocation();
        if (Main.blocksLocation.contains(location)) {
            Main.blocksLocation.remove(location);
            Main.blocksMaterial.remove(location);
        }
        if (Main.WblocksLocation.contains(location)) {
            Main.WblocksLocation.remove(location);
            Main.WblocksMaterial.remove(location);
        }
        if (BlockHelper.isCreativeBlock(location)) {
            Main.RblocksLocation.add(location);
            Main.RblocksMaterial.put(location, location.getBlock().getType());
            if (Main.RblocksLocation.size() >= Main.removingInterval) {
                new BlocksFromDB().runTaskAsynchronously(Main.thisPlugin);
            }
        }
    }

    public void addBlock(Material material) {
        Location location = block.getLocation();
        if (Main.blocksLocation.size() < Main.blockCache) {
            Main.blocksLocation.add(location);
            Main.blocksMaterial.put(location, material);
        }
        Main.WblocksLocation.add(location);
        Main.WblocksMaterial.put(location, material);
        if (Main.WblocksLocation.size() >= Main.loggingInterval) {
            new BlocksToDB().runTaskAsynchronously(Main.thisPlugin);
        }
    }

    public void updateBlock(Location oldLocation, Location newLocation) {
        if (Main.blocksLocation.contains(oldLocation)) {
            Main.blocksLocation.add(newLocation);
            Main.blocksMaterial.put(newLocation, Main.blocksMaterial.get(oldLocation));
            Main.blocksLocation.remove(oldLocation);
            Main.blocksMaterial.remove(oldLocation);
        }
        if (Main.WblocksLocation.contains(oldLocation)) {
            Main.WblocksLocation.add(newLocation);
            Main.WblocksMaterial.put(newLocation, Main.WblocksMaterial.get(oldLocation));
            Main.WblocksLocation.remove(oldLocation);
            Main.WblocksMaterial.remove(oldLocation);
        } else if (BlockHelper.isCreativeBlock(oldLocation)) {
            BlockHelper.updateBlock(oldLocation, newLocation);
        }
    }
}
