package hsb.config;

import hsb.blocks.BlockHsb;
import hsb.blocks.BlockHsbDoor;
import hsb.items.ItemBlockHsb;
import hsb.items.ItemBlockPlacer;
import hsb.items.ItemDebugTool;
import hsb.items.ItemHsbDoor;
import hsb.items.ItemHsbUpgrade;
import hsb.items.ItemLockHacker;
import hsb.items.ItemLockMonitor;
import hsb.tileentitys.TileEntityDoorBase;
import hsb.tileentitys.TileEntityHsb;
import hsb.tileentitys.TileEntityHsbBuilding;
import hsb.tileentitys.TileEntityHsbGuiAccess;
import hsb.tileentitys.TileEntityLockTerminal;
import ic2.api.Ic2Recipes;

import java.io.File;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Config {
	
	public static Configuration config;
	
	public static boolean DEBUG = false;
	public static boolean ECLIPSE = false;
	
	public static int maxPort = 99;
	
	//IC2
	public static boolean ic2Available = true;
	//energy
	public static double energyHsbBlock = 0;
	public static double energyUpgradeTesla = 0;
	
	public static int energyBlockPlacer = 0;
	public static int energyHsbMonitor = 0;
	public static int energyHsbHacker = 0;
	public static void preinit(FMLPreInitializationEvent evt)
	{
		//loading config
		config = new Configuration(new File(evt.getModConfigurationDirectory(), "hsb.cfg"));
		try {
			
			config.load();
		//Properties
			//Debug Mode
			Property debugMode = config.get(Configuration.CATEGORY_GENERAL, "debugMode", false);
			debugMode.comment="Debug Mode";
			DEBUG = Boolean.parseBoolean(debugMode.value);
			
			//Use IC2 (true recommend)
			Property propIC2 = config.get(Configuration.CATEGORY_GENERAL, "useIC2", true);
			propIC2.comment = "Use IC2 if installed";
			ic2Available = Boolean.parseBoolean(propIC2.value);
			
		//energyuse
			//Building Block
			Property propEnergyHsbBlock = config.get(Configuration.CATEGORY_GENERAL, "energyHsbBlock", Defaults.ENERGY_HSB_BLOCK);
			propEnergyHsbBlock.comment="Energyuse per one locked Block";
			energyHsbBlock = Double.parseDouble(propEnergyHsbBlock.value);
			
			//Tesla Upgrade
			Property propEnergyUpgradeTesla = config.get(Configuration.CATEGORY_GENERAL, "energyUpgradeTesla", Defaults.ENERGY_HSB_TESLA);
			propEnergyUpgradeTesla.comment="extra energyuse per one locked Block and Tesla Upgrade";
			energyUpgradeTesla = Double.parseDouble(propEnergyUpgradeTesla.value);
			
			//Block Placer
			Property propEnergyBlockPlacer = config.get(Configuration.CATEGORY_GENERAL, "energyBlockPlacer", Defaults.ENERGY_BLOCK_PLACER);
			propEnergyBlockPlacer.comment="EnergyUse for BlockPlacer";
			energyBlockPlacer = Integer.parseInt(propEnergyBlockPlacer.value);
			
			//Port Monitor
			Property propEnergyPortMonitor = config.get(Configuration.CATEGORY_GENERAL, "energyPortMonitor", Defaults.ENERGY_PORT_MONITOR);
			propEnergyPortMonitor.comment="EnergyUse for Port Monitor";
			energyHsbMonitor = Integer.parseInt(propEnergyPortMonitor.value);
			
			//Port Hacker
			Property propEnergyPortHacker = config.get(Configuration.CATEGORY_GENERAL, "energyPortHacker", Defaults.ENERGY_PORT_HACKER);
			propEnergyPortHacker.comment="EnergyUse for Port Hacker";
			energyHsbHacker = Integer.parseInt(propEnergyPortHacker.value);
						
		//Items
			//DebugTool
			HsbItems.itemDebugTool = new ItemDebugTool(Config.getItemId("Debug Tool", Defaults.ITEM_DEBUG)).setItemName("Debug Tool");
			LanguageRegistry.addName(HsbItems.itemDebugTool, "Debug Tool");
			
			//BlockPlacer
			HsbItems.itemBlockPlacer = new ItemBlockPlacer(Config.getItemId("itemBlockPlacer", Defaults.ITEM_BLOCKPLACER)).setItemName("Block MultiTool");
			LanguageRegistry.addName(HsbItems.itemBlockPlacer, "Block Placer");
			
			//LockMonitor
			HsbItems.itemLockMonitor = new ItemLockMonitor(Config.getItemId("itemLockMonitor", Defaults.ITEM_LOCK_MONITOR)).setItemName("LockMonitor");
			LanguageRegistry.addName(HsbItems.itemLockMonitor, "Lock Monitor");
			
			//LockHacker
			HsbItems.itemLockHacker = new ItemLockHacker(Config.getItemId("itemLockHacker", Defaults.ITEM_LOCK_HACKER)).setItemName("Lock Hacker");
			LanguageRegistry.addName(HsbItems.itemLockHacker, "Lock Port Hacker");
			
			//Item Hsb Door
			HsbItems.itemHsbDoor = new ItemHsbDoor(Config.getItemId("itemHsbDoor", Defaults.ITEM_HSB_DOOR)).setItemName("LockDoor");
			LanguageRegistry.addName(HsbItems.itemHsbDoor, "HSB Door");
			
			//Upgrades
			HsbItems.itemHsbUpgrade = new ItemHsbUpgrade(Config.getItemId("itemHsbUpgrade", Defaults.ITEM_HSB_UPGRADE));
			
		//Blocks
			//BlockHsb (unbreakable)
			HsbItems.blockHsb = new BlockHsb(Config.getBlockId("blockHsb", Defaults.BLOCK_HSB)).setBlockName("Hsb Building Block");
			
			//BlockHsbDoor
			HsbItems.blockHsbDoor = new BlockHsbDoor(Config.getBlockId("blockHsbDoor", Defaults.BLOCK_HSB_DOOR));
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
		GameRegistry.registerBlock(HsbItems.blockHsb, ItemBlockHsb.class, "blockHsb");
		
	// Adding names for Blocks
		//BlockHsb
		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb, 1, 0), "Hsb Building Block");
		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb, 1, 1), "Hsb Lock Terminal");
		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb, 1, 2), "Hsb Door Base");
		LanguageRegistry.addName(new ItemStack(HsbItems.blockHsb, 1, 3), "Hsb Gui Access");
		
	//Adding names for Items
		//Item Upgrade
		LanguageRegistry.addName(new ItemStack(HsbItems.itemHsbUpgrade, 1, 0), "Tesla Upgrade");
		LanguageRegistry.addName(new ItemStack(HsbItems.itemHsbUpgrade, 1, 1), "Password Upgrade");
		LanguageRegistry.addName(new ItemStack(HsbItems.itemHsbUpgrade, 1, 2), "Security Level Upgrade");
		LanguageRegistry.addName(new ItemStack(HsbItems.itemHsbUpgrade, 1, 3), "Camoflage Upgrade");
		
	//register TileEntitys
		GameRegistry.registerTileEntity(TileEntityHsb.class, "TileEntityHsb");
		GameRegistry.registerTileEntity(TileEntityHsbBuilding.class, "TileEntityHsbBuilding");
		GameRegistry.registerTileEntity(TileEntityLockTerminal.class, "TileEntityLockTerminal");
		GameRegistry.registerTileEntity(TileEntityDoorBase.class, "TileEntityDoorBase");
		GameRegistry.registerTileEntity(TileEntityHsbGuiAccess.class, "TileEntityHsbGuiAccess");
		
	//add Block Ids
		ItemBlockPlacer.setBlockId(HsbItems.blockHsb.blockID);
	}
	
	public static void postInit(FMLPostInitializationEvent evt) {
		if(Config.checkIC2Installed()) {
			Config.logInfo("IC2 found!!");
		} else {
			Config.logError("IC2 not found, this mod may not work without IC2!!!\n Please check if you installed IC2 (correctly).");
		}
		
	}
	
	//TODO: check if ic2 is installed (eg. different crafting recipes)
	public static void initRecipes() {
		//Adding recipes
		HsbItems.initIC2();
		//Recipes
		//Standard Recipes
		
		//IC2 Recipes
		//Shapeless:
		//Building Block
		addShapelessCraftingRecipe(new ItemStack(HsbItems.blockHsb, 1, 0), HsbItems.reinforcedStone, new ItemStack(Item.redstone,1));
		//Terminal
		addShapelessCraftingRecipe(new ItemStack(HsbItems.blockHsb, 1, 1), new ItemStack(HsbItems.blockHsb, 1, 0), HsbItems.circuit);
		//HsbDoor
		addShapelessCraftingRecipe(new ItemStack(HsbItems.itemHsbDoor, 1, 0), Config.getIC2Item("reinforcedDoor"), Item.redstone);
		
		//Shaped:
		
		//Lock Monitor(Charge-aware)
		addCraftingRecipe(new ItemStack(HsbItems.itemLockMonitor, 1), new Object[] 
			{
				"ICI", "IGI", " B ", 
				Character.valueOf('B'), HsbItems.battery, 
				Character.valueOf('I'), HsbItems.refinedIron, 
				Character.valueOf('C'), HsbItems.circuit,
				Character.valueOf('G'), Block.glass 
			});
		
		//Lock Monitor(Empty)
		addCraftingRecipe(new ItemStack(HsbItems.itemLockMonitor, 1), new Object[] 
			{
				"ICI", "IGI", " B ", 
				Character.valueOf('B'), HsbItems.battery_empty, 
				Character.valueOf('I'), HsbItems.refinedIron, 
				Character.valueOf('C'), HsbItems.circuit,
				Character.valueOf('G'), Block.glass 
			});
		
		//Lock Hacker(Charge-aware)
		addCraftingRecipe(new ItemStack(HsbItems.itemLockHacker, 1), new Object[] 
			{
				"IEI", "LML", "RAR", 
				Character.valueOf('E'), Item.enderPearl, 
				Character.valueOf('I'), HsbItems.refinedIron, 
				Character.valueOf('L'), new ItemStack(Item.dyePowder, 4),
				Character.valueOf('M'), HsbItems.itemLockMonitor,
				Character.valueOf('R'), Item.redstone,
				Character.valueOf('A'), Config.getIC2Item("advancedCircuit")
			});
		
		//Block Placer(Charge-aware)
		addCraftingRecipe(new ItemStack(HsbItems.itemBlockPlacer, 1), new Object[] 
			{
				"I I", "ICI", " B ", 
				Character.valueOf('B'), HsbItems.battery, 
				Character.valueOf('I'), HsbItems.refinedIron, 
				Character.valueOf('C'), HsbItems.circuit
			});
		
		//Block Placer(Empty)
		addCraftingRecipe(new ItemStack(HsbItems.itemBlockPlacer, 1), new Object[] 
			{
				"I I", "ICI", " B ", 
				Character.valueOf('B'), HsbItems.battery_empty, 
				Character.valueOf('I'), HsbItems.refinedIron, 
				Character.valueOf('C'), HsbItems.circuit
			});
		
		//Tesla Upgrade
		addCraftingRecipe(new ItemStack(HsbItems.itemHsbUpgrade, 1, 0), new Object[] 
			{
				"   ", "CTC", "   ", 
				Character.valueOf('T'), Config.getIC2Item("teslaCoil"), 
				Character.valueOf('C'), HsbItems.circuit 
			});
		
	}
	
	/**
	 * shaped recipe
	 * @param result
	 * @param args
	 */
	private static void addCraftingRecipe(ItemStack result, Object... args) {
		if(Config.ic2Available) {
			Ic2Recipes.addCraftingRecipe(result, args);
		} else {
//			GameRegistry.addRecipe(result, args);
		}
	}
	/**
	 * shapeless recipe
	 * @param result
	 * @param args
	 */
	private static void addShapelessCraftingRecipe(ItemStack result, Object... args) {
		if(Config.ic2Available) {
			Ic2Recipes.addShapelessCraftingRecipe(result, args);
		} else {
//			GameRegistry.addShapelessRecipe(result, args);
		}
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
    	return ic2.api.Items.getItem(name);
    }
    public static int getIC2ItemId(String name) {
    	ItemStack i = getIC2Item(name);
    	if(i != null) {
    		return i.itemID;
    	}
    	return -1;
    }
    public static void logDebug(String s) {
    	if(DEBUG) {
    		FMLLog.info("[HSB]" + s);
    	}
    }
    public static void logError(String s) {
		FMLLog.severe("[HSB]" + s);
    }
    
    public static void logInfo(String s) {
		FMLLog.info("[HSB]" + s);
    }
    
    private static boolean checkIC2Installed() {
    	return Config.ic2Available = Loader.isModLoaded("IC2");
    }

}