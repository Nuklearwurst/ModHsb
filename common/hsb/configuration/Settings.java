package hsb.configuration;

public class Settings {
	
	public static boolean DEBUG = false;
	
	//Plugins
	public static boolean usePluginIC2 = false;
	public static boolean usePluginBC3 = false;
	public static boolean usePluginUE = false;
	
	
	public static int maxPort = 99;	
	public static final int defaultPassLength = 8;
	
	//terminal energy
	public static float terminalEnergyUse = 0.25F;
	public static float terminalEnergyStorage = 10000;
	public static final float terminalMinInput = 0;
	public static final float terminalMaxInput = 100;
	
	//unlocker energy
	public static final float unlockerEnergyUse = 50;
	public static final float unlockerEnergyStorage = 5000;
	public static final float unlockerMinInput = 0;
	public static final float unlockerMaxInput = 100;

	
	//IC2
	public static int energyHsbMonitor = 32;
	public static int energyHsbHacker = 32;
	public static int energyUse_multiTool = 32;
	
	
	//Unlocker
	public static int ticksToUnlock = 100;
	
	public static int maxEnergyMonitor = 10000;
	//damage
	public static int damageHsbMonitor = 1;
	public static int damageHsbHacker = 4;
	
	public static int maxDamageMonitor = 20;

	//upgrades
	public static float UPGRADE_ENERGY_STORAGE = 1000;



}
