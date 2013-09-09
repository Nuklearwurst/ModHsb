package hsb.core.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.GameRegistry;

public class StackUtils {


	public static ItemStack coal = new ItemStack(Item.coal, 1, 0);
	public static ItemStack charcoal = new ItemStack(Item.coal, 1, 1);

	public static boolean isItemFuel(ItemStack is)
	{
		return StackUtils.getItemFuelValue(is) > 0;
	}

	/**
	 * 
	 * @param is
	 * @return MJ
	 */
	public static int getItemFuelValue(ItemStack is) {
		if (is == null) {
			return 0;
		}
		if (is.isItemEqual(coal)) {
			return 4800;
		}
		if (is.isItemEqual(charcoal)) {
			return 3200;
		}
		int itemId = is.getItem().itemID;
		if (is.getItem() instanceof ItemBlock && Block.blocksList[itemId].blockMaterial == Material.wood) {
			return 450;
		}
		if (itemId == Item.stick.itemID) {
			return 150;
		}
		if (itemId == Block.sapling.blockID) {
			return 150;
		}
		return GameRegistry.getFuelValue(is) * 3 / 2;
	}
	
	public static NBTTagCompound getOrCreateNBTTagCompound(ItemStack item) {
		if(!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		return item.getTagCompound();
	}
}
