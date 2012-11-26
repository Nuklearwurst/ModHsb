package hsb;

import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergySink;
import ic2.api.NetworkHelper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import hsb.config.Config;

public class TileEntityLockTerminal extends TileEntityHsb implements
		IEnergySink, IInventory {

	public int blocksInUse = 0;
	private boolean needReconnect = false;
	// IC2
	public boolean isAddedToEnergyNet = false;
	public int energyStored = 100;// TODO

	private int updateCounter = 0;

	private ItemStack[] chestContents = new ItemStack[this.getSizeInventory()];

	// Defaults
	// TODO Config, maybe move to Defaults.java
	public final int defaultEnergyStorage = 10000;
	public final int defaultPassLength = 8;
	public final static int maxPort = 100;
	public final static int UPGRADE_ENERGY_STORAGE = 1000; 

	// Upgrades //Old TODO
	private int storageUpgrades = 0;
	private int transformerUpgrades = 0;
	private int overclockerUpgrades = 0;
	
	public int extraStorage = 0;
	public int extraPassLength = 0;
	public double energyUse = 0.25;
	public int maxInput = 32;

	public TileEntityLockTerminal() {
		super();
		blocksInUse = 0;
		isAddedToEnergyNet = false;
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
			ItemStack stack;

			if (this.chestContents[slot].stackSize <= amount) {
				stack = this.chestContents[slot];
				this.chestContents[slot] = null;
				this.onInventoryChanged();
				return stack;
			} else {
				stack = this.chestContents[slot].splitStack(amount);

				if (this.chestContents[slot].stackSize == 0) {
					this.chestContents[slot] = null;
				}

				this.onInventoryChanged();
				return stack;
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean demandsEnergy() {
		return energyStored < (defaultEnergyStorage + extraStorage);
	}

	/**
	 * transfers a lock signal to all valid blocks
	 * 
	 * @param side
	 *            the side, can be 6 for all
	 * @param lock
	 *            true to lock, false to unlock
	 * @param port
	 *            the port to transfer
	 * @param pass
	 *            the password to transfer
	 * @return success
	 */
	private boolean emitLockSignal(int side, boolean lock) {
		if (this.worldObj == null)
			return false;
		if (this.worldObj.isRemote)
			return true;
		if (side < 0 || side > 6)
			return false;
		// if(port != this.port)
		// return false;
		// if(pass != this.pass)
		// return false;
		if (side != 6)
			return false;
		if (side == 6) {
			if (!lock) {
				this.blocksInUse = 0;
			}
			System.out.println("emitting: side: " + side + " lock: " + lock
					+ " pass: " + pass + " port: " + port);
			this.locked = lock;
			this.blocksInUse++;
			if (this.transferSignal_do(this, lock, pass, port, side)) {

			} else {
				System.out.println("error!");
			}
		}
		if (!Config.ECLIPSE)
			NetworkHelper.updateTileEntityField(this, "locked");
		return true;
	}

	public int getEnergyScaled(int length) {
		return this.energyStored * length
				/ (this.extraStorage + this.defaultEnergyStorage);
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
		return 15;
	}

	@Override
	public ItemStack getStackInSlot(int slotid) {
		return this.chestContents[slotid];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {

		if (this.chestContents[slot] != null) {
			ItemStack stack = this.chestContents[slot];
			this.chestContents[slot] = null;
			return stack;
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
		if(amount > this.maxInput)
		{
			this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 0);
			this.worldObj.createExplosion(null, xCoord, yCoord, zCoord, 0.8F, false);
			return 0;
		}
		int missing = (this.defaultEnergyStorage + this.extraStorage)
				- energyStored;
		if (missing < amount) {
			energyStored += missing;
			return amount - missing;
		}
		energyStored += amount;
		return 0;
	}

	@Override
	public void invalidate() {
		super.validate();
		if (!worldObj.isRemote && isAddedToEnergyNet) {
			EnergyNet.getForWorld(worldObj).removeTileEntity(this);
			isAddedToEnergyNet = false;
		}
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return this.isAddedToEnergyNet;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false
				: par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D,
						this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {
		/*
		 * case 0: lock case 1: unlock
		 */
		switch (event) {
		case 0:
			this.emitLockSignal(6, true);
			break;
		case 1:
			this.emitLockSignal(6, false);
			break;
		default:
			super.onNetworkEvent(player, event);
		}
	}

	@Override
	public void openChest() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		//Items
        NBTTagList nbtlist = nbttagcompound.getTagList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbtlist.tagCount(); ++i)
        {
            NBTTagCompound nbttag = (NBTTagCompound)nbtlist.tagAt(i);
            byte slot = nbttag.getByte("Slot");

            if (slot >= 0 && slot < this.chestContents.length)
            {
                this.chestContents[slot] = ItemStack.loadItemStackFromNBT(nbttag);
            }
        }
        
		this.energyStored = nbttagcompound.getInteger("energyStored");
	}

	@Override
	public void setFacing(short facing) {
		if (isAddedToEnergyNet) {
			EnergyNet.getForWorld(worldObj).removeTileEntity(this);
		}
		isAddedToEnergyNet = false;
		super.setFacing(facing);
		EnergyNet.getForWorld(worldObj).addTileEntity(this);
		isAddedToEnergyNet = true;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.chestContents[slot] = itemstack;

		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public boolean transferSignal(int side, TileEntityLockTerminal te,
			boolean value, String pass, int port) {
		if (this.worldObj.isRemote) {
			return true;
		}
		System.out.println("transfeSignal te Lock Terminal");
		if (this.locked && !value) {
			// if a tile is broken
			if (te == null) {
				if (port == this.port && pass == this.pass) {
					this.locked = false;
					this.needReconnect = true;
					this.blocksInUse = 0;
					return true;
				}
			}
			// TODO if a lock signal was send by a terminal
		}
		return super.transferSignal(side, te, value, pass, port);
	}
	
	public void updateUpgrades(EntityPlayer player)
	{
		this.storageUpgrades = 0;
		this.transformerUpgrades = 0;
		this.overclockerUpgrades = 0;
		for(int i = 0; i < this.chestContents.length; i++)
		{
			ItemStack stack = this.chestContents[i]; 
			if(stack.isItemEqual(Config.getIC2Item("transformerUpgrade")))
			{
				this.transformerUpgrades = this.transformerUpgrades + stack.stackSize;
			}
				
			if(stack.isItemEqual(Config.getIC2Item("energyStorageUpgrade")))
			{
				this.storageUpgrades = this.storageUpgrades + stack.stackSize;
			}
		
			if(stack.isItemEqual(Config.getIC2Item("overclockerUpgrade")))
			{
				this.overclockerUpgrades = this.overclockerUpgrades + stack.stackSize;
			}
		}
		this.extraStorage = this.storageUpgrades * UPGRADE_ENERGY_STORAGE;
		switch(this.transformerUpgrades)
		{
		case 0:
			this.maxInput = 32;
		case 1:
			this.maxInput = 128;
		case 2:
			this.maxInput = 512;
		case 3:
			this.maxInput = 2048;
		default:
			this.maxInput = 2048;
		}
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			if (!isAddedToEnergyNet && !Config.ECLIPSE) {
				EnergyNet.getForWorld(worldObj).addTileEntity(this);
				isAddedToEnergyNet = true;
			}
			this.updateCounter++;
			if (updateCounter >= 5) {
				updateCounter = 0;
				//reconnecting
				if (needReconnect) {
					this.needReconnect = false;
					this.emitLockSignal(6, true);
				}
				
				//Use Energy
				if (this.locked && this.blocksInUse > 0) {
					double need = (this.energyUse * this.blocksInUse + 2);
					if (need > energyStored) {
						this.emitLockSignal(6, false);
						System.out.println("not enough energy! need: " + need
								+ " stored: " + energyStored);
					} else {
						energyStored = (int) Math.round(energyStored - need);
					}
				}
				onInventoryChanged();
			}

		}
		super.updateEntity();
	}

	@Override
	public void validate() {
		super.validate();
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
		//Items
        NBTTagList nbtlist = new NBTTagList();

        for (int i = 0; i < this.chestContents.length; ++i)
        {
            if (this.chestContents[i] != null)
            {
                NBTTagCompound nbttag = new NBTTagCompound();
                nbttag.setByte("Slot", (byte)i);
                this.chestContents[i].writeToNBT(nbttag);
                nbtlist.appendTag(nbttag);
            }
        }
        nbttagcompound.setTag("Items", nbtlist);
        
		nbttagcompound.setInteger("energyStored", this.energyStored);
	}

}
