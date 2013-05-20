package hsb.core.addons;

import cpw.mods.fml.common.Loader;


/**
 * TODO
 * @author Nuklearwurst
 *
 */
public class PluginUE implements IPlugin{

	private boolean available;

	@Override
	public boolean isAvailable() {
		return available;
	}

	@Override
	public void initPlugin() {		
		if(!Loader.isModLoaded("UE")) {
			return;
		} else {
			available = true;
		}
	}

}
