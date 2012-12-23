package hsb;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntityHsbBuilding extends TileEntityHsb
{

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getWrenchDropRate() {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
