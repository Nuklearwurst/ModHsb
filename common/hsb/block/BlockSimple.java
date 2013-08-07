package hsb.block;

import hsb.creativetab.CreativeTabHsb;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public abstract class BlockSimple extends Block{

	public BlockSimple(int id, Material material) {
		super(id, material);
		this.setCreativeTab(CreativeTabHsb.tabHsb);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public abstract void registerIcons(IconRegister reg);

}
