package hsb.network.packet;

import hsb.config.Config;
import hsb.network.PacketIds;
import hsb.tileentitys.TileEntityLockTerminal;
import ic2.api.network.INetworkClientTileEntityEventListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.Player;

public class PacketClientTileEvent extends PacketPosition {

	public int event;
	@Override
	public int getID() {
		return PacketIds.TILE_CLIENT_EVENT;
	}
	
	public PacketClientTileEvent(){}
	
	public PacketClientTileEvent(int event, TileEntity te) {
		super(te.xCoord, te.yCoord, te.zCoord);
		this.event = event;
		
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.event = data.readInt();
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(event);
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);
		TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof INetworkClientTileEntityEventListener)
		{
			((INetworkClientTileEntityEventListener) te).onNetworkEvent((EntityPlayer) player, event);
		} else {
			Config.logError("TE == null, or not listening!!!");
		}
	}

}
