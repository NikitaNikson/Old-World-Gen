package owg.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Mouse;

import owg.data.DecodeGeneratorString;
import owg.generatortype.GeneratorType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;


public class GuiGeneratorSettings extends GuiScreen
{
	private final GuiCreateWorld createWorldGui;

	public GuiButton BUTTON_DONE;
	public GuiButton BUTTON_CATEGORY;

	public int generatorSelected = -1;
	public ArrayList<GuiGeneratorButton> generators;
	public ArrayList<GuiSettingsButton> settings;

	public boolean decodebool;
	public boolean setremember;
	public int[] rememberSettings;

	public boolean hasSettings = false;

	public String[] translatedDrawStrings;

	private GuiSettingsButton hoveredButton;
	private long hoverStartTime;
	private static final long TOOLTIP_DELAY = 500L; // 2000 milliseconds = 2 seconds

	public GuiGeneratorSettings(GuiCreateWorld gcw, String gs)
	{
		createWorldGui = gcw;
		decodebool = true;

		translatedDrawStrings = new String[2];
		translatedDrawStrings[0] = StatCollector.translateToLocal("gui.selectGenerator");
		translatedDrawStrings[1] = StatCollector.translateToLocal("gui.generatorSettings");
	}

	public void initGui()
	{
		this.buttonList.clear();
		this.buttonList.add(BUTTON_DONE = new GuiButton(0, this.width / 2 - 155, this.height - 24, 150, 20, StatCollector.translateToLocal("gui.done")));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 24, 150, 20, StatCollector.translateToLocal("gui.cancel")));

		if(decodebool)
		{
			decodebool = false;
			decodeString(createWorldGui.field_146334_a);
		}
		else
		{
			createList();

			for(int i = 0; i < generators.size(); i++)
			{
				generators.get(i).button.enabled = true;
				if(generators.get(i).generatorID == generatorSelected)
				{
					generators.get(i).button.enabled = false;
				}
			}
			selectGenerator();
		}
	}

	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0) //DONE
		{
			createWorldGui.field_146334_a = createString();
			this.mc.displayGuiScreen(this.createWorldGui);
		}
		else if (button.id == 1) //CANCEL
		{
			this.mc.displayGuiScreen(this.createWorldGui);
		}
		else if (button.id >= 10 && button.id < 20)
		{
			for(int i = 0; i < generators.size(); i++)
			{
				generators.get(i).button.enabled = true;
				if(generators.get(i).button.id == button.id)
				{
					generators.get(i).button.enabled = false;
					generatorSelected = generators.get(i).generatorID;
				}
			}
			selectGenerator();
		}
		else if (button.id >= 20 && button.id < 30)
		{
			for(int i = 0; i < settings.size(); i++)
			{
				if(settings.get(i).button.id == button.id)
				{
					settings.get(i).click();
				}
			}
			dependencies();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		// --- Draw titles and static text ---
		this.drawCenteredString(this.fontRendererObj, "Old World Generator mod", this.width / 2, 10, 16777215);
		this.drawString(this.fontRendererObj, translatedDrawStrings[0], this.width / 2 - 155 + 1, 40, 10526880);

		if (hasSettings)
		{
			this.drawString(this.fontRendererObj, translatedDrawStrings[1], this.width / 2 + 6, 40, 10526880);
		}

		this.drawString(this.fontRendererObj, "Server config: ", this.width / 2 - 155 + 1, 185, 16777215);
		this.drawString(this.fontRendererObj, "level-type=OWG", this.width / 2 - 155 + 1, 202, 10526880);
		this.drawString(this.fontRendererObj, "generator-settings=" + createString(), this.width / 2 - 155 + 1, 212, 10526880);

		// --- Manual button drawing loop ---
		for (int i = 0; i < this.buttonList.size(); ++i)
		{
			((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
		}

		// --- Tooltip logic ---
		GuiSettingsButton currentlyHovered = null;
		if (settings != null)
		{
			for (GuiSettingsButton setting : settings)
			{
				if (setting.button.visible && setting.tooltipText != null && !setting.tooltipText.isEmpty())
				{
					if (mouseX >= setting.button.xPosition && mouseY >= setting.button.yPosition && mouseX < setting.button.xPosition + setting.button.width && mouseY < setting.button.yPosition + setting.button.height)
					{
						currentlyHovered = setting;
						break; // Found the hovered button
					}
				}
			}
		}

		// Update state if the hovered button has changed
		if (currentlyHovered != this.hoveredButton)
		{
			this.hoveredButton = currentlyHovered;
			this.hoverStartTime = System.currentTimeMillis(); // Reset timer
		}

		// Draw tooltip if the delay has passed
		if (this.hoveredButton != null)
		{
			long elapsedTime = System.currentTimeMillis() - this.hoverStartTime;
			if (elapsedTime >= TOOLTIP_DELAY)
			{
				List<String> tooltipLines = Arrays.asList(this.hoveredButton.tooltipText.replace("\\n", "\n").split("\n"));
				this.drawHoveringText(tooltipLines, mouseX, mouseY, this.fontRendererObj);
			}
		}
	}

	public void createList()
	{
		if(generators != null)
		{
			for(int i = 0; i < generators.size(); i++)
			{
				this.buttonList.remove(generators.get(i).button);
			}
		}

		generators = new ArrayList<GuiGeneratorButton>();
		int count = 0;
		for(int g = 0; g < GeneratorType.generatortypes.length; g++)
		{
			if(GeneratorType.generatortypes[g] != null)
			{
				generators.add(new GuiGeneratorButton(StatCollector.translateToLocal("owg." + GeneratorType.generatortypes[g].GetName()), g, count + 10, 50 + (20 * count), this.width));
				this.buttonList.add(generators.get(generators.size() - 1).button);
				count++;
			}
		}
	}

	public void dependencies()
	{
		for(int i = 0; i < settings.size(); i++)
		{
			if(settings.get(i).dependencie > -1)
			{
				settings.get(i).button.visible = false;
				for(int p = 0; p < settings.get(i).depvalues.length; p++)
				{
					if(settings.get(settings.get(i).dependencie - 20).selected == settings.get(i).depvalues[p])
					{
						settings.get(i).button.visible = true;
					}
				}
			}
		}
	}

	public void selectGenerator()
	{
		if(generatorSelected > -1)
		{
			BUTTON_DONE.enabled = true;
		}
		else
		{
			BUTTON_DONE.enabled = false;
		}

		if(settings != null)
		{
			for(int i = 0; i < settings.size(); i++)
			{
				this.buttonList.remove(settings.get(i).button);
			}
		}
		settings = new ArrayList<GuiSettingsButton>();

		if(generatorSelected > -1)
		{
			hasSettings = GeneratorType.generatortypes[generatorSelected].getSettings(this);
		}

		for(int s = 0; s < settings.size(); s++)
		{
			this.buttonList.add(settings.get(s).button);
		}

		dependencies();

		if(setremember)
		{
			for(int rs = 0; rs < settings.size(); rs++)
			{
				settings.get(rs).setOldValue(rememberSettings[rs]);
			}
			setremember = false;
		}
	}

	public void decodeString(String decodestring)
	{
		String[] genstring = decodestring.split("#");
		String[] gensettings;
		if(genstring.length > 1 && genstring[1].length() > 0)
		{
			gensettings = genstring[1].split("&");
		}
		else
		{
			gensettings = new String[0];
		}

		int n = DecodeGeneratorString.getGeneratorIDFromName(genstring[0]);
		if(n > -1)
		{
			createList();
			generatorSelected = n;

			for(int i = 0; i < generators.size(); i++)
			{
				generators.get(i).button.enabled = true;
				if(generators.get(i).generatorID == generatorSelected)
				{
					generators.get(i).button.enabled = false;
				}
			}
			selectGenerator();

			for(int i = 0; i < settings.size(); i++)
			{
				if(i < gensettings.length)
				{
					settings.get(i).setOldValue(Integer.parseInt(gensettings[i]));
				}
			}
		}
		else
		{
			createList();
			generatorSelected = -1;
			selectGenerator();
		}
	}

	public String createString()
	{
		if(generatorSelected > -1 && generatorSelected < GeneratorType.generatortypes.length)
		{
			String genstring = GeneratorType.generatortypes[generatorSelected].GetName() + "#";
			for(int s = 0; s < settings.size(); s++)
			{
				genstring += s == 0 ? "" : "&";
				genstring += settings.get(s).valuearray[settings.get(s).selected];
			}

			return genstring;
		}
		else
		{
			return "BETA173#";
		}
	}
}
