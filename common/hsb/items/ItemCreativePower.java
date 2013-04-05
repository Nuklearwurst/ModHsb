package hsb.items;

import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import ic2.api.IElectricItem;
import net.minecraft.item.Item;

public class ItemCreativePower extends Item implements IElectricItem{

	public ItemCreativePower(int id) {
		super(id);
        this.setMaxStackSize(1);
        this.setMaxDamage(13);
        this.setCreativeTab(CreativeTabHsb.tabHsb);
        setIconIndex(0);
	}

    @Override
	public boolean canProvideEnergy() {
		return true;
	}
    
	@Override
	public int getChargedItemId() {
		return this.itemID;
	}

	@Override
	public int getEmptyItemId() {
		return this.itemID;
	}

	@Override
	public int getMaxCharge() {
		return 10000;
	}

	@Override
    public String getTextureFile()
    {
        return CommonProxy.TEXTURE_ITEMS;

    }

	@Override
	public int getTier() {
		return 4;
	}

	@Override
	public int getTransferLimit() {
		return 4000;
	}

}
