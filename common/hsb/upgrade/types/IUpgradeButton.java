package hsb.upgrade.types;

import hsb.tileentity.TileEntityHsbTerminal;
import net.minecraft.entity.player.EntityPlayer;

public interface IUpgradeButton extends IHsbUpgrade{
	/**
	 * the unlocalized name
	 * @return text displayed on the button
	 */
	public String getButton();
	public void onButtonClicked(EntityPlayer player, TileEntityHsbTerminal te);
}
