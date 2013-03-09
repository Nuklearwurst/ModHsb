package hsb.network;

import hsb.tileentitys.TileEntityLockTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.Player;

public class PacketUpgradeCamo extends PacketPosition{

	TileEntity te;

	public PacketUpgradeCamo(TileEntity te, String pass, int port) {
		super(te.xCoord, te.yCoord, te.zCoord);
		this.te = te;
	}
	
	public PacketUpgradeCamo() {
	}

	@Override
	public int getID() {
		return PacketIds.TILE_UPGRADECAMO;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);
		te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityLockTerminal)
		{
			//TODO update upgrades
//			((TileEntityLockTerminal)te)
			te.onInventoryChanged();
		}
	}

}
