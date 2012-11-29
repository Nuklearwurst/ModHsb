package hsb;

import hsb.config.Config;
import hsb.config.Items;
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemLockHacker extends Item
	implements IElectricItem
{

	private int energyUse = 2;

	public ItemLockHacker(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canProvideEnergy() {
		return false;
	}

	@Override
	public int getChargedItemId() {
		return Items.itemLockHacker.shiftedIndex;
	}

	@Override
	public int getEmptyItemId() {
		return Items.itemLockHacker.shiftedIndex;
	}

	@Override
	public int getMaxCharge() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public int getTier() {
		// TODO Auto-generated method stub
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
				entityplayer.sendChatToPlayer("Locked!");
				return true;
			} else {
				if(hackPort(itemstack, entityplayer,world, x, y, z, side, true, true)) {
					entityplayer.sendChatToPlayer("The Port is: " + ((TileEntityHsb) te).port);
				}
			}
			
		}
		return false;
	}

	private boolean hackPort(ItemStack itemstack, EntityPlayer entityplayer,
			World world, int x, int y, int z, int side, boolean simulate, boolean par) {
		TileEntityHsb te = (TileEntityHsb) world.getBlockTileEntity(x, y, z);
		if(!Config.ECLIPSE)
		while(ElectricItem.canUse(itemstack, energyUse))
		{
			ElectricItem.use(itemstack, energyUse, entityplayer);
			if(world.rand.nextInt(Config.maxPort) == te.port)
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
		
	}
	

}
