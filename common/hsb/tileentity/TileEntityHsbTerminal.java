package hsb.tileentity;

import hsb.ModHsb;
import hsb.configuration.Settings;
import hsb.core.plugin.PluginManager;
import hsb.core.plugin.PluginUE;
import hsb.core.plugin.ic2.PluginIC2;
import hsb.core.util.EnergyHelper;
import hsb.core.util.StackUtils;
import hsb.lib.GuiIds;
import hsb.lib.Strings;
import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.lock.LockManager;
import hsb.network.packet.PacketPasswordUpdate;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.terminal.UpgradeCamoflage;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.IMachineUpgradeItem;
import hsb.upgrade.types.INBTUpgrade;
import hsb.upgrade.types.IOnRemoveListener;
import hsb.upgrade.types.ITerminalUpgradeItem;
import hsb.upgrade.types.IUpgradeButton;
import hsb.upgrade.types.IUpgradeHsbTerminal;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.item.IElectricItem;
import ic2.api.tile.IWrenchable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import nwcore.network.NetworkManager;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.block.IElectricalStorage;
import universalelectricity.core.electricity.ElectricityPack;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class TileEntityHsbTerminal extends TileEntityHsbBuilding implements
ILockTerminal, IEnergySink, ISidedInventory, IWrenchable,
IPowerReceptor, IElectrical, IElectricalStorage, IMachine {
	// how many blocks are locked
	private int blocksInUse = 0;

	// if tile wants to reconnect
	private boolean needReconnect = false;

	// IC2
	private boolean isAddedToEnergyNet = false;
	public static final int ic2Tier = 2;

	// only used when no tech mod is installed
	private int burnTime = 0;
	private int maxBurnTime = 1;

	// Energy
	public float energyUse = Settings.terminalEnergyUse;
	public float energyStored = 0;
	public float maxEnergyStorage = Settings.terminalEnergyStorage;

	// input
	public int maxInputIC2 = 32;
	public float maxInputMJ = Settings.terminalBCMaxInput;
	public float maxInputUE = 128;

	// inventory
	private ItemStack[] mainInventory = new ItemStack[this.getSizeInventory()];

	// upgrades

	// is empty on client
	private Map<String, IHsbUpgrade> upgrades;
	// contains button ids
	public List<Integer> buttons;
	// used for upgrades
	NBTTagCompound nbttagcompound;
	// used for upgrades
	private boolean init;

	public int tesla = 0;
	public int securityLevel = 0;
	public int passLength = Settings.defaultPassLength;

	// constants
	public static final int EVENT_BUTTON_LOCK = -11;
	public static final int EVENT_GUI_TERMINAL = -12;
	public static final int EVENT_GUI_OPTIONS = -13;
	public static final int EVENT_CAMOUPGRADE = -20;

	public static final int SLOT_FUEL = 4;

	// facing
	public short facing = 2;
	private short prevFacing = 2;

	// BC power
	@Deprecated
	private PowerHandler bcPowerHandler;

	public TileEntityHsbTerminal() {
		super();
		upgrades = new HashMap<String, IHsbUpgrade>();
		buttons = new ArrayList<Integer>();
		init = false;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return canConnect(direction.toForgeDirection());
	}

	/**
	 * add a locked block, increasdes energy use
	 */
	@Override
	public void addBlockToTileEntity(ILockable te) {
		this.blocksInUse++;
		this.energyUse += te.getEnergyUsageForBlock();
		for(IUpgradeHsbTerminal upgrade : getTerminalUpgrades()) {
			this.energyUse += upgrade.getEnergyUse(blocksInUse, this, te);
		}
	}
	
	

	/**
	 * adds a button to the list
	 * 
	 * @param id
	 */
	private void addButton(int id) {
		ModHsb.logger.debug("adding button");
		if (buttons == null) {
			buttons = new ArrayList<Integer>();
		}
		buttons.add(id);
	}
	
	public List<IUpgradeHsbTerminal> getTerminalUpgrades() {
		List<IUpgradeHsbTerminal> list = new ArrayList<IUpgradeHsbTerminal>();
		for(IHsbUpgrade upgrade : upgrades.values()) {
			if(upgrade instanceof IUpgradeHsbTerminal) {
				list.add((IUpgradeHsbTerminal) upgrade);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param dir
	 *            source
	 * @param amount
	 *            in MJ
	 * @param addEnergy
	 *            if false action is simulated
	 * @return usedEnergy
	 */
	public float addEnergy(ForgeDirection dir, float amount, boolean addEnergy) {
		if (!canConnect(dir) || amount == 0) {
			return 0;
		}
		float used = EnergyHelper
				.addEnergy(getEnergy(), getMaxEnergy(), amount);
		if (addEnergy) {
			energyStored += used;
		}
		return used;
	}

	/**
	 * add an item to the terminals inventory
	 * 
	 * @param item
	 * @param number
	 *            how many should be added, can be -1 for all
	 * @return itemstack
	 */
	public ItemStack addToInventory(ItemStack item, int number) {
		// setting number
		if (number == -1) {
			number = item.stackSize;
		}
		// setting slot
		int start = 0; // including
		int end = this.getSizeInventory();// excluding --> see for-loop
		if (item == null) {
			return item;
		}
		if (item.getItem() instanceof IMachineUpgradeItem) {
			start = 0;
			end = 4;
		} else if (item.getItem() instanceof ITerminalUpgradeItem) {
			start = 5;
			end = this.getSizeInventory();
		} else if (Settings.usePluginIC2) {
			if (item.getItem() instanceof IElectricItem) {
				start = 4;
				end = 5;
			}
		} else {
			if (StackUtils.isItemFuel(item)) {
				start = 4;
				end = 5;
			}
		}
		// adding item
		for (int i = start; i < end; i++) {
			ItemStack slot = this.getStackInSlot(i);
			if (slot == null) {
				ItemStack stack = item.splitStack(number);
				this.mainInventory[i] = stack;
				// this.mainInventory[i].stackSize = number; //setting stacksize
				// item.stackSize = item.stackSize - number;//decreasing
				// stacksize of item
				return item;
			}
			if (slot.isItemEqual(item)) {
				int size = slot.stackSize + number;
				if (size <= slot.getMaxStackSize()) {
					this.mainInventory[i].stackSize = size; // setting slot
					item.stackSize = item.stackSize - number; // setting new
					// stacksize to
					// return
					if (item.stackSize == 0)
						item = null;
					return item;
				} else {
					int rest = size - slot.getMaxStackSize();
					this.mainInventory[i].stackSize = slot.getMaxStackSize();
					item.stackSize = rest;
					return item;
				}
			}
		}
		return item;
	}

	/**
	 * does connect to bcPowerHandler ?
	 */
	@Override
	public boolean canConnect(ForgeDirection direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i == SLOT_FUEL;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if (i == SLOT_FUEL) {
			if (isItemValidForSlot(i, itemstack)) {
				ItemStack stack = this.mainInventory[i];
				if ((stack.stackSize + itemstack.stackSize) > 64) { // needed ?
					return false;
				}
				return true;
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
	@Deprecated
	public int demandsEnergy() {
		return (int) PluginIC2.convertToEU(needsEnergy());
	}

	@Override
	public void doWork(PowerHandler workProvider) {
	}

	/**
	 * sends out a lock signal
	 * 
	 * @param lock
	 * @param pass
	 * @param port
	 * @param ignoreSide
	 */
	private void emitLockSignal(boolean lock, String pass, int port,
			int ignoreSide) {
		if (!worldObj.isRemote) {
			if (lock && getEnergy() < getEnergyUse() * 2)
				return;
			this.setLocked(lock);
			LockManager.tranferSignal(this, this, lock, pass, port, ignoreSide);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[] { SLOT_FUEL };
	}

	/**
	 * get the bcPowerHandler
	 * 
	 * @return
	 */
	private PowerHandler getBCPowerHandler() {
		if (bcPowerHandler == null) {
			bcPowerHandler = new PowerHandler(this, PowerHandler.Type.STORAGE);
			bcPowerHandler
			.configure(Settings.terminalBCMinInput,
					Settings.terminalBCMaxInput, 0,
					Settings.terminalBCMaxInput);
		}
		return bcPowerHandler;
	}

	/**
	 * 
	 * @param length
	 * @return returns burnTime left or charge in item left, scaled to length
	 */
	public int getBurnOrChargeScaled(int length) {
		int max = 0;
		int current = 0;

		if (PluginManager.energyModInstalled_Item()) {
			if (Settings.usePluginIC2) {
				current = PluginManager
						.getElectricChargeInItem(mainInventory[SLOT_FUEL]);
				max = PluginManager
						.getMaxElectricChargeInItem(mainInventory[SLOT_FUEL]);
			}
		} else {
			current = this.burnTime;
			max = this.maxBurnTime;
		}
		if (max == 0 || max == -1)
			return 0;

		int x = current * length / max;
		if (x > length)
			x = length;
		return x;
	}

	/**
	 * gets the upgrade for that position
	 * 
	 * @param id
	 * @return
	 */
	public IUpgradeButton getButton(int id) {
		if (buttons.size() <= id) {
			return null;
		}
		return (IUpgradeButton) upgrades.get(UpgradeRegistry.idToInt
				.get(buttons.get(id)));

	}

	@Override
	public int getCamoBlockId() {
		return this.camoId;
	}

	@Override
	public int getCamoMeta() {
		return this.camoMeta;
	}

	/**
	 * get energy (MJ) stored
	 */
	public float getEnergy() {
		return energyStored;
	}

	public int getEnergyScaled(int length) {
		int x = (int) (getEnergy() * length / getMaxEnergy());
		if (x > length)
			x = length;
		return x;
	}

	@Override
	@Deprecated
	public float getEnergyStored() {
		return PluginUE.convertToUE(getEnergy());
	}

	/**
	 * how much energy (MJ) is probably going to be used (inaccurate)
	 */
	public float getEnergyUse() {
		return energyUse;
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
		return StatCollector.translateToLocal(Strings.CONTAINER_TERMINAL_NAME);
	}

	/**
	 * MJ
	 * 
	 * @return
	 */
	public float getMaxEnergy() {
		return maxEnergyStorage;
	}

	@Override
	@Deprecated
	public float getMaxEnergyStored() {
		return PluginUE.convertToUE(getMaxEnergy());
	}

	@Override
	public int getMaxSafeInput() {
		return maxInputIC2;
	}

	@Override
	public List<String> getNetworkedFields() {
		List<String> list = super.getNetworkedFields();
		list.add("facing");
		return list;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return getBCPowerHandler().getPowerReceiver();
	}

	@Override
	@Deprecated
	public float getProvide(ForgeDirection direction) {
		return 0; // block doesn't provide energy
	}

	@Override
	@Deprecated
	public float getRequest(ForgeDirection direction) {
		return PluginUE.convertToUE(needsEnergy());
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

	/**
	 * get the upgrade for that id
	 * 
	 * @param s
	 * @return
	 */
	public IHsbUpgrade getUpgrade(String s) {
		return upgrades.get(s);
	}

	@Override
	@Deprecated
	public float getVoltage() {
		return maxInputUE; // TODO voltage (upgrades)
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public float getWrenchDropRate() {
		return 0;
	}

	/**
	 * increases maxStorage
	 * 
	 * @param s
	 */
	public void increaseMaxStorage(float s) {
		this.maxEnergyStorage += s;
	}

	/**
	 * inits upgrades
	 */
	protected void initData() {
		if (!worldObj.isRemote) {
			init = true;
			this.onInventoryChanged();
			// init upgrades
			for (IHsbUpgrade upgrade : upgrades.values()) {
				if (upgrade instanceof INBTUpgrade) {
					((INBTUpgrade) upgrade).readFromNBT(this.nbttagcompound);
					upgrade.updateUpgrade(this);
				}
			}
			this.nbttagcompound = null;

		}
	}

	/**
	 * @param amount
	 *            in EU
	 * @return leftover
	 */
	@Override
	@Deprecated
	public int injectEnergy(Direction directionFrom, int amount) {
		return (int) (amount - PluginIC2.convertToEU(addEnergy(
				directionFrom.toForgeDirection(),
				PluginIC2.convertToMJ(amount), true)));
	}

	@Override
	public void invalidate() {
		this.unloadTileIC2();
		super.invalidate();
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return this.isAddedToEnergyNet;
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i == SLOT_FUEL) {
			if (PluginManager.energyModInstalled_Item()) {
				return PluginManager.isItemElectricValid(itemstack);
			} else {
				return StackUtils.isItemFuel(itemstack);
			}
		} else {
			return true;
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false
						: entityplayer.getDistanceSq(this.xCoord + 0.5D,
								this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	/**
	 * 
	 * @return amount of energy needed until internal storage is full
	 */
	public float needsEnergy() {
		return getMaxEnergy() - getEnergy();
	}

	@Override
	public void onChunkUnload() {
		this.unloadTileIC2();
		super.onChunkUnload();
	}

	@Override
	public void onInventoryChanged() {
		// only on Server
		if (!worldObj.isRemote) {
			// reset values
			// BC
			bcPowerHandler
			.configure(Settings.terminalBCMinInput,
					Settings.terminalBCMaxInput, 0,
					Settings.terminalBCMaxInput);
			// Energy
			maxEnergyStorage = Settings.terminalEnergyStorage;

			this.passLength = Settings.defaultPassLength;
			this.securityLevel = 0;
			this.energyUse = Settings.terminalEnergyUse;
			// Camo
			this.camoMeta = -1;
			this.camoId = -1;

			this.buttons = new ArrayList<Integer>();
			// Upgrade Data
			// clearing List for new Data
			Map<String, IHsbUpgrade> oldData = upgrades;
			upgrades = new HashMap<String, IHsbUpgrade>();

			for (int i = 0; i < this.mainInventory.length; i++) {
				ItemStack stack = this.mainInventory[i];
				if (stack == null) {
					continue;
				}

				IHsbUpgrade upgrade = null;
				// /////////////
				// setting key//
				// /////////////
				String key = "";
				try {
					// register Upgrades (IHsbUpgrade/IItemHsbUpgrade)
					if (stack.getItem() instanceof ITerminalUpgradeItem) {
						ITerminalUpgradeItem item = (ITerminalUpgradeItem) stack
								.getItem();
						key = item.getUniqueId(stack.getItemDamage());
						upgrade = UpgradeRegistry.upgradesTerminal.get(key)
								.newInstance();

					} else if (stack.getItem() instanceof IMachineUpgradeItem) {
						key = ((IMachineUpgradeItem) stack.getItem())
								.getUniqueId(stack.getItemDamage());
						upgrade = UpgradeRegistry.upgradesMachine.get(key)
								.newInstance();

					} else if (Settings.usePluginIC2) {// check for ic2
						// installation and get
						// upgrades
						if (PluginIC2.getIc2UpgradeKey(stack) != null) {
							key = PluginIC2.getIc2UpgradeKey(stack);
							upgrade = UpgradeRegistry.upgradesMachine.get(key)
									.newInstance();
						}
					}
					// updating existing Upgrade
					if (this.getUpgrade(key) != null) {
						upgrade = this.getUpgrade(key);
					} else {
						// adding new Upgrade

						this.upgrades.put(key, upgrade);
						if (oldData.containsKey(key)
								&& oldData.get(key) != null) {
							upgrade.addInformation(oldData.get(key));
						}
					}
					if (upgrade != null)
						upgrade.setCount(upgrade.getCount() + stack.stackSize);

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
			// updating upgrade information
			for (IHsbUpgrade upgrade : upgrades.values()) {
				if (upgrade != null) {
					upgrade.updateUpgrade(this);
					if (upgrade instanceof IUpgradeButton) {
						addButton(UpgradeRegistry.idToInt.indexOf(upgrade
								.getUniqueId()));
					}
				}
			}

			// remove old upgrades
			for (String key : oldData.keySet()) {
				if (!upgrades.containsKey(key)) {
					IHsbUpgrade upgrade = oldData.get(key);
					if (upgrade instanceof IOnRemoveListener) {
						((IOnRemoveListener) upgrade).onRemove(worldObj,
								xCoord, yCoord, zCoord, 0, 0);
					}
				}
			}
		}
		super.onInventoryChanged();
	}

	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {

		// upgradebuttons 1 - 10
		if (event <= -1 && event >= -10) {
			if (buttons.size() >= event * (-1)) {
				// Upgrade Button
				IUpgradeButton button = getButton(event * (-1) - 1);
				if (button != null) {
					button.onButtonClicked(player, this);
					return;
				}
			}

		}

		switch (event) {
			case TileEntityHsbTerminal.EVENT_BUTTON_LOCK:
				this.emitLockSignal(!locked, getPass(), getPort(), 6);
				break;
			case EVENT_GUI_OPTIONS: {
				if (locked) {
					break;
				}
				PacketPasswordUpdate packet = new PacketPasswordUpdate(this,
						this.getPass());
				PacketDispatcher.sendPacketToPlayer(packet.getPacket(),
						(Player) player);
				player.openGui(ModHsb.instance,
						GuiIds.GUI_LOCKTERMINAL_OPTIONS, worldObj, xCoord,
						yCoord, zCoord);
				break;
			}
			case EVENT_GUI_TERMINAL: {
				player.openGui(ModHsb.instance, GuiIds.GUI_LOCKTERMINAL,
						worldObj, xCoord, yCoord, zCoord);
				break;
			}
			case EVENT_CAMOUPGRADE: {
				UpgradeCamoflage upgrade = (UpgradeCamoflage) getUpgrade(UpgradeRegistry.ID_UPGRADE_CAMO);
				if (upgrade != null) {
					upgrade.onActivateClicked(player, this);
					break;
				} else {
					ModHsb.logger.severe("invalid upgradeCamo event!");
				}
			}
			default:
				// port update (<=0)
				super.onNetworkEvent(player, event);
		}
	}

	@Override
	public void onNetworkUpdate(String field) {
		super.onNetworkUpdate(field);
		if (field.equals("facing") && prevFacing != facing) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevFacing = facing;
		}
	}

	@Override
	public void onRemove(World world, int x, int y, int z, int par5, int par6) {
		// only on server
		if (!this.worldObj.isRemote) {
			// on Remove upgrades:
			for (IHsbUpgrade upgrade : upgrades.values()) {
				if (upgrade instanceof IOnRemoveListener) {
					((IOnRemoveListener) upgrade).onRemove(world, x, y, z,
							par5, par6);
				}
			}
			// drop inventory
			for (int i = 0; i < this.getSizeInventory(); ++i) {
				ItemStack itemstack = this.getStackInSlot(i);

				if (itemstack != null) {
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int k1 = world.rand.nextInt(21) + 10;

						if (k1 > itemstack.stackSize) {
							k1 = itemstack.stackSize;
						}

						itemstack.stackSize -= k1;
						EntityItem entityitem = new EntityItem(world, x + f, y
								+ f1, z + f2, new ItemStack(itemstack.itemID,
										k1, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound(
									(NBTTagCompound) itemstack.getTagCompound()
									.copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (float) world.rand.nextGaussian()
								* f3;
						entityitem.motionY = (float) world.rand.nextGaussian()
								* f3 + 0.2F;
						entityitem.motionZ = (float) world.rand.nextGaussian()
								* f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
		super.onRemove(world, x, y, z, par5, par6);
	}

	@Override
	public void openChest() {
	}

	@Override
	@Deprecated
	public ElectricityPack provideElectricity(ForgeDirection from,
			ElectricityPack request, boolean doProvide) {
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		// facing
		this.facing = tag.getShort("facing");
		prevFacing = this.facing;

		// Items
		NBTTagList nbtlist = tag.getTagList("Items");
		this.mainInventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbtlist.tagCount(); ++i) {
			NBTTagCompound nbttag = (NBTTagCompound) nbtlist.tagAt(i);
			byte slot = nbttag.getByte("Slot");

			if (slot >= 0 && slot < this.mainInventory.length) {
				this.mainInventory[slot] = ItemStack
						.loadItemStackFromNBT(nbttag);
			}
		}
		bcPowerHandler.readFromNBT(tag);

		this.energyStored = tag.getFloat("energyStored");

		blocksInUse = tag.getInteger("blocksInUse");
		this.energyUse = tag.getFloat("energyUse");
		needReconnect = tag.getBoolean("needReconnect");

		// no tech mod
		if (!PluginManager.energyModInstalled_Item()) {
			if (tag.hasKey("burnTime")) {
				burnTime = tag.getInteger("burnTime");
				maxBurnTime = tag.getInteger("maxBurnTime");
			}
		}

		this.nbttagcompound = tag;

	}

	@Override
	@Deprecated
	public float receiveElectricity(ForgeDirection from,
			ElectricityPack receive, boolean doReceive) {
		return PluginUE.convertToUE(addEnergy(from,
				PluginUE.convertToMJ(receive.amperes), doReceive));
	}

	@Override
	public boolean receiveSignal(int side, ILockTerminal te, boolean value,
			String pass, int port) {
		if (this.worldObj.isRemote) {
			return true;
		}
		// this is locked and is not locked by another terminal
		if (this.locked && !value && this.blocksInUse > 0) {
			// if a tile is broken
			if (te == null) {
				// is action allowed
				if (port == this.getPort() && pass == this.getPass()) {
					ModHsb.logger.debug("tile destroyed!");
					this.needReconnect = true;
				}
			}
			// TODO if a lock signal was send by a terminal
		}
		return super.receiveSignal(side, te, value, pass, port);
	}

	/**
	 * sets energy in MJ
	 * 
	 * @param f
	 */
	public void setEnergy(float f) {
		energyStored = f;
	}

	@Override
	@Deprecated
	public void setEnergyStored(float energy) {
		setEnergy(PluginUE.convertToMJ(energy));
	}

	@Override
	public void setFacing(short facing) {
		this.facing = facing;
		if (this.prevFacing != this.facing) {
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
	}

	@Override
	public void setLocked(boolean lock) {
		if (!lock) {
			this.blocksInUse = 0;
			this.energyUse = 2;
		}
		super.setLocked(lock);
	}

	/**
	 * unloads IC2 Tile
	 */
	private void unloadTileIC2() {
		if (this.isAddedToEnergyNet && this.worldObj != null) {
			if (Settings.usePluginIC2) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}

			this.isAddedToEnergyNet = false;
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (worldObj.isRemote) {
			return;
		}
		// BC3 get power from bc powerHandler
		{
			getBCPowerHandler().update();
			float need = needsEnergy();
			if (need > 0) {
				float used = getBCPowerHandler()
						.useEnergy(
								1.0F,
								Math.min(need, getBCPowerHandler()
										.getMaxEnergyStored()), true);
				addEnergy(ForgeDirection.UNKNOWN, used, true);
			}
		}

		// Init IC2
		{
			// add to EnergyNet
			if (!isAddedToEnergyNet && Settings.usePluginIC2) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				isAddedToEnergyNet = true;
			}
		}
		// Init upgrades
		if (!init) {
			initData();
		}

		// Use Energy
		if (this.locked) {
			// energy usage
			int need = (int) ((this.energyUse * this.blocksInUse) + 2);

			if (useEnergy(need, true) < need) {
				LockManager.tranferSignal(this, this, false, getPass(),
						getPort(), 6);
			}
		}

		// charge
		ItemStack item = this.getStackInSlot(SLOT_FUEL);
		if (PluginManager.energyModInstalled_Item()) {
			addEnergy(ForgeDirection.UNKNOWN, PluginManager.dichargeItem(item,
					this.needsEnergy(), ic2Tier, false, false), true);
		} else {
			// no energy mod
			if (burnTime <= 0) {
				if (mainInventory[SLOT_FUEL] != null
						&& mainInventory[SLOT_FUEL].stackSize > 0
						&& this.getEnergy() < this.getMaxEnergy()) {
					maxBurnTime = burnTime = StackUtils
							.getItemFuelValue(mainInventory[SLOT_FUEL]);
					mainInventory[SLOT_FUEL].stackSize--;
				}
			}

			if (burnTime > 0) {
				burnTime -= 5;
				addEnergy(ForgeDirection.UNKNOWN, 5, true);
			}
		}

		// reconnecting
		if (this.needReconnect) {
			blocksInUse = 0;
			this.needReconnect = false;
			// emit a lock Signal to all sides
			this.emitLockSignal(true, getPass(), getPort(), 6);

			ModHsb.logger.debug("reconnecting after breaking a tile!");
		}
	}

	public float useEnergy(float energy, boolean doUse) {
		if (getEnergy() - energy < 0) {
			energy = getEnergy();
		}
		if (doUse) {
			energyStored -= energy;
		}
		return energy;
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
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setShort("facing", this.getFacing());

		// Items
		NBTTagList nbtlist = new NBTTagList();

		for (int i = 0; i < this.mainInventory.length; ++i) {
			if (this.mainInventory[i] != null) {
				NBTTagCompound nbttag = new NBTTagCompound();
				nbttag.setByte("Slot", (byte) i);
				this.mainInventory[i].writeToNBT(nbttag);
				nbtlist.appendTag(nbttag);
			}
		}
		tag.setTag("Items", nbtlist);

		tag.setFloat("energyStored", this.energyStored);
		bcPowerHandler.writeToNBT(tag);

		tag.setInteger("blocksInUse", blocksInUse);
		tag.setFloat("energyUse", energyUse);
		tag.setBoolean("needReconnect", needReconnect);

		// no tech mod
		if (!PluginManager.energyModInstalled_Item()) {
			tag.setInteger("maxBurnTime", maxBurnTime);
			tag.setInteger("burnTime", burnTime);
		}

		// init upgrades
		onInventoryChanged();
		for (IHsbUpgrade upgrade : upgrades.values()) {
			if (upgrade instanceof INBTUpgrade) {
				((INBTUpgrade) upgrade).writeToNBT(tag);
			}
		}
	}

}
