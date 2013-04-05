package hsb.network.packet;

import hsb.config.HsbItems;
import hsb.network.PacketIds;
import hsb.tileentitys.TileEntityLockTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.Player;

public class PacketUpgradeCamo extends PacketPosition{

	TileEntity te;
	
	public ItemStack inv; 
	
	public boolean active;
	
	

	public PacketUpgradeCamo() {}
	
	public PacketUpgradeCamo(TileEntity te, ItemStack stack, boolean active) {
		super(te.xCoord, te.yCoord, te.zCoord);
		this.inv = stack;
		this.active = active;

	}

	@Override
	public int getID() {
		return PacketIds.TILE_UPGRADECAMO;
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
//			((TileEntityLockTerminal) te).getUpgrade("Camo").handlePacket(this, (TileEntityLockTerminal) te);
			te.onInventoryChanged();
		}
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		inv = Packet.readItemStack(data);
		active = data.readBoolean();
		
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		Packet.writeItemStack(inv, data);
		data.writeBoolean(active);
		//new ItemStack(1,1).writeToNBT(new NBTTagCompound()).w
	}

}
