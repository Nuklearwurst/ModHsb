package hsb.tileentitys;

import hsb.config.HsbItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/*
 * ??
 */
public class TileEntityHsbGuiAccess extends TileEntityHsbBuilding{

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(HsbItems.blockHsb, 1, 3);
	}
}
