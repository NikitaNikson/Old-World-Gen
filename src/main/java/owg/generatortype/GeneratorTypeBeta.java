package owg.generatortype;

import owg.biomes.BiomeList;
import owg.generator.ChunkGeneratorBeta;
import owg.gui.GuiGeneratorSettings;
import owg.gui.GuiSettingsButton;
import owg.gui.GuiSettingsSlider;
import owg.world.ManagerOWG;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class GeneratorTypeBeta extends GeneratorType
{
    public GeneratorTypeBeta(int id, int cat, String name, boolean c)
    {
        super(id, cat, name, c);
    }

    @Override
    public boolean getSettings(GuiGeneratorSettings gui)
    {
        // Biome selection button. ID: 20
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        StatCollector.translateToLocal("owg.biomes.biomes") + ": " + StatCollector.translateToLocal("owg.biomes.original"),
                        StatCollector.translateToLocal("owg.biomes.biomes") + ": " + StatCollector.translateToLocal("owg.biomes.vanilla"),
                        StatCollector.translateToLocal("owg.biomes.biomes") + ": " + StatCollector.translateToLocal("owg.biomes.all")
                },
                new int[]{0, 1, 2},
                20, 50, gui.width
        ));

        // Tall Grass toggle button. ID: 21
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        StatCollector.translateToLocal("owg.setting.tallgrass") + ": " + StatCollector.translateToLocal("owg.setting.on"),
                        StatCollector.translateToLocal("owg.setting.tallgrass") + ": " + StatCollector.translateToLocal("owg.setting.off")
                },
                new int[]{0, 1}, // 0 = On, 1 = Off
                21, 70, gui.width,
                20, new int[]{0},
                StatCollector.translateToLocal("owg.tooltip.tallgrass")
        ));

        // New Ores toggle button. ID: 22
        String tooltip = StatCollector.translateToLocal("owg.tooltip.newores.main") + "\\n\u00A7a" + StatCollector.translateToLocal("owg.tooltip.newores.beta");
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        StatCollector.translateToLocal("owg.setting.newores") + ": " + StatCollector.translateToLocal("owg.setting.off"),
                        StatCollector.translateToLocal("owg.setting.newores") + ": " + StatCollector.translateToLocal("owg.setting.on")
                },
                new int[]{0, 1},
                22, 90, gui.width,
                -1, new int[0],
                tooltip
        ));

        // Beta Coloring toggle button. ID: 23
        String colorTooltip = StatCollector.translateToLocal("owg.tooltip.betacolor.line1") + "\\n\u00A7c" + StatCollector.translateToLocal("owg.tooltip.betacolor.line2");
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        StatCollector.translateToLocal("owg.setting.betacolor") + ": " + StatCollector.translateToLocal("owg.setting.off"),
                        StatCollector.translateToLocal("owg.setting.betacolor") + ": " + StatCollector.translateToLocal("owg.setting.on")
                },
                new int[]{0, 1}, // 0 = Off (default), 1 = On
                23, 110, gui.width,
                -1, new int[0],
                colorTooltip
        ));

        // Generate Villages toggle button. ID: 24
        // Default: Off (0)
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        "Generate Villages: Off",
                        "Generate Villages: On"
                },
                new int[]{0, 1},
                24, 130, gui.width,
                -1, new int[0],
                null
        ));

        // Generate Structures toggle button. ID: 25
        // Default: On (0)
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        "Generate Structures: On",
                        "Generate Structures: Off"
                },
                new int[]{0, 1},
                25, 150, gui.width,
                -1, new int[0],
                "Generates Strongholds and Mineshafts."
        ));

        // Generate Less Coal toggle button. ID: 26
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        StatCollector.translateToLocal("owg.setting.lesscoal") + ": " + StatCollector.translateToLocal("owg.setting.off"),
                        StatCollector.translateToLocal("owg.setting.lesscoal") + ": " + StatCollector.translateToLocal("owg.setting.on")
                },
                new int[]{0, 1}, // 0 = Off, 1 = On
                26, 170, gui.width,
                -1, new int[0],
                StatCollector.translateToLocal("owg.tooltip.lesscoal")
        ));

        // Fix Beaches toggle button. ID: 27
        gui.settings.add(new GuiSettingsButton(
                new String[]{
                        StatCollector.translateToLocal("owg.setting.fixbeaches") + ": " + StatCollector.translateToLocal("owg.setting.on"),
                        StatCollector.translateToLocal("owg.setting.fixbeaches") + ": " + StatCollector.translateToLocal("owg.setting.off")
                },
                new int[]{1, 0}, // 1 = On (Default), 0 = Off
                27, 190, gui.width,
                -1, new int[0],
                StatCollector.translateToLocal("owg.tooltip.fixbeaches")
        ));

        return true;
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
        return new ChunkGeneratorBeta(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), biomes);
    }
}
