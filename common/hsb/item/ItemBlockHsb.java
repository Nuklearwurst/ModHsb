package hsb.item;

import hsb.block.ModBlocks;
import hsb.lib.Strings;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockHsb extends ItemBlock {

	public ItemBlockHsb(int id) {
		super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
    @Override
	@SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int meta)
    {
    	//Side texture -- unused --> renderer
        return ModBlocks.blockHsb.getIcon(2, meta);
    }

    @Override
	public String getUnlocalizedName(ItemStack itemStack)
    {
    	switch(itemStack.getItemDamage())
    	{
    	case 0:
    		return "tile." + Strings.BLOCK_HSB_BUILDING;
    		
    	case 1:
    		return "tile." + Strings.BLOCK_HSB_TERMINAL;
    		
    	case 2:
    		return "tile." + Strings.BLOCK_HSB_DOOR_BASE;
    		
    	case 3:
    		return "tile." + Strings.BLOCK_HSB_GUI_ACCESS;
    	}
    	return null;
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    @Override
	public int getMetadata(int meta)
    {
        return meta;
    }

}
