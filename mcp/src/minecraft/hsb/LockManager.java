package hsb;

import hsb.api.lock.ILockReceiver;
import hsb.api.lock.ILockTerminal;
import hsb.config.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;

public class LockManager {

	/**
	 * function to transfer a locksignal in al directions from the given TileEntity
	 * 
	 * @param sender TileEntity the signal is send from
	 * @param te Terminal which sent the signal (can be null)
	 * @param lock value
	 * @param pass password
	 * @param port
	 * @param ignoreSide side that is ignored, if <0 or >5 no side is ignored
	 */
	public static boolean tranferSignal(TileEntity sender, ILockTerminal te, boolean lock, String pass, int port, int ignoreSide) {
		for(int i = 0; i<6; i++)
		{
			//ignore Side
			if(i == ignoreSide)
			{
				Config.logDebug("ignored Side: " + i);
				continue;
			}
			//get TileEntity on the current side
			TileEntity tile = sender.worldObj.getBlockTileEntity(
					sender.xCoord+Facing.offsetsXForSide[i],
					sender.yCoord+Facing.offsetsYForSide[i],
					sender.zCoord+Facing.offsetsZForSide[i]);
			
			if(tile != null && tile instanceof ILockReceiver)
			{
				//send signal to that tileentity
				if(((ILockReceiver) tile).connectsTo(Facing.faceToSide[i])) {
					((ILockReceiver) tile).receiveSignal(Facing.faceToSide[i], te, lock, pass, port);
				}
			}
		}
		return true;
	}
}
