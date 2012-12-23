package hsb;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import hsb.config.HsbItems;

public class ItemBlockHsb extends ItemBlock {

	public ItemBlockHsb(int id) {
		super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int meta)
    {
        return HsbItems.blockHsb.getBlockTextureFromSideAndMetadata(2, meta);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int meta)
    {
        return meta;
    }

    public String getItemNameIS(ItemStack itemStack)
    {
    	switch(itemStack.getItemDamage())
    	{
    	case 0:
    		return "hsbBuilding";
    		
    	case 1:
    		return "hsbLockTerminal";
    		
    	case 2:
    		return "hsbDoorBase";
    	}
    	return null;
    }

}
