package hsb.upgrade;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ButtonInfo {
	
	private String buttonName;
	private Icon icon;
	private String iconName;
	
	ButtonInfo(String text, String icon) {
		buttonName = text;
		this.iconName = icon;
	}
	
	public String getText() {
		return buttonName;
	}
	@SideOnly(Side.CLIENT)
	public Icon getIcon() {
		return icon;
	}
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icon = reg.registerIcon(iconName);
	}
}