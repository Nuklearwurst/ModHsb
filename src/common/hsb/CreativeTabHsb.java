package hsb;

import hsb.config.HsbItems;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.StringTranslate;

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
