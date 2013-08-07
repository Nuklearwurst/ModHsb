package hsb.upgrade.terminal;

import hsb.ModHsb;
import hsb.configuration.Settings;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;

public class UpgradePassword extends UpgradeHsbTerminal{

	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		te.passLength = Settings.defaultPassLength + this.count;
		ModHsb.logger.debug("setting passlength: " + te.passLength );
		
	}

	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_PASSWORD;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {}

}
