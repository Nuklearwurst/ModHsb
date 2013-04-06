package hsb.blocks;

import hsb.CreativeTabHsb;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachine extends BlockContainer{

	public BlockMachine(int id) {
		this(id, Material.iron);
	}
	
	public BlockMachine(int id, Material material) {
		super(id, material);	
		this.setHardness(1.0F);
		this.setResistance(10F);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return null;
	}

}
