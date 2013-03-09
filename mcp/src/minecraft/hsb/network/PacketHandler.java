package hsb.network;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler
	implements IPacketHandler
{

	//TODO: Clean-up
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		 DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
//			System.out.println("Hsb: PacketHandler: Packet arrived!");
			int packetID = data.readByte();
//			System.out.println("Hsb: PacketHandler: PacketId == " + String.valueOf(packetID) + "!");
			switch (packetID) {
			
			
			case PacketIds.TILE_UPDATE:
			{
//				PacketTileUpdate packetT = new PacketTileUpdate();
//				packetT.readData(data);
//				onTileUpdate((EntityPlayer)player, packetT);
//				System.out.println("Hsb: PacketHandler: PacketId == TILE_UPDATE!");
				break;
			}

			
			case PacketIds.ITEM_NBTTAG_UPDATE:
			{
				System.out.println("Hsb: PacketHandler: PacketId == ITEM_UPDATE!");
				PacketItemUpdate itemPacket  = new PacketItemUpdate();
				itemPacket.onPacketData(data, player);
				break;
			}
			
			
			case PacketIds.TILE_TERMINAL_UPDATE:
			{
				System.out.println("Hsb: PacketHandler: PacketId == TERMINAL_UPDATE!");
				PacketTerminalUpdate tilePacket = new PacketTerminalUpdate();
				tilePacket.onPacketData(data, player);
				break;
			}
			case PacketIds.TILE_UPGRADECAMO:
			{
				//From Server to Client
				//TODO create packet
				PacketUpgradeCamo packetCamo = new PacketUpgradeCamo();
				packetCamo.onPacketData(data, player);
				break;
			}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


}
