package owg.generatortype;

import owg.biomes.BiomeList;
import owg.generator.ChunkGeneratorBeta15;
import owg.gui.GuiGeneratorSettings;
import owg.gui.GuiSettingsButton;
import owg.world.ManagerOWG;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class GeneratorTypeBeta15 extends GeneratorType
{
    // The constructor needs to match the new class name
    public GeneratorTypeBeta15(int id, int cat, String name, boolean c)
    {
        super(id, cat, name, c);
    }

    @Override
    public WorldChunkManager getServerWorldChunkManager(World world)
    {
        int biomes = trySetting(0, 2);
        return new ManagerOWG(world, true, biomes);
    }

    @Override
    public WorldChunkManager getClientWorldChunkManager(World world)
    {
        return new WorldChunkManagerHell(BiomeList.OLDplains, 0.5F);
    }

    @Override
    public IChunkProvider getChunkGenerator(World world)
    {
        int biomes = trySetting(0, 2);
        return new ChunkGeneratorBeta15(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), biomes);
    }
}
