package hsb.config;

import cpw.mods.fml.common.registry.LanguageRegistry;
import hsb.ItemDebugTool;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class Items {

	//IC2 Items
	static ItemStack reinforcedStone = Config.getIC2Item("reinforcedStone");
	static ItemStack circuit = Config.getIC2Item("electronicCircuit");
	static ItemStack refinedIron = Config.getIC2Item("refinedIronIngot");
	static ItemStack battery_empty = Config.getIC2Item("reBattery");
	
	//List of Items
	public static Item itemDebugTool;
	public static Item itemBlockPlacer;	
	public static Item itemBlockPlacerEmpty;
	//ItemBlocks
	public static Item itemBlockHsb;
	
	public static Item itemHsbDoor;
	
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
