package hsb;

import ic2.api.NetworkHelper;
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
		int index = te.getUpgradeId(this);
		if(te.upgradeCount[index] > 32)
			te.upgradeCount[index]  = 32;
		if(te.upgradeActive[index])
			te.energyUse = te.energyUse + 0.25 * te.upgradeCount[index];
		
	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player, int button) {

		boolean active = te.upgradeActive[te.getUpgradeId(this)];
		if(active)
		{
			if(!te.worldObj.isRemote)
			{
				player.sendChatToPlayer("Tesla Upgrade disabled!");
			}
			te.upgradeActive[te.getUpgradeId(this)] = false;
		} else {
			if(!te.worldObj.isRemote)
			{
				player.sendChatToPlayer("Tesla Upgrade enabled!");
			}
			te.upgradeActive[te.getUpgradeId(this)] = true;
		}
		
	}

	@Override
	public String getButtonName() {
		return "Tesla";
	}
	@Override
	public String getUniqueId() {
		return "tesla";
	}
//
//	@Override
//	public UpgradeHsb getUpgrade() {
//		return new UpgradeHsb(this);
//	}
}
