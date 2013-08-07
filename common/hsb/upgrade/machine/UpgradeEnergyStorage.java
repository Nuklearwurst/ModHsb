package hsb.upgrade.machine;

import hsb.configuration.Settings;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;

public class UpgradeEnergyStorage extends UpgradeHsbMachine{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		float storage = te.getMaxEnergy();
		storage += Settings.UPGRADE_ENERGY_STORAGE * count;
		te.setMaxStorage(storage);
	}
	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_STORAGE;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		
	}

}
