package hsb.tileentity;

import hsb.ModHsb;
import hsb.configuration.Settings;
import hsb.core.addons.PluginIC2;
import hsb.core.helper.HsbLog;
import hsb.lib.GuiIds;
import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.lock.LockManager;
import hsb.network.NetworkManager;
import hsb.network.packet.PacketPasswordUpdate;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.IMachineUpgradeItem;
import hsb.upgrade.types.ITerminalUpgradeItem;
import hsb.upgrade.types.IUpgradeButton;
import ic2.api.Direction;
import ic2.api.IElectricItem;
import ic2.api.IWrenchable;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.tile.IEnergySink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class TileEntityHsbTerminal extends TileEntityHsb
	implements ILockTerminal, IEnergySink, IInventory, IWrenchable
{
	//how many blocks are locked
	private int blocksInUse = 0;
	
	//if tile wants to reconnect
	private boolean needReconnect = false;
	
	//IC2
	private boolean isAddedToEnergyNet = false;
	private int safeInput = 32;
	
	//Energy
	public int energyStored = 0;
	public double energyUse = Settings.terminalEnergyUse;
	public int maxEnergyStorage = Settings.terminalEnergyStorage;
	
	private ItemStack[] mainInventory = new ItemStack[this.getSizeInventory()];
	
	//is empty on client
	private Map<String, IHsbUpgrade> upgrades;
	//contains unique id on server || button text on Client
	private List<String> buttons;
	
	//Upgrades
	public int camoId = -1;
	public int camoMeta = -1;
	
	public int tesla = 0;
	
	public int securityLevel = 0;
	
	public int passLength = Settings.defaultPassLength;
	
	public static final int EVENT_BUTTON_LOCK = -11;

	public static final int EVENT_GUI_TERMINAL = -12;

	public static final int EVENT_GUI_OPTIONS = -13;

	public short facing = 2;

	private short prevFacing = 2;

	public TileEntityHsbTerminal() {
		super();
		upgrades = new HashMap<String, IHsbUpgrade>();
		buttons = new ArrayList<String>();
	}
	
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return true;
	}

	@Override
	public void addBlockToTileEntity(ILockable te) {
		this.blocksInUse++;
	}

	@Override
	public void closeChest() {}

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
		return this.maxEnergyStorage - energyStored;

	}
	
	private void emitLockSignal(boolean lock, String pass, int port, int ignoreSide) {
		if(!worldObj.isRemote) {
			if(lock && this.energyStored < 2) 
				return;
			this.setLocked(lock);
			LockManager.tranferSignal(this, this, lock, pass, port, ignoreSide);
		}
	}

	@Override
	public boolean receiveSignal(int side, ILockTerminal te,
			boolean value, String pass, int port) {
		if (this.worldObj.isRemote) {
			return true;
		}
		//this is locked and is not locked bby another terminal
		if (this.locked && !value && this.blocksInUse > 0) {
			// if a tile is broken
			if (te == null) {
				//is action allowed
				if (port == this.getPort() && pass == this.getPass()) {
					HsbLog.debug("tile destroyed!");
					this.needReconnect = true;
				}
			}
			// TODO if a lock signal was send by a terminal
		}
		return super.receiveSignal(side, te, value, pass, port);
	}
	
	//TODO
	public List<String> getButtons() {
		return buttons;
	}

	@Override
	public int getCamoBlockId() {
		return this.camoId;
	}
	
	@Override
	public void setLocked(boolean lock) {
		if(!lock)
			this.blocksInUse = 0;
		super.setLocked(lock);
	}
	public void setButtons(List<String> l)
	{
		this.buttons = l;
	}

	@Override
	public int getCamoMeta() {
		return this.camoMeta;
	}

	public int getEnergyScaled(int length) {
		int x = (this.energyStored * length
				/ (this.maxEnergyStorage));
		if(x > length)
			x = length;
		return x;
	}

	@Override
	public short getFacing() {
		return facing;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		// TODO InventoryName
		return "HsbTerminal";
	}

	@Override
	public int getMaxSafeInput() {
		return this.safeInput;
	}

	@Override
	public List<String> getNetworkedFields() {
		List<String> list = super.getNetworkedFields();
	    list.add("facing");
	    return list;
	}

	@Override
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

	@Override
	public int getTesla() {
		return tesla;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public float getWrenchDropRate() {
		return 0;
	}
	
	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		//Explosion
		if(amount > this.safeInput && Settings.ic2Available)
		{
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, 0, 0, 2);
			this.worldObj.createExplosion(null, xCoord, yCoord, zCoord, 0.8F, false);
			return 0;
		}
		
		int missing = this.demandsEnergy();
		if (missing < amount) {
			energyStored += missing;
			return amount - missing;
		}
		energyStored += amount;
		return 0;
	}
	
	@Override
	protected void initData() {
		if(!worldObj.isRemote)
		{
			this.onInventoryChanged();
		}
		super.initData();
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return this.isAddedToEnergyNet;
	}


	@Override
	public boolean isInvNameLocalized() {
		// TODO InventoryName
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true; //TODO maybe isStackValidForSlot?
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false
				: entityplayer.getDistanceSq(this.xCoord + 0.5D,
						this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {

		//upgradebuttons 1 - 10
		if(event <= -1 && event >= 10)
		{
			//Upgrade Button
		}
		
		switch (event) {
		case TileEntityHsbTerminal.EVENT_BUTTON_LOCK: 
			this.emitLockSignal(!locked, getPass(), getPort(), 6);
			break;
		case EVENT_GUI_OPTIONS:
		{
			PacketPasswordUpdate packet = new PacketPasswordUpdate(this, this.getPass());
			PacketDispatcher.sendPacketToPlayer(packet.getPacket(), (Player)player);
			player.openGui(ModHsb.instance, GuiIds.GUI_LOCKTERMINAL_OPTIONS, worldObj, xCoord, yCoord, zCoord);
			break;
		}
		case EVENT_GUI_TERMINAL:
		{
//			PacketTerminalButtons packet = new PacketTerminalButtons(this);
//			PacketDispatcher.sendPacketToPlayer(packet.getPacket(), (Player)player);
			player.openGui(ModHsb.instance, GuiIds.GUI_LOCKTERMINAL, worldObj, xCoord, yCoord, zCoord);
			break;
		}
		default:
			//port update (<=0)
			super.onNetworkEvent(player, event);	
		}
	}
	
	@Override
	public void onInventoryChanged() {
		//only on Server
		if(!worldObj.isRemote)
		{
			//reset values
			this.safeInput = 32;
			this.passLength = Settings.defaultPassLength;
			this.securityLevel = 0;
			this.energyUse = Settings.terminalEnergyUse;
			this.maxEnergyStorage = Settings.terminalEnergyStorage;
			//Camo
			this.camoMeta = -1;
			this.camoId = -1;
			
			//Upgrade Data
			//clearing List for new Data
			Map<String, IHsbUpgrade> oldData = upgrades;
			upgrades = new HashMap<String, IHsbUpgrade>();
			
			for(int i = 0; i < this.mainInventory.length; i++)
			{
				ItemStack stack = this.mainInventory[i]; 
				if(stack == null)
				{
					continue;
				}

				IHsbUpgrade upgrade = null;
				///////////////
				//setting key//
				///////////////
				String key = "";
				try{
					//register Upgrades (IHsbUpgrade/IItemHsbUpgrade)
					if(stack.getItem() instanceof ITerminalUpgradeItem)
					{
						ITerminalUpgradeItem item = (ITerminalUpgradeItem) stack.getItem();		
						key = item.getUniqueId(stack.getItemDamage());
						upgrade = UpgradeRegistry.upgradesTerminal.get(key).newInstance();
	//					upgrade = item.getUpgrade(stack.getItemDamage());
					} else if(Settings.ic2Available) {//check for ic2 installation
						if(TileEntityHsbTerminal.getIc2UpgradeKey(stack)!=null)
						{
							key = TileEntityHsbTerminal.getIc2UpgradeKey(stack);
							upgrade = UpgradeRegistry.upgradesMachine.get(key).newInstance();
						}
					} else if(stack.getItem() instanceof IMachineUpgradeItem){
						key = ((IMachineUpgradeItem) stack.getItem()).getUniqueId(stack.getItemDamage());
						upgrade = UpgradeRegistry.upgradesMachine.get(key).newInstance();
					}
					//updating existing  Upgrade
					if(this.getUpgrade(key)!=null)
					{
						upgrade = this.getUpgrade(key);
					} else {
					//adding new Upgrade
						
						this.upgrades.put(key, upgrade);
						if(oldData.containsKey(key) && oldData.get(key) != null){
							upgrade.addInformation(oldData.get(key));
						}
					}
					if(upgrade != null)
						upgrade.setCount(upgrade.getCount() + stack.stackSize);
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
			//updating upgrade information
			for(IHsbUpgrade update: upgrades.values()) 
			{
				if(update != null)
				{
					update.updateUpgrade(this);
					if(update instanceof IUpgradeButton)
					{
						HsbLog.debug("adding Button...");
						buttons.add(((IUpgradeButton) update).getUniqueId());
					}
					
					//TODO add buttons etc.
				}
			}
		}
		super.onInventoryChanged();
	}
	
	private static String getIc2UpgradeKey(ItemStack stack) {
		String key = null;
		if(Settings.ic2Available)
		{
			if(stack.isItemEqual(PluginIC2.getIC2Item("transformerUpgrade")))
			{
				key = UpgradeRegistry.ID_UPGRADE_DUMMY;
			}
				
			if(stack.isItemEqual(PluginIC2.getIC2Item("energyStorageUpgrade")))
			{
				key = UpgradeRegistry.ID_UPGRADE_STORAGE;
			}
		
			if(stack.isItemEqual(PluginIC2.getIC2Item("overclockerUpgrade")))
			{
				key = UpgradeRegistry.ID_UPGRADE_DUMMY;
			}
		}
		return key;
	}
	
	public IHsbUpgrade getUpgrade(String s) {
		return upgrades.get(s);
	}
	@Override
	public void onNetworkUpdate(String field) {
		super.onNetworkUpdate(field);
		 if (field.equals("facing") && prevFacing != facing)
	     {
	         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	         prevFacing = facing;
	     }
	}
	@Override
	public void openChest() {}
	
	@Override
    public void readFromNBT(NBTTagCompound tag)
    {
		super.readFromNBT(tag);
		//facing
		this.facing = tag.getShort("facing");
		prevFacing = this.facing;	
		
		//Items
        NBTTagList nbtlist = tag.getTagList("Items");
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
        
        energyStored = tag.getInteger("energyStored");
        blocksInUse = tag.getInteger("blocksInUse");
        needReconnect = tag.getBoolean("needReconnect");
    }
	
	@Override
	public void setFacing(short facing) {
		this.facing = facing;
        if (this.prevFacing != this.facing)
        {
            NetworkManager.getInstance().updateTileEntityField(this, "facing");
        }
        this.prevFacing = this.facing;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.mainInventory[slot] = itemstack;

		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		//Updateinventory
		this.onInventoryChanged();
	}
	@Override
	public void updateEntity() {
		super.updateEntity();
		//only Server
		if (!worldObj.isRemote) {
			//add to EnergyNet
			if (!isAddedToEnergyNet && Settings.ic2Available) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				isAddedToEnergyNet = true;
			}
		}
		
		//Use Energy
		if (this.locked && this.blocksInUse > 0) {
			//energy usage
			double need = (this.energyUse * this.blocksInUse + 2);
			
			//unlock if out of energy
			if (need > energyStored) {
				LockManager.tranferSignal(this, this, false, getPass(), getPort(), 6);
				HsbLog.debug("not enough energy! need: " + need
						+ " stored: " + energyStored);
			} else {
				energyStored = (int) Math.floor(energyStored - need);
			}
		}
		
		//charge //4:ChargeSlot(-->ContainerLockTerminal)
		ItemStack item = this.getStackInSlot(4);
		if(Settings.ic2Available)
		{
			//charge from battery
			if(item != null && item.getItem() instanceof IElectricItem && (this.demandsEnergy() > 0))
			{
				int leftover = this.injectEnergy(Direction.YN, PluginIC2.discharge(item, this.safeInput, 2, false, false));
				PluginIC2.charge(item, leftover, 2, false, false);	
			}
		} else {
			//charge from coal etc.
			if(item != null && item.stackSize > 0 && isItemFuel(item)) {
				int itemEnergy =  TileEntityHsbTerminal.getItemFuelValue(item);
				if(this.demandsEnergy() >= itemEnergy) {
					this.decrStackSize(4, 1);
					//TODO fix bugs when consuming items
					
					this.injectEnergy(Direction.YN, itemEnergy);
				}
			}
		}
		
		//reconnecting
		if (this.needReconnect) {
			blocksInUse = 0;
			this.needReconnect = false;
			//emit a lock Signal to all sides
			this.emitLockSignal(true, getPass(), getPort(), 6);
			
			HsbLog.debug("reconnecting after breaking a tile!");
		}
	}

    public static boolean isItemFuel(ItemStack is)
    {
        return getItemFuelValue(is) > 0;
    }
    
    public static int getItemFuelValue(ItemStack is) {
    	//works for now, maybe later add restrictons
    	return (int) (TileEntityFurnace.getItemBurnTime(is) * 2.5);
    }
    
	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return false;
	}
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return false;
	}
	
	@Override
    public void writeToNBT(NBTTagCompound tag)
    {
		super.writeToNBT(tag);
		
		tag.setShort("facing", this.getFacing());
		
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
        tag.setTag("Items", nbtlist);
        
        tag.setInteger("energyStored", this.energyStored);
        tag.setInteger("blocksInUse", blocksInUse);
        tag.setBoolean("needReconnect", needReconnect);
		
    }

}
