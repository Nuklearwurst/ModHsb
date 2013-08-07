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
	
	/**
	 * enum to remove dependency
	 * @author Nuklearwurst
	 *
	 */
	public static enum BcType
	{
		MACHINE;
	}
	
	
	public static boolean load() {
		boolean available = Loader.isModLoaded(modId);
		//loading plugin
		if(available) {
			//load here, set available false if needed
		}
		return available;
	}
}
