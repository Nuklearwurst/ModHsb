package hsb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import hsb.config.HsbItems;

public class ItemTeslaUpgrade extends Item
	implements ILockUpgrade
{

	public ItemTeslaUpgrade(int id) {
		super(id);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
		this.setIconIndex(5);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z); 
		if(te instanceof TileEntityLockTerminal && (!((TileEntityLockTerminal) te).locked))
		{
			((TileEntityLockTerminal)te).addToInventory(HsbItems.itemUpgradeTesla, 1);
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

	@Override
	public void onTileSave(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {}

	@Override
	public void onTileLoad(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {}

	@Override
	public void onGuiOpen(TileEntityLockTerminal te) {}
}
