package hsb.upgrade.types;

import hsb.tileentity.TileEntityHsbTerminal;
import net.minecraft.entity.player.EntityPlayer;

public interface IUpgradeButton extends IHsbUpgrade{
	public void onButtonClicked(EntityPlayer player, TileEntityHsbTerminal te);
}
