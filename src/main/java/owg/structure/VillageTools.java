package owg.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class VillageTools
{
    /**
     * Finds the average ground level by analyzing a raw block array, ignoring water.
     * This prevents villages from generating over large bodies of water.
     */
    public static int getAverageGroundLevel(Block[] blocks, int chunkX, int chunkZ, StructureBoundingBox sbb, World world)
    {
        int totalHeight = 0;
        int pointCount = 0;

        for (int z = sbb.minZ; z <= sbb.maxZ; ++z)
        {
            for (int x = sbb.minX; x <= sbb.maxX; ++x)
            {
                if (x >= (chunkX << 4) && x < ((chunkX << 4) + 16) && z >= (chunkZ << 4) && z < ((chunkZ << 4) + 16))
                {
                    if (sbb.isVecInside(x, 64, z))
                    {
                        int localX = x & 15;
                        int localZ = z & 15;
                        int topBlockY = 0;
                        Block topBlock = null;

                        for (int y = 127; y >= 0; --y)
                        {
                            int index = (localX * 16 + localZ) * 128 + y;
                            Block block = blocks[index];
                            if (block != null && block.getMaterial() != Material.air && block.getMaterial() != Material.leaves)
                            {
                                topBlockY = y;
                                topBlock = block;
                                break;
                            }
                        }

                        // Only consider points that are on solid ground, not in water.
                        if (topBlock != null && topBlock.getMaterial() != Material.water)
                        {
                            totalHeight += Math.max(topBlockY, world.provider.getAverageGroundLevel());
                            pointCount++;
                        }
                    }
                }
            }
        }

        if (pointCount == 0)
        {
            return -1;
        }
        else
        {
            return totalHeight / pointCount;
        }
    }
}
