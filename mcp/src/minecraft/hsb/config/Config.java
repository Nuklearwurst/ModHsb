package hsb.config;

import hsb.BlockHsb;
import hsb.BlockHsbDoor;
import hsb.ItemBlockHsb;
import hsb.ItemBlockPlacer;
import hsb.ItemDebugTool;
import hsb.ItemHsbDoor;
import hsb.ItemLockHacker;
import hsb.ItemLockMonitor;
import hsb.ItemTeslaUpgrade;
import hsb.ModHsb;
import hsb.TileEntityDoorBase;
import hsb.TileEntityHsb;
import hsb.TileEntityLockTerminal;
import hsb.TileEntityHsbBuilding;
import java.io.File;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Config {
	
	//TODO registerBlocks
	public static Configuration config;
	public static boolean DEBUG = false;
	public static boolean ECLIPSE = false;
	public static int maxPort = 99;
	public static void preinit(FMLPreInitializationEvent evt)
	{
		//loading config
		config = new Configuration(new File(evt.getModConfigurationDirectory(), "hsb.conf"));
		try {
			
			config.load();
			//Properties TODO
			Property debugMode = config.get(Configuration.CATEGORY_GENERAL, "debugMode", false);
			debugMode.comment="Debug Mode";
			DEBUG = Boolean.parseBoolean(debugMode.value);
			
			//Plugins TODO
			
			//Blocks, Items
			HsbItems.itemDebugTool = new ItemDebugTool(Config.getItemId("Debug Tool", Defaults.ITEM_DEBUG)).setItemName("Debug Tool");
			LanguageRegistry.addName(HsbItems.itemDebugTool, "Debug Tool");
			
			HsbItems.itemBlockPlacer = new ItemBlockPlacer(Config.getItemId("itemBlockPlacer", Defaults.ITEM_BLOCKPLACER)).setItemName("Block MultiTool");
			LanguageRegistry.addName(HsbItems.itemBlockPlacer, "Block Placer");
			
//			Items.itemBlockPlacerEmpty = new ItemBlockPlacer(Config.getItemId("itemBlockPlacerEmpty", Defaults.ITEM_BLOCKPLACEREMPTY)).setItemName("Block MultiTool");
			
			HsbItems.itemLockMonitor = new ItemLockMonitor(Config.getItemId("itemLockMonitor", Defaults.ITEM_LOCK_MONITOR)).setItemName("LockMonitor");
			LanguageRegistry.addName(HsbItems.itemLockMonitor, "Lock Monitor");
			
//			Items.itemLockMonitorEmpty = new ItemLockMonitor(Config.getItemId("itemLockMonitorEmpty", Defaults.ITEM_LOCK_MONITOR_EMPTY)).setItemName("LockMonitor");
			
			HsbItems.itemLockHacker = new ItemLockHacker(Config.getItemId("itemLockHacker", Defaults.ITEM_LOCK_HACKER)).setItemName("Lock Hacker");
			LanguageRegistry.addName(HsbItems.itemLockHacker, "Lock Port Hacker");
			
//			Items.itemLockHackerEmpty = new ItemLockHacker(Config.getItemId("itemLockHackerEmpty", Defaults.ITEM_LOCK_HACKER_EMPTY)).setItemName("Lock Hacker");
			
			HsbItems.blockHsb = new BlockHsb(Config.getBlockId("blockHsb", Defaults.BLOCK_HSB)).setBlockName("Hsb Building Block");
			
			HsbItems.blockHsbDoor = new BlockHsbDoor(Config.getBlockId("blockHsbDoor", Defaults.BLOCK_HSB_DOOR));
			
			HsbItems.itemHsbDoor = new ItemHsbDoor(Config.getItemId("itemHsbDoor", Defaults.ITEM_HSB_DOOR)).setItemName("LockDoor");
			LanguageRegistry.addName(HsbItems.itemHsbDoor, "HSB Door");
			
			HsbItems.itemUpgradeTesla = new ItemTeslaUpgrade(Config.getItemId("itemUpgradeTesla", Defaults.ITEM_UPGRADE_TESLA)).setItemName("Item Tesla Upgrade");
			LanguageRegistry.addName(HsbItems.itemUpgradeTesla, "Tesla Upgrade");
					
		//catching Errors	
		} catch(Exception e) {
			FMLLog.log(Level.SEVERE, e, "Hsb Core has had a problem loading it's configuration file");
		} finally {
			//saving
			config.save();
		}
	}
	
	public static void init(FMLInitializationEvent evt)
	{
		//register Blocks
		GameRegistry.registerBlock(HsbItems.blockHsb, ItemBlockHsb.class);
		
		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb, 1, 0), "Hsb Building Block");
		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb, 1, 1), "Hsb Lock Terminal");
		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb, 1, 2), "Hsb Door Base");
		
		//register TileEntitys
		GameRegistry.registerTileEntity(TileEntityHsb.class, "TileEntityHsb");
		GameRegistry.registerTileEntity(TileEntityHsbBuilding.class, "TileEntityHsbBuilding");
		GameRegistry.registerTileEntity(TileEntityLockTerminal.class, "TileEntityLockTerminal");
		GameRegistry.registerTileEntity(TileEntityDoorBase.class, "TileEntityDoorBase");
		
		//add Block Idsd
		ItemBlockPlacer.setBlockId(HsbItems.blockHsb.blockID);
		
		//Adding names...
