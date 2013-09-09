package hsb.client.gui;

import java.util.ArrayList;
import java.util.List;

import hsb.ModHsb;
import hsb.core.plugin.PluginManager;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import nwcore.network.NetworkManager;

import org.lwjgl.opengl.GL11;

public class GuiHsbTerminal extends GuiContainer
{
	protected TileEntityHsbTerminal te;
	private int xPos;
	private int yPos;

	//Upgrade button constants
	private static final int maxButtons = 10;
	/** place where upgrade buttons are */
	private static final int buttonWitdh = 218;
	private static final int buttonHeight = 20;

	private static final int buttonIdStart = 3;

	EntityPlayer player;
	
	private int lastButtons;


	public GuiHsbTerminal(TileEntityHsbTerminal te, Container container, EntityPlayer entityplayer)
	{
		super(container);
		this.te = te;
		xSize = 228;
		ySize = 222;
		player = entityplayer;
	}
	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		switch (guibutton.id)
		{
			//Exit
			case 0:
				this.mc.displayGuiScreen((GuiScreen)null);
				this.mc.setIngameFocus();
				break;
				//Lock / unlock
			case 1:
				NetworkManager.getInstance().initiateClientTileEntityEvent(te, TileEntityHsbTerminal.EVENT_BUTTON_LOCK);
				break;
				//Options menu
			case 2: 
				NetworkManager.getInstance().initiateClientTileEntityEvent(te, TileEntityHsbTerminal.EVENT_GUI_OPTIONS);
				break;
		}
		//button 0 - 9 (3 - 12)
		if((guibutton.id >= GuiHsbTerminal.buttonIdStart) && (guibutton.id < (GuiHsbTerminal.buttonIdStart + GuiHsbTerminal.maxButtons)))
		{
			//button : 0 - 9
			//events: -1 - -10
			//Send update
			NetworkManager.getInstance().initiateClientTileEntityEvent(te, ((guibutton.id-GuiHsbTerminal.buttonIdStart) + 1) * (-1));
		}
		super.actionPerformed(guibutton);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(Textures.GUI_TERMINAL);
		drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
		
		//Energy
		int energy = this.te.getEnergyScaled(100);
		this.drawTexturedModalRect(xPos + 55, yPos + 115, 26, 222, energy, 14);
		
		//Charge Item
		if(PluginManager.energyModInstalled_Item()) {
        	this.drawTexturedModalRect(xPos + 35, yPos + 97, 168, 222, 14, 14); //use empty electrical icon
		} else {
        	this.drawTexturedModalRect(xPos + 35, yPos + 97, 140, 222, 14, 14); //use empty burn icon
		}
		
