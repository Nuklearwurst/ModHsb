package hsb.lib;

public class Textures {

	public static final String GUI_LOCATION = "/mods/hsb/textures/gui/";
	
	public static final String GUI_TERMINAL_OPTIONS = GUI_LOCATION + "GuiLockTerminalOptions.png";
	public static final String GUI_TERMINAL = GUI_LOCATION + "GuiLockTerminal.png";
	public static final String GUI_MULTI_TOOL = GUI_LOCATION + "GuiBlockPlacer.png";
	
	private static final String texFolder = Reference.MOD_ID.toLowerCase() + ":";

	public static final String ITEM_LOCK_MONITOR = texFolder + "LockMonitor";
	public static final String ITEM_LOCK_HACKER = texFolder + "LockHacker";
	public static final String ITEM_MULTI_TOOL = texFolder + "BlockPlacer";
	public static final String ITEM_HSB_DOOR = texFolder + "itemLockDoor";
	
	public static final String[] ITEM_UPGRADE_HSB = {	texFolder + "UpgradeTesla", 
														texFolder + "UpgradePass", 
														texFolder + "UpgradeSecurity", 
														texFolder + "UpgradeCamoflage", 
														texFolder + "UpgradeEmpty", 
														texFolder + "UpgradeEmpty"};

	public static final String[] BLOCK_HSB_DOOR = {		texFolder + "doorTop_red", 
														texFolder + "doorTop_green", 
														texFolder + "doorBot_red", 
														texFolder + "doorBot_green"};

	public static final String[] ITEM_UPGRADE_HSB_MACHINE = {	texFolder + "UpgradeEnergyStorage"};



}
