package hsb.upgrade;

import hsb.core.helper.HsbLog;
import hsb.lib.Strings;
import hsb.upgrade.machine.UpgradeEnergyStorage;
import hsb.upgrade.terminal.UpgradeCamoflage;
import hsb.upgrade.terminal.UpgradeDummy;
import hsb.upgrade.terminal.UpgradePassword;
import hsb.upgrade.terminal.UpgradeSecurity;
import hsb.upgrade.terminal.UpgradeTesla;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.IUpgradeButton;
import hsb.upgrade.types.IUpgradeHsbMachine;
import hsb.upgrade.types.IUpgradeHsbTerminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Deprecated
	//buttons:id to int mapping
	public static List< Class <? extends IUpgradeButton> > buttons = new ArrayList< Class <? extends IUpgradeButton> >();
	@Deprecated
	//buttins int, name mapping
	public static Map<String, Integer> buttonNumberToName = new HashMap<String, Integer>();
	
	//Upgrade Text List
	public static List<String> buttonNames = new ArrayList<String>();
	
	public static boolean addButtonName(String name) {
		if(buttonNames.contains("name")) {
			HsbLog.info("Name already registered!");
			return false;
		}
		buttonNames.add(name);
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean registerUpgrade(Class<? extends IHsbUpgrade> upgrade, String id, Map map) {
		
		if(map.containsKey(id)) {
			HsbLog.severe("Upgrade key already used!");
			return false;
		}
		if(map.containsValue(upgrade))
		{
			HsbLog.severe("Upgrade already registred!");
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
	
	@Deprecated
	
	/**
	 * register button Upgrade, upgrade has to registered before
	 * @param upgrade
	 * @return success
	 */
	public static boolean registerButtonUpgrade(Class <? extends IUpgradeButton> upgrade)
	{
		if(!upgradesMachine.containsValue(upgrade) && !upgradesTerminal.containsValue(upgrade))
		{
			HsbLog.severe("Upgrade not registered, register Upgrade before registering buttons!");
			return false;
		}
		if(buttons.contains(upgrade))
		{
			HsbLog.severe("UpgradeButton already registered!");
			return false;
		}
		buttons.add(upgrade);
		try {
			buttonNumberToName.put(upgrade.newInstance().getButton(), buttons.indexOf(upgrade));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static void initUpgrades() {
		
		registerTerminalUpgrade(UpgradeDummy.class, ID_UPGRADE_DUMMY);
		registerTerminalUpgrade(UpgradeCamoflage.class, ID_UPGRADE_CAMO);
		registerTerminalUpgrade(UpgradeTesla.class, ID_UPGRADE_TESLA);
		registerTerminalUpgrade(UpgradePassword.class, ID_UPGRADE_PASSWORD);
		registerTerminalUpgrade(UpgradeSecurity.class, ID_UPGRADE_SECURITY);
		
		registerMachineUpgrade(UpgradeEnergyStorage.class, ID_UPGRADE_STORAGE);
		
		addButtonName(Strings.UPGRADE_GUI_BUTTON_CAMO);
		addButtonName(Strings.UPGRADE_GUI_BUTTON_TESLA);
	}
}
