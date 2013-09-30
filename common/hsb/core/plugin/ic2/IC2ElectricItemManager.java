package hsb.core.plugin.ic2;

import hsb.electric.HsbElectricItemManager;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class IC2ElectricItemManager implements IElectricItemManager{

	@Override
	public int charge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		if(itemStack == null || !(itemStack.getItem() instanceof IElectricItem)) {
			return 0;
		}
		IElectricItem item = (IElectricItem) itemStack.getItem();
		if(item.getTier(itemStack) < tier) {
			return 0;
		}
		if(amount > item.getTransferLimit(itemStack)) {
			amount = item.getTransferLimit(itemStack);
		}
		return (int) HsbElectricItemManager.default_manager.charge(itemStack, amount, simulate);
	}

	@Override
	public int discharge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		if(itemStack == null || !(itemStack.getItem() instanceof IElectricItem)) {
			return 0;
		}
		IElectricItem item = (IElectricItem) itemStack.getItem();
		if(item.getTier(itemStack) < tier) {
			return 0;
		}
		if(amount > item.getTransferLimit(itemStack)) {
			amount = item.getTransferLimit(itemStack);
		}
		return (int) HsbElectricItemManager.default_manager.discharge(itemStack, amount, simulate);

	}

	@Override
	public int getCharge(ItemStack itemStack) {
		return (int) HsbElectricItemManager.default_manager.getCharge(itemStack);
	}

	@Override
	public boolean canUse(ItemStack itemStack, int amount) {
		return HsbElectricItemManager.default_manager.canUse(itemStack, amount);
	}

	@Override
	public boolean use(ItemStack itemStack, int amount, EntityLivingBase entity) {
		return HsbElectricItemManager.default_manager.use(itemStack, amount, entity);
	}

	@Override
	public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {
		HsbElectricItemManager.default_manager.chargeFromArmor(itemStack, entity);		
	}

	@Override
	public String getToolTip(ItemStack itemStack) {
		return null;
	}

}
