package hsb.lock;

import hsb.core.helper.HsbLog;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;

public class LockManager {
	/**
	 * function to transfer a locksignal in all directions from the given TileEntity
	 * 
	 * @param sender TileEntity the signal is send from
	 * @param te Terminal which sent the signal (can be null)
	 * @param lock value
	 * @param pass password
	 * @param port
	 * @param ignoreSide side that is ignored, if <0 or >5 no side is ignored
	 */
	public static boolean tranferSignal(TileEntity sender, ILockTerminal te, boolean lock, String pass, int port, int ignoreSide) {
		//record success, maybe not needed
		boolean b = false;
		//to all sides
		for(int i = 0; i<6; i++)
		{
			//ignore Side
			if(i == ignoreSide)
			{
				continue;
			}
			if(sender == null) {
				HsbLog.severe("BUG, sender cannot be null (transferSignal, LockManager)");
				return false;
			}
			//get TileEntity on the current side
			TileEntity tile = sender.worldObj.getBlockTileEntity(
					sender.xCoord+Facing.offsetsXForSide[i],
					sender.yCoord+Facing.offsetsYForSide[i],
					sender.zCoord+Facing.offsetsZForSide[i]);
			
			if(tile != null && tile instanceof ILockReceiver)
			{
				//send signal to that tileentity
				if(((ILockReceiver) tile).connectsTo(Facing.oppositeSide[i])) {
					//if successful
					if(((ILockReceiver) tile).receiveSignal(Facing.oppositeSide[i], te, lock, pass, port))
						b = true;
				}
			}
		}
		//return success
		return b;
	}
}
