package hsb.block;

import hsb.ModHsb;
import hsb.core.util.MachineUtils;
import hsb.core.util.Utils;
import hsb.lib.GuiIds;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.tileentity.TileEntityGuiAccess;
import hsb.tileentity.TileEntityHsb;
import hsb.tileentity.TileEntityHsbBuilding;
import hsb.tileentity.TileEntityHsbTerminal;
import ic2.api.tile.IWrenchable;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHsb extends BlockSimpleContainer{	
	public static final int[][] blockTextureId = {{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, 
												{0, 0, 0, 2, 0, 0, 1, 1, 1, 2, 1, 1},
												{3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4},
												{0, 0, 0, 2, 0, 0, 1, 1, 1, 2, 1, 1}};//TODO: Texture for GuiAccessBlock
	
//    static final String[] textureNames = {"hsb_red", "hsb_green", "terminal", "doorBase_red", "doorBase_green", "Orange"};
	public static final Icon[] blockTextureIcons = new Icon[Textures.BLOCK_HSB.length];
	
	public static final int maxDamage = 3;
	
	public static final short META_BUILDING = 0;
	public static final short META_TERMINAL = 1;
	public static final short META_DOOR_BASE = 2;
	public static final short META_GUI_ACCESS = 3;

	public BlockHsb(int id) {
		super(id, Material.iron);
		this.setHardness(100F);
		this.setResistance(400F);
		this.setUnlocalizedName(Strings.BLOCK_HSB);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
    	int i = 0;
    	for(String s : Textures.BLOCK_HSB) {
    		BlockHsb.blockTextureIcons[i++] = reg.registerIcon(s);
    	}		
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
    public boolean canSilkHarvest()
    {
        return false;
    }
	
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) 
    {
    	TileEntity te = world.getBlockTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        
        MachineUtils.onMachinePlacedBy(world, x, y, z, player, stack);
        
        //Open Gui to set port:
        switch(metadata) {
        case 0: case 2:
        	if(player instanceof EntityPlayer && te!=null)
        		if(stack.itemID == this.blockID)
        			((EntityPlayer) player).openGui(ModHsb.instance, GuiIds.GUI_BLOCKBUILDING, world, x, y, z);
        	break;
        }
        
        
    }
    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) 
    {
    	//TODO rework to support multiple upgrades (other upgrades that trigger on hit)
    	if(!world.isRemote) {
	    	TileEntity te = world.getBlockTileEntity(x, y, z);
	    	if(te != null && te instanceof TileEntityHsb && ((TileEntityHsb)te).locked)
	    	{
	    		ILockTerminal terminal = ((ILockable)te).getConnectedTerminal();
	    		if(terminal != null)
	    		{
	    			int tesla = terminal.getTesla();
	    			if(tesla > 0)
	    			{
	    				player.sendChatToPlayer(Utils.getChatMessage(Strings.translate(Strings.CHAT_TESLA)));
	    				player.attackEntityFrom(DamageSource.magic, tesla);
	    			}
	    		}
	    	}
    	}
    }
    /* (non-Javadoc)
     * @see net.minecraft.block.Block#onBlockActivated(net.minecraft.world.World, int, int, int, net.minecraft.entity.player.EntityPlayer, int, float, float, float)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float m, float n, float o)
    {       
    	if(!MachineUtils.onMachineActivated(world, x, y, z, player, side, m, n, o))
    		return false;
    	
    	switch (world.getBlockMetadata(x, y, z))
        {
            case META_BUILDING: case META_DOOR_BASE:
            	return false;
            /**
             * Terminal
             */
            case META_TERMINAL:
            {
            	if(!world.isRemote) {
            		//open gui
            		player.openGui(ModHsb.instance, GuiIds.GUI_LOCKTERMINAL, world, x, y, z);
            	}
                return true;
            }
        	/**
        	 * Gui access
        	 */
            case META_GUI_ACCESS:
            {
            	TileEntityGuiAccess tile = (TileEntityGuiAccess) world.getBlockTileEntity(x, y, z);
            	int facing = Facing.oppositeSide[tile.getFacing()];
            	int i = Facing.offsetsXForSide[facing] + x;
            	int j = Facing.offsetsYForSide[facing] + y;
            	int k = Facing.offsetsZForSide[facing] + z;
            	int blockId = world.getBlockId(i, j, k);
            	int meta = world.getBlockMetadata(i, j, k);
//            	HsbLog.debug("Meta: " + meta + " side: " + side + " Block: " + Block.blocksList[blockId] + " Pos: " + x + ", " + y + ", " + z);
            	if(Block.blocksList[blockId] == null || (blockId == this.blockID && meta == 3))
            	{
            		return false;
            	}
            	if(Block.blocksList[blockId].onBlockActivated(world, i, j, k, player, Facing.oppositeSide[tile.getFacing()], m, n, o)) {
            	} else {
            		Utils.sendChatToPlayer(StatCollector.translateToLocal(Strings.CHAT_NOTHING_HAPPENED), player, true);
            	}
            }
            	return true;
            default:
                return false;
        }    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubBlocks(int id, CreativeTabs tab, List itemList)
    {
        itemList.add(new ItemStack(id, 1, 0));
        itemList.add(new ItemStack(id, 1, 1));
        itemList.add(new ItemStack(id, 1, 2));
        itemList.add(new ItemStack(id, 1, 3));
    }
    
	@Override
    public int damageDropped(int meta)
    {
        if(meta == META_DOOR_BASE)
        	return 0;
        return meta;
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch(meta)
		{
			case META_BUILDING:
				return new TileEntityHsbBuilding();
			case META_TERMINAL:
				return new TileEntityHsbTerminal();
			case META_DOOR_BASE:
				return new TileEntityHsbBuilding();
			case META_GUI_ACCESS:
				return new TileEntityGuiAccess();
			default:
				return null;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
    {
        TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
        int meta = iblockaccess.getBlockMetadata(i, j, k);
        
        int texid = Textures.sideAndFacingToSpriteOffset[side][5];
        
        if(meta == BlockHsb.META_GUI_ACCESS || meta == BlockHsb.META_TERMINAL) {
	        short facing = 0;
	        
	        facing = ((IWrenchable) te).getFacing();
	        texid = Textures.sideAndFacingToSpriteOffset[side][facing];
        }
        
		if(((ILockable)te).isLocked())
        {
        	texid = texid + 6;
        }
        
        int tex = blockTextureId[meta][texid];
        
        if(te instanceof TileEntityHsbBuilding && ((TileEntityHsbBuilding) te).isLocked() && ((TileEntityHsbBuilding) te).camoId != -1)
        {
        	TileEntityHsbBuilding tebuild = (TileEntityHsbBuilding) te;
        	Block block = Block.blocksList[tebuild.camoId];
        	if(block != null && block.isOpaqueCube())
        	{
        		return block.getIcon(side, tebuild.camoMeta);
        	}
        }
        
        return blockTextureIcons[tex];

    }
	
	@Override
    public Icon getIcon(int side, int meta)
	{
		if(meta > maxDamage)
			return blockTextureIcons[0];
		int texid = Textures.sideAndFacingToSpriteOffset[side][1];
		int tex = blockTextureId[meta][texid];
	    return blockTextureIcons[tex];

    }
	
    @Override
    public int getMobilityFlag()
    {
        return 2;
    }

}
