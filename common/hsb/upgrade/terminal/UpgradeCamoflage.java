package hsb.upgrade.terminal;

import hsb.ModHsb;
import hsb.core.util.Utils;
import hsb.lib.GuiIds;
import hsb.lib.Strings;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.INBTUpgrade;
import hsb.upgrade.types.IOnRemoveListener;
import hsb.upgrade.types.IUpgradeButton;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import nwcore.network.NetworkManager;

public class UpgradeCamoflage extends UpgradeHsbTerminal
	implements IUpgradeButton, IOnRemoveListener, INBTUpgrade, IInventory
{

	public ItemStack inv;
	TileEntityHsbTerminal te;
	public boolean active = false;
	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		this.te = te;
		if(te == null || te.worldObj.isRemote)
			return;
		if(!this.active)
		{
			te.camoMeta = -1;
			te.camoId = -1;
			NetworkManager.getInstance().updateTileEntityField(te, "camoMeta");
			NetworkManager.getInstance().updateTileEntityField(te, "camoId");
			return;
		}
		if(this.inv!=null)
		{
			if(inv.getItem() instanceof ItemBlock)
			{
				Block block = Block.blocksList[((ItemBlock)inv.getItem()).getBlockID()];
				int meta = ((ItemBlock)inv.getItem()).getMetadata(inv.getItemDamage());
				if(block.isOpaqueCube())
				{
					te.camoMeta = meta;
					te.camoId = inv.itemID;
				} else {
					te.camoMeta = -1;
					te.camoId = -1;
				}
				NetworkManager.getInstance().updateTileEntityField(te, "camoMeta");
				NetworkManager.getInstance().updateTileEntityField(te, "camoId");
			} else {
				ModHsb.logger.debug("Item invalid");
			}
		} else {
			ModHsb.logger.debug("Camo inv = null");
		}
	}

	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_CAMO;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		if(upgrade instanceof UpgradeCamoflage) {
			this.inv = ((UpgradeCamoflage) upgrade).inv;
			this.active = ((UpgradeCamoflage) upgrade).active;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		//Items
        NBTTagList nbtlist = tag.getTagList("upgradeCamoInv");
        this.inv = null;

        for (int i = 0; i < nbtlist.tagCount(); ++i)
        {
            NBTTagCompound nbttag = (NBTTagCompound)nbtlist.tagAt(i);
            this.inv = ItemStack.loadItemStackFromNBT(nbttag);
        }
        
        this.active = tag.getBoolean("upgradeCamoActive");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
        NBTTagList nbttaglist = new NBTTagList();

        if (this.inv != null)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            this.inv.writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
        }

        tag.setTag("upgradeCamoInv", nbttaglist);
        tag.setBoolean("upgradeCamoActive", active);
		
	}

	@Override
	public void onRemove(World world, int x, int y, int z, int par5, int par6) {
		te.camoMeta = -1;
		te.camoId = -1;
		NetworkManager.getInstance().updateTileEntityField(te, "camoMeta");
		NetworkManager.getInstance().updateTileEntityField(te, "camoId");
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
		return Strings.CONTAINER_UPGRADE_CAMO;
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {
		this.updateUpgrade(te);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return te.isUseableByPlayer(entityplayer);
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public void onButtonClicked(EntityPlayer player, TileEntityHsbTerminal te) {
		if(!te.isLocked()) {
			player.openGui(ModHsb.instance, GuiIds.GUI_UPGRADE_CAMOFLAGE, te.worldObj, te.xCoord, te.yCoord, te.zCoord);
		}
	}
	
	public void onActivateClicked(EntityPlayer player, TileEntityHsbTerminal te) {
		active = !active;
		player.sendChatToPlayer(Utils.getChatMessage(Strings.translate(active ? Strings.CHAT_ACTIVATED : Strings.CHAT_DISABLED)));
		this.updateUpgrade(te);
	}

}
