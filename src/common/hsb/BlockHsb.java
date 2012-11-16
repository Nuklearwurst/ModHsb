package hsb;

import hsb.config.Config;
import hsb.gui.GuiHandler;
import ic2.api.IWrenchable;
import ic2.api.Items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
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
/**
 * This is the Class for the unbreakable Blocks
 */
public class BlockHsb extends BlockContainer {

	//TODO maybe use Facing.java instead of sideAndFacingToSpriteOffset to get the texture
	public static final int[][] sideAndFacingToSpriteOffset = { { 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 }, { 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 }, { 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };
	public static final int[][] blockTexture = {{0, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 16}, {1, 1, 1, 17, 1, 1, 16, 16, 16, 17, 16, 16}};
	public String textureFile = "";
	
	/*
	 * Meta data
	 * 0: normaler Block
	 * 1: Terminal
	 */
	
	public BlockHsb(int id) {
		super(id, Material.iron); //TODO: Material
		this.setBlockUnbreakable();
		this.setResistance(999F);
		this.blockIndexInTexture = 0;
		this.textureFile = CommonProxy.TEXTURE_BLOCKS;
		this.setCreativeTab(CreativeTabs.tabBlock);
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float m, float n, float o)
    {
    	if(Config.DEBUG)
    	{
    	}
    	if(entityplayer.getCurrentEquippedItem()!=null && !Config.ECLIPSE) {
    		if(entityplayer.getCurrentEquippedItem().itemID == Items.getItem("wrench").itemID || entityplayer.getCurrentEquippedItem().itemID == Items.getItem("electricWrench").itemID)
    			return false;
    	}
    	switch (world.getBlockMetadata(i, j, k))
        {
            case 0:
            	return false;
            case 1:
            	//TODO add pass protected Gui (Upgrades)
                entityplayer.openGui(ModHsbCore.instance, GuiHandler.GUI_LOCKTERMINAL, world, i, j, k);
                return true;
            default:
                return false;
        }
    }
	@Override
	public TileEntity createNewTileEntity(World var1) {	
		return null;
	}
	@Override
	public TileEntity createNewTileEntity(World var1, int meta) {
		// TODO Auto-generated method stub
		switch(meta)
		{
		case 0:
			return new TileEntityHsbBuilding();
		case 1:
			return new TileEntityLockTerminal();
		default:
			return null;
		}
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
		//TODO
        TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
        short facing = 1;
        //testing...
        try{
        	facing = ((IWrenchable) te).getFacing();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        int meta = iblockaccess.getBlockMetadata(i, j, k);

        int texid = sideAndFacingToSpriteOffset[side][facing];
        if(((TileEntityHsb)te).locked)
        {
        	texid = texid + 6;
        }
        int tex = blockTexture[meta][texid];
        return tex;

    }
	@Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		 int texid = sideAndFacingToSpriteOffset[side][1];
	     int tex = blockTexture[meta][texid];
	     return tex;

    }
    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubBlocks(int id, CreativeTabs tab, List itemList)
    {
        itemList.add(new ItemStack(id, 1, 0));
        itemList.add(new ItemStack(id, 1, 1));
    }
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player) 
    {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        switch(metadata) {
        case 0:
        	if(player instanceof EntityPlayer)
        		if(((EntityPlayer) player).getCurrentEquippedItem().itemID == this.blockID)
        			((EntityPlayer) player).openGui(ModHsbCore.instance, GuiHandler.GUI_BLOCKBUILDING, world, x, y, z);
        }
        if (player != null && te instanceof IWrenchable) 
        {
        	System.out.println("Facing setting");
            IWrenchable wrenchable = (IWrenchable)te;
            int rotationSegment = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            if (player.rotationPitch >= 65) 
            {
                wrenchable.setFacing((short)1);
            } 
            else if (player.rotationPitch <= -65) 
            {
                wrenchable.setFacing((short)0);
            } 
            else 
            {
                switch (rotationSegment) 
                {
                case 0: wrenchable.setFacing((short)2); break;
                case 1: wrenchable.setFacing((short)5); break;
                case 2: wrenchable.setFacing((short)3); break;
                case 3: wrenchable.setFacing((short)4); break;
                default:
                    wrenchable.setFacing((short)0); break;
                }
            }
        }          
    }
    @Override
    public void updateBlockMetadata(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        super.updateBlockMetadata(world, x, y, z, side, hitX, hitY, hitZ);
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        int metadata = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getBlockTileEntity(x, y, z);
        if(tileentity instanceof IWrenchable)
        {
            ((IWrenchable)tileentity).setFacing((short)side);
        }
    
    }

}
