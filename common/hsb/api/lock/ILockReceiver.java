package hsb.api.lock;

public interface ILockReceiver {

	/**
	 * @param side
	 * @return does the TileEntity connects to the given side?
	 */
	boolean connectsTo(int side);
	
	/**
	 * called when signal arrives
	 * 
	 * @param side 
	 * @param te Terminal the signal comes from (can be null)
	 * @param value
	 * @param pass
	 * @param port
	 * 
	 * @return success
	 */
	public boolean receiveSignal(int side, ILockTerminal te, boolean value, String pass, int port);


}
