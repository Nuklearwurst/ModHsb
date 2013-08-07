package hsb.block;

import hsb.ModHsb;
import hsb.core.util.MachineUtils;
import hsb.lib.GuiIds;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.tileentity.IMachine;
import hsb.tileentity.TileEntityUnlocker;
import ic2.api.tile.IWrenchable;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends BlockSimpleContainer {

	// which icon to use
	public static final int[][] blockTextureId = { 	{ 0, 0, 0, 0, 0, 0 },
													{ 1, 1, 1, 1, 1, 1 } };

	public static final Icon[] blockTextureIcons = new Icon[Textures.BLOCK_MACHINE.length];

	public static final int MAX_META = 1;

	public static final int META_MACHINE = 0;
	public static final int META_UNLOCKER = 1;

	public BlockMachine(int id) {
		super(id, Material.iron);
		this.setHardness(2.0F);
		this.setResistance(2.0F);
		this.setUnlocalizedName(Strings.BLOCK_MACHINE);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z,
			int par5, int par6) {
		super.breakBlock(world, x, y, z, par5, par6);
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		
		if(te != null && te instanceof IMachine) {
			((IMachine)te).onRemove(world, x, y, z, par5, par6);
		}
		
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float m, float n, float o) {
		MachineUtils.onMachineActivated(world, x, y, z, player, side, m, n, o);
		int meta = world.getBlockMetadata(x, y, z);
		switch(meta)
		{
			case META_UNLOCKER:
				player.openGui(ModHsb.instance, GuiIds.GUI_UNLOCKER, world, x, y, z);
				return true;
		}
		return false;
	}
	
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) 
    {
    	MachineUtils.onMachinePlacedBy(world, x, y, z, player, stack);
    }
    
	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch (meta) {
			case META_UNLOCKER:
				return new TileEntityUnlocker();

			default:
				return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		int i = 0;
		for (String s : Textures.BLOCK_MACHINE) {
			blockTextureIcons[i++] = reg.registerIcon(s);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List itemList) {
		for (int i = 0; i <= MAX_META; i++) {
			itemList.add(new ItemStack(id, 1, i));
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k,
			int side) {
		TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
		int meta = iblockaccess.getBlockMetadata(i, j, k);

		int texid = Textures.sideAndFacingToSpriteOffset[side][5];

		if (te instanceof IWrenchable) {
			texid = Textures.sideAndFacingToSpriteOffset[side][((IWrenchable) te)
					.getFacing()];
		}

		int tex = blockTextureId[meta][texid];

		return blockTextureIcons[tex];

	}

	@Override
	public Icon getIcon(int side, int meta) {
		if (meta > MAX_META)
			return blockTextureIcons[0];
		int texid = Textures.sideAndFacingToSpriteOffset[side][3];
		int tex = blockTextureId[meta][texid];
		return blockTextureIcons[tex];

	}

}
