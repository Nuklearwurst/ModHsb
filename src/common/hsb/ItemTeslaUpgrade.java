package hsb;

import hsb.api.UpgradeHsb;
import hsb.config.Items;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemTeslaUpgrade extends Item
	implements ILockUpgrade
{

	public ItemTeslaUpgrade(int id) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z); 
		if(te instanceof TileEntityLockTerminal && (!((TileEntityLockTerminal) te).locked))
		{
			((TileEntityLockTerminal)te).addToInventory(Items.itemUpgradeTesla, 1);
			//TODO sound
			System.out.println("Tesla upgraded!");
			itemstack.stackSize--;
			return true;
		}
		return false;

	}
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
		
	}
	@Override
	public void updateUpgrade(TileEntityLockTerminal te) {
//		int i = te.teslaUpgrade + stack.stackSize;
//		if(i > 32)
//		{
//			i = 32;
//		}
//		te.teslaUpgrade = i; 
//		te.energyUse = te.energyUse + 0.25 * stack.stackSize;
		UpgradeHsb upgrade = te.getUpgrade(getUniqueId());
		if(upgrade.number > 32)
			upgrade.number = 32;
		if(upgrade.active)
			te.energyUse = te.energyUse + 0.25 * upgrade.number;
		
	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, ItemStack stack, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getButtonName() {
		return "Tesla";
	}
	@Override
	public String getUniqueId() {
		return "tesla";
	}

	@Override
	public UpgradeHsb getUpgrade() {
		return new UpgradeHsb(this);
	}
}
