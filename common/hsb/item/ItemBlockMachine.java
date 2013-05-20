package hsb.item;

import hsb.block.BlockMachine;
import hsb.block.ModBlocks;
import hsb.lib.Strings;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockMachine extends ItemBlock {

	public ItemBlockMachine(int id) {
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
        return ModBlocks.blockMachine.getIcon(2, meta);
    }

    @Override
	public String getUnlocalizedName(ItemStack itemStack)
    {
    	switch(itemStack.getItemDamage())
    	{
    	case BlockMachine.META_MACHINE:
    		return "tile." + Strings.BLOCK_MACHINE;
    	case BlockMachine.META_UNLOCKER:
    		return "tile." + Strings.BLOCK_MACHINE_UNLOCKER;
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
