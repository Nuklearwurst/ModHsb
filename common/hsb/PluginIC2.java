package hsb;

import ic2.api.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import hsb.config.Config;
import hsb.config.HsbItems;
import cpw.mods.fml.common.Loader;

public class PluginIC2 {

	private static boolean checkIC2Installed() {
		if(!Loader.isModLoaded("IC2")) {
			Config.ic2Available = false;
		}
		return Config.ic2Available;
    }
	
	public static void initPluginIC2() {
		if(checkIC2Installed()) {
			Config.logInfo("IC2 found!!");
			HsbItems.initIC2();
		} else {
			Config.logError("IC2 not found, this mod may not work without IC2!!!\n Please check if you installed IC2 (correctly).");
		}
	}
	
	/**
	 * Charge an item with a specified amount of energy
	 *
	 * @param itemStack electric item's stack
	 * @param amount amount of energy to charge in EU
	 * @param tier tier of the charging device, has to be at least as high as the item to charge
	 * @param ignoreTransferLimit ignore the transfer limit specified by getTransferLimit()
	 * @param simulate don't actually change the item, just determine the return value
	 * @return Energy transferred into the electric item
	 */
	public static int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
		if(Config.ic2Available)
			return ElectricItem.charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
		else
			return 0;
	}

	/**
	 * Discharge an item by a specified amount of energy
	 *
	 * @param itemStack electric item's stack
	 * @param amount amount of energy to charge in EU
	 * @param tier tier of the discharging device, has to be at least as high as the item to discharge
	 * @param ignoreTransferLimit ignore the transfer limit specified by getTransferLimit()
	 * @param simulate don't actually discharge the item, just determine the return value
	 * @return Energy retrieved from the electric item
	 */
	public static int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
		if(Config.ic2Available)
			return ElectricItem.discharge(itemStack, amount, tier, ignoreTransferLimit, simulate);
		else
			return 0;
	}

	/**
	 * Determine if the specified electric item has at least a specific amount of EU.
	 * This is supposed to be used in the item code during operation, for example if you want to implement your own electric item.
	 * BatPacks are not taken into account.
	 *
	 * @param itemStack electric item's stack
	 * @param amount minimum amount of energy required
	 * @return true if there's enough energy
	 */
	public static boolean canUse(ItemStack itemStack, int amount) {
		if(Config.ic2Available)
			return ElectricItem.canUse(itemStack, amount);
		else
			return true;
	}

	/**
	 * Try to retrieve a specific amount of energy from an Item, and if applicable, a BatPack.
	 * This is supposed to be used in the item code during operation, for example if you want to implement your own electric item.
	 *
	 * @param itemStack electric item's stack
	 * @param amount amount of energy to discharge in EU
	 * @param player player holding the item
	 * @return true if the operation succeeded
	 */
	public static boolean use(ItemStack itemStack, int amount, EntityPlayer player) {
		if(Config.ic2Available)
			return ElectricItem.use(itemStack, amount, player);
		else
			return true;
	}
	
	/**
	 * Credits: 
	 * 		IC2-Team
	 */
}
