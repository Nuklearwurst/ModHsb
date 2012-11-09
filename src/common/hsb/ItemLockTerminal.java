package hsb;

import hsb.network.PacketItemUpdate;
import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;


/**
 * Not in use
 * @author Nuklearwurst
 *
 */
public class ItemLockTerminal  extends Item
{
	/** The block ID of the Block associated with this ItemBlock */
    private int blockID;

    public ItemLockTerminal(int itemid, int blockid)
    {
        super(itemid);
        this.blockID = blockid;
        this.setIconIndex(Block.blocksList[blockid].getBlockTextureFromSide(2));
        isDefaultTexture = Block.blocksList[blockid].isDefaultTexture;
    }

    /**
     * Returns the blockID for this Item
     */
    public int getBlockID()
    {
        return this.blockID;
    }
    @Override 
    public String getTextureFile()
    {
    	return CommonProxy.TEXTURE_BLOCKS;//TODO
    }
    /** not used
    @Override
    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer entityplayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        int var11 = world.getBlockId(x, y, z);

        if (var11 == Block.snow.blockID)
        {
            par7 = 1;
        }
        else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID
                && (Block.blocksList[var11] == null || !Block.blocksList[var11].isBlockReplaceable(world, x, y, z)))
        {
            if (par7 == 0)
            {
                --y;
            }

            if (par7 == 1)
            {
                ++y;
            }

            if (par7 == 2)
            {
                --z;
            }

            if (par7 == 3)
            {
                ++z;
            }

            if (par7 == 4)
            {
                --x;
            }

            if (par7 == 5)
            {
                ++x;
            }
        }

        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }
        else if (!entityplayer.canPlayerEdit(x, y, z))
        {
            return false;
        }
        else if (y == 255 && Block.blocksList[this.blockID].blockMaterial.isSolid())
        {
            return false;
        }
        else if (world.canPlaceEntityOnSide(this.blockID, x, y, z, false, par7, entityplayer))
        {
            Block var12 = Block.blocksList[this.blockID];

            if (placeBlockAt(par1ItemStack, entityplayer, world, x, y, z, par7, par8, par9, par10))
            {
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), var12.stepSound.getStepSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
                --par1ItemStack.stackSize;
            }

            TileEntityLockTerminal te = (TileEntityLockTerminal) world.getBlockTileEntity(x, y, z);
            int yaw = MathHelper.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
            int pitch = Math.round(entityplayer.rotationPitch);
            short facing = 0;
            if (pitch >= 65)
            {
                facing = 1;
            }
            else if (pitch <= -65)
            {
                facing=0;
            }
            else
                switch (yaw)
                {
                    case 0:
                    	facing=2;
                        break;

                    case 1:
                    	facing=5;
                        break;

                    case 2:
                    	facing=3;
                        break;

                    case 3:
                    	facing=4;
                }
            te.setFacing(facing);
//            if(entityplayer instanceof EntityClientPlayerMP)
//            {
//            	System.out.println("Client");
//    	    	PacketTileEvent packetFacing = new PacketTileEvent(x, y, z, 1, facing);
//    	    	((EntityClientPlayerMP)entityplayer).sendQueue.addToSendQueue(packetFacing.getPacket()); 
//            } else {
//            	
//            }
            
            return true;
        }
        else
        {
            return false;
        }
    }
    */

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

        return par1World.canPlaceEntityOnSide(this.getBlockID(), par2, par3, par4, false, par5, (Entity)null);
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        return Block.blocksList[this.blockID].getBlockName();
    }

    public String getItemName()
    {
        return Block.blocksList[this.blockID].getBlockName();
    }

    @SideOnly(Side.CLIENT)

    /**
     * gets the CreativeTab this item is displayed on
     */
    public CreativeTabs getCreativeTab()
    {
        return CreativeTabs.tabMaterials;
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        Block.blocksList[this.blockID].getSubBlocks(par1, par2CreativeTabs, par3List);
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
       if (!world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, 1/*this.getMetadata(stack.getItemDamage())*/ ))
       {
               return false;
       }

       if (world.getBlockId(x, y, z) == this.blockID)
       {
           Block.blocksList[this.blockID].updateBlockMetadata(world, x, y, z, side, hitX, hitY, hitZ);
           Block.blocksList[this.blockID].onBlockPlacedBy(world, x, y, z, player);
       }

       return true;
    }

}
