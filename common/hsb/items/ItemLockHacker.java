package hsb.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.HsbInfo;
import hsb.PluginIC2;
import hsb.config.Config;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityHsb;
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;

public class ItemLockHacker extends Item
	implements IElectricItem
{

	public ItemLockHacker(int id) {
		super(id);
		this.setMaxDamage(13);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return false;
	}

	@Override
	public int getChargedItemId(ItemStack itemStack) {
		return HsbItems.itemLockHacker.itemID;
	}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {
		return HsbItems.itemLockHacker.itemID;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		// maxCharge
		return 10000;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return 1;
	}

	@Override
	public int getTransferLimit(ItemStack itemStack) {
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
					int energyUse = Config.energyHsbHacker * (((TileEntityHsb) te).getConnectedTerminal().getSecurityLevel() + 1);
					if(PluginIC2.canUse(itemstack, energyUse))
					{
						PluginIC2.use(itemstack, energyUse, entityplayer);
						entityplayer.sendChatToPlayer("The Port is: " + ((TileEntityHsb) te).getPort());
					} else {
						entityplayer.sendChatToPlayer("Not enough energy for operation!");
					}
					return true;
				} else {
					if(PluginIC2.canUse(itemstack, Config.energyHsbMonitor))
					{
						PluginIC2.use(itemstack, Config.energyHsbMonitor, entityplayer);
						entityplayer.sendChatToPlayer("The Port is: " + ((TileEntityHsb) te).getPort());
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
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void updateIcons(IconRegister reg)
    {
    	this.iconIndex = reg.registerIcon(HsbInfo.modId.toLowerCase() + ":" + "LockHacker");
    }
	

}
