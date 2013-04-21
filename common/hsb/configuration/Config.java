package hsb.configuration;


import hsb.block.ModBlocks;
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

	public static void readConfig(FMLPreInitializationEvent evt)
	{
		//loading config
		config = new Configuration(evt.getSuggestedConfigurationFile());
		try {
			//load Config
			config.load();
			
		//Properties
			//Debug Mode
			Property debugMode = config.get(Configuration.CATEGORY_GENERAL, "debugMode", false);
			debugMode.comment="Debug Mode";
			Settings.DEBUG = debugMode.getBoolean(false);
			
			//Use IC2 (true recommended)
			Property propIC2 = config.get(Configuration.CATEGORY_GENERAL, "useIC2", true);
			propIC2.comment = "Use IC2 if installed";
			Settings.ic2Available = propIC2.getBoolean(true);
			
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
