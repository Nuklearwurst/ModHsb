package hsb.upgrades;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.ModHsb;
import hsb.api.IHsbUpgrade;
import hsb.config.Config;
import hsb.config.HsbItems;
import hsb.gui.GuiHandler;
import hsb.network.PacketUpgradeCamo;
import hsb.tileentitys.TileEntityLockTerminal;

public class UpgradeCamoflage implements IHsbUpgrade, IInventory {

	public ItemStack itemSlot = null;
	
	@Override
	public void updateUpgrade(TileEntityLockTerminal te) {
		if(this.itemSlot!=null)
		{
			if(itemSlot.getItem() instanceof ItemBlock)
			{
				Block block = Block.blocksList[((ItemBlock)itemSlot.getItem()).getBlockID()];
				int meta = ((ItemBlock)itemSlot.getItem()).getMetadata(itemSlot.getItemDamage());
				if(block.isOpaqueCube())
				{
					te.camoMeta = meta;
					te.camoId = itemSlot.itemID;
				}
			}
		}
	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player,
			int button) {
		if(te.worldObj == null)
		{
			player.sendChatToPlayer("WorldObj == null!!");
			return;
		}
		
		PacketUpgradeCamo packet = new PacketUpgradeCamo();
		((EntityClientPlayerMP)player).sendQueue.addToSendQueue(packet.getPacket()); 
		player.openGui(ModHsb.instance, GuiHandler.GUI_UPGRADE_CAMOFLAGE, te.worldObj, te.xCoord, te.yCoord, te.zCoord);
		
	}

	@Override
	public String getButtonName() {		
		return "Camo";
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(HsbItems.itemHsbUpgrade, 1, 3);
	}

	@Override
	public String getUniqueId() {
		return "Camoflague";
	}

	@Override
	public void onTileSave(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
		//Items
		if (this.itemSlot != null)
        {
            NBTTagCompound nbttag = new NBTTagCompound();
            this.itemSlot.writeToNBT(nbttag);
            nbttagcompound.setTag("Items", nbttag);
        }		
	}

	@Override
	public void onTileLoad(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
		//Items
		NBTTagCompound nbttag = (NBTTagCompound) nbttagcompound.getTag("Items");
		if(nbttag != null)
			this.itemSlot = ItemStack.loadItemStackFromNBT(nbttag);		
	}

	@Override
	public void onGuiOpen(TileEntityLockTerminal te) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(slot == 0)
		{
			return itemSlot;
		} else {
			Config.logError("Unknnown Slot in UpgradeCamoflage!!!!");
			return null;
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(slot != 0)
		{
			Config.logError("Unknnown Slot in UpgradeCamoflage!!!!");
			return null;
		}
		if (itemSlot != null) {
			ItemStack stack;

			if (itemSlot.stackSize <= amount) {
				stack = this.itemSlot;
				this.itemSlot = null;
				this.onInventoryChanged();
				return stack;
			} else {
				stack = itemSlot.splitStack(amount);

				if (itemSlot.stackSize == 0) {
					itemSlot = null;
				}
				this.onInventoryChanged();
				return stack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot == 0) {
			if(itemSlot != null)
			{
				ItemStack stack = this.itemSlot;
				this.itemSlot = null;
				return stack;
			} else {
				return null;
			}
		} else {
			Config.logError("Unknnown Slot in UpgradeCamoflage!!!!");
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot == 0)
		{
			this.itemSlot = stack;
		} else {
			Config.logError("Unknnown Slot in UpgradeCamoflage!!!!");
		}		
	}

	@Override
	public String getInvName() {
		return "UpgradeCamoflage";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

}
