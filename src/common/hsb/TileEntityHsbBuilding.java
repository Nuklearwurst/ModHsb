package hsb;

import java.util.List;

import ic2.api.IWrenchable;
import net.minecraft.src.EntityPlayer;

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

}
