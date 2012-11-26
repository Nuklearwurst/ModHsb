package hsb;

import hsb.config.Items;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemDebugTool extends Item {

	public ItemDebugTool(int id) {
		super(id);
		 this.setCreativeTab(CreativeTabs.tabTools);
		 this.setIconIndex(1);//TODO
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
		
	}
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
    {
    	if(player == null)
    		return false;
    	TileEntity te = world.getBlockTileEntity(x, y, z); 
    	player.sendChatToPlayer("TileEntity:" + te + "\n");
    	if(te instanceof TileEntityHsb)
    	{
    		player.sendChatToPlayer("Port: " + String.valueOf(((TileEntityHsb)te).port) + " Pass: |" + ((TileEntityHsb)te).pass + "|\nFacing: " + String.valueOf(((TileEntityHsb) te).getFacing()) + " Textur der Side " + String.valueOf(side) + " ist: " + String.valueOf(Items.blockHsb.getBlockTexture(world, x, y, z, side)) + "\nLocked: " + ((TileEntityHsb) te).locked);
    		
    		return true;
    	}
        return false;
    }

	
}
