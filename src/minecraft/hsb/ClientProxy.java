package hsb;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	void initRendering()
	{
		MinecraftForgeClient.preloadTexture(TEXTURE_BLOCKS);
		MinecraftForgeClient.preloadTexture(TEXTURE_ITEMS);
	}
	
}
