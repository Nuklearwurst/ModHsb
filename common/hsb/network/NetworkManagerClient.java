package hsb.network;

import hsb.config.Config;
import hsb.network.packet.PacketClientTileEvent;
import hsb.network.packet.PacketRequestData;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.NetworkHelper;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;

public class NetworkManagerClient extends NetworkManager{

	public void initiateClientTileEntityEvent(TileEntity te, int ev) {
		if(Config.ic2Available) 
		{
			NetworkHelper.initiateClientTileEntityEvent(te, ev);
		} else {
			PacketDispatcher.sendPacketToServer(new PacketClientTileEvent(ev, te).getPacket());
		}
			
	}
	
	 
	public void requestInitialData(INetworkDataProvider te) {
		if(Config.ic2Available)
		{
			NetworkHelper.requestInitialData(te);
		} else {
			PacketDispatcher.sendPacketToServer(new PacketRequestData((TileEntity) te).getPacket());
		}
	}
}
