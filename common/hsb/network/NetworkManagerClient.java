package hsb.network;

import hsb.configuration.Settings;
import hsb.network.packet.tileentity.PacketClientTileEvent;
import hsb.network.packet.tileentity.PacketRequestData;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.NetworkHelper;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;

public class NetworkManagerClient extends NetworkManager {
	@Override
	public void initiateClientTileEntityEvent(TileEntity te, int ev) {
		if(Settings.ic2Available) 
		{
			NetworkHelper.initiateClientTileEntityEvent(te, ev);
		} else {
			PacketDispatcher.sendPacketToServer(new PacketClientTileEvent(ev, te).getPacket());
		}
			
	}
	
	 
	@Override
	public void requestInitialData(INetworkDataProvider te) {
		if(Settings.ic2Available)
		{
			NetworkHelper.requestInitialData(te);
		} else {
			PacketDispatcher.sendPacketToServer(new PacketRequestData((TileEntity) te).getPacket());
		}
	}
}
