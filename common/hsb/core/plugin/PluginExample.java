package hsb.core.plugin;

import cpw.mods.fml.common.Loader;


public class PluginExample {

	//the modId
	public static final String modId = "MODID";
	
	
	public static boolean load() {
		boolean available = Loader.isModLoaded(modId);
		//loading plugin
		if(available) {
			//load here, set available false if needed
		}
		return available;
	}
}
