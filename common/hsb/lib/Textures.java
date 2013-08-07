package hsb.lib;

import net.minecraft.util.ResourceLocation;

public class Textures {
	
	public static final String texFolder = Reference.MOD_ID.toLowerCase() + ":";
	
	public static final String GUI_LOCATION = "textures/gui/";
	public static final String ITEM_LOCATION = "textures/items/";
	
	public static final ResourceLocation GUI_TERMINAL_OPTIONS = getResourceLocation(GUI_LOCATION + "GuiLockTerminalOptions.png");
	public static final ResourceLocation GUI_TERMINAL = getResourceLocation(GUI_LOCATION + "GuiLockTerminal.png");
	public static final ResourceLocation GUI_MULTI_TOOL = getResourceLocation(GUI_LOCATION + "GuiBlockPlacer.png");
	public static final ResourceLocation GUI_UNLOCKER = getResourceLocation(GUI_LOCATION + "GuiUnlocker.png");
	public static final ResourceLocation GUI_UPGRADE_CAMO = getResourceLocation(GUI_LOCATION + "GuiUpgradeCamo.png");
	

	//Items
	public static final String ITEM_LOCK_MONITOR = texFolder + "LockMonitor";
	public static final String ITEM_LOCK_HACKER = texFolder + "LockHacker";
	public static final String ITEM_MULTI_TOOL = texFolder + "BlockPlacer";
	public static final String ITEM_HSB_DOOR = texFolder + "itemLockDoor";
	public static final String ITEM_DEBUG_TOOL = texFolder + "debugTool";//unused

	public static final String[] ITEM_UPGRADE_HSB_MACHINE = {	texFolder + "UpgradeEnergyStorage"};
	
	public static final String[] ITEM_UPGRADE_HSB = {	texFolder + "UpgradeTesla", 
														texFolder + "UpgradePass", 
														texFolder + "UpgradeSecurity", 
														texFolder + "UpgradeCamoflage", 
														texFolder + "UpgradeEmpty", 
														texFolder + "UpgradeEmpty"};
	//Blocks
	//no better solution yet
	public static final int[][] sideAndFacingToSpriteOffset = { { 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 }, { 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 }, { 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };
	
	
	public static final String[] BLOCK_HSB = {		texFolder + "hsb_red",
													texFolder + "hsb_green",
													texFolder + "terminal",
													texFolder + "doorBase_red",
													texFolder + "doorBase_green",
													texFolder + "Orange"};
	
	public static final String[] BLOCK_MACHINE = {		texFolder + "machine", 
														texFolder + "unlocker"};
	
	public static final String[] BLOCK_HSB_DOOR = {		texFolder + "doorTop_red", 
														texFolder + "doorTop_green", 
														texFolder + "doorBot_red", 
														texFolder + "doorBot_green"};
	
	public static final String BLOCK_HEAVY_STONE = texFolder + "heavyStone";




	public static ResourceLocation getResourceLocation(String path) {
		return new ResourceLocation(Reference.MOD_ID.toLowerCase(), path);
	}

}
