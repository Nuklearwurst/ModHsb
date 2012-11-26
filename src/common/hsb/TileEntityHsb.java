package hsb;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import hsb.api.ILockDataCable;
import hsb.config.Config;
import ic2.api.INetworkClientTileEntityEventListener;
import ic2.api.INetworkDataProvider;
import ic2.api.INetworkUpdateListener;
import ic2.api.IWrenchable;
import ic2.api.NetworkHelper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Facing;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class TileEntityHsb extends TileEntity
	implements IWrenchable, ILockDataCable,  INetworkDataProvider, INetworkUpdateListener, INetworkClientTileEntityEventListener
{
	public short facing = 1;
	protected short prevFacing;
	protected boolean init = false;
	public  boolean locked = false;
	public  boolean prevLocked = false;
	public int port = 0;
	public String pass;
	public boolean removed = false;
	
	
	public TileEntityHsb() {
		super();
		facing = 1;
		prevFacing = 1;
		init = false;
		locked = false;
		port = 0;
		pass = "password";

	}
	
	@Override
	public boolean connectsTo(int side) {return true;}
	
	@Override
	public short getFacing() {
		return facing;
	}
	
	@Override
	public List<String> getNetworkedFields() {
		System.out.println("get networkedFields");
		List list = new Vector(4);
	    list.add("facing");
	    list.add("port");
	    list.add("pass");
	    list.add("locked");
	    return list;
	}
	
	protected void initData()
    {
    	if(!Config.ECLIPSE)
    	{
	        if(worldObj.isRemote)
	        {
	    		NetworkHelper.requestInitialData(this);
	        }
	        init = true;
    	}
    }
	
	@Override
	public void invalidate() {
//		super.invalidate();
//		if(init)
//			init = false;
		
	}
	
	@Override
	public void onNetworkUpdate(String field) {
		 if (field.equals("facing") && prevFacing != facing)
	     {
	         worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
	         prevFacing = facing;
	     }
		 if (field.equals("locked") && prevLocked != locked)
	     {
	         worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
	         prevLocked = locked;
	     }
		
	}
	
	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        facing = nbttagcompound.getShort("facing");
        prevFacing = this.facing;
        locked = nbttagcompound.getBoolean("locked");
        prevLocked = locked;
        port = nbttagcompound.getInteger("port");
        pass = nbttagcompound.getString("pass");
    	this.onInventoryChanged();
    }
	
	@Override
	public void setFacing(short facing) {
        this.facing = facing;
        if (prevFacing != facing && !Config.ECLIPSE)
        {
            NetworkHelper.updateTileEntityField(this, "facing");
        }
        this.prevFacing = facing;

	}
	
	public void onRemove(World world, int x, int y, int z, int par5, int par6) {
		if(this.worldObj.isRemote)
		{
			return;
		}
		this.locked = false;
		this.removed = true;
		this.transferSignal_do(null, false, this.pass, this.port, 6);
	}

	@Override
	public boolean transferSignal(int side, TileEntityLockTerminal te, boolean value, String pass, int port) {
		if(this.worldObj.isRemote)
			return true;
		if(this.removed)
		{
			System.out.println("this is removed!");
			return false;
		}
//		System.out.println("transferSignal te Building");
		//check if Lock is the same, or if port is not the same
		if (value == this.locked || port != this.port) {
			System.out.println("transfer failed! this: " + this.locked + " lock: " + value + " this.port: " + this.port + " port: " + port);
			return false;
		}
		
		//Unlocking
		if(!value)
		{
			if(!this.pass.equals(pass))
			{
				System.out.println("transfer failed 2 ! |" + this.pass + "|" + pass + "|");
				return false;
			}
			
		}
		//locking
		if(value)
		{
			te.blocksInUse++;
			this.pass = pass;
		}
		//setting new properties
		System.out.println("transfer in progress: side: " + side + " te: " + te + " value: " + value + " pass: " + pass + " port: " + port);
		this.locked = value;
		
		//continue sending the signal
		this.transferSignal_do(te, value, pass, port, side);
		//updating
		if(!Config.ECLIPSE)
		{
			NetworkHelper.updateTileEntityField(this, "locked");
		}
		worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		return true;		
	}
	/**
	 * transfers a locksignal to all blocks, but the side specified
	 * 
	 * @param te the TileEntity the Signal is from, null if it is broken
	 * @param lock wether to lock or unlock
	 * @param pass password
	 * @param port port
	 * @param ignoreSide Side no signal shoudd be send to
	 * @return succes
	 */
	protected boolean transferSignal_do(TileEntityLockTerminal te, boolean lock, String pass, int port, int ignoreSide) {
		for(int i = 0; i<6; i++)
		{
			if(i == ignoreSide)
			{
				System.out.println("ignored Side: " + i);
				continue;
			}
			TileEntity tile = this.worldObj.getBlockTileEntity(xCoord+Facing.offsetsXForSide[i], yCoord+Facing.offsetsYForSide[i], zCoord+Facing.offsetsZForSide[i]);
			if(tile != null && tile instanceof ILockDataCable)
			{
				((ILockDataCable) tile).transferSignal(Facing.faceToSide[i], te, lock, pass, port);
			}
		}
		return true;
	}

	@Override
    public void updateEntity()
    {
		if (!init)
        {
            initData();
            onInventoryChanged();
        }  
        super.updateEntity();
    }
	
	@Override
	public void validate() {
		super.validate();
//        if (!init )
//        {
//        	if ((!isInvalid()) && (this.worldObj != null)) {
//        		initData();
//            }
//            else
//            	System.out.println("[Hsb] " + this + " (" + this.xCoord + "," + this.yCoord + "," + this.zCoord + ") was not added, isInvalid=" + isInvalid() + ", worldObj=" + this.worldObj);
//        }
	}
	
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("facing", this.facing);
        nbttagcompound.setBoolean("locked", locked);
        nbttagcompound.setInteger("port", port);
        nbttagcompound.setString("pass", pass);
    }
    
	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {
		event = event - 2;
		if(event > 99 || event < 0)
		{
			System.out.println("Unexpected event!! " + event);
			return;
		}
		this.port = event;
	}
}
