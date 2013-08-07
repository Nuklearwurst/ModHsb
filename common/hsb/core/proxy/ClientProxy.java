package hsb.core.proxy;


public class ClientProxy extends CommonProxy{ 

	@Override
	public void initRendering()
	{	
	}
	
	@Override 
	public void initSpecialRenderer() {
		
	}
	
	@Override
	public boolean isClient() {
		return true;
	}
}
