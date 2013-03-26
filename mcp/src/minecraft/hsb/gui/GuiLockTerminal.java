package hsb.gui;

import hsb.network.NetworkManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import hsb.tileentitys.TileEntityLockTerminal;

public class GuiLockTerminal extends GuiContainer
{
    protected String name = "HSB Lock";
    protected TileEntityLockTerminal te;
    private int xPos;
    private int yPos;
    private int maxButtons = 10;
    private final int buttonIdStart = 3;
    
    
    //energy bar
    //x1: 9 x2: 136
    //TODO design
    /*
     * Design:
     * Buttons:
     * 	exit
     * 	Lock/Unlock
     * 	(Settings)
     * 	Upgrades?
     * 	Password/Port
     * EnergyBar
     * Energy
     * Energy Upgrade Slots
     * Status
     * 
     * 
     * 
     * TODO network code	
     */

    public GuiLockTerminal(TileEntityLockTerminal te, Container container, EntityPlayer entityplayer)
    {
        super(container);
        this.te = te;
        xSize = 228;
        ySize = 222;
    }
    @SuppressWarnings("unchecked")
	public void initGui()
    {
        super.initGui();
        xPos = width / 2 - xSize / 2;
        yPos = height / 2 - ySize / 2;
        this.controlList.clear();

        //Normal width = 52px
        //TODO Design
        int bPosX = 4;
        int bPosY = 4;
        
        
        this.controlList.add(new GuiButton(0, xPos - 22, yPos- -4, 20, 20, "X"));
        this.controlList.add(new GuiButton(1, xPos + 175, yPos + 105, 40, 20, getLock(te.locked)));
        this.controlList.add(new GuiButton(2, xPos + 175, yPos + 85, 40, 20, "Options"));

        //TODO better
        int buttonSize = (int) Math.floor((this.xSize - 6) / (maxButtons / 2));
        for(int i=0; i<10; i++)
        {
        	if(i>4)
        	{
        		bPosY = 23; // one time bPosY + 20
        	}
        	bPosX= (i % 5) * buttonSize + 4;
        	String button = te.getUpgradeButtonText(i);
        	//String button = te.buttonNumber[i];
        	if(button != null && button != "" && button.length() > 0)
        	{
        		this.controlList.add(new GuiButton(i + this.buttonIdStart, xPos + bPosX, yPos + bPosY, buttonSize, 20, te.getUpgrade(i).getButtonName()/*te.buttonNumber[i]*/));
        	}
        }
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        switch (guibutton.id)
        {
            case 0:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;
            case 1:
			controlList.get(1);
                NetworkManager.initiateClientTileEntityEvent(te, 1);
                break;
            case 2: 
            	NetworkManager.initiateClientTileEntityEvent(te, -1);
            	break;
        }
        //button 0 - 9 (3 - 12)
        if((guibutton.id >= this.buttonIdStart) && (guibutton.id < (this.buttonIdStart + this.maxButtons)))
        {
    		//button : 0 - 9
    		//events: -3 - -12
    		NetworkManager.initiateClientTileEntityEvent(te, ((guibutton.id-this.buttonIdStart) + 3) * (-1));
        }
        super.actionPerformed(guibutton);
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
            sLock = "Unlock";
        }
        else
        {
            sLock = "Lock";
        }
        return sLock;

    }

    public void drawScreen(int par1, int par2, float par3)
    {
    	super.drawScreen(par1, par2, par3);
        String energyBar=te.energyStored + "EU/" + (TileEntityLockTerminal.defaultEnergyStorage + te.extraStorage) + "EU";
        this.drawString(this.fontRenderer, energyBar, xPos + 105 - this.fontRenderer.getStringWidth(energyBar) / 2, yPos + 118, 10526880);
        ((GuiButton)controlList.get(1)).displayString = getLock(te.locked);
        
    }

    protected void drawStringBorder(int x1, int y1, int x2)
    {
        drawRect(x1 - 3, y1 - 3, x2 + 3, y1 + 10, -16777216);
        drawRect(x1 - 2, y1 - 2, x2 + 2, y1 + 9, -1);
    }


    public boolean doesGuiPauseGame()
    {
        return false;
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		int i = mc.renderEngine.getTexture("/hsb/textures/GuiLockTerminal.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
        int energy = this.te.getEnergyScaled(100);
        this.drawTexturedModalRect(xPos + 55, yPos + 115, 26, 222, energy, 14);
		
	}
}
