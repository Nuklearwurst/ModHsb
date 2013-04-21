package hsb.upgrade.types;


public interface IMachineUpgradeItem {
	/**
	 * 
	 * @param meta
	 * @return the name the upgrade was registered with
	 */
	public String getUniqueId(int meta);
	/**
	 * 
	 * @param meta of the Item
	 * @return a new instance of the upgrade linked to this Item 
	 */
	public IUpgradeHsbMachine getUpgrade(int meta);

}
