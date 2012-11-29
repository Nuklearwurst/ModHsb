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
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;


public class BlockHsbDoor extends BlockDoor {

	public String textureFile = "";
	
	/*
	 * Meta data
	 * 0: 
	 * 8: top block 
	 */
	
	public BlockHsbDoor(int id) {
		super(id, Material.wood); //TODO: Material
		this.setBlockUnbreakable();
		this.setResistance(999F);
		this.blockIndexInTexture = 0;
		this.textureFile = CommonProxy.TEXTURE_BLOCKS;
	}
	
	@Override
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
    }
	
    @Override
	/**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
    	if(world.getBlockMetadata(x, y, z) < 8)
    	{
    		TileEntity te = world.getBlockTileEntity(x, y, z);
    		if(te != null && te instanceof TileEntityDoorBase)
    		{
    			((TileEntityDoorBase)te).onDoorBreak(world, x, y, z);
    		}
    	}
        super.breakBlock(world, x, y, z, par5, par6);
    }
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int l, float m, float n, float o)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	TileEntity te = this.getTileEntity(world, x, y, z, meta);
    	if(te != null && te instanceof TileEntityDoorBase)
    	{
    		if(entityplayer.getEntityName() == ((TileEntityDoorBase)te).placer && ((TileEntityDoorBase)te).upgradePlayer)
    		{
    			this.toggleDoor(world, x, y, z, entityplayer);
    		} else {
    			if(world.isRemote)
    			{
    				entityplayer.sendChatToPlayer("You are not Allowed to enter!");
    				if(Config.DEBUG)
    				{
    					entityplayer.sendChatToPlayer("Placer: " + ((TileEntityDoorBase)te).placer + " Player: " + entityplayer.getEntityName());
    				}
    			}
    		}
    	}
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
    
    private TileEntity getTileEntity(World world, int x, int y, int z, int meta) {
    	TileEntity te;
    	if(meta >= 8)
    	{
    		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = world.getBlockTileEntity(x, y - 2, z);
    	} else {
     		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = world.getBlockTileEntity(x, y - 1, z);
    	}
    	return te;
    	
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
			te = iblockaccess.getBlockTileEntity(i, j - 2, k);
		} else
		if(meta <= 0)
		{
			te = iblockaccess.getBlockTileEntity(i, j - 1, k);
		}

        //testing...

		
		int index = 0;
        if(te != null && ((TileEntityHsb)te).locked)
        {
        	index = 16;
        	
        }
        return index;

    }
	@Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
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
		if(world.getBlockMetadata(x, y, z) < 8)
		{
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te instanceof TileEntityDoorBase)
			{
				((TileEntityDoorBase) te).upgradePlayer = true;
				((TileEntityDoorBase) te).placer = ((EntityPlayer)player).getEntityName();
			}
		}
    }
    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return hsb.config.Items.itemHsbDoor.shiftedIndex;
    }

}
