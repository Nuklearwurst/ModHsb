package hsb.blocks;

import hsb.ClientProxy;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.HsbInfo;
import hsb.ModHsb;
import hsb.Utils;
import hsb.api.lock.ILockTerminal;
import hsb.api.lock.ILockable;
import hsb.config.Config;
import hsb.gui.GuiHandler;
import hsb.items.ItemBlockPlacer;
import hsb.network.packet.PacketTerminalInvUpdate;
import hsb.tileentitys.TileEntityDoorBase;
import hsb.tileentitys.TileEntityHsb;
import hsb.tileentitys.TileEntityHsbBuilding;
import hsb.tileentitys.TileEntityHsbGuiAccess;
import hsb.tileentitys.TileEntityLockTerminal;
import ic2.api.IWrenchable;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
/**
 * This is the Class for the unbreakable Blocks
 */
public class BlockHsb extends BlockContainer {
	public static final int[][] sideAndFacingToSpriteOffset = { { 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 }, { 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 }, { 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };
	public static final int[][] blockTextureId = {{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, 
												{0, 0, 0, 2, 0, 0, 1, 1, 1, 2, 1, 1},
												{3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4},
												{0, 0, 2, 0, 0, 0, 1, 1, 1, 2, 1, 1}};//TODO: Texture for GuiAccessBlock
	public static final int maxDamage = 3;
    static final String[] textureNames = /*new String[]*/{"hsb_red", "hsb_green", "terminal", "doorBase_red", "doorBase_green", "Orange"};
	public static final Icon[] blockTextureIcons = new Icon[textureNames.length];

	public String textureFile = "";
	
	/*
	 * Meta data
	 * 0: normal Block
	 * 1: Terminal
	 * 2: Door Base Block
	 * 3: gui Access Block
	 */
	
	public BlockHsb(int id) {
		super(id, Material.iron); //TODO: Material
		this.setBlockUnbreakable();
		this.setResistance(999F);
		this.textureFile = CommonProxy.TEXTURE_BLOCKS;
		this.setCreativeTab(CreativeTabHsb.tabHsb);
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
	public TileEntity createNewTileEntity(World world) {	
		return null;
	}
    
    
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch(meta)
		{
		case 0:
			return new TileEntityHsbBuilding();
		case 1:
			return new TileEntityLockTerminal();
		case 2: 
			return new TileEntityDoorBase();
		default:
			return new TileEntityHsbGuiAccess();
		}
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
        	return 0;
        case 3:
        	return 3;
        }
        return 0;
    }
	@SideOnly(Side.CLIENT)
	@Override
    public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
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
        int tex = blockTextureId[meta][texid];
        
        if(((TileEntityHsb)te).getConnectedTerminal()!= null && ((TileEntityHsb)te).getConnectedTerminal().getCamoBlockId()!=-1)
        {
        	return Block.blocksList[((TileEntityHsb)te).getConnectedTerminal().getCamoBlockId()].getBlockTextureFromSideAndMetadata(side, ((TileEntityHsb)te).getConnectedTerminal().getCamoBlockId());
        }
        return blockTextureIcons[tex];

    }
	@Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		if(meta > maxDamage)
			return blockTextureIcons[0];
		int texid = sideAndFacingToSpriteOffset[side][1];
		int tex = blockTextureId[meta][texid];
	    return blockTextureIcons[tex];

    }
	@Override
    public int getRenderType()
    {
        return ClientProxy.HSBRENDERER_ID;
    }
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubBlocks(int id, CreativeTabs tab, List itemList)
    {
        itemList.add(new ItemStack(id, 1, 0));
        itemList.add(new ItemStack(id, 1, 1));
        itemList.add(new ItemStack(id, 1, 2));
        itemList.add(new ItemStack(id, 1, 3));
    }
    
