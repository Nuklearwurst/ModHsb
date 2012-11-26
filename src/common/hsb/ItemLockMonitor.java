package hsb;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEmptyItemId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxCharge() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTransferLimit() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z); 
		if(te instanceof TileEntityHsb)
		{
			if(((TileEntityHsb) te).locked)
			{
				entityplayer.sendChatToPlayer("Locked!");
				return true;
			}
			
		}
		return false;
	}
	

}
