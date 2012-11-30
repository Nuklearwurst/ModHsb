package hsb.api;

import hsb.ILockUpgrade;
import net.minecraft.src.Item;

public class UpgradeHsb{
	
	public UpgradeHsb(ILockUpgrade item){
		this.item = item;
	}
	
	public ILockUpgrade item;
	
	public static final String TESLA= "tesla";
	
	//is active?
	public boolean active = false;
	//number of Upgrades
	public int number = 0;
	

}
