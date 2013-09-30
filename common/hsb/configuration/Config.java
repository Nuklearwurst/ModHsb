package hsb.configuration;



import hsb.block.ModBlocks;
import hsb.core.plugin.PluginBC3;
import hsb.core.plugin.PluginUE;
import hsb.lib.BlockIds;
import hsb.lib.ItemIds;
import hsb.lib.Strings;

import java.util.logging.Level;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Config {
	
	public static Configuration config;
	
	public static final String CATEGORY_GENERAL = Configuration.CATEGORY_GENERAL;
	public static final String CATEGORY_BLOCK = Configuration.CATEGORY_BLOCK;
	public static final String CATEGORY_ITEM = Configuration.CATEGORY_ITEM;
	public static final String CATEGORY_COMPATIBILITY = "compatibility"; //Compatibility
	public static final String CATEGORY_ENERGY = "energy"; //energy values

	public static void readConfig(FMLPreInitializationEvent evt)
	{
		//loading config
		config = new Configuration(evt.getSuggestedConfigurationFile());
		try {
			//load Config
			config.load();
			
		//Properties
			Property prop;
			
			
			//Debug Mode
			prop = config.get(CATEGORY_GENERAL, "debugMode", false);
			prop.comment="Debug Mode";
			Settings.DEBUG = prop.getBoolean(false);
			
			////////////
			// Energy //
			////////////
			
			prop = config.get(CATEGORY_ENERGY, "energyUseTerminal", Settings.terminalEnergyUse);
			prop.comment = "terminal energy usage per block";
			Settings.terminalEnergyUse = (float) prop.getDouble(Settings.terminalEnergyUse);
			
			prop = config.get(CATEGORY_ENERGY, "energyStorageTerminal", Settings.terminalEnergyStorage);
			prop.comment = "terminal energy storage";
			Settings.terminalEnergyStorage = (float) prop.getDouble(Settings.terminalEnergyStorage);
			
			prop = config.get(CATEGORY_ENERGY, "energyStorageUpgrade", Settings.UPGRADE_ENERGY_STORAGE);
			prop.comment = "upgrade energy storage";
			Settings.UPGRADE_ENERGY_STORAGE = (float) prop.getDouble(Settings.UPGRADE_ENERGY_STORAGE);
			
			//////////////
			// Settings //
			//////////////
			
			prop = config.get(CATEGORY_GENERAL, "unlocker Ticks to Unlock", Settings.ticksToUnlock);
			prop.comment = "number of ticks unlocker needs to unlock a terminal by default";
			Settings.ticksToUnlock = prop.getInt(Settings.ticksToUnlock);
			
			///////////////////
			// Compatibility //
			///////////////////
			
			//Use IC2 (true recommended)
			prop = config.get(CATEGORY_COMPATIBILITY, "useIC2", true);
			prop.comment = "Use IC2 if installed";
			Settings.usePluginIC2 = prop.getBoolean(true);
			
			//Use BC3 (true recommended)
			prop = config.get(CATEGORY_COMPATIBILITY, "useBC3", true);
			prop.comment = "Use BC3 if installed";
			Settings.usePluginBC3 = prop.getBoolean(true);
			
			//Use UE (true recommended)
			prop = config.get(CATEGORY_COMPATIBILITY, "useUE", true);
			prop.comment = "Use UE if installed";
			Settings.usePluginUE = prop.getBoolean(true);
			
			//EU to MJ
			prop = config.get(CATEGORY_COMPATIBILITY, "MJ to EU conversionrate", PluginBC3.BC3_RATIO);
			prop.comment = "Conversion rate from MJ to EU";
			PluginBC3.BC3_RATIO = (float)prop.getDouble(PluginBC3.BC3_RATIO);
			
			//UE Joules to EU
			prop = config.get(CATEGORY_COMPATIBILITY, "Joules to EU conversionrate", PluginUE.UE_RATIO);
			prop.comment = "Conversion rate from UE Joules to EU";
			PluginUE.UE_RATIO = (float)prop.getDouble(PluginUE.UE_RATIO);
			
		//Items
			initItems(config);
		//Blocks
			initBlocks(config);
			
		//catching Errors	
		} catch(Exception e) {
			FMLLog.log(Level.SEVERE, e, "Hsb has had a problem loading it's configuration file");
		} finally {
			//saving
			config.save();
		}
	}
	
	private static void initItems(Configuration config)
	{
		ItemIds.MULTI_TOOL = getItem(Strings.ITEM_MULTI_TOOL, ItemIds.MULTI_TOOL_DEFAULT);
		ItemIds.UPGRADE_HSB = getItem(Strings.ITEM_HSB_UPGRADES[4], ItemIds.UPGRADE_HSB_DEFAULT);
		ItemIds.UPGRADE_HSB_MACHINE = getItem(Strings.ITEM_HSB_MACHINE_UPGRADES[0], ItemIds.UPGRADE_HSB_MACHINE_DEFAULT);
		ItemIds.LOCK_MONITOR = getItem(Strings.ITEM_LOCK_MONITOR, ItemIds.LOCK_MONITOR_DEFAULT);
		ItemIds.LOCK_HACKER = getItem(Strings.ITEM_LOCK_HACKER, ItemIds.LOCK_HACKER_DEFAULT);
		ItemIds.HSB_DOOR = getItem(Strings.BLOCK_HSB_DOOR, ItemIds.HSB_DOOR_DEFAULT);
	}
	private static void initBlocks(Configuration config) 
	{
		BlockIds.HEAVY_STONE = getBlock(Strings.BLOCK_HEAVY_STONE, BlockIds.HEAVY_STONE_DEFAULT);
		BlockIds.HSB = getBlock(Strings.BLOCK_HSB, BlockIds.HSB_DEFAULT);
		BlockIds.HSB_DOOR = getBlock(Strings.BLOCK_HSB_DOOR, BlockIds.HSB_DOOR_DEFAULT);
		BlockIds.MACHINE = getBlock(Strings.BLOCK_MACHINE, BlockIds.MACHINE_DEFAULT);
	}
	
	private static int getBlock(String name, int defaultID) {
		return config.getBlock(name, defaultID).getInt();
	}
	private static int getItem(String name, int defaultID) {
		return config.getItem(name, defaultID).getInt();
	}
	
	/**
	 * unused
	 */
	@Deprecated
    public static void initNames() 
    {
    	//Blocks
			//BlockHsb
			LanguageRegistry.addName(new ItemStack(ModBlocks.blockHsb, 1, 0), "Hsb Building Block");
			LanguageRegistry.addName(new ItemStack(ModBlocks.blockHsb, 1, 1), "Hsb Lock Terminal");
			LanguageRegistry.addName(new ItemStack(ModBlocks.blockHsb, 1, 2), "Hsb Door Base");
			LanguageRegistry.addName(new ItemStack(ModBlocks.blockHsb, 1, 3), "Hsb Gui Access");
			
		LanguageRegistry.addName(new ItemStack(ModBlocks.blockHeavyStone,  1, 0), "Heavy Stone");
    	//Items
    }
}
