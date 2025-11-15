package owg.structure;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.ChunkCoordIntPair;
import owg.config.ConfigOWG;

public class MapGenVillageOWG extends MapGenVillage
{
    private static Method updateBoundingBoxMethod;

    public MapGenVillageOWG()
    {
        super();
    }

    public MapGenVillageOWG(Map map)
    {
        super(map);
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        if (!ConfigOWG.enableImprovedVillageGen)
        {
            return false;
        }
        return super.canSpawnStructureAtCoords(chunkX, chunkZ);
    }

    @Override
    public void func_151539_a(IChunkProvider chunkProvider, World world, int chunkX, int chunkZ, Block[] blocks)
    {
        if (blocks == null)
        {
            super.func_151539_a(chunkProvider, world, chunkX, chunkZ, blocks);
            return;
        }

        StructureStart structureStart = (StructureStart) this.structureMap.get(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)));

        if (structureStart != null && structureStart.isSizeableStructure())
        {
            for (StructureComponent component : (List<StructureComponent>) structureStart.getComponents())
            {
                if (component instanceof StructureVillagePieces.Village)
                {
                    StructureVillagePieces.Village villagePiece = (StructureVillagePieces.Village) component;
                    StructureBoundingBox sbb = villagePiece.getBoundingBox();

                    int averageY = VillageTools.getAverageGroundLevel(blocks, chunkX, chunkZ, sbb, world);

                    if (averageY > -1)
                    {
                        int verticalOffset = averageY - sbb.minY;
                        villagePiece.getBoundingBox().offset(0, verticalOffset, 0);
                    }
                }
            }

            try
            {
                if (updateBoundingBoxMethod == null)
                {
                    updateBoundingBoxMethod = StructureStart.class.getDeclaredMethod("updateBoundingBox");
                    updateBoundingBoxMethod.setAccessible(true);
                }
                updateBoundingBoxMethod.invoke(structureStart);
            }
            catch (Exception e)
            {
                System.err.println("OWG: Failed to call updateBoundingBox via reflection: " + e.getMessage());
            }
        }

        super.func_151539_a(chunkProvider, world, chunkX, chunkZ, blocks);
    }
}
