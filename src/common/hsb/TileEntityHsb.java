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
	public int port = 0;
	
	
	public TileEntityHsb() {
		super();
		facing = 1;
		prevFacing = 1;
		init = false;
		locked = false;
		port = 0;

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
        port = nbttagcompound.getInteger("port");
    	System.out.println("Nbttag read: facing = " + String.valueOf(facing));
    	this.onInventoryChanged();
    }
	@Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("facing", this.facing);
        nbttagcompound.setBoolean("locked", locked);
        nbttagcompound.setInteger("port", port);
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
	public boolean transferSignal(int side, TileEntityLockTerminal te, boolean lock) {
		if (lock == this.locked || te.port != this.port) {
			return false;
		}
		else
		{
			if(te!=null)
			{
				this.locked=lock;
				
				if(lock)
				{
					te.blocksInUse++;
				} 
				else 
				{
					te.blocksInUse--;
				}
			}
			for(int i = 0; i>5; i++)
			{
				TileEntity tile = this.worldObj.getBlockTileEntity(xCoord+Facing.offsetsXForSide[i],yCoord+Facing.offsetsYForSide[i], zCoord+Facing.offsetsZForSide[i]);
				if(tile != null && tile instanceof ILockDataCable)
				{
					((ILockDataCable) tile).transferSignal(Facing.faceToSide[i], te, lock);
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
		
	}

	@Override
	public List<String> getNetworkedFields() {
		System.out.println("get networkedFields");
//		List<String> list = new ArrayList<String>(1);
		List list = new Vector(1);
	    list.add("facing");
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