        int burnTime = this.te.getBurnOrChargeScaled(14);
        if(PluginManager.energyModInstalled_Item()) {
        	this.drawTexturedModalRect(xPos + 35, yPos + 97 + 14 - burnTime, 126 + 28, 222 + 14 - burnTime, 14, burnTime); //use electrical icon
        } else {
        	this.drawTexturedModalRect(xPos + 35, yPos + 97 + 14 - burnTime, 126, 222 + 14 - burnTime, 14, burnTime); //use burn icon
        }
        
		
		String energyBar=(int)te.getEnergy() + "MJ/" + ((int)te.getMaxEnergy()) + "MJ";
		this.drawString(this.fontRenderer, energyBar, xPos + 105 - this.fontRenderer.getStringWidth(energyBar) / 2, yPos + 118, 10526880);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3)
	{
		super.drawScreen(mouseX, mouseY, par3);
		((GuiButton)buttonList.get(1)).displayString = getLock(te.locked);
    	drawInfoToolTip(mouseX, mouseY);

	}

	private void drawInfoToolTip(int x, int y) {
		
    	List<String> list = new ArrayList<String>();
    	List<String> color = new ArrayList<String>();
    	
    	color.add(0, EnumChatFormatting.GRAY + ""); //setting Gray as standard
    	
    	if( (x >= (xPos + 55) && x <= (xPos + 155)) && (y >= (yPos + 115) && y <= (yPos + 129)) ) //Energy Stored
    	{
    		list.add( ((int) te.getEnergy()) + " MJ");
    	} else if( (x >= (xPos + 35) && x <= (xPos + 49)) && (y >= (yPos + 97) && y <= (yPos + 111)) ) {
    		String s = PluginManager.getElectricChargeInfoString(te.getStackInSlot(TileEntityHsbTerminal.SLOT_FUEL));
    		if(s != null) {
    			list.add(s);
    		}
    	} else {
    		return;
    	}
    	
        for (int k = 0; k < list.size(); ++k)
        {
            {
                list.set(k, color.get(k) + (String)list.get(k) + EnumChatFormatting.RESET); //setting formatting
            }
        }
        drawHoveringText(list, x, y, (fontRenderer ));
		
	}
	protected void drawStringBorder(int x1, int y1, int x2)
	{
		drawRect(x1 - 3, y1 - 3, x2 + 3, y1 + 10, -16777216);
		drawRect(x1 - 2, y1 - 2, x2 + 2, y1 + 9, -1);
	}


	/**
	 * function to update the button text
	 * 
	 * @param lockStatus the status after the button is pressed
	 */

	public String getLock(boolean lockStatus)
	{
		String sLock;
		if (lockStatus)
		{
			sLock = Strings.translate(Strings.GUI_UNLOCK);
		}
		else
		{
			sLock = Strings.translate(Strings.GUI_LOCK);
		}
		return sLock;

	}
	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		super.initGui();
		xPos = width / 2 - xSize / 2;
		yPos = height / 2 - ySize / 2;
		this.buttonList.clear();
		
		lastButtons = te.buttons.hashCode();

		this.buttonList.add(new GuiButton(0, xPos - 22, yPos- -4, 20, 20, "X"));
		this.buttonList.add(new GuiButton(1, xPos + 175, yPos + 105, 40, 20, getLock(te.locked)));
		this.buttonList.add(new GuiButton(2, xPos + 175, yPos + 85, 40, 20, Strings.translate(Strings.GUI_OPTIONS)));

		if(te.buttons != null) {
			int buttonNumber = te.buttons.size();
			int[] buttonLength = new int[buttonNumber];
			int row1 = 0;
			int row2 = 0;

			for(int i = 0; i < buttonNumber; i++) {
				String id = UpgradeRegistry.idToInt.get(te.buttons.get(i));
				int width = GuiButtonExtended.getSuggestedWidth(Strings.translate(UpgradeRegistry.getButtonName(id)), this.fontRenderer);
				ModHsb.logger.debug("sug length: " + width);
				buttonLength[i] = width;
				if(i >= 5) {
					row2 += width;
				} else {
					row1 += width;
				}
			}
			int index = 0;
			while(row1 > buttonWitdh) {
				//format
				int maxButton = (buttonNumber > 5) ? 5 : buttonNumber;
				if(index < maxButton) { //display text only
					buttonLength[index] -= (16 + GuiButtonExtended.iconButtonSpace);
					row1 -= (16 + GuiButtonExtended.iconButtonSpace);
					index++;
				} else if(index < ((maxButton * 2) -1) ){ //display icon only
					int oldLength = buttonLength[index - maxButton];
					int newLength = buttonLength[index - maxButton] = GuiButtonExtended.xOffset * 2 + 16;
					row1 -= oldLength - newLength; //subtract the difference
					index++;
				} else {
					//whatever
					break;
				}
			}
			index = 0;
			while(row2 > buttonWitdh) {
				//format
				int maxButton = buttonNumber - 5;
				if(index < maxButton) { //display text only
					buttonLength[index + 5] -= (16 + GuiButtonExtended.iconButtonSpace);
					row2 -= (16 + GuiButtonExtended.iconButtonSpace);
					index++;
				} else if(index < ((maxButton * 2) -1) ){ //display icon only
					int oldLength = buttonLength[index - maxButton + 5];
					int newLength = buttonLength[index - maxButton + 5] = GuiButtonExtended.xOffset * 2 + 16;
					row2 -= oldLength - newLength; //subtract the difference
					index++;
				} else {
					//whatever
					break;
				}
			}
			int space1 = 0;
			int space2 = 0;
			if(buttonNumber > 5) {
				space1 = (buttonWitdh - row1) / 4; //space between buttons row1
				space2 = (buttonWitdh - row2) / (buttonNumber - 5 - 1); //space between buttons row 2
			} else if(buttonNumber > 1) {
				space1 = (int) ((buttonWitdh - row1) / (buttonNumber - 1));
				row2 = space2 = 0;
			}
			if(space1 > 5) {
				space1 = 5;
			}
			if(space2 > 5) {
				space2 = 5;
			}
			row1 += ( ( (buttonNumber > 5) ? 5 : buttonNumber ) - 1) * space1;
			row2 += (buttonNumber % 5 -1 ) * space2;
			
					
			int bPosX = 4; //left top (see sprite)
			int bPosY = 4;
			
			bPosX += (buttonWitdh - row1) / 2; //center buttons
			
			if(buttonNumber <=5) {
				bPosY += buttonHeight / 2; //center one row
			}

			int space = space1;
			
			for(int i = 0; i < buttonNumber; i++)
			{
				if(i == 5) //next row
				{
					bPosY += buttonHeight; 
					bPosX = 4;
					bPosX += (buttonWitdh - row2) / 2; //center buttons
					space = space2; 
				}
				if(i != 0 && i != 5) {
					bPosX += space + buttonLength[i - 1]; //add last button and space to next position
				}
				ModHsb.logger.debug("space: " + space);
				String id = UpgradeRegistry.idToInt.get(te.buttons.get(i));
				buttonList.add(new GuiButtonExtended(i + buttonIdStart, xPos + bPosX, yPos + bPosY, buttonLength[i], buttonHeight, Strings.translate(UpgradeRegistry.getButtonName(id)), UpgradeRegistry.getButtonIcon(id)));
			}
		} 
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if(te.buttons.hashCode() != lastButtons)
		{
			this.initGui();
		}
	}
}
