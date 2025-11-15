package owg.gui;

import net.minecraft.client.gui.GuiButton;

public class GuiSettingsButton
{
	public GuiButton button;
	public String[] textarray;
	public int[] valuearray;
	public int selected;
	public int dependencie;
	public int[] depvalues;
	public String tooltipText;

	// Constructor for simple buttons (no dependencies, no tooltip)
	public GuiSettingsButton(String[] text, int[] values, int buttonID, int posY, int width)
	{
		this(text, values, buttonID, posY, width, -1, new int[0], null);
	}

	// Constructor for dependent buttons (no tooltip)
	public GuiSettingsButton(String[] text, int[] values, int buttonID, int posY, int width, int dep, int[] vel)
	{
		this(text, values, buttonID, posY, width, dep, vel, null);
	}

	// The primary public constructor that handles all options, including the tooltip.
	public GuiSettingsButton(String[] text, int[] values, int buttonID, int posY, int width, int dep, int[] vel, String tooltip)
	{
		this(new GuiButton(buttonID, width / 2 + 5, posY, 150, 20, text[0]), text, values, buttonID, dep, vel, tooltip);
	}

	protected GuiSettingsButton(GuiButton b, String[] text, int[] values, int buttonID, int dep, int[] vel, String tooltip)
	{
		button = b;
		textarray = text;
		valuearray = values;
		selected = 0;
		dependencie = dep;
		depvalues = vel;
		tooltipText = tooltip;
	}

	public void click()
	{
		selected++;
		if(selected >= textarray.length)
		{
			selected = 0;
		}
		button.displayString = textarray[selected];
	}

	public void setOldValue(int oldValue)
	{
		for(int i = 0; i < valuearray.length; i++)
		{
			if(valuearray[i] == oldValue)
			{
				selected = i;
				button.displayString = textarray[selected];
				break;
			}
		}
	}
}
