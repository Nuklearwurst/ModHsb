package hsb.lib;

import nwcore.core.handler.LocalizationHandler;

public class Strings {
	
	// Block Names
	//blockHeavyStone
	public static final String BLOCK_HEAVY_STONE = "heavyStone";
	//blockHsb
	public static final String BLOCK_HSB = "hsb";
	public static final String BLOCK_HSB_BUILDING = "hsbBuilding";
	public static final String BLOCK_HSB_TERMINAL = "hsbTerminal";
	public static final String BLOCK_HSB_GUI_ACCESS = "hsbGuiAccess";
	public static final String BLOCK_HSB_DOOR_BASE = "hsbDoorBase";
	//blockMachine
	public static final String BLOCK_MACHINE = "machine";
	public static final String BLOCK_MACHINE_UNLOCKER = "hsbUnlocker";
	//blockDoor
	public static final String BLOCK_HSB_DOOR = "hsbDoor";
	
	//Item names
	public static final String ITEM_MULTI_TOOL = "multiTool";
	public static final String[] ITEM_HSB_UPGRADES = {"upgradeTesla", "upgradePassword", "upgradeSecurity", "upgradeCamoflage", "upgradeDummy", "upgradeEmpty"};
	public static final String[] ITEM_HSB_MACHINE_UPGRADES = {"upgradeStorage"};
	public static final String ITEM_LOCK_MONITOR = "lockMonitor";
	public static final String ITEM_LOCK_HACKER = "lockHacker";
	public static final String ITEM_HSB_DOOR = "hsbDoor";
	
	public static final String ITEM_BOAT_BLOCK = "boatBlock";
		
	public static final String TILE_ENTITY_HSB_BUILDING = "tileEntityHsbBuilding";
	public static final String TILE_ENTITY_HSB_TERMINAL = "tileEntityHsbTerminal";
	public static final String TILE_ENTITY_HSB_GUI_ACCESS = "tileEntityHsbGuiAccess";
	public static final String TILE_ENTITY_HSB_DOOR_BASE = "tileEntityHsbDoorBase";
	public static final String TILE_ENTITY_UNLOCKER = "tileEntityUnlocker";
	
	public static final String INVALID_FINGERPRINT_MESSAGE = "Fingerprint is invalid!";
	
	
	//Gui Translation
	public static final String GUI_PORT = "gui.port";
	public static final String GUI_PASSWORD = "gui.password";
	public static final String GUI_BACK = "gui.back";
	public static final String GUI_LOCK = "gui.lock";
	public static final String GUI_UNLOCK = "gui.unlock";
	public static final String GUI_OPTIONS = "gui.options";
	public static final String GUI_DONE = "gui.done";
	public static final String GUI_MODE = "gui.mode";
	public static final String GUI_ACTIVATE = "chat.activate";
	public static final String GUI_DISABLE = "chat.disable";
	
	public static final String UPGRADE_GUI_BUTTON_TESLA = "upgrade.gui.button.tesla";
	public static final String UPGRADE_GUI_BUTTON_CAMO = "upgrade.gui.button.camo";
	
	//container
	public static final String CONTAINER_MULTI_TOOL = "container.multitool";
	public static final String CONTAINER_TERMINAL_OPTIONS_NAME = "container." + BLOCK_HSB_TERMINAL + "Options";
	public static final String CONTAINER_TERMINAL_NAME = "container." + BLOCK_HSB_TERMINAL;
	public static final String CONTAINER_UPGRADE_CAMO = "container." + "upgrade.camoflage";
	public static final String CONTAINER_UNLOCKER = "container." + BLOCK_MACHINE_UNLOCKER;
	
	//MultiTool Modes
	public static final String MULTI_TOOL_REMOVE = "multitool.remove";
	public static final String MULTI_TOOL_PLACE = "multitool.place";
	public static final String MULTI_TOOL_WRENCH = "multitool.wrench";
	public static final String MULTI_TOOL_ERROR = "multitool.error";
	
	public static final String CHAT_NOT_ENOUGH_ENERGY = "chat.energyMissing";
	public static final String CHAT_LOCKED = "chat.locked";
	public static final String CHAT_PORT = "chat.port";
	public static final String CHAT_NOTHING_HAPPENED = "chat.nothingHappened";
	public static final String CHAT_INVALID_DOOR = "chat.invalidDoor";
	public static final String CHAT_NO_ENTRY_ALLOWED = "chat.noEntryAllowed";
	public static final String CHAT_TESLA = "chat.tesla";
	public static final String CHAT_ACTIVATED = "chat.activated";
	public static final String CHAT_DISABLED = "chat.disabled";
	
	
	
	
	public static String translate(String s) {
		return LocalizationHandler.getLocal(s);
	}
	
}
