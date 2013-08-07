package hsb.upgrade;


import hsb.ModHsb;
import hsb.item.ItemUpgradeHsb;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.upgrade.machine.UpgradeEnergyStorage;
import hsb.upgrade.terminal.UpgradeCamoflage;
import hsb.upgrade.terminal.UpgradeDummy;
import hsb.upgrade.terminal.UpgradePassword;
import hsb.upgrade.terminal.UpgradeSecurity;
import hsb.upgrade.terminal.UpgradeTesla;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.IUpgradeHsbMachine;
import hsb.upgrade.types.IUpgradeHsbTerminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;


public class UpgradeRegistry {
    public static final String ID_UPGRADE_CAMO = "Camoflage";
    public static final String ID_UPGRADE_TESLA = "tesla";
    public static final String ID_UPGRADE_PASSWORD = "password";
    public static final String ID_UPGRADE_SECURITY = "security";
    public static final String ID_UPGRADE_DUMMY = "dummy";
    
    public static final String ID_UPGRADE_STORAGE = "energyStorage";
    
    //Upgrade to id mapping (Machine)
	public static Map<String, Class<? extends IUpgradeHsbMachine>> upgradesMachine = new HashMap<String, Class<? extends IUpgradeHsbMachine>>();
	//Upgrade to id mapping (Terminal)
	public static Map<String, Class<? extends IUpgradeHsbTerminal>> upgradesTerminal = new HashMap<String, Class<? extends IUpgradeHsbTerminal>>();
	
	//unique id to integer used for networking
	public static List<String> idToInt = new ArrayList<String>();
	
	public static Map<String, ButtonInfo> buttons= new HashMap<String, ButtonInfo>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean registerUpgrade(Class<? extends IHsbUpgrade> upgrade, String id, Map map) {
		
		if(map.containsKey(id)) {
			ModHsb.logger.severe("Upgrade key already used!");
			return false;
		}
		if(map.containsValue(upgrade))
		{
			ModHsb.logger.severe("Upgrade already registred!");
			return false;
		}
		map.put(id, upgrade);
		return true;

	}
	/**
	 * 
	 * @param upgrade
	 * @param id unique id
	 * @return success
	 */
	public static boolean registerTerminalUpgrade(Class<? extends IUpgradeHsbTerminal> upgrade, String id)
	{
		return registerUpgrade(upgrade, id, upgradesTerminal);
	}
	/**
	 * 
	 * @param upgrade
	 * @param id unique id
	 * @return
	 */
	public static boolean registerMachineUpgrade(Class<? extends IUpgradeHsbMachine> upgrade, String id)
	{
		return registerUpgrade(upgrade, id, upgradesMachine);
	}
	
	public static boolean registerUpgradeButton(String uniqueId, String buttonText, String icon) {
		if(!upgradesTerminal.containsKey(uniqueId) && !upgradesMachine.containsKey(uniqueId)) {
			ModHsb.logger.severe("Upgrade " + uniqueId + " not registered! Upgrades have to be registered first before being added to the Button Upgrade List!");
			return false;
		}
		if(uniqueId == null || uniqueId.isEmpty()) {
			ModHsb.logger.severe("uniqueId must not be null!");
			return false;
		}
		if( (buttonText == null || buttonText.isEmpty()) && icon == null ) {
			ModHsb.logger.severe("ButtonText AND icon can't be null both!");
			return false;
		}
		
		idToInt.add(uniqueId);
		
		if(ModHsb.proxy.isClient()) {
			ButtonInfo button = new ButtonInfo(buttonText, icon);
			buttons.put(uniqueId, button);
		}
		return true;
	}
	
	public static String getButtonName(String id) {
		return UpgradeRegistry.buttons.get(id).getText();
	}
	
	public static Icon getButtonIcon(String id) {
		return UpgradeRegistry.buttons.get(id).getIcon();
	}
	
	public static void initUpgrades() {
		
		registerTerminalUpgrade(UpgradeDummy.class, ID_UPGRADE_DUMMY);
		registerTerminalUpgrade(UpgradeCamoflage.class, ID_UPGRADE_CAMO);
		registerTerminalUpgrade(UpgradeTesla.class, ID_UPGRADE_TESLA);
		registerTerminalUpgrade(UpgradePassword.class, ID_UPGRADE_PASSWORD);
		registerTerminalUpgrade(UpgradeSecurity.class, ID_UPGRADE_SECURITY);
		
		registerMachineUpgrade(UpgradeEnergyStorage.class, ID_UPGRADE_STORAGE);
		
		
		registerUpgradeButton(ID_UPGRADE_CAMO, Strings.UPGRADE_GUI_BUTTON_CAMO,Textures.ITEM_UPGRADE_HSB[ItemUpgradeHsb.metaCamo]);
		registerUpgradeButton(ID_UPGRADE_TESLA, Strings.UPGRADE_GUI_BUTTON_TESLA, Textures.ITEM_UPGRADE_HSB[ItemUpgradeHsb.metaTesla]);
	}
	
	public static void initUpgradeIcons(IconRegister reg) {
		for(ButtonInfo button : buttons.values()) {
			button.registerIcons(reg);
		}
	}
}
