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

public abstract class TileEntityHsb extends TileEntity
	implements IWrenchable, ILockDataCable,  INetworkDataProvider, INetworkUpdateListener
{
	public short facing = 1;;
	protected short prevFacing;
	protected boolean init = false;
	public  boolean locked = false;
	public  boolean prevLocked = false;
	public int port = 0;
	public String pass;
	
	
	public TileEntityHsb() {
		super();
		facing = 1;
		prevFacing = 1;
		init = false;
		locked = false;
		port = 0;
		pass = "";

	}
	@Override
	public void validate() {
		super.validate();
        if (!init )
        {
        	if ((!isInvalid()) && (this.worldObj != null)) {
        		initData();
            }
            else
            	System.out.println("[Hsb] " + this + " (" + this.xCoord + "," + this.yCoord + "," + this.zCoord + ") was not added, isInvalid=" + isInvalid() + ", worldObj=" + this.worldObj);
        }
	}
	@Override
	public void invalidate() {
		super.invalidate();
		if(init)
			init = false;
		
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
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("facing", this.facing);
        nbttagcompound.setBoolean("locked", locked);
        nbttagcompound.setInteger("port", port);
        nbttagcompound.setString("pass", pass);
    }
	@Override
	public short getFacing() {
		return facing;
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
	@Override
	public boolean connectsTo(int side) {return true;}
	@Override
	public boolean transferSignal(int side, TileEntityLockTerminal te, boolean lock, String pass, int port) {
		System.out.println("transferSignal te Building");
		if(te != null && te.worldObj.isRemote)
		{
			System.out.println("is Remote!");
		}else {
			System.out.println("is Server");
		}
		//check if Lock is the same, or if port is not the same
		if (lock == this.locked || port != this.port) {
			System.out.println("transfer failed!");
			return false;
		}
		else
		{
			//Unlocking
			if(!lock)
			{
				if(this.pass != pass)
				{
					System.out.println("transfer failed 2 !");
					return false;
				}
				
			}
			//setting new properties
			System.out.println("transfer in progress!");
			this.locked = lock;
			
			//locking
			if(lock)
			{
				te.blocksInUse++;
				this.pass = pass;
			} 
			//updating
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
			if(!Config.ECLIPSE)
			{
				NetworkHelper.updateTileEntityField(this, "locked");
			}
		
//			System.out.println("test !");
			//continue sending the signal
			for(int i = 0; i<6; i++)
			{
//				System.out.println("starting");
				TileEntity tile = this.worldObj.getBlockTileEntity(xCoord+Facing.offsetsXForSide[i], yCoord+Facing.offsetsYForSide[i], zCoord+Facing.offsetsZForSide[i]);
				if(tile != null && tile instanceof ILockDataCable)
				{
					((ILockDataCable) tile).transferSignal(Facing.faceToSide[i], te, lock, pass, port);
//					System.out.println("transfer success!");
				}
			}
			return true;
		}
		
	}

	@Override
	public void onNetworkUpdate(String field) {
		System.out.println("onNetworkUpdate()");
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
	public List<String> getNetworkedFields() {
		System.out.println("get networkedFields");
//		List<String> list = new ArrayList<String>(1);
		List list = new Vector(4);
	    list.add("facing");
	    list.add("port");
	    list.add("pass");
	    list.add("locked");
	    return list;
	}
	 @Override
    public void updateEntity()
    {

        if (!worldObj.isRemote)
        {
//            if (updateTicker-- > 0)
//                return;
//            updateTicker = tickRate;
            onInventoryChanged();
        }      
        super.updateEntity();
    }
    protected void initData()
    {
    	if(worldObj != null && !Config.ECLIPSE)
    	{
	        if(worldObj.isRemote)
	        {
	    		NetworkHelper.requestInitialData(this);
	    		System.out.println("requesting initial Data");
	        }
	        init = true;
    	}
    }

}
