package hsb;

import hsb.config.Items;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.BlockCloth;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemDye;
import net.minecraft.src.ItemStack;

public class ItemBlockHsb extends ItemBlock {

	public ItemBlockHsb(int id) {
		super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
//        Items.itemBlockHsb = this;
	}
    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int meta)
    {
        return Items.blockHsb.getBlockTextureFromSideAndMetadata(2, meta);
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
    	}
    	return null;
    }

}
