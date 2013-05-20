package hsb.core.proxy;

import hsb.client.renderer.RenderBlockHsb;
import net.minecraft.util.StringTranslate;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{ 
	public static int HSBRENDERER_ID=0;

	@Override
	public void initRendering()
	{
		HSBRENDERER_ID = RenderingRegistry.getNextAvailableRenderId();	
	}
	
	@Override 
	public void initSpecialRenderer() {
		
		RenderingRegistry.registerBlockHandler(new RenderBlockHsb());
	}
	
	@Override
	public String getCurrentLanguage() {
		return StringTranslate.getInstance().getCurrentLanguage();
	}
}
