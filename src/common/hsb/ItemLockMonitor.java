package hsb;

import hsb.config.Items;
import ic2.api.IElectricItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemLockMonitor extends Item
	implements IElectricItem
{

	public ItemLockMonitor(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canProvideEnergy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getChargedItemId() {
		return Items.itemLockMonitor.shiftedIndex;
	}

	@Override
	public int getEmptyItemId() {
		return Items.itemLockMonitorEmpty.shiftedIndex;
	}

	@Override
	public int getMaxCharge() {
		//TODO
		return 1000;
	}

	@Override
	public int getTier() {
		//TODO
		return 1;
	}

	@Override
	public int getTransferLimit() {
		// TODO Auto-generated method stub
		return 32;
	}
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z); 
		if(te instanceof TileEntityHsb)
		{
			if(((TileEntityHsb) te).locked)
			{
				if(world.isRemote)
					entityplayer.sendChatToPlayer("Locked!");
				return true;
			}
			
		}
		if(world.isRemote)
			entityplayer.sendChatToPlayer("This does nothing yet!");
		return true;
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
		
	}

}
