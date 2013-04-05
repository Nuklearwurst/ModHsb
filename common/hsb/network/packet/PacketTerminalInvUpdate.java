package hsb.network.packet;

import hsb.config.Config;
import hsb.network.PacketIds;
import hsb.tileentitys.TileEntityLockTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.Player;

public class PacketTerminalInvUpdate extends PacketPosition{

	TileEntityLockTerminal te;
	public ItemStack[] inv = new ItemStack[15];
//	public boolean[] active;
	
	public PacketTerminalInvUpdate() {}
	
	public PacketTerminalInvUpdate(TileEntityLockTerminal te) {
		
		super(te.xCoord, te.yCoord, te.zCoord);
		
		
		this.te = te;
		try {
			for(int i = 0; i<te.getSizeInventory();i++) {
				this.inv[i] = te.getStackInSlot(i);
			}
//			this.active = te.upgradeActive;
		} catch(Exception e) {
			FMLLog.log(Level.SEVERE, e, "Hsb had a problem handling a PacketTerminalInvUpdate Packet");
		}
	}
	
	@Override
	public int getID() {
		return PacketIds.TILE_UPGRADEINV_UPDATE;
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);
		te = (TileEntityLockTerminal) ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityLockTerminal)
		{
//			for(int i = 0; i < te.getSizeInventory(); i++){
//				te.setInventorySlotContents(i, inv[i]);
//			}
//			te.upgradeActive = active;
//			te.onInventoryChanged();
			te.setInventory(inv);
		} else {
			Config.logError("TE == NULL!!");
		}
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		//size of inventory  = 15
		for(int i = 0;i<15;i++) {
			this.inv[i] = Packet.readItemStack(data);
		}		
	}
	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);

		for(int i = 0;i<te.getSizeInventory();i++) {
			Packet.writeItemStack(inv[i], data);
		}
	}

}
