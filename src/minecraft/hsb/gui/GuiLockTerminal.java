package hsb.gui;

import ic2.api.NetworkHelper;

import org.lwjgl.opengl.GL11;

import hsb.ILockUpgrade;
import hsb.ModHsbCore;
import hsb.TileEntityLockTerminal;
import hsb.api.UpgradeHsb;
import hsb.config.Config;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;

public class GuiLockTerminal extends GuiContainer
{
	protected int xSize;
    protected int ySize;
    protected String name = "HSB Lock";
    protected TileEntityLockTerminal te;
    private String lock;
    private EntityPlayer entityplayer;
    private int updateCounter;
    private int xPos;
    private int yPos;
    private int maxButtons = 10;
    private final int buttonIdStart = 3;
    private Container container;
    
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
     * Maybe Add Buttons, by Upgrades
     * 	--> maximum Buttons (eg. 10)
     * 
     * 
     * TODO network code	
     */

    public GuiLockTerminal(TileEntityLockTerminal te, Container container, EntityPlayer entityplayer)
    {
        super(container);
        this.container = container;
        this.te = te;
        this.entityplayer = entityplayer;
        xSize = 228;//176 //TODO
        ySize = 222;//166
    }
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
        	ItemStack stack = te.getStackInSlot(i+5);
        	if(stack != null)
        	{
        		this.controlList.add(new GuiButton(i + this.buttonIdStart, xPos + bPosX, yPos + bPosY, buttonSize, 20, ((ILockUpgrade)stack.getItem()).getButtonName()));
        	}
//        	if(i<maxButtons)
//        		this.controlList.add(new GuiButton(i + this.buttonIdStart, xPos + bPosX, yPos + bPosY, buttonSize, 20, String.valueOf(buttonSize)));
        }
//        this.controlList.add(new GuiButton(0, xPos + xSize - 57, yPos + ySize - 94, 52, 20, lock));
//        this.controlList.add(new GuiButton(1, xPos + xSize - 57, yPos + ySize - 54, 52, 20, "Upgrades"));
//        this.controlList.add(new GuiButton(2, xPos + xSize - 57, yPos + ySize - 74, 52, 20, "Password"));

    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
    	//TODO
        switch (guibutton.id)
        {
            case 0:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;
            case 1:
//                toggleButtonLock(!te.locked);
            	if(!Config.ECLIPSE)
            	{
                    GuiButton button = (GuiButton)controlList.get(1);
            		if(te.locked)
            		{
            			NetworkHelper.initiateClientTileEntityEvent(te, 1);
            	        button.displayString = getLock(false);
            			
            		} else {
            			NetworkHelper.initiateClientTileEntityEvent(te, 0);	
            	        button.displayString = getLock(true);
            		}
            	}
            	
                break;
            case 2: 
//            	this.entityplayer.openGui(ModHsbCore.instance, GuiHandler.GUI_LOCKTERMINAL_OPTIONS, te.worldObj, te.xCoord, te.yCoord, te.zCoord);
            	if(!Config.ECLIPSE)
            	{
            		NetworkHelper.initiateClientTileEntityEvent(te, -1);
            	}
            	break;
        }
        //button 0 - 9
        if((guibutton.id >= this.buttonIdStart) && (guibutton.id < (this.buttonIdStart + this.maxButtons)))
        {
    		//button : 0 - 9
    		//events: -3 - -12
    		NetworkHelper.initiateClientTileEntityEvent(te, ((guibutton.id-this.buttonIdStart) + 3) * (-1));
    		te.getUpgrade(guibutton.id-this.buttonIdStart).onButtonClicked(te, entityplayer, guibutton.id - this.buttonIdStart);
    		te.updateUpgrades();
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
        String energyBar=te.energyStored + "EU/" + (te.defaultEnergyStorage + te.extraStorage) + "EU";
        this.drawString(this.fontRenderer, energyBar, xPos + 105 - this.fontRenderer.getStringWidth(energyBar) / 2, yPos + 118, 10526880);
        
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
