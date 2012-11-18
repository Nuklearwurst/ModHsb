package hsb;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import hsb.config.Items;
import hsb.gui.GuiHandler;
import ic2.api.IElectricItem;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

import net.minecraft.src.World;
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
	        this.setCreativeTab(CreativeTabs.tabMaterials);
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
		    		System.out.println("nbttag == null!!!");
		    		itemstack.setTagCompound(new NBTTagCompound());
		    		itemstack.getTagCompound().setInteger("port", 0);
		    		itemstack.getTagCompound().setBoolean("placeMode", true);
	    		} else {
	    			return itemstack;
	    		}
	    	}
//	    	if(itemstack.getTagCompound()==null)
	    		entityplayer.openGui(ModHsbCore.instance, GuiHandler.GUI_BLOCKPLACER, world, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
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
		    		System.out.println("nbttag == null!!!");
		    		itemstack.setTagCompound(new NBTTagCompound());
		    		itemstack.getTagCompound().setInteger("port", 0);
		    		itemstack.getTagCompound().setBoolean("placeMode", true);
	    		} else {
	    			return false;
	    		}
	    	}
	    	/*
	    	 * Blockremoving Part
	    	 */
	    	if(!itemstack.getTagCompound().getBoolean("placeMode"))
	    	{
		    	TileEntity te = world.getBlockTileEntity(x, y, z);
		    	if(te instanceof TileEntityHsb)
		    	{
		    		if(((TileEntityHsb) te).port != itemstack.getTagCompound().getInteger("port") || ((TileEntityHsb) te).locked)
		    		{
		    			return true;
		    		}
		    		//TODO better?
		    		System.out.println("Removing Block at: " + x + ", " + y + ", " + z);
		    		
//		    		world.removeBlockTileEntity(x, y, z);
		    		Items.blockHsb.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		    		world.setBlockAndMetadataWithNotify(x, y, z, 0, 0);
//		    		world.markBlockNeedsUpdate(x, y, z);
//		    		world.setBlock(x, y, y, 0);
		    		return true;
		    	}
				return true;
			/*
			 * Blockplacing Part
			 */
	    	} else {
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
		        else if (!entityplayer.func_82247_a(x, y, z, side, itemstack))
		        {
		            return false;
		        }
		        else if (y == 255)
		        {
		            return false;
		        }
		        if (world.canPlaceEntityOnSide(this.blockID, x, y, z, false, side, entityplayer))
		        {
		            Block blockPlace = Block.blocksList[this.blockID];
	
		            if (placeBlockAt(itemstack, entityplayer, world, x, y, z, side, par8, par9, par10))
		            {
		                world.playSoundEffect((x + 0.5F), (y + 0.5F), (z + 0.5F), blockPlace.stepSound.getStepSound(), (blockPlace.stepSound.getVolume() + 1.0F) / 2.0F, blockPlace.stepSound.getPitch() * 0.8F);
		                //TODO consume Items
		             
			            return true;
		            }
	
	
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
	        nbttagcompound.setBoolean("placeMode", true);
	    }

	    @Override
		public boolean canProvideEnergy()
	    {
	        return false;
	    }
	    @Override
		public int getChargedItemId()
	    {
	        return Items.itemBlockPlacer.shiftedIndex;
	    }
	    @Override
		public int getEmptyItemId()
	    {
	        return Items.itemBlockPlacerEmpty.shiftedIndex;
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
	       if (!world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, 0))
	       {
	    	   System.out.println("placing failed!");
	           return false;
	       }

	       if (world.getBlockId(x, y, z) == this.blockID)
	       {
	           Block.blocksList[this.blockID].updateBlockMetadata(world, x, y, z, side, hitX, hitY, hitZ);
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
}
