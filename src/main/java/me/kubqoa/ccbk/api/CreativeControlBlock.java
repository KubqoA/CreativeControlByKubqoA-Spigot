package me.kubqoa.ccbk.api;

import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.managers.CreativeControlDatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class CreativeControlBlock {
    private final Block block;

    public CreativeControlBlock(Block block) {
        this.block = block;
    }

    public Location getLocation() {
        return block.getLocation();
    }

    public int getX() {
        return block.getX();
    }

    public int getY() {
        return block.getY();
    }

    public int getZ() {
        return block.getZ();
    }

    public World getWorld() {
        return block.getWorld();
    }

    public BlockState getState() {
        return block.getState();
    }

    public Material getMaterial() {
        return block.getType();
    }

    public byte getData() {
        return block.getData();
    }

    public void setMaterial(Material material) {
        block.setType(material);
    }

    public void setData(byte data) {
        block.setData(data);
    }

    public CreativeControlBlock getRelative(BlockFace blockFace) {
        return new CreativeControlBlock(block.getRelative(blockFace));
    }

    public boolean isExcluded() {
        return CreativeControl.excludedWorlds.contains(getWorld());
    }

    public boolean isTracked() {
        return !CreativeControl.noTracking.contains(getMaterial());
    }

    public boolean isDisabled() {
        return CreativeControl.disabledMaterials.contains(getMaterial());
    }

    public void blocksAround() {
        blockAbove();
    }

    public void blockAbove() {

    }

    public void removeBlock() {
        setMaterial(Material.AIR);
    }

    public boolean isCreativeBlock() {
        if (CreativeControl.playerCheckedLocations.contains(getLocation())) return CreativeControl.playerCreativeLocations.contains(getLocation());
        /*else {
            return CreativeControlDatabaseManager.executeQuery("SELECT * FROM `" + CreativeControl.databasePrefix + "blocks` " +
                    "WHERE x=? AND y=? AND z=? AND world=?;", new Object[]{getX(), getY(), getZ(), getWorld().getUID().toString()}) > 0;
        }*/
        return false;
    }

    public void removeBlockFromDB() {
        Bukkit.getScheduler().runTaskAsynchronously(CreativeControl.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                CreativeControlDatabaseManager.executeUpdate("DELETE FROM `" +CreativeControl.databasePrefix+ "blocks` "+
                        "WHERE x=? AND y=? AND z=? AND world=?;",new Object[]{getX(),getY(),getZ(),getWorld().getUID().toString()});
            }
        });
    }

    public void removeBlockFromCache() {
        CreativeControl.creative.remove(getLocation());
        CreativeControl.checked.remove(getLocation());
    }

    public void addBlockToDB() {
        Bukkit.getScheduler().runTaskAsynchronously(CreativeControl.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                CreativeControlDatabaseManager.executeUpdate("INSERT INTO `" +CreativeControl.databasePrefix+ "blocks` "+
                "(x,y,z,world) VALUES (?,?,?,?);",new Object[]{getX(),getY(),getZ(),getWorld().getUID().toString()});
            }
        });
    }

    public void updateBlockInDB() {

    }
}
