package hsb;

import ic2.api.Direction;
import ic2.api.IEnergySink;
import ic2.api.INetworkClientTileEntityEventListener;
import ic2.api.NetworkHelper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import hsb.config.Config;

public class TileEntityLockTerminal extends TileEntityHsb implements
		IEnergySink, IInventory, INetworkClientTileEntityEventListener {
	public int blocksInUse = 0;
	// IC2
	public boolean isAddedToEnergyNet = false;
	public int energyStored = 0;

	private int updateCounter = 0;

	private ItemStack[] chestContents = new ItemStack[this.getSizeInventory()];

	// Defaults
	// TODO Config, maybe move to Defaults.java
	public int defaultEnergyStorage = 1000;
	public int defaultPassLength = 8;
	public static int maxPort = 100;

	// Upgrades //Old TODO
	public int extraStorage = 0;
	public int extraPassLength = 0;
	public double energyUse = 0.25;

	public TileEntityLockTerminal() {
		super();
		blocksInUse = 0;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return true;
	}

	@Override
	public void closeChest() {
	}

	@Override
	// TODO rewrite ?
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.chestContents[slot] != null) {
			ItemStack var3;

			if (this.chestContents[slot].stackSize <= amount) {
				var3 = this.chestContents[slot];
				this.chestContents[slot] = null;
				this.onInventoryChanged();
				return var3;
			} else {
				var3 = this.chestContents[slot].splitStack(amount);

				if (this.chestContents[slot].stackSize == 0) {
					this.chestContents[slot] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean demandsEnergy() {
		return energyStored < defaultEnergyStorage + extraStorage;
	}
	
	/**
	 * transfers a lock signal to all valid blocks
	 * 
	 * @param side the side, can be 6 for all
	 * @param lock true to lock, false to unlock
	 * @param port the port to transfer
	 * @param pass the password to transfer
	 * @return success
	 */
	public boolean emitLockSignal(int side, boolean lock, int port, String pass) {
		if(this.locked == lock)
			return false;
		if(side < 0 || side > 6)
			return false;
		if(port != this.port)
			return false;
		if(pass != this.pass)
			return false;
		if(side != 6)
			return false;
		if(side == 6) {
			System.out.println("transferring");
			this.transferSignal(0, this, lock, pass, port);
		}
//		this.locked = lock;
//		if(!Config.ECLIPSE)
//			NetworkHelper.updateTileEntityField(this, "locked");
//		System.out.println("hallo");
		return true;
	}

	public int getEnergyScaled(int length) {
		return /* this.energyStored */492 * length
				/ (this.extraStorage + this.defaultEnergyStorage); // DEBUG
																	// value:
																	// 492
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		// Inventory Name
		return "LockTerminal Inventory";
	}

	@Override
	public int getSizeInventory() {
		// TODO Inventory Size
		return 10;
	}

	@Override
	public ItemStack getStackInSlot(int slotid) {
		// TODO Inventory
		return this.chestContents[slotid];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {

		// TODO INventory
		if (this.chestContents[slot] != null) {
			ItemStack var2 = this.chestContents[slot];
			this.chestContents[slot] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public float getWrenchDropRate() {
		// TODO Upgrade?
		return 0;
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		int missing = (this.defaultEnergyStorage + this.extraStorage)
				% energyStored;
		if (Config.DEBUG)
			System.out
					.println("Hsb: TileEntityLockTerminal: missing Energy to full: "
							+ String.valueOf(missing));
		return 0;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return this.isAddedToEnergyNet;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,this.zCoord) != this ? false
				: par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D,
						this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		// TODO Inventory
		this.chestContents[slot] = itemstack;

		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		// TODO Upgrade?
		return false;
	}

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
	}

	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {
		/*
		 * case 0: lock
		 * case 1: unlock
		 */
		switch(event)
		{
		case 0:
			this.emitLockSignal(6, true, this.port, this.pass);
			break;
		case 1:
			this.emitLockSignal(6, false, this.port, this.pass);
			break;
		default:
			System.out.println("Unexpected event!! " + event);
		}
	}
	@Override
	public boolean transferSignal(int side, TileEntityLockTerminal te, boolean lock, String pass, int port) {
		System.out.println("transfeSignal te Lock Terminal");
		if(this.locked && !locked)
		{
			//if a tile is broken
			if(te == null){
				if(port == this.port && pass == this.pass)
				{	
					this.blocksInUse = 0;
					return super.transferSignal(0, this, true, pass, port);
				}
			}
			//if a lock signal was send by a terminal
			//TODO
		}	
		return super.transferSignal(side, te, lock, pass, port);	
	}

}
