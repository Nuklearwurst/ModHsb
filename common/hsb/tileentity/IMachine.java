package hsb.tileentity;

import net.minecraft.world.World;

/**
 * interface for all machines, currently used for dropping inventory
 * @author Nuklearwurst
 *
 */
public interface IMachine {

	public void onRemove(World world, int x, int y, int z, int par1, int par2);
}
