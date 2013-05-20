package hsb.client.gui;

import hsb.lib.Textures;
import hsb.network.NetworkManager;
import hsb.tileentity.TileEntityUnlocker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;

public class GuiUnlocker extends GuiContainer{

	protected TileEntityUnlocker te;
	
    private int xPos;
    private int yPos;
    
    EntityPlayer player;


    public GuiUnlocker(TileEntityUnlocker te, Container container, EntityPlayer entityplayer)
    {
        super(container);
        this.te = te;
        xSize = 176;
        ySize = 166;
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
            case 1:
            	NetworkManager.getInstance().initiateClientTileEntityEvent(te, 0);
            	break;
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
        mc.renderEngine.bindTexture(Textures.GUI_UNLOCKER);
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
        int energy = this.te.getEnergyScaled(49);
        this.drawTexturedModalRect(xPos + 45, yPos + 8 + 49 - energy, 177, 63 - energy, 6, energy);
        int burnTime = this.te.getBurnTimeScaled(14);
        this.drawTexturedModalRect(xPos + 17, yPos + 38 - burnTime, 176, 14 - burnTime, 14, burnTime);
        int progress = this.te.getProgressScaled(76);
        this.drawTexturedModalRect(xPos + 58, yPos + 13, 0, 166, progress, 17);
        if(te.active) {
            this.drawTexturedModalRect(xPos + 150, yPos + 14, 176, 63, 7, 15);
        }
		
	}

    @Override
	public void drawScreen(int mouseX, int mouseY, float par3)
    {
    	super.drawScreen(mouseX, mouseY, par3);     
    	drawInfoToolTip(mouseX, mouseY);
    }
    
    private void drawInfoToolTip(int x, int y) {
    	List<String> list = new ArrayList<String>();
    	List<String> color = new ArrayList<String>();
    	color.add(0, EnumChatFormatting.GRAY + ""); //setting Gray as standard
    	
    	if( (x >= (xPos + 45) && x <= (xPos + 50)) && (y >= (yPos + 8) && y <= (yPos + 57)) )
    	{
    		list.add(te.energyStored + " EU");// + EnumChatFormatting.RESET);
    	} else if( (x >= (xPos + 58) && x <= (xPos + 134)) && (y >= (yPos + 13) && y <= (yPos + 30)) )
    	{
    		float progress = te.progress * 100 / te.ticksToUnlock;
    		list.add("Progress: " + progress + "%");// +  EnumChatFormatting.RESET);
    		if(te.progress == -1) {
    			list.add("No Block to unlock found!!");
    			color.add(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD);
    		}
    		if(te.progress == -2) {
    			list.add("Last try failed!!");
    			color.add(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD);
    		}
    	} else if( (x >= (xPos + 149) && x <= (xPos + 158)) && (y >= (yPos + 13) && y <= (yPos + 30)) )
    	{
    		list.add(te.active ? "Active" : "Inactive");// +  EnumChatFormatting.RESET);
    		if(te.active)
    			color.set(0, EnumChatFormatting.GREEN + "");
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
	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
    {
        super.initGui();
        xPos = width / 2 - xSize / 2;
        yPos = height / 2 - ySize / 2;
        this.buttonList.clear();
        
        this.buttonList.add(new GuiButton(0, xPos - 22, yPos- -4, 20, 20, "X"));
        this.buttonList.add(new GuiButton(1, xPos + 57, yPos + 37, 78, 20, "Unlock"));
    }
	
    @Override
    public void updateScreen()
    {
    	super.updateScreen();
    }

}
