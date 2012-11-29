package hsb;

import hsb.config.Items;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemDoor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class ItemHsbDoor extends ItemDoor{

	public ItemHsbDoor(int id) {
		super(id, Material.wood);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
		
	}

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side != 1)
        {
            return false;
        }
        else
        {
        	if(!(world.getBlockTileEntity(x, y, z) instanceof TileEntityDoorBase))
        	{
        		return false;
        	}
            ++y;
            Block block = Items.blockHsbDoor;


            if (player.func_82247_a(x, y, z, side, stack) && player.func_82247_a(x, y + 1, z, side, stack))
            {
                if (!block.canPlaceBlockAt(world, x, y, z))
                {
                    return false;
                }
                else
                {
                    int var12 = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(world, x, y, z, var12, block);
                    block.onBlockAdded(world, x, y, z);
                    block.onBlockPlacedBy(world, x, y -1, z, player);
                    --stack.stackSize;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

}
