package hsb.tileentity;

import hsb.configuration.Settings;
import hsb.core.helper.StackUtils;
import hsb.lib.Strings;
import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.lock.LockManager;
import hsb.network.NetworkManager;
import ic2.api.IWrenchable;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;

import java.util.List;
import java.util.Vector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;

public class TileEntityUnlocker extends TileEntitySimple
	implements IWrenchable, IInventory, INetworkClientTileEntityEventListener, INetworkDataProvider, INetworkUpdateListener
{

	public short facing = 0;
	private short prevFacing = 0;
	
	public int energyStored = 0;
	private ItemStack inv;
	
	public int burnTime = 0;
	private int maxBurnTime = 1;
	public int progress = 0;
	
	public boolean active = false;
	
	public int ticksToUnlock = Settings.ticksToUnlock;
	
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
		int x = (this.energyStored * length
				/ (Settings.maxEnergyStorageUnlocker));
		if(x > length)
			x = length;
		return x;
	}
	public int getBurnTimeScaled(int length) {
		int x = (this.burnTime * length
				/ (maxBurnTime));
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
        
        energyStored = tag.getInteger("energyStored");
        burnTime = tag.getInteger("burnTime");
        maxBurnTime = tag.getInteger("maxBurnTime");
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
        
        tag.setInteger("energyStored", this.energyStored);
        tag.setInteger("maxBurnTime", maxBurnTime);
        tag.setInteger("burnTime", burnTime);
        tag.setInteger("progress", progress);
        
        tag.setBoolean("active", active);
    }
	
	@Override
	public void updateEntity() {
		//TODO
		if(burnTime <= 0) {
			if(inv != null && inv.stackSize > 0 && this.energyStored < Settings.maxEnergyStorageUnlocker)
			{
				maxBurnTime = burnTime = StackUtils.getItemBurnTime(inv);
				inv.stackSize--;
			}
		}
		
		if(burnTime > 0) {
			burnTime -= 2;
			energyStored += 5;
			if(energyStored > Settings.maxEnergyStorageUnlocker) {
				energyStored = Settings.maxEnergyStorageUnlocker;
			}
		}
		
		if(active)
		{
			if((energyStored - Settings.energyUseUnlocker) > 0) {
				progress++;
				energyStored -= Settings.energyUseUnlocker;
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
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

}
