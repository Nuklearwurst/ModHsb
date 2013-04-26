package hsb.upgrade.types;

import net.minecraft.world.World;

public interface IOnRemoveListener extends IHsbUpgrade{

	public void onRemove(World world, int x, int y, int z, int par5, int par6);
}
