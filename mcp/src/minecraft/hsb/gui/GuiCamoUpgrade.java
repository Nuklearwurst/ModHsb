package hsb.gui;

import ic2.api.network.NetworkHelper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hsb.ContainerLockTerminal;
import hsb.ContainerLockTerminalOptions;
import hsb.ModHsb;
import hsb.config.Config;
import hsb.network.PacketItemUpdate;
import hsb.network.PacketTerminalUpdate;
import hsb.tileentitys.TileEntityLockTerminal;

public class GuiCamoUpgrade extends GuiContainer
{
	protected int xSize;
    protected int ySize;
    protected String name = "HSB Lock Options";
    private EntityPlayer entityplayer;
    private int updateCounter;
    private int xPos;
    private int yPos;
    private Container container;
    TileEntityLockTerminal te;
    private boolean active;

    public GuiCamoUpgrade(TileEntityLockTerminal te, Container container, EntityPlayer entityplayer)
    {
        super(container);
        this.container = container;
        this.entityplayer = entityplayer;
        xSize = 228;
        ySize = 222;
        this.te = te;
        int id = te.getUpgradeId("Camoflague");
        if(id != -1)
        {
        	active = te.upgradeActive[id];
        } else {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
    }
    public void initGui()
    {
        super.initGui();
        xPos = width / 2 - xSize / 2;
        yPos = height / 2 - ySize / 2;
        this.controlList.clear();
        
        this.controlList.add(new GuiButton(1, xPos + 70, yPos + 20, 60, 20, active ? "Deactivate" : "Activate"));
        
        this.controlList.add(new GuiButton(2, xPos + xSize / 2 - 20, yPos + 115, 40, 20, "back"));       
        this.controlList.add(new GuiButton(0, xPos - 22, yPos- -4, 20, 20, "X"));

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
            	NetworkHelper.initiateClientTileEntityEvent(te, -20);
            	break;
            case 2:
            	NetworkHelper.initiateClientTileEntityEvent(te, -2);
            	break;
        }
        super.actionPerformed(guibutton);
    }

    public void drawScreen(int par1, int par2, float par3)
    {
    	super.drawScreen(par1, par2, par3);
//        this.drawString(this.fontRenderer, "Port:", xPos + xSize / 2 - this.fontRenderer.getStringWidth("H") / 2,yPos + 35, 10526880);
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
		int i = mc.renderEngine.getTexture("/hsb/textures/GuiUpgradeCamo.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
		
	}
	@Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
}
