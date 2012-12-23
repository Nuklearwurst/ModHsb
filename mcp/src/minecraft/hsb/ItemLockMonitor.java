package hsb;

import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import hsb.config.Config;
import hsb.config.HsbItems;

public class ItemLockMonitor extends Item
	implements IElectricItem
{

	private static final int energyUse = 10;
	public ItemLockMonitor(int id) {
		super(id);
		this.setMaxDamage(13);
	}

	@Override
	public boolean canProvideEnergy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getChargedItemId() {
		return HsbItems.itemLockMonitor.shiftedIndex;
	}

	@Override
	public int getEmptyItemId() {
		return HsbItems.itemLockMonitor.shiftedIndex;
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
		return 32;
	}
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(!world.isRemote)
		{ 
			if(te instanceof TileEntityHsb)
			{
				if(((TileEntityHsb) te).locked)
				{
					entityplayer.sendChatToPlayer("Locked!");
					return true;
				} else {
					if(ElectricItem.canUse(itemstack, energyUse))
					{
						ElectricItem.use(itemstack, energyUse, entityplayer);
						entityplayer.sendChatToPlayer("The Port is: " + ((TileEntityHsb) te).port);
					} else {
						entityplayer.sendChatToPlayer("Not enough energy for operation!");
					}
				}
			}
			return false;
		} else {
			return te instanceof TileEntityHsb;
		}
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
		
	}

}
