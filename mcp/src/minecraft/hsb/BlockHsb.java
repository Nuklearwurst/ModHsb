package hsb;

import hsb.config.Config;
import hsb.gui.GuiHandler;
import ic2.api.IWrenchable;
import ic2.api.Items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
/**
 * This is the Class for the unbreakable Blocks
 */
public class BlockHsb extends BlockContainer {

	//TODO maybe use Facing.java instead of sideAndFacingToSpriteOffset to get the texture
	public static final int[][] sideAndFacingToSpriteOffset = { { 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 }, { 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 }, { 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };
	public static final int[][] blockTexture = {{0, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 16}, 
												{1, 1, 1, 17, 1, 1, 16, 16, 16, 17, 16, 16}, 
												{2, 2, 2, 2, 2, 2, 18, 18, 18, 18, 18, 18}};
	public static final int maxDamage = 2;
	public String textureFile = "";
	
	/*
	 * Meta data
	 * 0: normaler Block
	 * 1: Terminal
	 * 2: Door Base Block
	 */
	
	public BlockHsb(int id) {
		super(id, Material.iron); //TODO: Material
		this.setBlockUnbreakable();
		this.setResistance(999F);
		this.blockIndexInTexture = 0;
		this.textureFile = CommonProxy.TEXTURE_BLOCKS;
		this.setCreativeTab(CreativeTabHsb.tabHsb);
	}
	
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float m, float n, float o)
    {
    	//Sneaking
    	if(entityplayer.isSneaking())
    		return false;
    	//Items
    	if(entityplayer.getCurrentEquippedItem()!=null) {
    		if(!Config.ECLIPSE)
    			if(entityplayer.getCurrentEquippedItem().itemID == Items.getItem("wrench").itemID || entityplayer.getCurrentEquippedItem().itemID == Items.getItem("electricWrench").itemID)
    				return false;
    		if(entityplayer.getCurrentEquippedItem().getItem() instanceof ItemBlockPlacer)
    			return false;
    	}
    	switch (world.getBlockMetadata(i, j, k))
        {
            case 0:
            	return false;
            case 1:
            	//TODO add pass protected Gui (Upgrades)
            	world.getBlockTileEntity(i, j, k).onInventoryChanged();
                entityplayer.openGui(ModHsb.instance, GuiHandler.GUI_LOCKTERMINAL, world, i, j, k);
                return true;
            case 2:
            	return false;
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
		switch(meta)
		{
		case 0:
			return new TileEntityHsbBuilding();
		case 1:
			return new TileEntityLockTerminal();
		case 2: 
			return new TileEntityDoorBase();
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
        return this.blockID;
    }
	@Override
    public int damageDropped(int meta)
    {
        switch(meta) {
        case 0:
        	return 0;
        case 1: 
        	return 1;
        case 2:
        	return 2;
        default: 
        	return 0;
        }
    }
	@SideOnly(Side.CLIENT)
	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
    {
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
		if(meta > maxDamage)
			return 0;
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
        itemList.add(new ItemStack(id, 1, 2));
    }
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	super.onBlockAdded(world, x, y, z);
    	
    }
    @Override
	/**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
    	TileEntityHsb te = (TileEntityHsb) world.getBlockTileEntity(x, y, z);
    	if(te!=null)
    	{
    		te.onRemove(world, x, y, z, par5, par6);
    	}
        super.breakBlock(world, x, y, z, par5, par6);      
    }
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player) 
    {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        
        switch(metadata) {
        case 0:
        	if(player instanceof EntityPlayer && te!=null)
        		if(((EntityPlayer) player).getCurrentEquippedItem().itemID == this.blockID)
        			((EntityPlayer) player).openGui(ModHsb.instance, GuiHandler.GUI_BLOCKBUILDING, world, x, y, z);
        	break;
        case 2:
        	if(player instanceof EntityPlayer && te!=null)
        		if(((EntityPlayer) player).getCurrentEquippedItem().itemID == this.blockID)
        			((EntityPlayer) player).openGui(ModHsb.instance, GuiHandler.GUI_BLOCKBUILDING, world, x, y, z);
        }
        
        if (player != null && te instanceof IWrenchable) 
        {
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
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) 
    {
    	if(!world.isRemote) {
	    	TileEntity te = world.getBlockTileEntity(x, y, z);
	    	if(te != null && te instanceof TileEntityHsb && ((TileEntityHsb)te).locked)
	    	{
	    		TileEntityLockTerminal terminal = ((TileEntityHsb)te).getConnectedTerminal();
	    		if(terminal != null && terminal.getUpgradeId("tesla")!=-1)
	    		{
	    			int tesla = terminal.upgradeCount[terminal.getUpgradeId("tesla")];
	    			if(tesla > 0 && terminal.upgradeActive[terminal.getUpgradeId("tesla")])
	    			{
	    				player.sendChatToPlayer("Don't do that!");
	    				player.attackEntityFrom(DamageSource.magic, tesla);
	    			}
	    		}
	    	}
    	}
    }


}
