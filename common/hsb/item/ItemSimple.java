package hsb.item;

import hsb.creativetab.CreativeTabHsb;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemSimple extends Item {

	public ItemSimple(int id) {
		super(id);
		this.setCreativeTab(CreativeTabHsb.tabHsb);

	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public abstract void registerIcons(IconRegister reg);

}
