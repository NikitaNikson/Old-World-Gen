package owg.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigOWG 
{
	public static Configuration config;
	public static int[] biomeIDs = new int[12];

	public static boolean enableCustomDungeonLoot;
	public static boolean enableImprovedVillageGen;

	public static void init(FMLPreInitializationEvent event)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		
		for(int c = 0; c < biomeIDs.length; c++)
		{
			biomeIDs[c] = 188 + c;
		}
		
		try 
		{
			config.load();

			//BETA-ALPHA BIOMES
			biomeIDs[0] = config.get("1 - Beta & Alpha", "Desert", 188).getInt();
			biomeIDs[1] = config.get("1 - Beta & Alpha", "Forest", 189).getInt();
			biomeIDs[2] = config.get("1 - Beta & Alpha", "Plains", 190).getInt();
			biomeIDs[3] = config.get("1 - Beta & Alpha", "Rainforest", 191).getInt();
			biomeIDs[4] = config.get("1 - Beta & Alpha", "Savanna", 192).getInt();
			biomeIDs[5] = config.get("1 - Beta & Alpha", "SeasonalForest", 193).getInt();
			biomeIDs[6] = config.get("1 - Beta & Alpha", "Shrubland", 194).getInt();
			biomeIDs[7] = config.get("1 - Beta & Alpha", "Swampland", 195).getInt();
			biomeIDs[8] = config.get("1 - Beta & Alpha", "Taiga", 196).getInt();
			biomeIDs[9] = config.get("1 - Beta & Alpha", "Tundra", 197).getInt();

			//INFDEV-INDEV-CLASSIC BIOMES
			biomeIDs[10] = config.get("2 - Infdev & Indev", "Classic", 198).getInt();
			biomeIDs[11] = config.get("2 - Infdev & Indev", "ClassicSnow", 199).getInt();

			enableCustomDungeonLoot = config.getBoolean("Enable custom dungeon loot", "Structures", true, "Set this to false to use Forge's API for populating dungeon chest contents instead of OWG's algorithm."); // Thanks makamys
			enableImprovedVillageGen = config.getBoolean("Enable Improved Village Generation", "Structures", true, "Set this to true to enable improved village placement. Fixes villages generating in the air, water or underground. Set this to false to remove all village generation.");
		}
		catch (Exception e) 
		{
			for(int c = 0; c < biomeIDs.length; c++)
			{
				biomeIDs[c] = 188 + c;
			}
		}
		finally 
		{
			if (config.hasChanged()) 
			{
				config.save();
			}
		}
	}
}
