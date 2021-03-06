package hsb.network;


import hsb.lib.PacketIds;
import hsb.network.packet.PacketItemUpdate;
import hsb.network.packet.PacketPasswordUpdate;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;



public class PacketHandler implements IPacketHandler{

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packetData, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packetData.data));
		try {
			int packetId = data.readByte();
			switch(packetId)
			{
			/* Old code, see nwcore
				case PacketIds.TILE_CLIENT_EVENT:
				{
					PacketClientTileEvent packet = new PacketClientTileEvent();
					packet.onPacketData(data, player);
					break;
				}
				case PacketIds.TILE_DATA_REQUEST:
				{
					PacketRequestData packet = new PacketRequestData();
					packet.onPacketData(data, player);
					break;
				}
				case PacketIds.TILE_FIELD_UPDATE:
				{
					PacketTileFieldUpdate packet = new PacketTileFieldUpdate();
					packet.onPacketData(data, player);
					break;
				}
			*/
				case PacketIds.GUI_PASSWORD_UPDATE:
				{
					PacketPasswordUpdate packet = new PacketPasswordUpdate();
					packet.onPacketData(data, player);
					break;
				}
				case PacketIds.ITEM_NBTTAG_UPDATE:
				{
					PacketItemUpdate packet = new PacketItemUpdate();
					packet.onPacketData(data, player);
					break;
				}
				//not needed
//				case PacketIds.TILE_TERMINAL_BUTTONS:
//				{
//					PacketTerminalButtons packet = new PacketTerminalButtons();
//					packet.onPacketData(data, player);
//					break;
//				}
//				case PacketIds.TILE_REQUEST_BUTTON_UPDATE:
//				{
//					PacketRequestButtons packet = new PacketRequestButtons();
//					packet.onPacketData(data, player);
//					break;
//				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
