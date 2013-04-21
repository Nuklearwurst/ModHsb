package hsb.upgrade;

import hsb.upgrade.types.IHsbUpgrade;

public abstract class UpgradeHsb 
	implements IHsbUpgrade
{
	protected int count = 0;

	@Override
	public void setCount(int i) {
		count = i;		
	}

	@Override
	public int getCount() {
		return count;
	}
	


}
