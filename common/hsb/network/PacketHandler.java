package hsb.network;


import hsb.config.Config;
import hsb.network.packet.PacketClientTileEvent;
import hsb.network.packet.PacketItemUpdate;
import hsb.network.packet.PacketRequestData;
import hsb.network.packet.PacketTerminalInvUpdate;
import hsb.network.packet.PacketTerminalUpdate;
import hsb.network.packet.PacketTileFieldUpdate;
import hsb.network.packet.PacketUpgradeCamo;

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
				Config.logDebug("Hsb: PacketHandler: PacketId == ITEM_UPDATE!");
				PacketItemUpdate itemPacket  = new PacketItemUpdate();
				itemPacket.onPacketData(data, player);
				break;
			}
			
			
			case PacketIds.TILE_TERMINAL_UPDATE:
			{
				Config.logDebug("Hsb: PacketHandler: PacketId == TERMINAL_UPDATE!");
				PacketTerminalUpdate tilePacket = new PacketTerminalUpdate();
				tilePacket.onPacketData(data, player);
				break;
			}
			case PacketIds.TILE_UPGRADECAMO:
			{
				//Both directions
				PacketUpgradeCamo packetCamo = new PacketUpgradeCamo();
				packetCamo.onPacketData(data, player);
				break;
			}
			case PacketIds.TILE_UPGRADEINV_UPDATE:
			{
				//From Server to Client
				//TODO create packet
				Config.logDebug("Hsb: PacketHandler: PacketId == TERMINALINV_UPDATE!");

				PacketTerminalInvUpdate packetInv = new PacketTerminalInvUpdate();
				packetInv.onPacketData(data, player);
				break;
			}
			case PacketIds.TILE_CLIENT_EVENT:
			{
				//Client --> Server
				Config.logDebug("Hsb: PacketHandler: PacketId == TILE_CLIENT_EVENT!");
				
				PacketClientTileEvent packetEv = new PacketClientTileEvent();
				packetEv.onPacketData(data, player);
				break;
			}
			case PacketIds.TILE_FIELD_UPDATE:
			{
				//Server --> Client
				Config.logDebug("Hsb: PacketHandler: PacketId == TILE_FIELD_UPDATE!");
				
				PacketTileFieldUpdate packetFU = new PacketTileFieldUpdate();
				packetFU.onPacketData(data, player);
				break;
			}
			case PacketIds.TILE_DATA_REQUEST:
			{
				//Client --> Server
				Config.logDebug("Hsb: PacketHandler: PacketId == TILE_DATA_REQUEST!");
				
				PacketRequestData packetD = new PacketRequestData();
				packetD.onPacketData(data, player);
				break;
			}
			default: 
			{
				Config.logError("Invalid Packet!");
			}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


}
