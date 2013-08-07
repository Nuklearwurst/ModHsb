package hsb.upgrade.terminal;

import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;

public class UpgradeSecurity extends UpgradeHsbTerminal{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		te.securityLevel = this.count;
	}

	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_SECURITY;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		
	}

}
