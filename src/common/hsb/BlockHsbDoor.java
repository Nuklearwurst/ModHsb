package hsb;

import hsb.config.Config;
import hsb.gui.GuiHandler;
import ic2.api.IWrenchable;
import ic2.api.Items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.BlockDoor;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;


public class BlockHsbDoor extends BlockDoor {

	//TODO maybe use Facing.java instead of sideAndFacingToSpriteOffset to get the texture
//	public static final int[][] sideAndFacingToSpriteOffset = { { 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 }, { 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 }, { 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };
//	public static final int[][] blockTexture = {{0, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 16}, {1, 1, 1, 17, 1, 1, 16, 16, 16, 17, 16, 16}};
	public String textureFile = "";
	
	/*
	 * Meta data
	 * 0: 
	 * 1: 
	 */
	
	public BlockHsbDoor(int id) {
		super(id, Material.wood); //TODO: Material
		this.setBlockUnbreakable();
		this.setResistance(999F);
		this.blockIndexInTexture = 0;
		this.textureFile = CommonProxy.TEXTURE_BLOCKS;
        this.isBlockContainer = true;
	}
	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	@Override
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        par1World.setBlockTileEntity(par2, par3, par4, this.createTileEntity(par1World, par1World.getBlockMetadata(par2, par3, par4)));
    }
	
    @Override
	/**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int l, float m, float n, float o)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	TileEntity te;
    	if(meta >= 8)
    	{
    		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = world.getBlockTileEntity(x, y - 1, z);
    	} else {
     		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = world.getBlockTileEntity(x, y, z);
    	}
    	if(te==null)
    	{
    		System.out.println("te == null!!");
    		return true;
    	}
    	if(!((TileEntityHsb)te).locked)
    		toggleDoor(world, x, y, z, entityplayer);
        return true;
    }
    
    private void toggleDoor(World world, int x, int y, int z, EntityPlayer entityplayer) {
    	int meta = this.getFullMetadata(world, x, y, z);
        int var11 = meta & 7;
        var11 ^= 4;

        if ((meta & 8) == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, var11);
            world.markBlocksDirty(x, y, z, x, y, z);
        }
        else
        {
            world.setBlockMetadataWithNotify(x, y - 1, z, var11);
            world.markBlocksDirty(x, y - 1, z, x, y, z);
        }

        world.playAuxSFXAtEntity(entityplayer, 1003, x, y, z, 0);
    }
    
	public TileEntity createNewTileEntity(World var1) {	
		return new TileEntityHsbBuilding();
	}
	
	public TileEntity createNewTileEntity(World var1, int meta) {
		return new TileEntityHsbBuilding();
	} 
	@Override
	public String getTextureFile() {
		return this.textureFile;
		
	}
	@Override
    public int idDropped(int meta, Random random, int j)
    {
        return 0;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
    {
		int meta = iblockaccess.getBlockMetadata(i, j, k);
		int facing = meta % 8;
		TileEntity te = null;
		if(meta >= 8)
		{
			te = iblockaccess.getBlockTileEntity(i, j - 1, k);
		} else
		if(meta <= 0)
		{
			te = iblockaccess.getBlockTileEntity(i, j, k);
		}

        //testing...

		
		int index = 0;
//        int texid = sideAndFacingToSpriteOffset[side][facing];
        if(te != null && ((TileEntityHsb)te).locked)
        {
//        	texid = texid + 8;
        	index = 16;
        	
        }
//        int tex = blockTexture[meta][texid];
        return index;

    }
	@Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
//		 int texid = sideAndFacingToSpriteOffset[side][1];
//	     int tex = blockTexture[meta][texid];
	     return 0;

    }
    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player) 
    {
//        TileEntity te = world.getBlockTileEntity(x, y, z);
//        int metadata = world.getBlockMetadata(x, y, z);
//        switch(metadata) {
//        case 0:
//        	if(player instanceof EntityPlayer)
//        		if(((EntityPlayer) player).getCurrentEquippedItem().itemID == this.blockID)
//        			((EntityPlayer) player).openGui(ModHsbCore.instance, GuiHandler.GUI_BLOCKBUILDING, world, x, y, z);
//        }
//        if (player != null && te instanceof IWrenchable) 
//        {
//        	System.out.println("Facing setting");
//            IWrenchable wrenchable = (IWrenchable)te;
//            int rotationSegment = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//            if (player.rotationPitch >= 65) 
//            {
//                wrenchable.setFacing((short)1);
//            } 
//            else if (player.rotationPitch <= -65) 
//            {
//                wrenchable.setFacing((short)0);
//            } 
//            else 
//            {
//                switch (rotationSegment) 
//                {
//                case 0: wrenchable.setFacing((short)2); break;
//                case 1: wrenchable.setFacing((short)5); break;
//                case 2: wrenchable.setFacing((short)3); break;
//                case 3: wrenchable.setFacing((short)4); break;
//                default:
//                    wrenchable.setFacing((short)0); break;
//                }
//            }
//        }          
    }
    @Override
    public void updateBlockMetadata(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        super.updateBlockMetadata(world, x, y, z, side, hitX, hitY, hitZ);
//        ForgeDirection dir = ForgeDirection.getOrientation(side);
//        int metadata = world.getBlockMetadata(x, y, z);
//        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
//        if(tileentity instanceof IWrenchable)
//        {
//            ((IWrenchable)tileentity).setFacing((short)side);
//        }
    
    }

}