//    @Override
//	public String getTextureFile() {
//		return this.textureFile;
//		
//	}
    @Override
    public int idDropped(int meta, Random random, int j)
    {
        return this.blockID;
    }
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float m, float n, float o)
    {
    	//Sneaking
    	if(entityplayer.isSneaking())
    		return false;
    	//Items
    	if(entityplayer.getCurrentEquippedItem()!=null) {
    		if(Config.ic2Available)
    			if(entityplayer.getCurrentEquippedItem().itemID == Config.getIC2ItemId("wrench") || entityplayer.getCurrentEquippedItem().itemID == Config.getIC2ItemId("electricWrench"))
    				return false;
    		if(entityplayer.getCurrentEquippedItem().getItem() instanceof ItemBlockPlacer)
    			return false;
    	}
    	switch (world.getBlockMetadata(i, j, k))
        {
            case 0:
            	return false;
            case 1:
            {
            	//TODO add pass protected Gui (Upgrades)
            	TileEntityLockTerminal te = (TileEntityLockTerminal) world.getBlockTileEntity(i, j, k);
            	te.onInventoryChanged();
            	if(!world.isRemote) {
            		PacketTerminalInvUpdate packet = new PacketTerminalInvUpdate(te);
            		PacketDispatcher.sendPacketToPlayer(packet.getPacket(), (Player)entityplayer);
            		entityplayer.openGui(ModHsb.instance, GuiHandler.GUI_LOCKTERMINAL, world, i, j, k);
            	}
                return true;
            }
            case 2:
            	return false;
            case 3:
            {
            	TileEntityHsbBuilding tile = (TileEntityHsbBuilding) world.getBlockTileEntity(i, j, k);
            	int facing = Facing.faceToSide[tile.getFacing()];
            	int x = Facing.offsetsXForSide[facing] + i;
            	int y = Facing.offsetsYForSide[facing] + j;
            	int z = Facing.offsetsZForSide[facing] + k;
            	int blockId = world.getBlockId(x, y, z);
            	int meta = world.getBlockMetadata(x, y, z);
            	Config.logDebug("Meta: " + meta + " side: " + side + " Block: " + Block.blocksList[blockId] + " Pos: " + x + ", " + y + ", " + z);
            	if(Block.blocksList[blockId] == null || (blockId == this.blockID && meta == 3))
            	{
            		return false;
            	}
            	if(Block.blocksList[blockId].onBlockActivated(world, x, y, z, entityplayer, Facing.faceToSide[tile.facing], m, n, o)) {
            	} else {
            		Utils.sendChatToPlayer("Nothing happend", entityplayer, true);
            	}
            }
            	return true;
            default:
                return false;
        }
    }
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	super.onBlockAdded(world, x, y, z);
    	
    }
    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) 
    {
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
	    				player.sendChatToPlayer("Don't do that!");
	    				player.attackEntityFrom(DamageSource.magic, tesla);
	    			}
	    		}
	    	}
    	}
    }
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack stack) 
    {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        
        switch(metadata) {
        case 0:
        	if(player instanceof EntityPlayer && te!=null)
        		if(stack.itemID == this.blockID)
        			((EntityPlayer) player).openGui(ModHsb.instance, GuiHandler.GUI_BLOCKBUILDING, world, x, y, z);
        	break;
        case 2:
        	if(player instanceof EntityPlayer && te!=null)
        		if(stack.itemID == this.blockID)
        			((EntityPlayer) player).openGui(ModHsb.instance, GuiHandler.GUI_BLOCKBUILDING, world, x, y, z);
        }
        
        if (player != null && te instanceof IWrenchable) 
        {
            IWrenchable wrenchable = (IWrenchable)te;
            int rotationSegment = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
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
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister reg)
    {
    	int i = 0;
    	for(String s : textureNames) {
    		BlockHsb.blockTextureIcons[i++] = reg.registerIcon(HsbInfo.modId.toLowerCase() + ":" + s);
    	}
    }
    


}
