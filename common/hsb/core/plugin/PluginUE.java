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
	
	public static float UE_RATIO = 0.4F;
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
	
	/**
	 * converts Joules to EU
	 * @param i
	 * @return
	 */
	public static float convertToEU(float i) {
		return i * UE_RATIO;
	}
	/**
	 * converts EU to Joules
	 * @param i
	 * @return
	 */
	public static float convertToUE(float i) {
		return i * TO_UE_RATIO;
	}

}
