package hsb;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

/**
 * Not in Use!
 * @author Jonas
 *
 */
public class ItemBlockBuilding extends Item{

	public ItemBlockBuilding(int id) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setIconIndex(2);//TODO
	}
	
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS; 
	}

}
