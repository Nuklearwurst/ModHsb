package hsb.configuration;

public class Settings {
	
	public static boolean DEBUG = false;
	public static boolean ic2Available = false;
	public static int maxPort = 99;	
	public static final int defaultPassLength = 8;
	public static final double terminalEnergyUse = 0.25;
	public static final int terminalEnergyStorage = 10000;
	
	//IC2
	public static int energyHsbMonitor = 32;
	public static int energyHsbHacker = 32;
	public static int energyUseUnlocker = 50;
	public static int energyUse_multiTool = 32;
	
	public static int maxEnergyStorageUnlocker = 10000;//TODO
	
	//Unlocker
	public static int ticksToUnlock = 100;
	
	public static int maxEnergyMonitor = 10000;
	//damage
	public static int damageHsbMonitor = 1;
	public static int damageHsbHacker = 4;
	
	public static int maxDamageMonitor = 20;


}
