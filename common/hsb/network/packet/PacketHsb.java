package hsb.network.packet;


import hsb.lib.Reference;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.Player;

public abstract class PacketHsb {

	protected boolean isChunkDataPacket = false;
	protected String channel = Reference.CHANNEL_NAME;

	/**
	 * 
	 * @return PacketId
	 */
	public abstract int getID();

	public Packet getPacket() {

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getID());
			writeData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		packet.isChunkDataPacket = this.isChunkDataPacket;
		return packet;
	}

	/**
	 * handle Packet when arrives
	 * @param data
	 * @param player
	 * @throws IOException
	 */
	public abstract void onPacketData(DataInputStream data, Player player) throws IOException;

	/**
	 * read data from dataImputStream
	 * @param data
	 * @throws IOException
	 */
	public abstract void readData(DataInputStream data) throws IOException;
	
	/**
	 * write data to DataOutputStream
	 * @param data
	 * @throws IOException
	 */
	public abstract void writeData(DataOutputStream data) throws IOException;
}