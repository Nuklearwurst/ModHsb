package hsb.upgrade.terminal;

import net.minecraft.util.StatCollector;
import hsb.lib.Strings;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.IUpgradeButton;

public class UpgradeTesla extends UpgradeHsbTerminal
	implements IUpgradeButton
{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		te.tesla = this.count;//for now
	}

	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_TESLA;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getButton() {
		return StatCollector.translateToLocal(Strings.UPGRADE_GUI_BUTTON_TESLA);
	}

}
