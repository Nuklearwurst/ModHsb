package hsb.item;

import hsb.configuration.Settings;
import hsb.core.plugin.ic2.HsbElectricItemManager;
import hsb.core.plugin.ic2.PluginIC2;
import hsb.core.util.Utils;
import hsb.creativetab.CreativeTabHsb;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.tileentity.TileEntityHsb;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLockMonitor extends ItemSimple
	implements ISpecialElectricItem
{
	private boolean isHacker = false;
	public ItemLockMonitor(int id, boolean hacker) {
		super(id);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
		this.setMaxDamage(Settings.maxDamageMonitor);
		this.isHacker = hacker;
		if(isHacker) {
			this.setUnlocalizedName(Strings.ITEM_LOCK_HACKER);
		} else {
			this.setUnlocalizedName(Strings.ITEM_LOCK_MONITOR);
		}
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return false;
	}

	@Override
	public int getChargedItemId(ItemStack itemStack) {
		return ModItems.itemLockMonitor.itemID;
	}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {
		return ModItems.itemLockMonitor.itemID;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return Settings.maxEnergyMonitor;
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
				//if locked
				if(((TileEntityHsb) te).locked)
				{
					if(isHacker)
					{
						if(Settings.usePluginIC2)
						{
							if(PluginIC2.canUse(itemstack, Settings.energyHsbHacker))
							{
								PluginIC2.use(itemstack, Settings.energyHsbHacker, entityplayer);
							} else {
								entityplayer.sendChatToPlayer(Utils.getChatMessage(Strings.translate(Strings.CHAT_NOT_ENOUGH_ENERGY)));
								return true;
							}
						} else {
							itemstack.damageItem(Settings.damageHsbHacker, entityplayer);
						}
					} else {
						entityplayer.sendChatToPlayer(Utils.getChatMessage(Strings.translate(Strings.CHAT_LOCKED)));
						return true;
					}
					//if NOT locked
				} else {
					if(Settings.usePluginIC2)
					{
						if(PluginIC2.canUse(itemstack, Settings.energyHsbMonitor))
						{
							PluginIC2.use(itemstack, Settings.energyHsbMonitor, entityplayer);
						} else {
							entityplayer.sendChatToPlayer(Utils.getChatMessage(Strings.translate(Strings.CHAT_NOT_ENOUGH_ENERGY)));
							return true;
						}
					} else {
						itemstack.damageItem(Settings.damageHsbMonitor, entityplayer);
					}
				}
				entityplayer.sendChatToPlayer(Utils.getChatMessage(Strings.translate(Strings.CHAT_PORT) + ((TileEntityHsb) te).getPort()));
				return true;
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
    public void registerIcons(IconRegister reg)
    {
    	if(isHacker) {
    		this.itemIcon = reg.registerIcon(Textures.ITEM_LOCK_HACKER);
    	} else {
    		this.itemIcon = reg.registerIcon(Textures.ITEM_LOCK_MONITOR);

    	}
    }

	@Override
	public IElectricItemManager getManager(ItemStack itemStack) {
		return new HsbElectricItemManager();
	}

}
