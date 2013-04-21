package hsb.block;

import hsb.configuration.Settings;
import hsb.core.helper.HsbLog;
import hsb.item.ModItems;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.tileentity.TileEntityDoorBase;
import hsb.tileentity.TileEntityHsb;
import hsb.tileentity.TileEntityHsbBuilding;

import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockHsbDoor extends BlockDoor {

	
	/*
	 * Meta data
	 * 0: 
	 * 8: top block 
	 */
	Icon doorRedTop;
	Icon doorRedBot;
	Icon doorGreenTop;
	Icon doorGreenBot;
	
	
	public BlockHsbDoor(int id) {
		super(id, Material.iron);
		this.setBlockUnbreakable();
		this.setResistance(999F);
		this.setUnlocalizedName(Strings.BLOCK_HSB_DOOR);
	}
	
	@Override
	/**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
		if(!world.isRemote)
		{
	    	TileEntity te;
	        int meta = this.getFullMetadata(world, x, y, z);
	        if ((meta & 8) == 0)
	        {
	    		te = world.getBlockTileEntity(x, y - 1, z);
				if(te != null && te instanceof TileEntityDoorBase)
				{
					((TileEntityDoorBase)te).onDoorBreak(world, x, y, z);
				} else {
					HsbLog.severe("Error during removal of Door Block, see BlockHsbDoor l. 69");
				}
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
    public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
		int meta = iblockaccess.getBlockMetadata(x, y, z);
		//get Door Base
		TileEntity te = null;
    	if(meta >= 8)
    	{
//    		HsbLog.debug("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = iblockaccess.getBlockTileEntity(x, y - 2, z);
    	} else {
//    		HsbLog.debug("meta == " + meta + "\nCoordinates: " + x + ", " + y + ", " + z);
    		te = iblockaccess.getBlockTileEntity(x, y - 1, z);
    	}
    	//Texture for unlocked door
		Icon tex = this.doorRedBot;
		if(meta >= 8)
		{
			tex = this.doorRedTop;
		}
		//texture for locked door
        if(te != null && ((TileEntityHsb)te).locked)
        {
        	tex = this.doorGreenBot;
        	if(meta >= 8)
    		{
    			tex = this.doorGreenTop;
    		}
        	
        }
        return tex;
    }
    
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
    	//texture for unlocked Door
		Icon tex = this.doorRedBot;
		if(meta >= 8)
		{
			tex = this.doorRedTop;
		}
	     return tex;
    }
    
	public TileEntity getTileEntity(World world, int x, int y, int z) {
    	TileEntity te;
        int meta = this.getFullMetadata(world, x, y, z);
        if ((meta & 8) == 0)
        {
    		te = world.getBlockTileEntity(x, y - 1, z);
    	} else {
    		te = world.getBlockTileEntity(x, y - 2, z);
    	}
    	return te;   	
    }
	@Override
    public int idDropped(int meta, Random random, int j)
    {
        return ModItems.itemHsbDoor.itemID;
    }
	
	@Override
	@SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return ModItems.itemHsbDoor.itemID;
    }
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int l, float m, float n, float o)
    {
		//if clicked with multitool
    	if(entityplayer != null && entityplayer.getCurrentEquippedItem()!=null)
    	{
    		if(entityplayer.getCurrentEquippedItem().itemID == ModItems.itemMultiTool.itemID)
    			return false;
    	}
		if(!world.isRemote)
		{
	    	//open door
	    	TileEntity te = this.getTileEntity(world, x, y, z);
	    	if(te != null && te instanceof TileEntityDoorBase)
	    	{
	    		if((entityplayer.username.equals(((TileEntityDoorBase)te).placer)) && ((TileEntityDoorBase)te).upgradePlayer)
	    		{
	    			this.toggleDoor(world, x, y, z, entityplayer);
	    		} else {
					entityplayer.sendChatToPlayer(StatCollector.translateToLocal(Strings.CHAT_NO_ENTRY_ALLOWED));
					if(Settings.DEBUG)
					{
						entityplayer.sendChatToPlayer("Placer: " + ((TileEntityDoorBase)te).placer + " Player: " + entityplayer.username);
					}
	    		}
			} else {
				entityplayer.sendChatToPlayer(StatCollector.translateToLocal(Strings.CHAT_INVALID_DOOR));
				this.dropBlockAsItem(world, x, y, z, l, 0);
			}
		}
    	return true;
    }
    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) 
    {
    	if(!world.isRemote) {
	    	TileEntity te = this.getTileEntity(world, x, y, z);
	    	if(te != null && te instanceof TileEntityHsb && ((TileEntityHsb)te).locked)
	    	{
	    		ILockTerminal terminal = ((ILockable)te).getConnectedTerminal();
	    		if(terminal != null)
	    		{
	    			int tesla = terminal.getTesla();
	    			if(tesla > 0)
	    			{
	    				player.sendChatToPlayer("Don't do that!");
	    				player.attackEntityFrom(DamageSource.magic, tesla);
	    			}
	    		}
	    	}
    	}
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack stack) 
    {
    	//lower door block
		if(world.getBlockMetadata(x, y, z) < 8)
		{
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te instanceof TileEntityHsbBuilding && player instanceof EntityPlayer)
			{
				int port = ((TileEntityHsbBuilding) te).getPort();
				world.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 2, 2);
				world.setBlockTileEntity(x, y, z, new TileEntityDoorBase());
				te = world.getBlockTileEntity(x, y, z);
				if(te instanceof TileEntityDoorBase)
				{
					((TileEntityDoorBase) te).upgradePlayer = true;
					((TileEntityDoorBase) te).placer = ((EntityPlayer)player).username;
					((TileEntityDoorBase) te).setPort(port);
				} else {
					HsbLog.severe("Hsb: error when placing Door!");
					this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
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
            world.setBlockMetadataWithNotify(x, y, z, var11, 2);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
        }
        else
        {
            world.setBlockMetadataWithNotify(x, y - 1, z, var11, 2);
            world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
        }

        world.playAuxSFXAtEntity(entityplayer, 1003, x, y, z, 0);
    }
    
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister reg)
    {
    	this.doorRedTop = reg.registerIcon(Textures.BLOCK_HSB_DOOR[0]);
    	this.doorGreenTop = reg.registerIcon(Textures.BLOCK_HSB_DOOR[1]);
    	this.doorRedBot = reg.registerIcon(Textures.BLOCK_HSB_DOOR[2]);
    	this.doorGreenBot = reg.registerIcon(Textures.BLOCK_HSB_DOOR[3]);
    }

}
