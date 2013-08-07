package hsb.core.plugin.ic2;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import ic2.api.item.IElectricItemManager;

public class HsbElectricItemManager implements IElectricItemManager{

	@Override
	public int charge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int discharge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharge(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canUse(ItemStack itemStack, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean use(ItemStack itemStack, int amount, EntityLivingBase entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getToolTip(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return null;
	}

}
