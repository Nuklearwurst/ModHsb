package hsb.core.plugin;

import cpw.mods.fml.common.Loader;


/**
 * TODO
 * @author Nuklearwurst
 *
 */
public class PluginUE {


	//the modId
	public static final String modId = "UE";
	
	public static float UE_RATIO = 1;
	public static float TO_UE_RATIO = 1 / UE_RATIO;
	
	
	public static boolean load() {
		boolean available = Loader.isModLoaded(modId);
		//loading plugin
		if(available) {
			//load here, set available false if needed
		}
		//load independent things
		TO_UE_RATIO = 1 / UE_RATIO;
		return available;
	}
	
	public static float convertToMJ(float i) {
		return i * UE_RATIO;
	}
	
	public static float convertToUE(float i) {
		return i * TO_UE_RATIO;
	}

}
