package hsb.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.NetworkRegistry;

import hsb.ModHsb;
import hsb.config.Config;
import hsb.network.PacketItemUpdate;
import hsb.tileentitys.TileEntityHsb;

public class GuiLockMonitorDoor extends GuiScreen {

	
    protected int xSize;
    protected int ySize;
    protected ItemStack itemstack;
    protected NBTTagCompound nbttag;
    protected int port = 0;
    int xPos;
    int yPos;
    private World world;
    private int x;
    private int y;
    private int z;
    private EntityPlayer player;
    
	public GuiLockMonitorDoor(Object placer, int x, int y, int z, World world, EntityPlayer player) {
		xSize=176;
		ySize=105;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.player = player;
        nbttag = itemstack.getTagCompound();
	}
	@Override
    public void initGui() {
        xPos = width / 2 - xSize / 2;
        yPos = height / 2 - ySize / 2;
        controlList.clear();
        controlList.add(new GuiButton(0, xPos + xSize / 2 + 40, yPos + 80, 40, 20, "Done"));
        super.initGui();
    	
    }
	 protected void actionPerformed(GuiButton guibutton)
	    {
	        switch (guibutton.id)
	        {

	            case 0:
	                this.mc.displayGuiScreen(null);
	                break;
	        }
	    }
	    public void updateScreen()
	    {
	        updateCounter++;
	    }
	    public void onGuiClosed()
	    {
	        super.onGuiClosed();
	    }
	    private void updateNBTTag() {
	    }
	    
	    public void drawScreen(int i, int j, float f)
	    {
	        drawDefaultBackground();
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        drawGuiContainerBackgroundLayer(f);
	        GL11.glPushMatrix();
	        GL11.glTranslatef(k, l, 0.0F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        RenderHelper.disableStandardItemLighting();
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        drawGuiContainerForegroundLayer();
	        GL11.glPopMatrix();
	        super.drawScreen(i, j, f);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        drawStringBorder(xPos + xSize / 2 - this.fontRenderer.getStringWidth(port + "") / 2, yPos + ySize / 2 - 4, xPos + xSize / 2 + this.fontRenderer.getStringWidth(port + "") / 2);
	        this.fontRenderer.drawString(port + "", xPos + xSize / 2 - this.fontRenderer.getStringWidth(port + "") / 2, yPos + ySize / 2 - 4, 4210752);
	        GL11.glEnable(GL11.GL_LIGHTING);
	    }

	    protected void drawGuiContainerForegroundLayer()
	    {
//	        drawStringBorder(this.xSize / 2 - this.fontRenderer.getStringWidth(name + " " + mode) / 2, 6, this.xSize / 2 + this.fontRenderer.getStringWidth(name + " " + mode) / 2);
//	        this.fontRenderer.drawString(name + " " + mode, this.xSize / 2 - this.fontRenderer.getStringWidth(name + " " + mode) / 2, 6, 4210752);

	    }

	    protected void drawGuiContainerBackgroundLayer(float f)
	    {
	        int i = mc.renderEngine.getTexture("/hsb/textures/GuiBlockPlacer.png");
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        mc.renderEngine.bindTexture(i);
	        int j = (width - xSize) / 2;
	        int k = (height - ySize) / 2;
	        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
	    }
	    protected void drawStringBorder(int x1, int y1, int x2)
	    {
	        drawRect(x1 - 3, y1 - 3, x2 + 3, y1 + 10, -16777216);
	        drawRect(x1 - 2, y1 - 2, x2 + 2, y1 + 9, -1);
	    }
	    private int updateCounter;
	}

