package hsb.client.gui;

import hsb.ModHsb;
import hsb.lib.GuiIds;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.terminal.UpgradeCamoflage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import nwcore.network.NetworkManager;

import org.lwjgl.opengl.GL11;

public class GuiCamoUpgrade extends GuiContainer
{
    private int xPos;
    private int yPos;
    TileEntityHsbTerminal te;
    private boolean active;

    public GuiCamoUpgrade(TileEntityHsbTerminal te, Container container, EntityPlayer entityplayer)
    {
    	super(container);
        xSize = 228;
        ySize = 222;
        this.te = te;
        UpgradeCamoflage upgrade = (UpgradeCamoflage) te.getUpgrade(UpgradeRegistry.ID_UPGRADE_CAMO);
        if(upgrade != null)
        {
        	active = upgrade.active;
        } else {
            ModHsb.logger.severe("UpgradeNotFound!!");
            entityplayer.openGui(ModHsb.instance, GuiIds.GUI_LOCKTERMINAL, te.worldObj, te.xCoord, te.yCoord, te.zCoord);
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
            	NetworkManager.getInstance().initiateClientTileEntityEvent(te, TileEntityHsbTerminal.EVENT_CAMOUPGRADE);
            	break;
            case 2:
            	NetworkManager.getInstance().initiateClientTileEntityEvent(te, TileEntityHsbTerminal.EVENT_GUI_TERMINAL);
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
        mc.getTextureManager().bindTexture(Textures.GUI_UPGRADE_CAMO);
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
		
	}

    @Override
	public void drawScreen(int par1, int par2, float par3)
    {
    	super.drawScreen(par1, par2, par3);
    	((GuiButton)buttonList.get(0)).displayString = Strings.translate( active ? Strings.GUI_ACTIVATE : Strings.GUI_DISABLE);
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
        
        this.buttonList.add(new GuiButton(1, xPos + 70, yPos + 20, 60, 20, Strings.translate( active ? Strings.GUI_ACTIVATE : Strings.GUI_DISABLE)));
        
        this.buttonList.add(new GuiButton(2, xPos + xSize / 2 - 20, yPos + 115, 40, 20, Strings.translate(Strings.GUI_BACK)));       
        this.buttonList.add(new GuiButton(0, xPos - 22, yPos- -4, 20, 20, "X"));

    }
	@Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
}
