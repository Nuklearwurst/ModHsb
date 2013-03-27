package hsb.network;

import net.minecraft.tileentity.TileEntity;
import hsb.config.Config;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.NetworkHelper;

/**
 * for network-code
 * currently only used for ic2
 * @author Nuklearwurst
 *
 */
public class NetworkManager {

	/**
	 * workaround if ic2 is not installed
	 * @param te
	 */
	public static void requestInitialData(INetworkDataProvider te) {
		if(Config.ic2Available)
		{
			NetworkHelper.requestInitialData(te);
		} else {
			
		}
	}
	
	public static void updateTileEntityField(TileEntity te, String s) {
		if(Config.ic2Available)
		{
			NetworkHelper.updateTileEntityField(te, s);
		} else {
			
		}
	}
	
	public static void initiateClientTileEntityEvent(TileEntity te, int ev) {
		if(Config.ic2Available) 
		{
			NetworkHelper.initiateClientTileEntityEvent(te, ev);
		} else {
			
		}
	}
	
	
	
	
}
