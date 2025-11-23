package owg.generatortype;

import owg.biomes.BiomeList;
import owg.generator.ChunkGeneratorInfdev;
import owg.gui.GuiGeneratorSettings;
import owg.gui.GuiSettingsButton;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class GeneratorTypeAlpha11 extends GeneratorType
{
	public GeneratorTypeAlpha11(int id, int cat, String name, boolean c)
	{
		super(id, cat, name, c);
	}

	@Override
	public boolean getSettings(GuiGeneratorSettings gui)
	{
		// Added tooltip parameter to the button constructor.
		gui.settings.add(new GuiSettingsButton(
				new String[]{
						StatCollector.translateToLocal("owg.setting.snow") + ": " + StatCollector.translateToLocal("owg.setting.off"),
						StatCollector.translateToLocal("owg.setting.snow") + ": " + StatCollector.translateToLocal("owg.setting.on")
				},
				new int[]{0, 1},
				20, 50, gui.width,
				-1, new int[0],
				StatCollector.translateToLocal("owg.tooltip.snowworld")
		));

		// New Ores toggle button. ID: 21
		String tooltip = StatCollector.translateToLocal("owg.tooltip.newores.main") + "\\n\u00A7a" + StatCollector.translateToLocal("owg.tooltip.newores.alpha11");
		gui.settings.add(new GuiSettingsButton(
				new String[]{
						StatCollector.translateToLocal("owg.setting.newores") + ": " + StatCollector.translateToLocal("owg.setting.off"),
						StatCollector.translateToLocal("owg.setting.newores") + ": " + StatCollector.translateToLocal("owg.setting.on")
				},
				new int[]{0, 1},
				21, 70, gui.width,
				-1, new int[0],
				tooltip
		));

		// Generate Less Coal toggle button. ID: 22
		gui.settings.add(new GuiSettingsButton(
				new String[]{
						StatCollector.translateToLocal("owg.setting.lesscoal") + ": " + StatCollector.translateToLocal("owg.setting.off"),
						StatCollector.translateToLocal("owg.setting.lesscoal") + ": " + StatCollector.translateToLocal("owg.setting.on")
				},
				new int[]{0, 1},
				22, 90, gui.width,
				-1, new int[0],
				StatCollector.translateToLocal("owg.tooltip.lesscoal")
		));

		return true;
	}

	@Override
	public WorldChunkManager getServerWorldChunkManager(World world)
	{
		if(trySetting(0, 1) == 0)
		{
			return new WorldChunkManagerHell(BiomeList.CLASSICnormal, 0.5F);
		}
		return new WorldChunkManagerHell(BiomeList.CLASSICsnow, 0.5F);
	}

	@Override
	public WorldChunkManager getClientWorldChunkManager(World world)
	{
		return new WorldChunkManagerHell(BiomeList.CLASSICnormal, 0.5F);
	}

	@Override
	public IChunkProvider getChunkGenerator(World world)
	{
		return new ChunkGeneratorInfdev(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), true);
	}
}
