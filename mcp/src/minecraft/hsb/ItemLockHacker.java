package hsb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import hsb.config.Config;
import hsb.config.HsbItems;
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;

public class ItemLockHacker extends Item
	implements IElectricItem
{

	private int energyUse = 300;

	public ItemLockHacker(int id) {
		super(id);
		this.setMaxDamage(13);
		this.setIconIndex(4);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
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
		// maxCharge
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
