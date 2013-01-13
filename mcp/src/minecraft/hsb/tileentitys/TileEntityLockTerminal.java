package hsb.tileentitys;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

import ic2.api.Direction;
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.NetworkHelper;
import hsb.ModHsb;
import hsb.api.IHsbUpgrade;
import hsb.api.IItemHsbUpgrade;
import hsb.config.Config;
import hsb.config.HsbItems;
import hsb.gui.GuiHandler;

public class TileEntityLockTerminal extends TileEntityHsb implements
		IEnergySink, IInventory {

	//TODO clean Up
	//upgradeCount reset!!!!
	public int blocksInUse = 0;
	private boolean needReconnect = false;
	// IC2
	public boolean isAddedToEnergyNet = false;
	public int energyStored = 0;

	private int updateCounter = 0;
//private
	private ItemStack[] mainInventory = new ItemStack[this.getSizeInventory()];

	// Defaults
	public static final int defaultEnergyStorage = 10000;
	public static final int defaultPassLength = 8;
	public static final int maxPort = 100;
	public static final int UPGRADE_ENERGY_STORAGE = 1000; 

	// Upgrades
	//IC2
	public int storageUpgrades = 0;
	public int transformerUpgrades = 0;
	public int overclockerUpgrades = 0;
	//other
	//private
	private List upgrades;
	//used to sync buttons
	public String buttonNumber[] = {"", "", "", "", "", "", "", "", "", ""};
	
	public int[] upgradeCount;
	public boolean[] upgradeActive;
	
	public int extraStorage = 0;
	public int extraPassLength = 0;
	public double energyUse = 0.25;
	public int maxInput = 32;
	public int securityLevel = 0;

	public TileEntityLockTerminal() {
		super();
		upgradeCount = new int[10];
		upgradeActive = new boolean[10];
		upgrades = new ArrayList();
		blocksInUse = 0;
		maxInput = 32;
		energyUse = 0.25;
		isAddedToEnergyNet = false;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return true;
	}

	public boolean addToInventory(ItemStack item, int number)
	{
		for(int i = 5; i <= 15; i++)
		{
			ItemStack slot = this.getStackInSlot(i);
			if(slot == null)
			{
				this.mainInventory[i] = item;
				return true;
			}
			if(slot.itemID == item.itemID && slot.getItemDamage() == item.getItemDamage())
			{
				int size = slot.stackSize + number;
				if(size <= 64)
				{
					this.mainInventory[i].stackSize = size;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.mainInventory[slot] != null) {
			ItemStack stack;

			if (this.mainInventory[slot].stackSize <= amount) {
				stack = this.mainInventory[slot];
				this.mainInventory[slot] = null;
				this.onInventoryChanged();
				return stack;
			} else {
				stack = this.mainInventory[slot].splitStack(amount);

				if (this.mainInventory[slot].stackSize == 0) {
					this.mainInventory[slot] = null;
				}

				this.onInventoryChanged();
				return stack;
			}
		} else {
			return null;
		}
	}

	@Override
	public int demandsEnergy() {
		return (defaultEnergyStorage + extraStorage) - energyStored;
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

				this.xTer = this.xCoord;
				this.yTer = this.yCoord;
				this.zTer = this.zCoord;

			} else {
				System.out.println("error!");
			}
		}
		if (!Config.ECLIPSE)
			NetworkHelper.updateTileEntityField(this, "locked");
		return true;
	}

	public int getEnergyScaled(int length) {
		int x = (this.energyStored * length
					/ (this.extraStorage + TileEntityLockTerminal.defaultEnergyStorage));
		if(x > length)
			x = length;
		return x;
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
	public int getMaxSafeInput() {
		return this.maxInput;
	}
	
	@Override
	public List<String> getNetworkedFields() {
		List list = super.getNetworkedFields();
	    list.add("upgradeActive");
	    list.add("buttonNumber");
	    return list;
	}
	
	public int getSecurityLevel() {
		return this.securityLevel;
	}
	@Override
	public int getSizeInventory() {
		return 15;
	}
	@Override
	public ItemStack getStackInSlot(int slotid) {
		return this.mainInventory[slotid];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {

		if (this.mainInventory[slot] != null) {
			ItemStack stack = this.mainInventory[slot];
			this.mainInventory[slot] = null;
			return stack;
		} else {
			return null;
		}
	}

	public IHsbUpgrade getUpgrade(int id) {
		if(this.upgrades.size() > id)
		{
			return (IHsbUpgrade) this.upgrades.get(id);
		} else {
			return null;
		}
	}

	public IHsbUpgrade getUpgrade(String id) {
		for(int i = 0; i<this.upgrades.size();i++){
			IHsbUpgrade upgrade = (IHsbUpgrade) this.upgrades.get(i);
			if(upgrade.getUniqueId() == id)
			{
				return upgrade;
			}
		}
		return null;
	}

//	public int getUpgradeId(IHsbUpgrade upgrade)
//	{
//		return this.upgrades.indexOf(upgrade);
//	}

	public int getUpgradeId(String id)
	{
		return this.upgrades.indexOf(this.getUpgrade(id));
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(HsbItems.blockHsb, 1, 1);
	}

	@Override
	public float getWrenchDropRate() {
		// TODO Upgrade?
		return 0;
	}

	@Override
	protected void initData()
	{
		super.initData();
		this.onInventoryChanged();
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		if(amount > this.maxInput)
		{
			this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 0);
			this.worldObj.createExplosion(null, xCoord, yCoord, zCoord, 0.8F, false);
			return 0;
		}
		int missing = (TileEntityLockTerminal.defaultEnergyStorage + this.extraStorage)
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
	public void onInventoryChanged()
	{
		if(worldObj != null /*&& !worldObj.isRemote*/)
		{
			//IC2
			this.storageUpgrades = 0;
			this.transformerUpgrades = 0;
			this.overclockerUpgrades = 0;
			this.maxInput = 32;
			this.extraPassLength = 0;
			this.securityLevel = 0;
			
			//Upgrade Data
			//clearing List for new Data
			List oldData = upgrades;
			boolean oldActive[] = this.upgradeActive;
			this.upgrades.clear();
			//variable to track index of Upgrade Arrays
			int index = 0;
			
			for(int i = 0; i<this.buttonNumber.length ; i++)
			{
				this.buttonNumber[i] = "";
			}
			
			
			this.energyUse = 0.25;
	
			for(int i = 0; i < this.mainInventory.length; i++)
			{
				ItemStack stack = this.mainInventory[i]; 
				if(stack == null)
				{
					continue;
				}
				
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
				
				//register Upgrades (IHsbUpgrade/IItemHsbUpgrade)
				if(stack.getItem() instanceof IItemHsbUpgrade)
				{
					IItemHsbUpgrade item = (IItemHsbUpgrade) stack.getItem();
					IHsbUpgrade upgrade = item.getUpgrade(stack.getItemDamage());
					if(this.getUpgrade(upgrade.getUniqueId())!=null)
					{
					//updating existing  Upgrade
						int upgradeId = this.getUpgradeId(upgrade.getUniqueId());
						this.upgradeCount[upgradeId] = this.upgradeCount[upgradeId] + stack.stackSize;
						Config.logDebug("updated: " + stack + "__" + this.upgradeCount[upgradeId] + "  " + upgradeId + "   isremote: " + worldObj.isRemote + "    buttonNumber: " + buttonNumber[upgradeId]);
					} else {
					//adding new Upgrade
						this.buttonNumber[index] = upgrade.getButtonName();
						this.upgrades.add(upgrade);
						this.upgradeCount[index] = stack.stackSize;
						if(oldData.contains(upgrade)){
							Config.logDebug("Upgrade found!");
							this.upgradeActive[index] = oldActive[oldData.lastIndexOf(upgrade)];
						} else {
							Config.logDebug("No prior Upgrade found!");
							this.upgradeActive[index] = upgrade.isEnabledByDefault();
						}
						Config.logDebug("added: " + stack + "__" + this.upgradeCount[index] + "  " + index + "   isremote: " + worldObj.isRemote);
						index++;
					}
				}
			}
			
			//register IC2 Upgrades
			this.extraStorage = this.storageUpgrades * UPGRADE_ENERGY_STORAGE;
			switch(this.transformerUpgrades)
			{
			case 0:
				this.maxInput = 32;
				break;
			case 1:
				this.maxInput = 128;
				break;
			case 2:
				this.maxInput = 512;
				break;
			case 3:
				this.maxInput = 2048;
				break;
			default:
				this.maxInput = 2048;
			}
			this.updateUpgrades();
			NetworkHelper.updateTileEntityField(this, "buttonNumber");
			if(this.energyStored > (this.extraStorage + TileEntityLockTerminal.defaultEnergyStorage))
			{
				this.energyStored = (this.extraStorage + TileEntityLockTerminal.defaultEnergyStorage);
			}
		}
		super.onInventoryChanged();
	}

	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {
		/*
		 * case 0: lock case 1: unlock
		 */
		switch (event) {
		case -2:
			player.openGui(ModHsb.instance, GuiHandler.GUI_LOCKTERMINAL, worldObj, xCoord, yCoord, zCoord);
			return;
		case -1:
			player.openGui(ModHsb.instance, GuiHandler.GUI_LOCKTERMINAL_OPTIONS, worldObj, xCoord, yCoord, zCoord);
			return;
		case 1:
			this.emitLockSignal(6, !this.locked);
			return;
		}
		if(event >= -12 && event <= -3)
		{
			// -3 - -12
			int number = event * (-1) - 3;
			//0 - 9
			this.onInventoryChanged();
			IHsbUpgrade upgrade = this.getUpgrade(number);
			if(upgrade != null)
			{

				upgrade.onButtonClicked(this, player, number);
				this.updateUpgrades();
				NetworkHelper.updateTileEntityField(this, "upgradeActive");
				NetworkHelper.updateTileEntityField(this, "upgradeCount");
				NetworkHelper.updateTileEntityField(this, "buttonNumber");
			} else {
				System.out.println("Upgrade == null!" + number);
			}
			return;
		}
		super.onNetworkEvent(player, event);
	}
	
	@Override
	public void onNetworkUpdate(String field) {
		super.onNetworkUpdate(field);
	}

	@Override
	public void openChest() {}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		//Upgrades
		for(int i = 0; i<10;i++){
			String name = "upgradeActive" + i;
			this.upgradeActive[i]= nbttagcompound.getBoolean(name);
		}
		
		//Items
        NBTTagList nbtlist = nbttagcompound.getTagList("Items");
        this.mainInventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbtlist.tagCount(); ++i)
        {
            NBTTagCompound nbttag = (NBTTagCompound)nbtlist.tagAt(i);
            byte slot = nbttag.getByte("Slot");

            if (slot >= 0 && slot < this.mainInventory.length)
            {
                this.mainInventory[slot] = ItemStack.loadItemStackFromNBT(nbttag);
            }
        }
        
		this.energyStored = nbttagcompound.getInteger("energyStored");
		this.blocksInUse = nbttagcompound.getInteger("blocksInUse");
		onInventoryChanged();
	}
	
	@Override
	public void setFacing(short facing) {
		if (isAddedToEnergyNet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
		isAddedToEnergyNet = false;
		super.setFacing(facing);
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
		isAddedToEnergyNet = true;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.mainInventory[slot] = itemstack;

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

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			//add to EnergyNet
			if (!isAddedToEnergyNet) {
				this.onInventoryChanged();
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				isAddedToEnergyNet = true;
			}
			//charge from battery //4:ChargeSlot(-->ContainerLockTerminal)
			ItemStack item = this.getStackInSlot(4);
			if(item != null && item.getItem() instanceof IElectricItem && (this.demandsEnergy() > 0))
			{
				int leftover = this.injectEnergy(Direction.YN, ElectricItem.discharge(item, this.maxInput, 2, false, false));
				ElectricItem.charge(item, leftover, 2, false, false);
			}
			//Use Energy
			if (this.locked && this.blocksInUse > 0 && this.isAddedToEnergyNet) {
				double need = (this.energyUse * this.blocksInUse + 2);
				if (need > energyStored) {
					this.emitLockSignal(6, false);
					Config.logDebug("not enough energy! need: " + need
							+ " stored: " + energyStored);
				} else {
					energyStored = (int) Math.round(energyStored - need);
				}
			}
			this.updateCounter++;
			if (updateCounter >= 20) {
				updateCounter = 0;
				//reconnecting
				if (needReconnect) {
					this.needReconnect = false;
					this.emitLockSignal(6, true);
				}
			}
		}
		super.updateEntity();
	}

	public void updateUpgrades(){
		for(int i = 0; i<this.upgrades.size(); i++)
		{
			((IHsbUpgrade) this.upgrades.get(i)).updateUpgrade(this);
		}
		
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
		//UpgradeActive
		//TODO save Upgrade Data
		for(int i = 0; i<10;i++){
			String name = "upgradeActive" + i;
			nbttagcompound.setBoolean(name, this.upgradeActive[i]);
		}

		//Items
        NBTTagList nbtlist = new NBTTagList();

        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null)
            {
                NBTTagCompound nbttag = new NBTTagCompound();
                nbttag.setByte("Slot", (byte)i);
                this.mainInventory[i].writeToNBT(nbttag);
                nbtlist.appendTag(nbttag);
            }
        }
        nbttagcompound.setTag("Items", nbtlist);
        
		nbttagcompound.setInteger("energyStored", this.energyStored);
		
		nbttagcompound.setInteger("blocksInUse", this.blocksInUse);
	}

}