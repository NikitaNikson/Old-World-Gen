package owg.gui;

import net.minecraft.client.gui.GuiButton;

public class GuiSettingsSlider extends GuiSettingsButton
{
	public GuiSettingsSlider(String[] text, int[] values, int def, int buttonID, int posY, int width)
	{
		// Pass null for the new tooltip parameter
		super(new GuiSlider(buttonID, width / 2 + 5, posY), text, values, buttonID, -1, new int[0], null);
		((GuiSlider) button).setSlider(this, def);
	}

	public GuiSettingsSlider(String[] text, int[] values, int def, int buttonID, int posY, int width, int dep, int[] vel)
	{
		// Pass null for the new tooltip parameter
		super(new GuiSlider(buttonID, width / 2 + 5, posY), text, values, buttonID, dep, vel, null);
		((GuiSlider) button).setSlider(this, def);
	}

	@Override
	public void click()
	{
	}

	@Override
	public void setOldValue(int oldValue)
	{
		for(int i = 0; i < valuearray.length; i++)
		{
			if(valuearray[i] == oldValue)
			{
				((GuiSlider) button).sliderValue = Math.round(oldValue) / (float) (valuearray.length - 1);
				((GuiSlider) button).setText();
				break;
			}
		}
	}
}
