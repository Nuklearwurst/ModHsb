package hsb;

import hsb.config.Config;
import hsb.gui.GuiHandler;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
    	TileEntity te = this.getTileEntity(world, x, y, z);
    	if(te != null && te instanceof TileEntityDoorBase)
    	{
    		if((entityplayer.username.equals(((TileEntityDoorBase)te).placer)) && ((TileEntityDoorBase)te).upgradePlayer)
    		{
    			this.toggleDoor(world, x, y, z, entityplayer);
    		} else {
				entityplayer.sendChatToPlayer("You are not Allowed to enter!");
				if(Config.DEBUG)
				{
					entityplayer.sendChatToPlayer("Placer: " + ((TileEntityDoorBase)te).placer + " Player: " + entityplayer.username);
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
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
        }
        else
        {
            world.setBlockMetadataWithNotify(x, y - 1, z, var11);
            world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
        }

        world.playAuxSFXAtEntity(entityplayer, 1003, x, y, z, 0);
    }
    
    private TileEntity getTileEntity(World world, int x, int y, int z) {
    	TileEntity te;
        int meta = this.getFullMetadata(world, x, y, z);
        if ((meta & 8) == 0)
        {
    		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = world.getBlockTileEntity(x, y - 1, z);
    	} else {
     		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = world.getBlockTileEntity(x, y - 2, z);
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
    public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
		int meta = iblockaccess.getBlockMetadata(x, y, z);
//		int facing = meta % 8;
		TileEntity te = null;
    	if(meta >= 8)
    	{
    		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = iblockaccess.getBlockTileEntity(x, y - 2, z);
    	} else {
     		System.out.println("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = iblockaccess.getBlockTileEntity(x, y - 1, z);
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
			if(te instanceof TileEntityDoorBase && player instanceof EntityPlayer)
			{
				((TileEntityDoorBase) te).upgradePlayer = true;
				((TileEntityDoorBase) te).placer = ((EntityPlayer)player).username;
			}
		}
    }
    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return hsb.config.HsbItems.itemHsbDoor.shiftedIndex;
    }

}
