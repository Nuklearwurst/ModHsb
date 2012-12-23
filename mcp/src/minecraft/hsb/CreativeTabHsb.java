package hsb;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import hsb.config.HsbItems;

public class CreativeTabHsb extends CreativeTabs{

	public static final CreativeTabs tabHsb = new CreativeTabHsb("tabHsb");
	
	public CreativeTabHsb(String label) {
		super(label);
	}
	
	@Override
    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return HsbItems.blockHsb.blockID;
    }
	
	@Override
    @SideOnly(Side.CLIENT)

    /**
     * Gets the translated Label.
     */
    public String getTranslatedTabLabel()
    {
		//TODO
        return "Hsb";
    }

}
