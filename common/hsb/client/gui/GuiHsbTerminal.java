package hsb.client.gui;

import hsb.core.helper.HsbLog;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.network.NetworkManager;
import hsb.network.packet.PacketRequestButtons;
import hsb.tileentity.TileEntityHsbTerminal;

import java.util.List;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiHsbTerminal extends GuiContainer
{
    protected TileEntityHsbTerminal te;
    private int xPos;
    private int yPos;
    
    //Upgrade button constants
    private static final int maxButtons = 10;
    private static final int buttonIdStart = 3;
    
    private int lastButtonLength = 0;
    
    EntityPlayer player;


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
        mc.renderEngine.bindTexture(Textures.GUI_TERMINAL);
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
        int energy = this.te.getEnergyScaled(100);
        this.drawTexturedModalRect(xPos + 55, yPos + 115, 26, 222, energy, 14);
		
	}

    @Override
	public void drawScreen(int par1, int par2, float par3)
    {
    	super.drawScreen(par1, par2, par3);
        String energyBar=te.energyStored + "EU/" + (te.maxEnergyStorage) + "EU";
        this.drawString(this.fontRenderer, energyBar, xPos + 105 - this.fontRenderer.getStringWidth(energyBar) / 2, yPos + 118, 10526880);
        ((GuiButton)buttonList.get(1)).displayString = getLock(te.locked);
        
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
            sLock = StatCollector.translateToLocal(Strings.GUI_UNLOCK);
        }
        else
        {
            sLock = StatCollector.translateToLocal(Strings.GUI_LOCK);
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

        //Normal width = 52px
        //TODO Design
        int bPosX = 4;
        int bPosY = 4;
        
        
        this.buttonList.add(new GuiButton(0, xPos - 22, yPos- -4, 20, 20, "X"));
        this.buttonList.add(new GuiButton(1, xPos + 175, yPos + 105, 40, 20, getLock(te.locked)));
        this.buttonList.add(new GuiButton(2, xPos + 175, yPos + 85, 40, 20, StatCollector.translateToLocal(Strings.GUI_OPTIONS)));

        //TODO better
        int buttonSize = (int) Math.floor((this.xSize - 6) / (maxButtons / 2));
        List<String> buttons = te.getButtons();
//        HsbLog.debug("gui button size: " + buttons.size());
        if(buttons != null) {
        	int i = 0;
	        for(String s : buttons)
	        {
	        	if(i>4)
	        	{
	        		bPosY = 23; // one time bPosY + 20
	        	}
	        	bPosX= (i % 5) * buttonSize + 4;
	        	if(buttons.get(i) != null && buttons.get(i) != "" && buttons.get(i).length() > 0)
	        	{
	        		this.buttonList.add(new GuiButton(i + GuiHsbTerminal.buttonIdStart, xPos + bPosX, yPos + bPosY, buttonSize, 20, s));
	        	}
	        	i++;
	        }
	        if(buttons.size() == 0)
	        {
		    	PacketRequestButtons packet = new PacketRequestButtons(te);
		    	((EntityClientPlayerMP)player).sendQueue.addToSendQueue(packet.getPacket());
	        }
        } else {
        	HsbLog.severe("buttons = null!");
        }
    }
	
    @Override
    public void updateScreen()
    {
    	super.updateScreen();
    	if(te.getButtons() != null && te.getButtons().size() > this.lastButtonLength)
    	{
    		this.initGui();
    		this.lastButtonLength = te.getButtons().size();
    	}
    }
}
