package hsb.core.addons;

import cpw.mods.fml.common.Loader;


/**
 * TODO
 * @author Nuklearwurst
 *
 */
public class PluginBC implements IPlugin{
	
	boolean available = false;
	
	public boolean isAvailable() {
		return available;
	}
	public void initPlugin() {
		if(!Loader.isModLoaded("BC3")) {
			return;
		} else {
			available = true;
		}
	}
}
