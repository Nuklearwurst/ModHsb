package hsb.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.HsbInfo;
import ic2.api.IElectricItem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCreativePower extends Item implements IElectricItem{

	public ItemCreativePower(int id) {
		super(id);
        this.setMaxStackSize(1);
        this.setMaxDamage(13);
        this.setCreativeTab(CreativeTabHsb.tabHsb);
	}

    @Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}
    
	@Override
	public int getChargedItemId(ItemStack itemStack) {
		return this.itemID;
	}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {
		return this.itemID;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return 10000;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return 4;
	}

	@Override
	public int getTransferLimit(ItemStack itemStack) {
		return 4000;
	}
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void updateIcons(IconRegister reg)
    {
    	this.iconIndex = reg.registerIcon(HsbInfo.modId.toLowerCase() + ":" + "debugTool");//TODO texture
    }

}
