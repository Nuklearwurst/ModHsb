package hsb.item;

import hsb.lib.ItemIds;
import net.minecraft.item.Item;

public class ModItems {

	public static Item itemMultiTool;
	public static Item itemUpgradeHsb;
	public static Item itemUpgradeHsbMachine;
	public static Item itemLockMonitor;
	public static Item itemLockHacker;
	public static Item itemHsbDoor;
	
	public static void init() {
		itemMultiTool = new ItemHsbMultiTool(ItemIds.MULTI_TOOL);
		itemUpgradeHsb = new ItemUpgradeHsb(ItemIds.UPGRADE_HSB);
		itemUpgradeHsbMachine = new ItemHsbMachineUpgrade(ItemIds.UPGRADE_HSB_MACHINE);
		itemLockMonitor = new ItemLockMonitor(ItemIds.LOCK_MONITOR, false);
		itemLockHacker = new ItemLockMonitor(ItemIds.LOCK_HACKER, true);
		itemHsbDoor = new ItemHsbDoor(ItemIds.HSB_DOOR);
	}
}
