package owg.handler;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.event.world.WorldEvent;
import owg.generatortype.GeneratorType;
import owg.generatortype.GeneratorTypeAlpha11;
import owg.generatortype.GeneratorTypeIndev;
import owg.generatortype.GeneratorTypeInfdev;

public class SnowHandler
{
    private static final Set<Integer> snowyWorlds = new HashSet<Integer>();

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (event.world.isRemote || GeneratorType.currentGenerator == null)
        {
            return;
        }

        GeneratorType gen = GeneratorType.currentGenerator;
        boolean isSnowWorld = false;

        // Check for Indev generator with Snow theme.
        if (gen instanceof GeneratorTypeIndev)
        {
            // For Indev, the theme is the first setting (index 0).
            // The value for the "Snow" theme is 4.
            if (GeneratorType.trySetting(0, 4) == 4)
            {
                isSnowWorld = true;
            }
        }
        // Check for Infdev or Alpha 1.1 generators with Snow option enabled.
        else if (gen instanceof GeneratorTypeInfdev || gen instanceof GeneratorTypeAlpha11)
        {
            if (GeneratorType.trySetting(0, 1) == 1)
            {
                isSnowWorld = true;
            }
        }

        if (isSnowWorld)
        {
            snowyWorlds.add(event.world.provider.dimensionId);
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event)
    {
        // Clean up to prevent memory leaks.
        if (!event.world.isRemote)
        {
            snowyWorlds.remove(event.world.provider.dimensionId);
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END && !event.world.isRemote)
        {
            if (snowyWorlds.contains(event.world.provider.dimensionId))
            {
                WorldInfo worldInfo = event.world.getWorldInfo();

                // If it's not raining/snowing, force it to start.
                if (!worldInfo.isRaining())
                {
                    worldInfo.setRaining(true);
                    // Set a very long rain time to make it effectively permanent.
                    worldInfo.setRainTime(Integer.MAX_VALUE);
                }
            }
        }
    }
}
