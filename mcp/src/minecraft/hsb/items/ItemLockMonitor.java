package hsb.items;

import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.config.Config;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityHsb;

public class ItemLockMonitor extends Item
	implements IElectricItem
{
	public ItemLockMonitor(int id) {
		super(id);
		this.setMaxDamage(13);
		this.setIconIndex(3);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
	}

	@Override
	public boolean canProvideEnergy() {
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
		//TODO Charge
		return 10000;
	}

	@Override
	public int getTier() {
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
					if(ElectricItem.canUse(itemstack, Config.energyHsbMonitor))
					{
						ElectricItem.use(itemstack, Config.energyHsbMonitor, entityplayer);
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
