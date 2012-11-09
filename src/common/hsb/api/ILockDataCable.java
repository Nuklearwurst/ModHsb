package hsb.api;

import hsb.TileEntityLockTerminal;

public interface ILockDataCable {

	/**
	 * transfer a lock signal
	 * 
	 * @return success
	 */
	boolean transferSignal(int side, TileEntityLockTerminal te, boolean lock);
	
	boolean connectsTo(int side);

}
