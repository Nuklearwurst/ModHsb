package hsb.client.gui;

import hsb.configuration.Settings;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.network.packet.PacketPasswordUpdate;
import hsb.tileentity.TileEntityHsbTerminal;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;
import nwcore.network.NetworkManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiHsbTerminalOptions extends GuiContainer
{
    private GuiTextField textField;
    protected String name = Strings.CONTAINER_TERMINAL_OPTIONS_NAME;
    protected TileEntityHsbTerminal te;
    private int xPos;
    private int yPos;
    private int port;
    
    private int lastPassLength = 0;
    
    EntityPlayer entityplayer;


    public GuiHsbTerminalOptions(TileEntityHsbTerminal te, Container container, EntityPlayer entityplayer)
    {
        super(container);
        this.te = te;
        xSize = 228;
        ySize = 222;
        port = te.getPort();
        lastPassLength = te.passLength;
        this.entityplayer = entityplayer;
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
        mc.func_110434_K().func_110577_a(Textures.GUI_TERMINAL_OPTIONS);
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
	}
    
    

    @Override
	public void drawScreen(int par1, int par2, float par3)
    {
    	super.drawScreen(par1, par2, par3);
    	String sPort = StatCollector.translateToLocal(Strings.GUI_PORT);
    	String sPass = StatCollector.translateToLocal(Strings.GUI_PASSWORD);
        this.drawString(this.fontRenderer, sPort, xPos + xSize / 2 - this.fontRenderer.getStringWidth(sPort) / 2,yPos + 35, 10526880);
        this.drawString(this.fontRenderer, sPass, xPos + xSize / 2 - this.fontRenderer.getStringWidth(sPass) / 2, yPos + 75, 10526880);
        this.drawString(this.fontRenderer, String.valueOf(port), xPos + xSize / 2 - this.fontRenderer.getStringWidth(String.valueOf(port)) / 2, yPos + 55, 10526880);
        this.textField.drawTextBox();
        
    	String text = this.textField.getText();
    	if(text.length() > te.passLength)
    	{
            this.drawString(this.fontRenderer, StatCollector.translateToLocal(Strings.MULTI_TOOL_ERROR),xPos + xSize / 2 - 70, yPos + 115, 0xFF0000);
    	}
        
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
        
        this.buttonList.add(new GuiButton(1, xPos + xSize / 2 - 40, yPos + 50, 20, 20, "-"));
        this.buttonList.add(new GuiButton(2, xPos + xSize / 2 + 20, yPos + 50, 20, 20, "+"));
        this.buttonList.add(new GuiButton(3, xPos + xSize / 2 - 70, yPos + 50, 30, 20, "-10"));
        this.buttonList.add(new GuiButton(4, xPos + xSize / 2 + 40, yPos + 50, 30, 20, "+10"));
        
        this.buttonList.add(new GuiButton(5, xPos + xSize / 2 - 20, yPos + 115, 40, 20, StatCollector.translateToLocal(Strings.GUI_BACK)));
        
        this.textField = new GuiTextField(this.fontRenderer,  xPos + xSize / 2 - 70, yPos + 90, 140, 20);
        this.textField.setMaxStringLength(te.passLength);
        this.textField.setFocused(false);
        this.textField.setText(te.getPass());
        this.buttonList.add(new GuiButton(0, xPos - 22, yPos- -4, 20, 20, "X"));

    }
	@Override
	protected void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);
        if (par2 == 1 && !textField.isFocused())
        {
            this.mc.thePlayer.closeScreen();
        }
        this.textField.textboxKeyTyped(par1, par2);
        this.te.setPass(textField.getText());
    }
	@Override
	protected void mouseClicked(int x, int y, int par3)
    {
        this.textField.mouseClicked(x, y, par3);
        //Clear TextField when rightclicked
        int xPos = this.xPos + xSize / 2 - 70;
        int yPos = this.yPos + + 90;
        boolean var4 = x >= xPos && x < xPos + 140 && y >= yPos && y < yPos + 20;

        if (par3 == 1 && var4)
        {
            textField.setText("");
        }

        super.mouseClicked(x, y, par3);
    }
	@Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        
    	String text = this.textField.getText();
    	if(text.length() > te.passLength)
    	{
    		this.textField.setMaxStringLength(te.passLength);
    	}
    	PacketPasswordUpdate packet = new PacketPasswordUpdate(te, textField.getText());
    	((EntityClientPlayerMP)entityplayer).sendQueue.addToSendQueue(packet.getPacket());  
        super.onGuiClosed();
    }
    //update port
    private void updatePort(int number)
    {
        this.port += number;

        if (this.port < 0)
        {
            this.port = Settings.maxPort + this.port + 1;
        }

        if (this.port > Settings.maxPort)
        {
            this.port -= Settings.maxPort;
        }
        //setting port on tileentity
        te.setPort(this.port);
        //sending new port to server
        NetworkManager.getInstance().initiateClientTileEntityEvent(te, port);
    }

    @Override
    public void updateScreen()
    {
    	super.updateScreen();
        this.textField.updateCursorCounter();
        if(te.passLength > lastPassLength)
        {
//        	String text = this.textField.getText();
//        	if(text.length() > te.passLength)
//        	{
//        		this.textField.setText("error");
//        	}
        	this.textField.setMaxStringLength(te.passLength);
        	this.textField.setText(te.getPass());
        	lastPassLength = te.passLength;

        }
    }
}
