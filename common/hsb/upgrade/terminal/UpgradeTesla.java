package hsb.upgrade.terminal;

import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;

public class UpgradeTesla extends UpgradeHsbTerminal{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {}

	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_TESLA;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		// TODO Auto-generated method stub
		
	}

}
