package hsb;

import hsb.config.Config;
import hsb.config.HsbItems;
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
		this.setMaxDamage(13);
	}

	@Override
	public boolean canProvideEnergy() {
		return false;
	}

	@Override
	public int getChargedItemId() {
		return HsbItems.itemLockHacker.shiftedIndex;
	}

	@Override
	public int getEmptyItemId() {
		return HsbItems.itemLockHacker.shiftedIndex;
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
		if(!world.isRemote)
		{ 
			if(te instanceof TileEntityHsb)
			{
				if(((TileEntityHsb) te).locked)
				{
					int energyUse = this.energyUse * 32 * (((TileEntityHsb) te).getConnectedTerminal().getSecurityLevel() + 1);
					if(ElectricItem.canUse(itemstack, energyUse))
					{
						ElectricItem.use(itemstack, energyUse, entityplayer);
						entityplayer.sendChatToPlayer("The Port is: " + ((TileEntityHsb) te).port);
					} else {
						entityplayer.sendChatToPlayer("Not enough energy for operation!");
					}
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
