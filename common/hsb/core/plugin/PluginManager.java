package hsb.core.plugin;

import hsb.ModHsb;
import hsb.configuration.Settings;
import hsb.core.plugin.ic2.PluginIC2;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;

import java.util.logging.Level;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginManager {
	
	public static void initPlugins() {
		
		//BuildCraft
		if(Settings.usePluginBC3) {
			Settings.usePluginBC3 = PluginBC3.load();
		}
		
		//IndustrialCraft
		if(Settings.usePluginIC2) {
			Settings.usePluginIC2 = PluginIC2.load();
		}
		
		//Universal Electricity
		if(Settings.usePluginUE) {
			Settings.usePluginUE = PluginUE.load();
		}
		
		/*
		//ComputerCraft
		if(Settings.usePluginCC) {
			Settings.usePluginCC = PluginCC.load();
		}
		//RedPower
		if(Settings.usePluginRP2) {
			Settings.usePluginRP2 = PluginRP2.load();
		}
		//ICBM
		if(Settings.usePluginICBM) {
			Settings.usePluginICBM = PluginICBM.load();
		}
		*/
		
		ModHsb.logger.log(Level.FINER, "IC2 integration " + (Settings.usePluginIC2 ? "enabled." : "disabled."));
		ModHsb.logger.log(Level.FINER, "Buildcraft integration " + (Settings.usePluginBC3 ? "enabled." : "disabled."));
		ModHsb.logger.log(Level.FINER, "Universal Electricity integration " + (Settings.usePluginUE ? "enabled." : "disabled."));
	}
	/**
	 * is a mod installed that can supply energy using a block ?
	 * @return
	 */
	public static boolean energyModInstalled_Block() {
		return Settings.usePluginBC3 || Settings.usePluginIC2 || Settings.usePluginUE;
	}
	
	/**
	 * is a mod installed that can supply energy using an item ?
	 * @return
	 */
	public static boolean energyModInstalled_Item() {
		return Settings.usePluginIC2 || Settings.usePluginUE;
	}
	
	public static boolean isItemElectricValid(ItemStack stack) {
		if(Settings.usePluginIC2) {
			return stack.getItem() instanceof IElectricItem;
		}
		return false;
	}
	
	public static int getElectricChargeInItem(ItemStack stack) {
		if(stack == null)
			return -1;
		Item item = stack.getItem();
		if(Settings.usePluginIC2) {
			if(item instanceof IElectricItem) {
				return ElectricItem.manager.getCharge(stack);
			}
		}
		return -1;
	}
	
	public static int getMaxElectricChargeInItem(ItemStack stack) {
		if(stack == null)
			return -1;
		Item item = stack.getItem();
		if(Settings.usePluginIC2) {
			if(item instanceof IElectricItem) {
				return ((IElectricItem)item).getMaxCharge(stack);
			}
		}
		return -1;
	}
	
	public static String getElectricChargeInfoString(ItemStack stack) {
		if(stack == null) {
			return null;
		}
		if(Settings.usePluginIC2 && stack.getItem() instanceof IElectricItem) {
			return getElectricChargeInItem(stack) + " EU / " + getMaxElectricChargeInItem(stack) + " EU";
		}
		return null;
	}
	/**
	 * returns gained energy
	 * @param item
	 * @param (int) amount
	 * @return
	 */
	public static float dichargeItem(ItemStack item, float amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
		if(item == null) {
			return 0;
		}
		if(Settings.usePluginIC2) {
			//charge from battery
			if(item.getItem() instanceof IElectricItem)
			{
				return PluginIC2.convertToMJ(PluginIC2.discharge(item, (int) amount, tier, ignoreTransferLimit, simulate));
			}
		}
		if(Settings.usePluginUE) {
			
		}
		return 0;
	}		
}
