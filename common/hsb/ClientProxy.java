package hsb;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	public static int HSBRENDERER_ID=0;

	@Override
	void initRendering()
	{
		MinecraftForgeClient.preloadTexture(TEXTURE_BLOCKS);
		MinecraftForgeClient.preloadTexture(TEXTURE_ITEMS);
		HSBRENDERER_ID = RenderingRegistry.getNextAvailableRenderId();
		
	}
	
	@Override 
	public void initSpecialRenderer() {
		
		RenderingRegistry.registerBlockHandler(new HsbBlockRenderer());
	}
	
}
