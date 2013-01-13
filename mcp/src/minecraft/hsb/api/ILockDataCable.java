package hsb.api;

import hsb.tileentitys.TileEntityLockTerminal;

public interface ILockDataCable {

	/**
	 * transfers a lock signal, called when a block wants to transfer a signal to that tileentity
	 * @param side
	 * @param te the TileEntity the signal belongs to, can be null if already broken
	 * @param lock lock/unlock
	 * @param pass password
	 * @param port
	 * @return success
	 */
	boolean transferSignal(int side, TileEntityLockTerminal te, boolean lock, String pass, int port);
	/**
	 * @param side
	 * @return does the TileEntity connects to the given side?
	 */
	boolean connectsTo(int side);

}
