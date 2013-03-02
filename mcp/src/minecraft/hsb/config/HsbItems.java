package hsb.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class HsbItems {
	

	//IC2 Items
	static ItemStack reinforcedStone;
	static ItemStack circuit;
	static ItemStack refinedIron;
	static ItemStack battery_empty;
	static ItemStack battery;
	
	public static void initIC2() {
		reinforcedStone = Config.getIC2Item("reinforcedStone");
		circuit = Config.getIC2Item("electronicCircuit");
		refinedIron = Config.getIC2Item("refinedIronIngot");
		battery_empty = Config.getIC2Item("reBattery");
		battery = Config.getIC2Item("chargedReBattery");
	}
	
	//List of Items
	public static Item itemDebugTool;
	public static Item itemBlockPlacer;	
	public static Item itemBlockPlacerEmpty;
	public static Item itemLockMonitor;
	public static Item itemLockMonitorEmpty;
	public static Item itemLockHacker;
	public static Item itemLockHackerEmpty;
	//ItemBlocks
	public static Item itemBlockHsb;
	
	public static Item itemHsbDoor;
	
	public static Item itemHsbUpgrade;
	
	//Not in use!
//	public static Item itemBlockBuilding;
//	public static Item itemBlockLockTerminal;

	//Upgrades
	
	//List of Blocks
	public static Block blockHsb;
	public static Block blockHsbDoor;
	//Config

	//Register
	public static void register()
	{
		LanguageRegistry.addName(itemDebugTool, "Debug Tool");
	}
}
