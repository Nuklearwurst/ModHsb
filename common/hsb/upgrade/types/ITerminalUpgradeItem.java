package hsb.upgrade.types;


public interface ITerminalUpgradeItem {
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
	public IUpgradeHsbTerminal getUpgrade(int meta);

}
