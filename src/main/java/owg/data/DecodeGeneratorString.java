package owg.data;

import owg.biomes.BiomeBeta;
import owg.generatortype.GeneratorType;
import owg.generatortype.GeneratorTypeBeta;

public class DecodeGeneratorString
{
	public static void decode(String generatorString)
	{
		String[] genstring = generatorString.split("#");
		GeneratorType gentype = getGeneratorFromName(genstring[0]);

		if(gentype != null) //GENERATOR NAME
		{
			GeneratorType.currentGenerator = gentype;
		}
		if(genstring.length > 1 && genstring[1].length() > 0) //GENERATOR SETTINGS
		{
			String[] settingstring = genstring[1].split("&");
			GeneratorType.currentSettings = new int[settingstring.length];
			for(int i = 0; i < settingstring.length; i++)
			{
				GeneratorType.currentSettings[i] = Integer.parseInt(settingstring[i]);
			}
		}
		else
		{
			GeneratorType.currentSettings = new int[0];
		}

		// Set the static flag for Beta coloring based on the generator settings.
		if (gentype instanceof GeneratorTypeBeta)
		{
			BiomeBeta.useSaturatedColors = GeneratorType.trySetting(3, 1) == 1;
		}
		else
		{
			BiomeBeta.useSaturatedColors = false;
		}
	}

	public static GeneratorType getGeneratorFromName(String name)
	{
		for(int g = 0; g < GeneratorType.generatortypes.length; g++)
		{
			if(GeneratorType.generatortypes[g] != null)
			{
				if(GeneratorType.generatortypes[g].GetName().equals(name))
				{
					return GeneratorType.generatortypes[g];
				}
			}
		}
		return null;
	}

	public static int getGeneratorIDFromName(String name)
	{
		for(int g = 0; g < GeneratorType.generatortypes.length; g++)
		{
			if(GeneratorType.generatortypes[g] != null)
			{
				if(GeneratorType.generatortypes[g].GetName().equals(name))
				{
					return GeneratorType.generatortypes[g].GetID();
				}
			}
		}
		return -1;
	}
}
