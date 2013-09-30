package hsb.tileentity;

import hsb.configuration.Settings;
import hsb.core.plugin.PluginManager;
import hsb.core.plugin.PluginUE;
import hsb.core.util.EnergyHelper;
import hsb.core.util.StackUtils;
import hsb.lib.Strings;
import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.lock.LockManager;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.tile.IWrenchable;

import java.util.List;
import java.util.Vector;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
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

public class TileEntityUnlocker extends TileEntitySimple
	implements IWrenchable, INetworkClientTileEntityEventListener, INetworkDataProvider, INetworkUpdateListener, ISidedInventory,
				IEnergySink, IPowerReceptor, IElectrical, IElectricalStorage, IMachine 
{

	public short facing = 0;
	private short prevFacing = 0;
	
//	public int energyStored = 0;
	private ItemStack inv;
	
	public int burnTime = 0;
	private int maxBurnTime = 1;
	
	private float energyStored = 0;
	private float maxEnergyStored = Settings.unlockerEnergyStorage;
	
	
	public int progress = 0;
	
	public boolean active = false;
	
	public int ticksToUnlock = Settings.ticksToUnlock;
	
	private boolean isAddedToEnergyNet = false;
	
	private static int  TIER = 2;
	
	@Deprecated
	private PowerHandler power;
	
	public TileEntityUnlocker() {
		super();		
	}
	
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return true;
	}

	@Override
	public short getFacing() {
		return facing;
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
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public float getWrenchDropRate() {
		return 1F;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		return new ItemStack(this.blockType.idDropped(meta, this.worldObj.rand, 0), meta, 1);
	}

	public int getEnergyScaled(int length) {
		float x = (this.getEnergy() * length
				/ maxEnergyStored);
		if(x > length)
			x = length;
		return (int) x;
	}
	public int getBurnOrChargeScaled(int length) {
		int max = 0;
		int current = 0;
		
		if(PluginManager.energyModInstalled_Item()) {
			if(Settings.usePluginIC2) {
				current = PluginManager.getElectricChargeInItem(inv);
				max = PluginManager.getMaxElectricChargeInItem(inv);
			}
		} else {
			current = this.burnTime;
			max = this.maxBurnTime;
		}
		if(max == 0 || max == -1)
			return 0;
		
		int x = current * length / max;
		if(x > length)
			x = length;
		return x;		
	}
	
	public int getProgressScaled(int length) {
		if(progress == -1)
			return 0;
		int x = (this.progress * length
				/ (ticksToUnlock));
		if(x > length)
			x = length;
		return x;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound tag)
    {
		super.readFromNBT(tag);
		//facing
		this.facing = tag.getShort("facing");
		prevFacing = this.facing;	
		
		//Items
        NBTTagList nbtlist = tag.getTagList("Items");

        for (int i = 0; i < nbtlist.tagCount(); ++i)
        {
            NBTTagCompound nbttag = (NBTTagCompound)nbtlist.tagAt(i);
            this.inv = ItemStack.loadItemStackFromNBT(nbttag);
        }
        
        getBCPowerHandler().readFromNBT(tag);
        
        energyStored = tag.getFloat("energyStored");
        maxEnergyStored = tag.getFloat("maxEnergyStored");
        
        if(!PluginManager.energyModInstalled_Item()) {
        	if(tag.hasKey("burnTime")) {
	        burnTime = tag.getInteger("burnTime");
	        maxBurnTime = tag.getInteger("maxBurnTime");
        	}
        }
        progress = tag.getInteger("progress");
        
        active = tag.getBoolean("active");

    }
	
	
	@Override
    public void writeToNBT(NBTTagCompound tag)
    {
		super.writeToNBT(tag);
		
		tag.setShort("facing", this.getFacing());
		
		//Items
        NBTTagList nbtlist = new NBTTagList();

        if (this.inv != null)
        {
            NBTTagCompound nbttag = new NBTTagCompound();
            this.inv.writeToNBT(nbttag);
            nbtlist.appendTag(nbttag);
        }
        tag.setTag("Items", nbtlist);
        
        getBCPowerHandler().writeToNBT(tag);
        
        tag.setFloat("energyStored", energyStored);
        tag.setFloat("maxEnergyStored", maxEnergyStored);
        
        if(!PluginManager.energyModInstalled_Item()) {
	        tag.setInteger("maxBurnTime", maxBurnTime);
	        tag.setInteger("burnTime", burnTime);
        }
        tag.setInteger("progress", progress);
        
        tag.setBoolean("active", active);
    }
	
	@Override
	public void invalidate()
	{
		this.unloadTileIC2();
		super.invalidate();
	}

	@Override
	public void onChunkUnload()
	{
		this.unloadTileIC2();
		super.onChunkUnload();
	}
	

	private void unloadTileIC2()
	{
		if (this.isAddedToEnergyNet && this.worldObj != null)
		{
			if (Settings.usePluginIC2)
			{
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}

			this.isAddedToEnergyNet = false;
		}
	}
	
	@Override
	public void updateEntity() {
		
		//BC3
		{
			getBCPowerHandler().update();
			float need = needsEnergy();
			if(need > 0) {
				float used = getBCPowerHandler().useEnergy(1.0F, Math.min(need, getBCPowerHandler().getMaxEnergyStored()), true);
				this.addEnergy(ForgeDirection.UNKNOWN, used, true);
			}
		}
		//Init IC2
		if (!worldObj.isRemote) {
			//add to EnergyNet
			if (!isAddedToEnergyNet && Settings.usePluginIC2) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				isAddedToEnergyNet = true;
			}
		}
		if(PluginManager.energyModInstalled_Item()) {
			addEnergy(ForgeDirection.UNKNOWN, PluginManager.dischargeItem(inv, needsEnergy(), TIER, false, false), true);
		} else {
			//no energy mod
			if( burnTime <= 0) {
				if(inv != null && inv.stackSize > 0 && this.getEnergy() < this.getMaxEnergy())
				{
					maxBurnTime = burnTime = StackUtils.getItemFuelValue(inv);
					inv.stackSize--;
				}
			}
			//use up coal
			if(burnTime > 0) {
				burnTime -= 5;
				addEnergy(ForgeDirection.UNKNOWN, 5, true);
			}
		}
		
		if(active)
		{
			if(getEnergy() > getEnergyUse()) {
				progress++;
				useEnergy(getEnergyUse(), true);
				if(progress > ticksToUnlock) {
					progress = 0;
					unlock();
					active = false;
				}
			}
		}
		super.updateEntity();
	}
	
	private boolean unlock() {
		if(worldObj.isRemote)
			return true;
		int side = Facing.oppositeSide[facing];
		TileEntity te = this.worldObj.getBlockTileEntity(
									Facing.offsetsXForSide[side] + xCoord,
									Facing.offsetsYForSide[side] + yCoord, 
									Facing.offsetsZForSide[side] + zCoord);
		if(te != null && te instanceof ILockable)
		{
			if(!LockManager.tranferSignal(this, null, false, ((ILockable) te).getPass(), ((ILockable) te).getPort(), 6))
				progress = -2;
		} else {
			progress = -2;
		}
		if(progress == -2)
			return false;
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
        if (this.inv != null)
        {
            ItemStack itemstack;

            if (this.inv.stackSize <= j)
            {
                itemstack = this.inv;
                this.inv = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inv.splitStack(j);

                if (this.inv.stackSize == 0)
                {
                    this.inv = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.inv != null)
        {
            ItemStack itemstack = inv;
            this.inv = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.inv = itemstack;

		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}

	@Override
	public String getInvName() {
		return Strings.CONTAINER_UNLOCKER;
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false
				: entityplayer.getDistanceSq(this.xCoord + 0.5D,
						this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return StackUtils.isItemFuel(itemstack);
	}

	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {
		switch(event) {
			case 0:
				active = !active;
				if(active) {
					int side = Facing.oppositeSide[facing];
					TileEntity tile = worldObj.getBlockTileEntity(	Facing.offsetsXForSide[side] + xCoord,
																	Facing.offsetsYForSide[side] + yCoord,
																	Facing.offsetsZForSide[side] + zCoord);
					if(tile != null && tile instanceof ILockable)
					{
						ILockTerminal terminal = ((ILockable)tile).getConnectedTerminal();
						if(terminal != null)
							ticksToUnlock = Settings.ticksToUnlock * (1 + terminal.getSecurityLevel()); 
					} else {
						active = false;
						progress = -1;
					}
				}
				break;
		}
	}

	@Override
	public void onNetworkUpdate(String field) {
		if (field.equals("facing") && prevFacing != facing)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			prevFacing = facing;
		}
	}

	@Override
	public List<String> getNetworkedFields() {
		List<String> list = new Vector<String>();
	    list.add("facing");
	    return list;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return canConnect(direction);
	}

	@Override
	public boolean canConnect(ForgeDirection direction) {
		return true;
	}

	/**
	 * sets energy in MJ
	 * @param f
	 */
	public void setEnergy(float f) {
		energyStored = f;
	}
	/**
	 * get energy (MJ) stored
	 */
	public float getEnergy() {
		return energyStored;
	}
	/**
	 * how much energy is needed?
	 * @return
	 */
	public float needsEnergy() {
		return maxEnergyStored - energyStored;
	}
	/**
	 * how much energy (MJ) is probably going to be used (inaccurate)
	 */
	public float getEnergyUse() {
		//TODO
		return Settings.unlockerEnergyUse;
	}
	
	/**
	 * MJ
	 * @return
	 */
	public float getMaxEnergy() {
		return maxEnergyStored;
	}
	
	/**
	 * 
	 * @param dir source
	 * @param amount in MJ
	 * @param addEnergy if false action is simulated
	 * @return usedEnergy
	 */
	public float addEnergy(ForgeDirection dir, float amount, boolean addEnergy) {
		if(!canConnect(dir) || amount == 0) {
			return 0;
		}
		return EnergyHelper.addEnergy(energyStored, maxEnergyStored, amount);
	}
	
	/**
	 * increases maxStorage
	 * @param s
	 */
	public void increaseMaxStorage(float s) {
		this.maxEnergyStored += s;
	}
	
	public float useEnergy(float energy, boolean doUse) {
		if(getEnergy() - energy < 0)
		{
			energy = getEnergy();
		}
		if(doUse) {
			energyStored -= energy;
		}
		return energy;
	}
	@Override
	@Deprecated
	public void setEnergyStored(float energy) {
		setEnergy(PluginUE.convertToEU(energy));
	}

	@Override
	@Deprecated
	public float getEnergyStored() {
		return PluginUE.convertToUE(getEnergy());
	}

	@Override
	@Deprecated
	public float getMaxEnergyStored() {
		return PluginUE.convertToUE(getMaxEnergy());
	}

	@Override
	@Deprecated
	public float receiveElectricity(ForgeDirection from,
			ElectricityPack receive, boolean doReceive) {
		return PluginUE.convertToUE(addEnergy(from, PluginUE.convertToEU(receive.amperes), doReceive));
	}

	@Override
	@Deprecated
	public ElectricityPack provideElectricity(ForgeDirection from,
			ElectricityPack request, boolean doProvide) {
		return null;
	}

	@Override
	@Deprecated
	public float getRequest(ForgeDirection direction) {
		return PluginUE.convertToUE(needsEnergy());
	}

	@Override
	@Deprecated
	public float getProvide(ForgeDirection direction) {
		return 0;
	}

	@Override
	@Deprecated
	public float getVoltage() {
		return 120; //TODO voltage (upgrades)
	}

	/**
	 * get the bcPowerHandler
	 * @return
	 */
	private PowerHandler getBCPowerHandler() {
		if(power == null) {
			power = new PowerHandler(this, PowerHandler.Type.STORAGE);
			power.configure(Settings.terminalBCMinInput, Settings.terminalBCMaxInput, 0, Settings.terminalBCMaxInput);
		}
		return power;
	}
	
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return getBCPowerHandler().getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public int getMaxSafeInput() {
		if(Settings.usePluginIC2) {
			EnergyNet.instance.getPowerFromTier(TIER);
		}
		return 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}


	@Override
	public void onRemove(World world, int x, int y, int z, int par1, int par2) {
		//drop inventory
		 for (int i = 0; i < this.getSizeInventory(); ++i)
           {
               ItemStack itemstack = this.getStackInSlot(i);

               if (itemstack != null)
               {
                   float f = world.rand.nextFloat() * 0.8F + 0.1F;
                   float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                   float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                   while (itemstack.stackSize > 0)
                   {
                       int k1 = world.rand.nextInt(21) + 10;

                       if (k1 > itemstack.stackSize)
                       {
                           k1 = itemstack.stackSize;
                       }

                       itemstack.stackSize -= k1;
                       EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                       if (itemstack.hasTagCompound())
                       {
                           entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                       }

                       float f3 = 0.05F;
                       entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                       entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                       entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                       world.spawnEntityInWorld(entityitem);
                   }
               }
           }		
	}


	@Override
	public double demandedEnergyUnits() {
		return needsEnergy();
	}


	@Override
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount) {
		return addEnergy(directionFrom, (float) amount, true);
	}

}