//		LanguageRegistry.addName(HsbItems.itemDebugTool, "Debug Tool");
//		LanguageRegistry.addName(HsbItems.itemBlockPlacer, "Block Placer");
//		LanguageRegistry.addName(HsbItems.itemBlockPlacerEmpty, "Block Placer");
//		LanguageRegistry.addName(HsbItems.itemLockMonitor, "Lock Monitor");
//		LanguageRegistry.addName(HsbItems.itemLockMonitorEmpty, "Lock Monitor");
//		LanguageRegistry.addName(HsbItems.itemLockHacker, "Lock Port Hacker");
//		LanguageRegistry.addName(HsbItems.itemLockHackerEmpty, "Lock Port Hacker");
//		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb.blockID, 1, 0), "Hsb Building Block");
//		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb.blockID, 1, 1), "Hsb Lock Terminal");
//		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb.blockID, 1, 2), "Hsb Door Base");
//		LanguageRegistry.addName(HsbItems.itemUpgradeTesla, "Tesla Upgrade");
	}
	
	public static void initRecipes() {
		//Adding recipes
		HsbItems.initIC2();
		//Recipes
		
		//TODO : fix
		GameRegistry.addShapelessRecipe(new ItemStack(HsbItems.blockHsb, 1, 0), HsbItems.reinforcedStone, new ItemStack(Item.redstone,1));
		GameRegistry.addShapelessRecipe(new ItemStack(HsbItems.blockHsb, 1, 1), new ItemStack(HsbItems.blockHsb, 1, 0), HsbItems.circuit);
		
//		Ic2Recipes.addCraftingRecipe(new ItemStack(HsbItems.itemBlockPlacer, 1), "I I", "ICI", " B ", 'B', HsbItems.battery, 'I', HsbItems.refinedIron, 'C', HsbItems.circuit);
		
//		GameRegistry.addRecipe(new ItemStack(HsbItems.itemUpgradeTesla, 1), "   ", "TCT", "   ", 'T', Config.getIC2Item("teslaCoil"), 'C', HsbItems.circuit);
	}
	
    public static int getBlockId(String name, int defaultId)//BlockIDs
    {
        return Integer.parseInt(config.getBlock(name, defaultId).value);
    }
    public static int getItemId(String name, int defaultId)
    {
        return Integer.parseInt(config.getItem(name, defaultId).value); //ItemIDs
    }
    public static ItemStack getIC2Item(String name)
    {
    	if(!Config.ECLIPSE)
    	{
    		return ic2.api.Items.getItem(name);
    	} else {
    		return new ItemStack(Block.dirt, 1);
    	}
    }
    public static void logDebug(String s) {
    	if(DEBUG) {
    		FMLLog.info(s);
    	}
    }

}
