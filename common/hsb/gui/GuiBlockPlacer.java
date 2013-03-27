package hsb.gui;

import hsb.network.NetworkManager;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hsb.config.Config;
import hsb.network.packet.PacketItemUpdate;
import hsb.tileentitys.TileEntityHsb;

public class GuiBlockPlacer extends GuiScreen {

	
    protected int xSize;
    protected int ySize;
    protected ItemStack itemstack;
    private TileEntityHsb te;
    protected NBTTagCompound nbttag;
    protected int port = 0;
    protected String name;
    int xPos;
    int yPos;
    private EntityPlayer player;
    
    /*
     * Place: 0
     * Remove: 1
     */
    private int mode = 0;
//    private boolean placeMode=true;
    private boolean item;
    
	public GuiBlockPlacer(Object placer, int x, int y, int z, World world, EntityPlayer player) {
		xSize=176;
		ySize=105;
		if(placer instanceof ItemStack)
		{
			this.itemstack = (ItemStack)placer;
			this.item = true;
		} else if(placer instanceof TileEntityHsb)
		{
			this.item = false;
			this.te = (TileEntityHsb)placer;
		} else {

		}
        this.player = player;
        if(item)
        {
	        nbttag = itemstack.getTagCompound();
	        if(nbttag == null) {
	        	Config.logDebug("nbttag == null Constructor");	        	
	        }
	        mode = nbttag.getInteger("mode");
	        port = nbttag.getInteger("port");
//	        name = itemstack.getItem().getItemDisplayName(itemstack);
	        name = "Block Placer";
        } else {
        	mode = 0;
        	port = 0;
        	name = "Set Port:";
        }
	}
	@SuppressWarnings("unchecked")
	@Override
    public void initGui() {
        xPos = width / 2 - xSize / 2;
        yPos = height / 2 - ySize / 2;
        controlList.clear();
        controlList.add(new GuiButton(0, xPos + xSize / 2 - 30, yPos + ySize / 2 - 10, 20 , 20 , "-1"));
        controlList.add(new GuiButton(1, xPos + xSize / 2 + 10, yPos + ySize / 2 - 10, 20, 20, "+1"));
        controlList.add(new GuiButton(2, xPos + xSize / 2 - 50, yPos + ySize / 2 - 10, 20 , 20 , "-10"));
        controlList.add(new GuiButton(3, xPos + xSize / 2 + 30, yPos + ySize / 2 - 10, 20, 20, "+10"));
        controlList.add(new GuiButton(4, xPos + xSize / 2 + 40, yPos + 80, 40, 20, "Done"));
        if(item)
        	controlList.add(new GuiButton(5, xPos + xSize / 2 - 10, yPos + 80, 40, 20, "Mode"));
        super.initGui();
    	
    }
	 protected void actionPerformed(GuiButton guibutton)
	    {
	        switch (guibutton.id)
	        {
	            case 0:
	                this.updatePort(-1);
	                break;

	            case 1:
	                this.updatePort(1);
	                break;

	            case 2:
	                this.updatePort(-10);
	                break;

	            case 3:
	                this.updatePort(10);
	                break;

	            case 4:
	                this.mc.displayGuiScreen(null);
	                break;
	                
	            case 5:
	            	//See Declaration
	                this.mode++;
	                if(mode > 2)
	                {
	                	mode = 0;
	                }
	                break;
	        }
	    }
	 //works for now
	    public void updatePort(int number)
	    {
	        this.port += number;

	        if (this.port < 0)
	        {
	            this.port = Config.maxPort + this.port;
	        }

	        if (this.port >= Config.maxPort)
	        {
	            this.port -= Config.maxPort;
	        }
	    }
	    public void updateScreen()
	    {
	    }
	    public void onGuiClosed()
	    {
	    	if(item)
	    	{
	    		updateNBTTag();
	    	} else {
	    		te.setPort(this.port);
	    		NetworkManager.initiateClientTileEntityEvent(te, port);
	    	}
	        super.onGuiClosed();
	    }
	    private void updateNBTTag() {
	    	itemstack.getTagCompound().setInteger("port", port);
	    	itemstack.getTagCompound().setInteger("mode", mode);
	    	//Sending Packet to Server
	    	PacketItemUpdate packetPort = new PacketItemUpdate(this.itemstack.getItem(), "port", port);
	    	((EntityClientPlayerMP)player).sendQueue.addToSendQueue(packetPort.getPacket());    	
	    	PacketItemUpdate packetMode = new PacketItemUpdate(this.itemstack.getItem(), "mode", mode);
	    	((EntityClientPlayerMP)player).sendQueue.addToSendQueue(packetMode.getPacket());  
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
	    	if(item)
	    	{
		    	String modeStr = "Error";
		    	switch(mode) 
		    	{
		    	case 0:
		    		modeStr = "Place";
		    		break;
		    	case 1:
		    		modeStr = "Remove";
		    		break;
		    	case 2:
		    		modeStr = "Wrench";
		    		break;
		    	default:
		    		modeStr = "Error";
		    		break;
		    	}
		        drawStringBorder(this.xSize / 2 - this.fontRenderer.getStringWidth(name + " " + modeStr) / 2, 6, this.xSize / 2 + this.fontRenderer.getStringWidth(name + " " + modeStr) / 2);
		        this.fontRenderer.drawString(name + " " + modeStr, this.xSize / 2 - this.fontRenderer.getStringWidth(name + " " + modeStr) / 2, 6, 4210752);
	    	}
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
	}

