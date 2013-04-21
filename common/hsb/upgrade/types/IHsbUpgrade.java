package hsb.upgrade.types;

import hsb.tileentity.TileEntityHsbTerminal;


public interface IHsbUpgrade {
	/**
	 * called whenever an upgrade needs to update
	 * 
	 * TODO support other terminals
	 * @param te
	 */
	public void updateUpgrade(TileEntityHsbTerminal te);
	
	/**
	 * called when inventory changed
	 */
	public void setCount(int i);
	public int getCount();
	
	public String getUniqueId();
	
	public void addInformation(IHsbUpgrade upgrade);
}
