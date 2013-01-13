package hsb.items;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.ModHsb;
import hsb.Utils;
import hsb.blocks.BlockHsbDoor;
import hsb.config.Config;
import hsb.config.HsbItems;
import hsb.gui.GuiHandler;
import hsb.tileentitys.TileEntityDoorBase;
import hsb.tileentitys.TileEntityHsb;
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
/**
 * Need to set the blockId before Use!!
 * 
 */
public class ItemBlockPlacer extends Item
	implements IElectricItem
{
	 public static int blockID;
	    public int energyUse;

	    public ItemBlockPlacer(int id)
	    {
	        super(id);
	        this.setMaxStackSize(1);
	        this.setMaxDamage(13);
	        this.energyUse = 32;
	        this.setCreativeTab(CreativeTabHsb.tabHsb);
	        setIconIndex(0);//TODO energyUse
	    }
	    /**
	     * Set the BlockId placed by this Item
	     * Call this before Using
	     * 
	     * @param id
	     * @return BlockId
	     */
	    public static int setBlockId(int id)
	    {
	    	return blockID=id;
	    }
	    @Override
	    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	    {
	    	//Open the Gui when not aiming a block
	    	
	    	if(itemstack.getTagCompound()==null)
	    	{
	    		if(!world.isRemote)
	    		{
		    		Config.logDebug("nbttag == null!!!");
		    		itemstack.setTagCompound(new NBTTagCompound());
		    		itemstack.getTagCompound().setInteger("port", 0);
		    		itemstack.getTagCompound().setInteger("mode", 0);
	    		} else {
	    			return itemstack;
	    		}
	    	}
	    	if(entityplayer.isSneaking())
	    		entityplayer.openGui(ModHsb.instance, GuiHandler.GUI_BLOCKPLACER, world, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
	        return itemstack;
	    }
	    @Override
	    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	    {
	    	/*
	    	 * checking NBTTag
	    	 */
	    	if(itemstack.getTagCompound()==null)
	    	{
	    		if(!world.isRemote)
	    		{
	    			Config.logDebug("nbttag == null!!!");
		    		itemstack.setTagCompound(new NBTTagCompound());
		    		itemstack.getTagCompound().setInteger("port", 0);
		    		itemstack.getTagCompound().setInteger("mode", 0);
	    		} else {
	    			return true;
	    		}
	    	}
	    	/*
	    	 * open Gui while sneaking
	    	 */
	    	if(entityplayer.isSneaking())
	    		return false;
	    	
	    	switch(itemstack.getTagCompound().getInteger("mode"))
	    	{
	    	/*
	    	 * Blockremoving Part
	    	 */
		    	case 1:
		    	{
			    	TileEntity te = world.getBlockTileEntity(x, y, z);
			    	if(te instanceof TileEntityHsb)
			    	{
			    		if(((TileEntityHsb) te).port != itemstack.getTagCompound().getInteger("port") || ((TileEntityHsb) te).locked)
			    		{
			    			return true;
			    		}
			    		Config.logDebug("Removing Block at: " + x + ", " + y + ", " + z);
			    		if(world.setBlockAndMetadataWithNotify(x, y, z, 0, 0))
			    			HsbItems.blockHsb.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			    		return true;
			    	} else if(world.getBlockId(x, y, z) == HsbItems.blockHsbDoor.blockID)
			    	{
			    		te = ((BlockHsbDoor)HsbItems.blockHsbDoor).getTileEntity(world, x, y, z);
			    		if(te != null && te instanceof TileEntityDoorBase)
			    		{
			    			if(((TileEntityDoorBase)te).port != itemstack.getTagCompound().getInteger("port") || ((TileEntityDoorBase) te).locked)
			    			{
			    				return true;
			    			}
				    		Config.logDebug("Trying to Remove Door!!");
				    		if(world.setBlockAndMetadataWithNotify(x, y, z, 0, 0))
				    		{
				    			HsbItems.blockHsbDoor.breakBlock(world, x, y, z, 0, 0);
				    			HsbItems.blockHsbDoor.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				    			Config.logDebug("Door succesfully removed!");
				    			return true;
				    		} else {
				    			Config.logDebug("Door removal failed!");
				    		}
			    		}
			     	}
					return true;
		    	}
				/*
				 * Blockplacing Part
				 */
		    	case 0:
		    	{
			        int block = world.getBlockId(x, y, z);
		
			        if (block == Block.snow.blockID)
			        {
			            side = 1;
			        }
			        //is replacable?
			        else if (block != Block.vine.blockID && block != Block.tallGrass.blockID && block != Block.deadBush.blockID
			                && (Block.blocksList[block] == null || !Block.blocksList[block].isBlockReplaceable(world, x, y, z)))
			        {
			        	//Placing on Side
			            if (side == 0)
			            {
			                --y;
			            }
		
			            if (side == 1)
			            {
			                ++y;
			            }
		
			            if (side == 2)
			            {
			                --z;
			            }
		
			            if (side == 3)
			            {
			                ++z;
			            }
		
			            if (side == 4)
			            {
			                --x;
			            }
		
			            if (side == 5)
			            {
			                ++x;
			            }
			        }
			        else if (!entityplayer.canPlayerEdit(x, y, z, side, itemstack))
			        {
			            return false;
			        }
			        else if (y == 255)
			        {
			            return false;
			        }
		            Block blockPlace = Block.blocksList[this.blockID];
			        if (world.canPlaceEntityOnSide(this.blockID, x, y, z, false, side, null))
		            {
			            if (placeBlockAt(itemstack, entityplayer, world, x, y, z, side, par8, par9, par10))
			            {
			                world.playSoundEffect((x + 0.5F), (y + 0.5F), (z + 0.5F), blockPlace.stepSound.getStepSound(), (blockPlace.stepSound.getVolume() + 1.0F) / 2.0F, blockPlace.stepSound.getPitch() * 0.8F);
			             
				            return true;
			            }
		
		
			        }
			        break;
		    	}
	    	}
			return false;
	    }
	    @Override
	    public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer)
	    {
	        NBTTagCompound nbttagcompound;
	        nbttagcompound = itemstack.getTagCompound();

	        if (nbttagcompound == null)
	        {
	            nbttagcompound = new NBTTagCompound();
	            itemstack.setTagCompound(nbttagcompound);
	        }

	        nbttagcompound.setInteger("port", 0);
	        nbttagcompound.setInteger("mode", 0);
	    }

	    @Override
		public boolean canProvideEnergy()
	    {
	        return false;
	    }
	    @Override
		public int getChargedItemId()
	    {
	        return HsbItems.itemBlockPlacer.shiftedIndex;
	    }
	    @Override
		public int getEmptyItemId()
	    {
//	        return HsbItems.itemBlockPlacerEmpty.shiftedIndex;
	        return HsbItems.itemBlockPlacer.shiftedIndex;	    	
	    }
	    @Override
		public int getMaxCharge()
	    {
	        return 9600;
	        //TODO tuning/balance
	    }
	    @Override
		public int getTier()
	    {
	        return 1;
	    }
	    @Override
		public int getTransferLimit()
	    {
	        return 100;
	    }
	    @Override
	    public String getTextureFile()
	    {
	        return CommonProxy.TEXTURE_ITEMS;
	    }
	    /**
	     * Called to actually place the block, after the location is determined
	     * and all permission checks have been made.
	     * 
	     * @param stack The item stack that was used to place the block. This can be changed inside the method.
	     * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
	     * @param side The side the player (or machine) right-clicked on.
	     */
	    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	    {
	    	if(!player.capabilities.isCreativeMode)
	    	{
	    		if(!Config.ECLIPSE)
	    		{
		    		if(ElectricItem.discharge(stack, energyUse, getTier(), true, true) >= 0)
		    		{
		    			ElectricItem.discharge(stack, energyUse, getTier(), false, false);
		    		} else {
		    			return false;
		    		}
	    		}
				if(!Utils.removeFromInventory(this.blockID, 0, player.inventory))
				{
					return false;
				}
	    	}
			if (!world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, 0))
			{
				   Config.logDebug("placing failed!");
			       return false;
			}
			
			if (world.getBlockId(x, y, z) == this.blockID)
			{
			    Block.blocksList[this.blockID].onBlockPlacedBy(world, x, y, z, player);
			    NBTTagCompound tag = stack.getTagCompound();
			    if(tag!=null)
			    {
			 	   TileEntityHsb te = ((TileEntityHsb)world.getBlockTileEntity(x, y, z));
			 	   te.port=tag.getInteger("port");
			    }
			}
	
		   return true;
	    }
	    @Override
	    public boolean getShareTag()
	    {
	        return true;
	    }
	    
	    @SideOnly(Side.CLIENT)
	    @Override

	    /**
	     * allows items to add custom lines of information to the mouseover description
	     * Here: Port
	     */
	    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	    {
	        if(itemstack.getTagCompound()!=null)
	        {
	        	int port = itemstack.getTagCompound().getInteger("port");
	        	list.add("Port: " + port);
	        }
	    }
	    
	    @Override
	    public String getItemDisplayName(ItemStack itemstack)
	    {
    		String mode = "";
	    	if(itemstack.getTagCompound() != null)
	    	{
	    		switch(itemstack.getTagCompound().getInteger("mode"))
	    		{
	    		case 0:
	    			mode = " <Place> ";
	    			break;
	    		case 1:
	    			mode = " <Remove> ";
	    			break;
	    		default:
	    			mode = " <Error> ";
	    			break;
	    		}
	    	}
	    	return super.getItemDisplayName(itemstack) + mode;
	    }
}
