package hsb.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.HsbInfo;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityHsbBuilding;

public class ItemHsbDoor extends ItemDoor{

	public ItemHsbDoor(int id) {
		super(id, Material.wood);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
	}

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side != 1)
        {
            return false;
        }
        else
        {
        	if(!(world.getBlockTileEntity(x, y, z) instanceof TileEntityHsbBuilding))
        	{
        		return false;
        	}
        	if(((TileEntityHsbBuilding)world.getBlockTileEntity(x, y, z)).locked)
        	{
        		if(world.isRemote)
        			player.sendChatToPlayer("Locked!");
        		return true;
        	}
            ++y;
            Block block = HsbItems.blockHsbDoor;


            if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
            {
                if (!block.canPlaceBlockAt(world, x, y, z))
                {
                    return false;
                }
                else
                {
                    int var12 = MathHelper.floor_double((player.rotationYaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 3;
                    placeDoorBlock(world, x, y, z, var12, block);
                    block.onBlockAdded(world, x, y, z);
                    block.onBlockPlacedBy(world, x, y -1, z, player, stack);
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
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void updateIcons(IconRegister reg)
    {
    	this.iconIndex = reg.registerIcon(HsbInfo.modId.toLowerCase() + ":" + "itemLockDoor");
    }

}
