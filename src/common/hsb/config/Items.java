package hsb.config;

import cpw.mods.fml.common.registry.LanguageRegistry;
import hsb.ItemDebugTool;
import net.minecraft.src.Block;
import net.minecraft.src.Item;

public class Items {

	
	//List of Items
	public static Item itemDebugTool;
	public static Item itemBlockPlacer;	
	public static Item itemBlockPlacerEmpty;
	//ItemBlocks
	public static Item itemBlockHsb;
	
	//Not in use!
//	public static Item itemBlockBuilding;
//	public static Item itemBlockLockTerminal;

	//Upgrades
	
	//List of Blocks
	public static Block blockHsb;
	//Config

	//Register
	public static void register()
	{
		LanguageRegistry.addName(itemDebugTool, "Debug Tool");
	}
}
