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
	    	if(!itemstack.getTagCompound().getBoolean("placeMode"))
	    	{
	    		return false;
	    	}
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

//	        if (itemstack.stackSize == 0)
//	        {
//	            return false;
//	        }
//	        else if (!entityplayer.canPlayerEdit(x, y, z))
//	        {
//	            return false;//?
//	        }
	        else if (y == 255)// && Block.blocksList[this.blockID].blockMaterial.isSolid()
	        {
	            return false;//?
	        }
	        else if (world.canPlaceEntityOnSide(this.blockID, x, y, z, false, side, null))
	        {
	            Block blockPlace = Block.blocksList[this.blockID];

	            if (placeBlockAt(itemstack, entityplayer, world, x, y, z, side, par8, par9, par10))
	            {
	                world.playSoundEffect((x + 0.5F), (y + 0.5F), (z + 0.5F), blockPlace.stepSound.getStepSound(), (blockPlace.stepSound.getVolume() + 1.0F) / 2.0F, blockPlace.stepSound.getPitch() * 0.8F);
	                //TODO consume Items
	                
//	                TileEntityHsbBuilding te = (TileEntityHsbBuilding) world.getBlockTileEntity(x, y, z);
//	                int yaw = MathHelper.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
//                    int pitch = Math.round(entityplayer.rotationPitch);
//                    short facing = 0;
//                    if (pitch >= 65)
//                    {
//                        facing = 1;
//                    }
//                    else if (pitch <= -65)
//                    {
//                        facing=0;
//                    }
//                    else
//                        switch (yaw)
//                        {
//                            case 0:
//                            	facing=2;
//                                break;
//
//                            case 1:
//                            	facing=5;
//                                break;
//
//                            case 2:
//                            	facing=3;
//                                break;
//
//                            case 3:
//                            	facing=4;
//                        }
//                    te.setFacing(facing);
//	                entityplayer.inventory.consumeInventoryItem(Items.itemBlockBuilding.shiftedIndex);
		            return true;
	            }


	        }
			return false;
	    }

	    @SideOnly(Side.CLIENT)

	    /**
	     * Returns true if the given ItemBlock can be placed on the given side of the given block position.
	     */
	    public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack)
	    {
	        int var8 = par1World.getBlockId(par2, par3, par4);

	        if (var8 == Block.snow.blockID)
	        {
	            par5 = 1;
	        }
	        else if (var8 != Block.vine.blockID && var8 != Block.tallGrass.blockID && var8 != Block.deadBush.blockID
	                && (Block.blocksList[var8] == null || !Block.blocksList[var8].isBlockReplaceable(par1World, par2, par3, par4)))
	        {
	            if (par5 == 0)
	            {
	                --par3;
	            }

	            if (par5 == 1)
	            {
	                ++par3;
	            }

	            if (par5 == 2)
	            {
	                --par4;
	            }

	            if (par5 == 3)
	            {
	                ++par4;
	            }

	            if (par5 == 4)
	            {
	                --par2;
	            }

	            if (par5 == 5)
	            {
	                ++par2;
	            }
	        }

	        return par1World.canPlaceEntityOnSide(this.blockID, par2, par3, par4, false, par5, (Entity)null);
	    }
	    @Override
	    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	    {
	        NBTTagCompound nbttagcompound;
	        nbttagcompound = stack.getTagCompound();
	        if (nbttagcompound == null || nbttagcompound.getBoolean("placeMode"))
	        {
	            return false;
	        }
	    	TileEntity te = world.getBlockTileEntity(x, y, z);
	    	if(te instanceof TileEntityHsbBuilding)
	    	{
	    		//TODO better?
	    		System.out.println("Removing Block at: " + x + ", " + y + ", " + z);
	    		
	    		world.removeBlockTileEntity(x, y, z);
	    		world.setBlockAndMetadataWithNotify(x, y, z, 0, 0);
	    		world.markBlockNeedsUpdate(x, y, z);
	    		world.setBlock(x, y, y, 0);
	    		return true;
	    	}
			return true;//false
	    	
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
//	    @Override
//	    @SideOnly(Side.CLIENT)
//
//	    /**
//	     * gets the CreativeTab this item is displayed on
//	     */
//	    public CreativeTabs getCreativeTab()
//	    {
//		        return Block.blocksList[this.blockID].getCreativeTabToDisplayOn();
//	    }
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
	       if (!world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, this.getMetadata(stack.getItemDamage())))
	       {
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
	    public boolean getShareTag()
	    {
	        return false;
	    }
}
