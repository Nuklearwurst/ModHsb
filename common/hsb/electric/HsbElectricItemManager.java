package hsb.electric;

import hsb.configuration.Settings;
import hsb.core.plugin.ic2.PluginIC2;
import hsb.core.util.StackUtils;
import ic2.api.item.IElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HsbElectricItemManager {
	
	public static HsbElectricItemManager default_manager = new HsbElectricItemManager();

	/**
	 * charges an HsbElectricItem
	 * @param itemstack
	 * @param amount
	 * @param simulate
	 * @return the amount of energy added
	 */
	public float charge(ItemStack itemstack, float amount, boolean simulate) {
		if(itemstack == null || itemstack.stackSize < 1) {
			return 0;
		}
		if(!(itemstack.getItem() instanceof IHsbElectricItem)) {
			return 0;
		}
		IHsbElectricItem item = (IHsbElectricItem)itemstack.getItem();
		NBTTagCompound tag = StackUtils.getOrCreateNBTTagCompound(itemstack);
		float energy = tag.getFloat("energyStored");
		float used = 0;
		if(energy + amount > item.getMaxEnergyStored()) {
			used = item.getMaxEnergyStored() - energy;
			energy = item.getMaxEnergyStored();
		} else {
			used = amount;
			energy += amount;
		}
		if(!simulate) {
			tag.setFloat("energyStored", energy);
		}
		return used;
	}

	/**
	 * discharges an HsbElectricItem
	 * @param itemstack
	 * @param amount
	 * @param simulate
	 * @return the amount of energy removed
	 */
	public float discharge(ItemStack itemstack, float amount, boolean simulate) {
		if(itemstack == null || itemstack.stackSize < 1) {
			return 0;
		}
		if(!(itemstack.getItem() instanceof IHsbElectricItem)) {
			return 0;
		}
		NBTTagCompound tag = StackUtils.getOrCreateNBTTagCompound(itemstack);
		float energy = tag.getFloat("energyStored");
		float used = 0;
		if(energy - amount < 0) {
			used = energy;
			energy = 0;
		} else {
			used = amount;
			energy -= amount;
		}
		if(!simulate) {
			tag.setFloat("energyStored", energy);
		}
		return used;
	}

	/**
	 * 
	 * @param itemstack
	 * @return the charge left in the HsbElectricItem
	 */
	public float getCharge(ItemStack itemstack) {
		if(itemstack == null || itemstack.stackSize < 1) {
			return 0;
		}
		if(!(itemstack.getItem() instanceof IHsbElectricItem)) {
			return 0;
		}
		NBTTagCompound tag = StackUtils.getOrCreateNBTTagCompound(itemstack);
		return tag.getFloat("energyStored");
	}

	/**
	 * 
	 * @param itemStack
	 * @param amount
	 * @return returns true if the item hold enough energy
	 */
	public boolean canUse(ItemStack itemStack, float amount) {
		return getCharge(itemStack) >= amount;
	}

	/**
	 * use the item, try to remove the amount of energy from the item
	 * @param itemStack
	 * @param amount
	 * @param entity
	 * @return item has enough energy
	 */
	public boolean use(ItemStack itemStack, float amount, EntityLivingBase entity) {
		if(!canUse(itemStack, amount)) {
			chargeFromArmor(itemStack, entity);
			if(!canUse(itemStack, amount)) {
				return false;
			}
		}
		discharge(itemStack, amount, false);
		chargeFromArmor(itemStack, entity);
		return true;
	}
	
	/**
	 * helper to charge item from armor
	 * @param itemStack
	 * @param entity
	 */
	public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {
		if(!Settings.usePluginIC2) {
			return;
		}
		if(itemStack == null || !(itemStack.getItem() instanceof IHsbElectricItem) || entity == null || !(entity instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) entity;
		IHsbElectricItem item = (IHsbElectricItem) itemStack.getItem();
		
		float need = item.getMaxEnergyStored() - getCharge(itemStack); // need in EU
		for(ItemStack armor : player.inventory.armorInventory) {
			if(armor != null && armor.getItem() instanceof IElectricItem)
			{
				IElectricItem itemArmor = (IElectricItem) armor.getItem();
				if(itemArmor.canProvideEnergy(armor)) { // can provide energy
					int dischargeIC2 = PluginIC2.discharge(armor, (int) need, 3, false, false);
					need -= dischargeIC2;
				}
				if(need <= 0) {
					break;
				}
			}
		}
		
	}
}
