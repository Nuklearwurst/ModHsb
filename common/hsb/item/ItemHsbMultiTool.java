package hsb.item;

import hsb.ModHsb;
import hsb.block.ModBlocks;
import hsb.configuration.Settings;
import hsb.core.addons.PluginIC2;
import hsb.core.helper.HsbLog;
import hsb.core.helper.Utils;
import hsb.creativetab.CreativeTabHsb;
import hsb.lib.GuiIds;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.tileentity.TileEntityHsb;
import ic2.api.IElectricItem;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHsbMultiTool extends ItemSimple
	implements IElectricItem
{

	public ItemHsbMultiTool(int id) {
        super(id);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabHsb.tabHsb);
        this.setUnlocalizedName(Strings.ITEM_MULTI_TOOL);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
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
        	list.add(StatCollector.translateToLocal(Strings.GUI_PORT) + ": " + port);
        }
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		this.itemIcon = reg.registerIcon(Textures.ITEM_MULTI_TOOL);
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack itemStack)
    {
        return false;
    }
    @Override
	public int getChargedItemId(ItemStack itemStack)
    {
        return ModItems.itemMultiTool.itemID;
    }

    @Override
	public int getEmptyItemId(ItemStack itemStack)
    {
        return ModItems.itemMultiTool.itemID;	    	
    }
    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
		String mode = " <" + StatCollector.translateToLocal(Strings.MULTI_TOOL_ERROR) + "> ";
    	if(itemstack.getTagCompound() != null)
    	{
    		switch(itemstack.getTagCompound().getInteger("mode"))
    		{
    		case 0:
    			mode = " <" + StatCollector.translateToLocal(Strings.MULTI_TOOL_PLACE) + "> ";
    			break;
    		case 1:
    			mode = " <" + StatCollector.translateToLocal(Strings.MULTI_TOOL_REMOVE) + "> ";
    			break;
    		case 2:
    			mode = " <" + StatCollector.translateToLocal(Strings.MULTI_TOOL_WRENCH) + "> ";
    			break;
    		default:
    			mode = " <" + StatCollector.translateToLocal(Strings.MULTI_TOOL_ERROR) + "> ";
    			break;
    		}
    	}
    	return super.getItemDisplayName(itemstack) + mode;
    }
    @Override
	public int getMaxCharge(ItemStack itemStack)
    {
        return 9600;
        //TODO tuning/balance
    }
    @Override
    public boolean getShareTag()
    {
        return true;
    }
    @Override
	public int getTier(ItemStack itemStack)
    {
        return 1;
    }
    @Override
	public int getTransferLimit(ItemStack itemStack)
    {
        return 100;
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
    
    public NBTTagCompound createNBTTagCompound(ItemStack itemstack) {
    	if(itemstack.getTagCompound()==null)
    	{
    		itemstack.setTagCompound(new NBTTagCompound());
    		itemstack.getTagCompound().setInteger("port", 0);
    		itemstack.getTagCompound().setInteger("mode", 0);
    	}
    	return itemstack.getTagCompound();
    }
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	//Open the Gui when not aiming a block
    	
    	if(itemstack.getTagCompound()==null)
    	{
    		createNBTTagCompound(itemstack);
    	}
    	if(entityplayer.isSneaking())
    		entityplayer.openGui(ModHsb.instance, GuiIds.GUI_MULTI_TOOL, world, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
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
    		createNBTTagCompound(itemstack);
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
		    		//check for port and lock
		    		if(((TileEntityHsb) te).getPort() != itemstack.getTagCompound().getInteger("port") || ((TileEntityHsb) te).locked)
		    		{
		    			return true;
		    		}
		    		HsbLog.debug("Removing Block at: " + x + ", " + y + ", " + z);
		    		//removing block
		    		if(world.setBlock(x, y, z, 0, 0, 0) && !entityplayer.capabilities.isCreativeMode)
		    			ModBlocks.blockHsb.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		    		return true;
		    		//door
		    		
		    	} /*else if(world.getBlockId(x, y, z) == HsbItems.blockHsbDoor.blockID)
		    	{
		    		te = ((BlockHsbDoor)HsbItems.blockHsbDoor).getTileEntity(world, x, y, z);
		    		if(te != null && te instanceof TileEntityDoorBase)
		    		{
		    			if(((TileEntityDoorBase)te).getPort() != itemstack.getTagCompound().getInteger("port") || ((TileEntityDoorBase) te).locked)
		    			{
		    				return true;
		    			}
			    		Config.logDebug("Trying to Remove Door!!");
			    		if(world.setBlock(x, y, z, 0, 0, 0))
			    		{
			    			HsbItems.blockHsbDoor.breakBlock(world, x, y, z, 0, 0);
			    			HsbItems.blockHsbDoor.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			    			Config.logDebug("Door succesfully removed!");
			    			return true;
			    		} else {
			    			Config.logDebug("Door removal failed!");
			    		}
		    		}
		     	}*/
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
	            Block blockPlace = ModBlocks.blockHsb;
		        if (world.canPlaceEntityOnSide(blockPlace.blockID, x, y, z, false, side, null, itemstack))
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
    		if(Settings.ic2Available)
    		{
	    		if(PluginIC2.discharge(stack, Settings.energyUse_multiTool, getTier(stack), true, true) >= 0)
	    		{
	    			PluginIC2.discharge(stack, Settings.energyUse_multiTool, getTier(stack), false, false);
	    		} else {
	    			return false;
	    		}
    		}
    		
			if(!Utils.removeFromInventory(ModBlocks.blockHsb.blockID, 0, player.inventory))
			{
				return false;
			}
    	}
		if (!world.setBlock(x, y, z, ModBlocks.blockHsb.blockID, 0, 0))
		{
			   HsbLog.debug("placing failed!");
		       return false;
		}
		
		if (world.getBlockId(x, y, z) == ModBlocks.blockHsb.blockID)
		{
		    Block.blocksList[ModBlocks.blockHsb.blockID].onBlockPlacedBy(world, x, y, z, player, stack);
		    NBTTagCompound tag = stack.getTagCompound();
		    if(tag!=null)
		    {
		 	   TileEntityHsb te = ((TileEntityHsb)world.getBlockTileEntity(x, y, z));
		 	   te.setPort(tag.getInteger("port"));
		    }
		}

	   return true;
    }

}
