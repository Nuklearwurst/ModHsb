package hsb;

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
	public void updateUpgrade(ItemStack stack, TileEntityLockTerminal te) {
		te.teslaUpgrade = te.teslaUpgrade + stack.stackSize;
		te.energyUse = te.energyUse + 0.25 * stack.stackSize;
		
	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, ItemStack stack, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getButtonName() {
		return "Tesla";
	}
}
