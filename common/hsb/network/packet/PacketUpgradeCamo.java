package hsb.network.packet;

import hsb.config.HsbItems;
import hsb.network.PacketIds;
import hsb.tileentitys.TileEntityLockTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.Player;

public class PacketUpgradeCamo extends PacketPosition{

	TileEntity te;
	
	public int upgradeId=-1;
	
	public int camoBlockId=-1;
	public int camoMeta=-1;
	public boolean active=false;
	
	

	public PacketUpgradeCamo(TileEntity te, int upgradeId, int blockId, int blockMeta, boolean active) {
		super(te.xCoord, te.yCoord, te.zCoord);
		this.te = te;
		this.upgradeId = upgradeId;
		this.camoBlockId = blockId;
		this.camoMeta = blockMeta;
		this.active = active;
	}
	
	public PacketUpgradeCamo() {}

	@Override
	public int getID() {
		return PacketIds.TILE_UPGRADECAMO;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		upgradeId = data.readInt();
		camoBlockId = data.readInt();
		camoMeta = data.readInt();
		active = data.readBoolean();
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(upgradeId);
		data.writeInt(camoBlockId);
		data.writeInt(camoMeta);
		data.writeBoolean(active);
		//new ItemStack(1,1).writeToNBT(new NBTTagCompound()).w
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);
		te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityLockTerminal)
		{
			/**
			 * Packt//Item Id// Meta // upgradeId
			 */
			((TileEntityLockTerminal)te).handleUpgradePacket(this, HsbItems.itemHsbUpgrade.itemID, 3, x);
			te.onInventoryChanged();
		}
	}

}
