package hsb.network;

import hsb.TileEntityLockTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

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
		if (te != null && te instanceof TileEntityLockTerminal)
		{
			((TileEntityLockTerminal)te).pass = pass;
			((TileEntityLockTerminal)te).port = port;
			te.onInventoryChanged();
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
