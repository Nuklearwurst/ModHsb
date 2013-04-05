package hsb.blocks;

import hsb.CommonProxy;
import hsb.config.Config;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityDoorBase;
import hsb.tileentitys.TileEntityHsb;
import hsb.tileentitys.TileEntityHsbBuilding;

import java.util.Random;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


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
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
		TileEntity te = this.getTileEntity(world, x, y, z);
		if(te != null && te instanceof TileEntityDoorBase)
		{
			((TileEntityDoorBase)te).onDoorBreak(world, x, y, z);
		} else {
			if(te != null && world.getBlockId(te.xCoord, te.yCoord, te.zCoord) == HsbItems.blockHsb.blockID && world.getBlockMetadata(te.xCoord, te.yCoord, te.zCoord) == 2)
			{
				world.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0);
				world.setBlockTileEntity(te.xCoord, te.yCoord, te.zCoord, new TileEntityHsbBuilding());
				world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
			} else {
				FMLLog.severe("Error during removal of Door Block, see BlockHsbDoor l. 66");
			}
		}
        super.breakBlock(world, x, y, z, par5, par6);
    }
	
    @Override
    public boolean canSilkHarvest()
    {
        return false;
    }
	
    @SideOnly(Side.CLIENT)
	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
		int meta = iblockaccess.getBlockMetadata(x, y, z);
		TileEntity te = null;
    	if(meta >= 8)
    	{
    		Config.logDebug("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = iblockaccess.getBlockTileEntity(x, y - 2, z);
    	} else {
    		Config.logDebug("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = iblockaccess.getBlockTileEntity(x, y - 1, z);
    	}

        //testing...

		
		int index = 19;
		if(meta >= 8)
		{
			index = 3;
		}
        if(te != null && ((TileEntityHsb)te).locked)
        {
        	index = index + 32;
        	
        }
        return index;

    }
    
    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		int index = 19;
		if(meta >= 8)
		{
			index = 3;
		}
	     return index;

    }
    
    @Override
	public String getTextureFile() {
		return this.textureFile;
		
	}
	public TileEntity getTileEntity(World world, int x, int y, int z) {
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
    public int idDropped(int meta, Random random, int j)
    {
        return HsbItems.itemHsbDoor.itemID;
    }
	
	@Override
	@SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return hsb.config.HsbItems.itemHsbDoor.itemID;
    }
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int l, float m, float n, float o)
    {
    	if(entityplayer != null && entityplayer.getCurrentEquippedItem()!=null && entityplayer.getCurrentEquippedItem().itemID == HsbItems.itemBlockPlacer.itemID)
    	{
    		return false;
    	}
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
		} else {
			entityplayer.sendChatToPlayer("Huch!! Die Tuer sollte nicht hier sein!");
		}
    	return true;
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player) 
    {
		if(world.getBlockMetadata(x, y, z) < 8)
		{
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te instanceof TileEntityHsbBuilding && player instanceof EntityPlayer)
			{
				int port = ((TileEntityHsbBuilding) te).getPort();
				world.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 2);
				world.setBlockTileEntity(x, y, z, new TileEntityDoorBase());
				te = world.getBlockTileEntity(x, y, z);
				if(te instanceof TileEntityDoorBase)
				{
					((TileEntityDoorBase) te).upgradePlayer = true;
					((TileEntityDoorBase) te).placer = ((EntityPlayer)player).username;
					((TileEntityDoorBase) te).setPort(port);
				} else {
					FMLLog.severe("Hsb: error when placing Door!");
				}
			}
		}
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

}
