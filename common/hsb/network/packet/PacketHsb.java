package hsb.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.network.Player;

import hsb.config.Defaults;

public abstract class PacketHsb {

	protected boolean isChunkDataPacket = false;
	protected String channel = Defaults.NET_CHANNEL;

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

	public abstract void onPacketData(DataInputStream data, Player player) throws IOException;

	public abstract void readData(DataInputStream data) throws IOException;
	
	public abstract void writeData(DataOutputStream data) throws IOException;
}