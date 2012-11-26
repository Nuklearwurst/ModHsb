package hsb.network;

import hsb.TileEntityLockTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.TileEntity;

import cpw.mods.fml.common.network.Player;

public class PacketTerminalUpdate extends PacketPosition{
	TileEntity te;
	String pass;
	int port;

	public PacketTerminalUpdate(){}
	
	public PacketTerminalUpdate(TileEntity te, String pass, int port) {
		super(te.xCoord, te.yCoord, te.zCoord);
		this.te = te;
		this.pass = pass;
		this.port = port;

	}
	
	@Override
	public int getID() {
		return PacketIds.TILE_TERMINAL_UPDATE;
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);
		te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		System.out.println("HEY!");
		if (te != null && te instanceof TileEntityLockTerminal)
		{
			System.out.println("YIPPIE!");
			((TileEntityLockTerminal)te).pass = pass;
			((TileEntityLockTerminal)te).port = port;
		} else {
			System.out.println("Tile Update: " + te +" x: " + x + " y: " + " z: " + z);
		}
		
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		pass = data.readUTF();
		port = data.readInt();
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeUTF(pass);
		data.writeInt(port);
	}
}
