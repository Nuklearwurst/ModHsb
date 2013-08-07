package hsb.upgrade.terminal;

import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;

public class UpgradeDummy extends UpgradeHsbTerminal{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		
	}

	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_DUMMY;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		
	}

}
