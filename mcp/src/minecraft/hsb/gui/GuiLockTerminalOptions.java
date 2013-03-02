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

import hsb.network.PacketTerminalUpdate;
import hsb.tileentitys.TileEntityLockTerminal;

public class GuiLockTerminalOptions extends GuiContainer
{
    private GuiTextField textField;
	protected int xSize;
    protected int ySize;
    protected String name = "HSB Lock Options";
    protected TileEntityLockTerminal te;
    private EntityPlayer entityplayer;
    private int xPos;
    private int yPos;
    private int port;
    
    private int lastPassLength = 0;


    public GuiLockTerminalOptions(TileEntityLockTerminal te, Container container, EntityPlayer entityplayer)
    {
        super(container);
        this.te = te;
        this.entityplayer = entityplayer;
        xSize = 228;
        ySize = 222;
        port = te.port;
        lastPassLength = te.extraPassLength;
    }
    @SuppressWarnings("unchecked")
	public void initGui()
    {
        super.initGui();
        xPos = width / 2 - xSize / 2;
        yPos = height / 2 - ySize / 2;
        this.controlList.clear();
        
        this.controlList.add(new GuiButton(1, xPos + xSize / 2 - 40, yPos + 50, 20, 20, "-"));
        this.controlList.add(new GuiButton(2, xPos + xSize / 2 + 20, yPos + 50, 20, 20, "+"));
        this.controlList.add(new GuiButton(3, xPos + xSize / 2 - 70, yPos + 50, 30, 20, "-10"));
        this.controlList.add(new GuiButton(4, xPos + xSize / 2 + 40, yPos + 50, 30, 20, "+10"));
        
        this.controlList.add(new GuiButton(5, xPos + xSize / 2 - 20, yPos + 115, 40, 20, "back"));
        
        this.textField = new GuiTextField(this.fontRenderer,  xPos + xSize / 2 - 70, yPos + 90, 140, 20);
        this.textField.setMaxStringLength(TileEntityLockTerminal.defaultPassLength + te.extraPassLength);
        this.textField.setFocused(false);
        this.textField.setText(te.pass);
        
        

        
        
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
                this.updatePort(-1);
                break;

            case 2:
                this.updatePort(1);
                break;

            case 3:
                this.updatePort(-10);
                break;

            case 4:
                this.updatePort(+10);
                break;
            case 5:
            	NetworkHelper.initiateClientTileEntityEvent(te, -2);
            	break;
        }
        super.actionPerformed(guibutton);
    }
    
    private void updatePort(int number)
    {
        this.port += number;

        if (this.port < 0)
        {
            this.port = TileEntityLockTerminal.maxPort + this.port;
        }

        if (this.port >= TileEntityLockTerminal.maxPort)
        {
            this.port -= TileEntityLockTerminal.maxPort;
        }
    }

    public void drawScreen(int par1, int par2, float par3)
    {
    	super.drawScreen(par1, par2, par3);
        this.drawString(this.fontRenderer, "Port:", xPos + xSize / 2 - this.fontRenderer.getStringWidth("Port:") / 2,yPos + 35, 10526880);
        this.drawString(this.fontRenderer, "Password:", xPos + xSize / 2 - this.fontRenderer.getStringWidth("Password:") / 2, yPos + 75, 10526880);
        this.drawString(this.fontRenderer, String.valueOf(port), xPos + xSize / 2 - this.fontRenderer.getStringWidth(String.valueOf(port)) / 2, yPos + 55, 10526880);
        this.textField.drawTextBox();
        
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
		int i = mc.renderEngine.getTexture("/hsb/textures/GuiLockTerminalOptions.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
		
	}
	@Override
    public void updateScreen()
    {
    	super.updateScreen();
//        updateCounter++;
        this.textField.updateCursorCounter();
        if(te.extraPassLength != lastPassLength)
        {
        	this.textField.setMaxStringLength(te.extraPassLength +TileEntityLockTerminal.defaultPassLength);
        	lastPassLength = te.extraPassLength;
        	String text = this.textField.getText();
        	if(text.length() > this.textField.getMaxStringLength())
        	{
        		this.textField.setText("error");
        	}
        }
    }
	@Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        te.pass = textField.getText();
        te.port = this.port;
    	PacketTerminalUpdate packet = new PacketTerminalUpdate(te, textField.getText(), this.port);
    	((EntityClientPlayerMP)entityplayer).sendQueue.addToSendQueue(packet.getPacket());  
        super.onGuiClosed();
    }
	protected void mouseClicked(int par1, int par2, int par3)
    {
        this.textField.mouseClicked(par1, par2, par3);
        //Clear TextField when rightclicked
        int xPos = this.xPos + xSize / 2 - 70;
        int yPos = this.yPos + ySize - 50;
        boolean var4 = par1 >= xPos && par1 < xPos + 140 && par2 >= yPos && par2 < yPos + 20;

        if (par3 == 1 && var4)
        {
            textField.setText("");
        }

        super.mouseClicked(par1, par2, par3);
    }

    protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);
        if (par2 == 1)
        {
            this.mc.thePlayer.closeScreen();
        }
        this.textField.textboxKeyTyped(par1, par2);
    }
}
