package hsb.core.plugin;

import cpw.mods.fml.common.Loader;


/**
 * TODO
 * @author Nuklearwurst
 *
 */
public class PluginBC3 {

	//the modId
	public static final String modId = "BC3";
	
	public static float BC3_RATIO = 0.4f;
	public static float TO_BC3_RATIO = 1 / BC3_RATIO;
	
//	/**
//	 * enum to remove dependency
//	 * @author Nuklearwurst
//	 *
//	 */
//	public static enum BcType
//	{
//		MACHINE;
//	}
	
	
	public static boolean load() {
		boolean available = Loader.isModLoaded(modId);
		//loading plugin
		if(available) {
			//load here, set available false if needed
		}
		//load independent things
		TO_BC3_RATIO = 1 / BC3_RATIO;
		return available;
	}
	/**
	 * convert EU to MJ
	 * @param value
	 * @return
	 */
	public static float convertToMJ(int value) {
		return value * TO_BC3_RATIO;
	}
	/**
	 * convert MJ to EU
	 * @param value
	 * @return
	 */
	public static float convertToEU(float value) {
		return value * BC3_RATIO;
	}
}
