package hsb.upgrade.terminal;

import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.upgrade.UpgradeHsb;
import hsb.upgrade.types.IUpgradeHsbTerminal;

public abstract class UpgradeHsbTerminal extends UpgradeHsb
	implements IUpgradeHsbTerminal
{
	@Override
	public float getEnergyUse(int blocksInUse, ILockTerminal terminal, ILockable tile) { return 0;};
}
