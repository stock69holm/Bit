package net.stockholm.bit.bititem.processor;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.stockholm.bit.Bit;
import net.stockholm.bit.bititem.BitItem;
import net.stockholm.bit.bititem.parameters.BitParameters;
import net.stockholm.bit.config.Config;
import net.stockholm.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BitBlockBreakProcessor {
    public static void processBlockBreak(BitItem bitItem, Location location) {
        World world = location.getWorld();
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        BitParameters bitParameters = bitItem.getBitParameters();
        int halfX = bitParameters.getX() / 2;
        int halfY = bitParameters.getY() / 2;
        int halfZ = bitParameters.getZ() / 2;

        RegionContainer container = null;
        com.sk89q.worldedit.world.World adaptedWorld = null;
        if (Bit.getInstance().getWorldGuardPlugin() != null) {
            container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            adaptedWorld = BukkitAdapter.adapt(world);
        }

        Location mutableLocation = new Location(world, centerX, centerY, centerZ);

        for (int dx = -halfX; dx <= halfX; dx++) {
            int x = centerX + dx;
            for (int dy = -halfY; dy <= halfY; dy++) {
                int y = centerY + dy;
                for (int dz = -halfZ; dz <= halfZ; dz++) {
                    int z = centerZ + dz;

                    if (container != null) {
                        if (!container.get(adaptedWorld)
                                .getApplicableRegions(BlockVector3.at(x, y, z))
                                .getRegions().isEmpty()) continue;
                    }

                    mutableLocation.setX(x);
                    mutableLocation.setY(y);
                    mutableLocation.setZ(z);

                    Block block = world.getBlockAt(x, y, z);
                    Material blockType = block.getType();

                    if (blockType == Material.AIR || !blockType.isItem() || Config.blockedBlocks.contains(blockType)) continue;

                    block.setType(Material.AIR, false);
                    world.dropItem(mutableLocation.clone(), new ItemBuilder(blockType).build());
                }
            }
        }
    }
}