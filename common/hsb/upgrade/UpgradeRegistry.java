package hsb.upgrade;

import hsb.core.helper.HsbLog;
import hsb.upgrade.machine.UpgradeEnergyStorage;
import hsb.upgrade.terminal.UpgradeCamoflage;
import hsb.upgrade.terminal.UpgradeDummy;
import hsb.upgrade.terminal.UpgradePassword;
import hsb.upgrade.terminal.UpgradeSecurity;
import hsb.upgrade.terminal.UpgradeTesla;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.IUpgradeHsbMachine;
import hsb.upgrade.types.IUpgradeHsbTerminal;

import java.util.HashMap;
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
	public static boolean registerTerminalUpgrade(Class<? extends IUpgradeHsbTerminal> upgrade, String id)
	{
		return registerUpgrade(upgrade, id, upgradesTerminal);
	}
	
	public static boolean registerMachineUpgrade(Class<? extends IUpgradeHsbMachine> upgrade, String id)
	{
		return registerUpgrade(upgrade, id, upgradesMachine);
	}
	
	public static void initUpgrades() {
		
		registerTerminalUpgrade(UpgradeDummy.class, ID_UPGRADE_DUMMY);
		registerTerminalUpgrade(UpgradeCamoflage.class, ID_UPGRADE_CAMO);
		registerTerminalUpgrade(UpgradeTesla.class, ID_UPGRADE_TESLA);
		registerTerminalUpgrade(UpgradePassword.class, ID_UPGRADE_PASSWORD);
		registerTerminalUpgrade(UpgradeSecurity.class, ID_UPGRADE_SECURITY);
		
		registerMachineUpgrade(UpgradeEnergyStorage.class, ID_UPGRADE_STORAGE);
	}
}
