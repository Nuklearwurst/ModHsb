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
import hsb.ModHsbCore;
import hsb.TileEntityDoorBase;
import hsb.TileEntityHsb;
import hsb.TileEntityLockTerminal;
import hsb.TileEntityHsbBuilding;

import java.io.File;
import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
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
			//Guis ?
			//Properties TODO
			Property debugMode = config.get(Configuration.CATEGORY_GENERAL, "debugMode", false);
			debugMode.comment="Debug Mode";
			DEBUG = Boolean.parseBoolean(debugMode.value);
			Property eclipseMode = config.get(Configuration.CATEGORY_GENERAL, "eclipseMode", false);
			eclipseMode.comment="Eclipse Mode (no IC2)";
			ECLIPSE = Boolean.parseBoolean(eclipseMode.value);
			
			//Plugins TODO
			
			//Blocks, Items
			Items.itemDebugTool = new ItemDebugTool(Config.getItemId("Debug Tool", Defaults.ITEM_DEBUG)).setItemName("Debug Tool");
			Items.itemBlockPlacer = new ItemBlockPlacer(Config.getItemId("itemBlockPlacer", Defaults.ITEM_BLOCKPLACER)).setItemName("Block MultiTool");
			Items.itemBlockPlacerEmpty = new ItemBlockPlacer(Config.getItemId("itemBlockPlacerEmpty", Defaults.ITEM_BLOCKPLACEREMPTY)).setItemName("Block MultiTool");
			Items.itemLockMonitor = new ItemLockMonitor(Config.getItemId("itemLockMonitor", Defaults.ITEM_LOCK_MONITOR)).setItemName("LockMonitor");
			Items.itemLockMonitorEmpty = new ItemLockMonitor(Config.getItemId("itemLockMonitorEmpty", Defaults.ITEM_LOCK_MONITOR_EMPTY)).setItemName("LockMonitor");
			Items.itemLockHacker = new ItemLockHacker(Config.getItemId("itemLockHacker", Defaults.ITEM_LOCK_HACKER)).setItemName("Lock Hacker");
			Items.itemLockHackerEmpty = new ItemLockHacker(Config.getItemId("itemLockHackerEmpty", Defaults.ITEM_LOCK_HACKER_EMPTY)).setItemName("Lock Hacker");
			
			Items.blockHsb = new BlockHsb(Config.getBlockId("blockHsb", Defaults.BLOCK_HSB)).setBlockName("Hsb Block");
			Items.blockHsbDoor = new BlockHsbDoor(Config.getBlockId("blockHsbDoor", Defaults.BLOCK_HSB_DOOR));
			
			Items.itemHsbDoor = new ItemHsbDoor(Config.getItemId("itemHsbDoor", Defaults.ITEM_HSB_DOOR));
			
			Items.itemUpgradeTesla = new ItemTeslaUpgrade(Config.getItemId("itemUpgradeTesla", Defaults.ITEM_UPGRADE_TESLA)).setItemName("Item Tesla Upgrade");
			
			//Not in use!
//			Items.itemBlockBuilding = new ItemBlockBuilding(Config.getItemId("itemBlockBuilding", Defaults.ITEM_BLOCKBUILDING)).setItemName("Everything");
//			Items.itemBlockLockTerminal = new ItemLockTerminal(Config.getItemId("itemBlockLockTermninal", Defaults.ITEM_BLOCKLOCKTERMINAL), Items.blockHsb.blockID).setItemName("Lock Terminal");
			
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
		GameRegistry.registerBlock(Items.blockHsb, ItemBlockHsb.class);
		//register TileEntitys
		GameRegistry.registerTileEntity(TileEntityHsb.class, "TileEntityHsb");
		GameRegistry.registerTileEntity(TileEntityHsbBuilding.class, "TileEntityHsbBuilding");
		GameRegistry.registerTileEntity(TileEntityLockTerminal.class, "TileEntityLockTerminal");
		GameRegistry.registerTileEntity(TileEntityDoorBase.class, "TileEntityDoorBase");
		//Adding recipes
		Items.initIC2();
		//DEBUG Recipes
		if(Config.DEBUG)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.itemDebugTool, 1),  new Object[] { Block.dirt});
			GameRegistry.addShapelessRecipe(new ItemStack(Items.blockHsb, 1, 1),  new Object[] { Block.dirt, Block.dirt});
			GameRegistry.addShapelessRecipe(new ItemStack(Items.itemBlockPlacer, 1),  new Object[] { Block.dirt, Block.dirt, Block.dirt});
		}
	
		//Recipes
		
		//TODO : fix
//		GameRegistry.addShapelessRecipe(new ItemStack(Items.blockHsb, 1, 0), new Object[] {Items.reinforcedStone, new ItemStack(Item.redstone,1)});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.blockHsb, 1, 1), new Object[] {new ItemStack(Items.blockHsb, 1, 0), Items.circuit});
		
		GameRegistry.addRecipe(new ItemStack(Items.itemBlockPlacer, 1), new Object[] {"I I", "ICI", " B ", 'B', Items.battery_empty, 'I', Items.refinedIron, 'C', Items.circuit});
		GameRegistry.addRecipe(new ItemStack(Items.itemUpgradeTesla, 1), new Object[] {"   ", "TCT", "   ", 'T', Config.getIC2Item("teslaCoil"), 'C', Items.circuit});
		//add Block Idsd
		ItemBlockPlacer.setBlockId(Items.blockHsb.blockID);
		//Adding names...
		LanguageRegistry.addName(Items.itemDebugTool, "Debug Tool");
		LanguageRegistry.addName(Items.itemBlockPlacer, "Block Placer");
		LanguageRegistry.addName(Items.itemBlockPlacerEmpty, "Block Placer");
		LanguageRegistry.addName(Items.itemLockMonitor, "Lock Monitor");
		LanguageRegistry.addName(Items.itemLockMonitorEmpty, "Lock Monitor");
		LanguageRegistry.addName(Items.itemLockHacker, "Lock Port Hacker");
		LanguageRegistry.addName(Items.itemLockHackerEmpty, "Lock Port Hacker");
		LanguageRegistry.addName(new ItemStack(Items.blockHsb.blockID, 1, 0), "Hsb Building Block");
		LanguageRegistry.addName(new ItemStack(Items.blockHsb.blockID, 1, 1), "Hsb Lock Terminal");
		LanguageRegistry.addName(new ItemStack(Items.blockHsb.blockID, 1, 2), "Hsb Door Base");
		LanguageRegistry.addName(Items.itemUpgradeTesla, "Tesla Upgrade");
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

}
