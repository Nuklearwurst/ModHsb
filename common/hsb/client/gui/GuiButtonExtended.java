package hsb.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

/**
 * Button with icon that may depending on its Size display text And icon or only one of them
 * @author Nuklearwurst
 *
 */
public class GuiButtonExtended extends GuiButton {

	private Icon icon;
	
	public static final int xOffset = 2;
	public static final int iconButtonSpace = 1;

	public GuiButtonExtended(int id, int x, int y, String text, Icon icon) {
		this(id, x, y, 200, 20, text, icon);
	}
	
	public GuiButtonExtended(int id, int x, int y, int width, int height, String text, Icon icon) {
		super(id, x, y, width, height, text);
		this.icon = icon;
	}
	public static int getSuggestedWidth(String text, FontRenderer font) {
//		ModHsb.logger.debug("str width: " + font.getStringWidth(text));
		return xOffset * 2 + iconButtonSpace + 16 + font.getStringWidth(text);
	}

	@Override
	public void drawButton(Minecraft mc, int cursorX, int cursorY) {
		if (this.drawButton)
		{
			FontRenderer fontrenderer = mc.fontRenderer;
			
			/**
			 * 0 - draw all
			 * 1 - draw text only
			 * 2 - draw icon only
			 * 3 - empty button
			 */
			byte drawMode = 0;
			
			if(icon == null && (displayString == null || displayString.isEmpty()) ) {
				drawMode = 3;
			} else if(icon == null) {
				drawMode = 1;
			} else if(displayString == null || displayString.isEmpty()) {
				drawMode = 2;
			} else {
				int strWidth = fontrenderer.getStringWidth(displayString);
				
				if(strWidth > this.width + xOffset * 2) {
					drawMode = 2;
				} else if( (xOffset * 2 + 16 + iconButtonSpace + strWidth) > this.width) {
					drawMode = 1;
				} else {
					drawMode = 0;
				}
			}
			
			
			
			
			//bind Button Texture
			mc.getTextureManager().bindTexture(buttonTextures);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			//is mouse over button?
			this.field_82253_i = cursorX >= this.xPosition && cursorY >= this.yPosition && cursorX < this.xPosition + this.width && cursorY < this.yPosition + this.height;
			//get hover state
			int k = this.getHoverState(this.field_82253_i);
			//draw button texture
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
			//mouse dragged
			this.mouseDragged(mc, cursorX, cursorY);
			
			if(drawMode == 0 || drawMode == 2) {
				mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
				if(drawMode == 0) {
					//draw icon
					this.drawTexturedModelRectFromIcon(this.xPosition + xOffset, this.yPosition + (this.height - 16) / 2, icon, 16, 16);
				} else {
					//draw centered
					this.drawTexturedModelRectFromIcon(this.xPosition + this.width / 2, this.yPosition + (this.height - 16) / 2, icon, 16, 16);
				}
			}
			
			if(drawMode == 0 || drawMode == 1) {
				//draw Button Text
				int l = 14737632;

				if (!this.enabled)
				{
					l = -6250336;
				}
				else if (this.field_82253_i)
				{
					l = 16777120;
				}
				if(drawMode == 0) {
					//draw with offset
					this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + (this.width / 2) + 8 + iconButtonSpace, this.yPosition + (this.height - 8) / 2, l);
				} else {
					//draw centered
					this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
				}
			}
		}
	}
}
