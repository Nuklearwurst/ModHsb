package hsb.upgrade.machine;

import hsb.configuration.Settings;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;

public class UpgradeEnergyStorage extends UpgradeHsbMachine{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		te.increaseMaxStorage(Settings.UPGRADE_ENERGY_STORAGE * count);
	}
	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_STORAGE;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		
	}

}
