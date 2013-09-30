package hsb.core.plugin.ic2;

import hsb.configuration.Settings;
import hsb.item.ModItems;
import hsb.upgrade.UpgradeRegistry;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;

public class PluginIC2{

	
	public static final String modId = "IC2";
	
	public static boolean load() {
		boolean available = Loader.isModLoaded(modId);
		//loading plugin
		if(available) {
			//enable electric items etc.
			ModItems.itemLockHacker.setMaxDamage(13);
			ModItems.itemLockMonitor.setMaxDamage(13);
		}		
		
		return available;
	}	 

	
	///////////
	// Items //
	///////////
	
	public static ItemStack getIC2Item(String name)
    {
		if(Settings.usePluginIC2)
			return ic2.api.item.Items.getItem(name);
		return null;
    }
	
	public static int getIC2ItemId(String name) {
		if(Settings.usePluginIC2) {
	    	ItemStack i = getIC2Item(name);
	    	if(i != null) {
	    		return i.itemID;
	    	}
		}
    	return -1;
    }
	
	////////////
	// Energy //
	////////////
	
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
		if(Settings.usePluginIC2)
			return ElectricItem.manager.charge(itemStack, amount, tier, ignoreTransferLimit, simulate);
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
		if(Settings.usePluginIC2)
			return ElectricItem.manager.discharge(itemStack, amount, tier, ignoreTransferLimit, simulate);
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
		if(Settings.usePluginIC2)
			return ElectricItem.manager.canUse(itemStack, amount);
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
		if(Settings.usePluginIC2)
			return ElectricItem.manager.use(itemStack, amount, player);
		else
			return true;
	}

	/**
	 * get upgradekeys for ic2 upgrades
	 * @param stack
	 * @return
	 */
	public static String getIc2UpgradeKey(ItemStack stack) {
		String key = null;
		if(Settings.usePluginIC2)
		{
			if(stack.isItemEqual(getIC2Item("transformerUpgrade")))
			{
				key = UpgradeRegistry.ID_UPGRADE_DUMMY;
			}
				
			if(stack.isItemEqual(getIC2Item("energyStorageUpgrade")))
			{
				key = UpgradeRegistry.ID_UPGRADE_STORAGE;
			}
		
			if(stack.isItemEqual(getIC2Item("overclockerUpgrade")))
			{
				key = UpgradeRegistry.ID_UPGRADE_DUMMY;
			}
		}
		return key;
	}

	
	/**
	 * Credits: 
	 * 		IC2-Team
	 */
}
