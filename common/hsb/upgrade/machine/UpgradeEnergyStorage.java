package hsb.upgrade.machine;

import hsb.core.addons.PluginIC2;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;

public class UpgradeEnergyStorage extends UpgradeHsbMachine{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		te.maxEnergyStorage += PluginIC2.UPGRADE_ENERGY_STORAGE * count;
		
	}
	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_STORAGE;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		// TODO Auto-generated method stub
		
	}

}
