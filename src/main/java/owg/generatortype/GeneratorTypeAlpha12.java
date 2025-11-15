package owg.generatortype;

import owg.biomes.BiomeList;
import owg.generator.ChunkGeneratorAlpha;
import owg.gui.GuiGeneratorSettings;
import owg.gui.GuiSettingsButton;
import owg.world.ManagerOWG;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class GeneratorTypeAlpha12 extends GeneratorType
{
	public GeneratorTypeAlpha12(int id, int cat, String name, boolean c) 
	{
		super(id, cat, name, c);
	}

	@Override
	public boolean getSettings(GuiGeneratorSettings gui)
	{
		// New Ores toggle button. ID: 20
		String tooltip = StatCollector.translateToLocal("owg.tooltip.newores.main") + "\\n\u00A7a" + StatCollector.translateToLocal("owg.tooltip.newores.alpha12");
		gui.settings.add(new GuiSettingsButton(
				new String[]{
						StatCollector.translateToLocal("owg.setting.newores") + ": " + StatCollector.translateToLocal("owg.setting.off"),
						StatCollector.translateToLocal("owg.setting.newores") + ": " + StatCollector.translateToLocal("owg.setting.on")
				},
				new int[]{0, 1},
				20, 50, gui.width,
				-1, new int[0],
				tooltip
		));

		return true;
	}
	
	@Override
	public WorldChunkManager getServerWorldChunkManager(World world)
    {
		return new ManagerOWG(world, true, 0);
    }

	@Override
	public WorldChunkManager getClientWorldChunkManager(World world)
    {
		return new WorldChunkManagerHell(BiomeList.OLDplains, 0.5F);
    }

	@Override
    public IChunkProvider getChunkGenerator(World world)
    {	
		return new ChunkGeneratorAlpha(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
    }
}
