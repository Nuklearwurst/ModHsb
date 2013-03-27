package hsb.tileentitys;

import ic2.api.IWrenchable;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import hsb.LockManager;
import hsb.network.NetworkManager;

import java.util.List;
import java.util.Vector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import hsb.api.lock.ILockReceiver;
import hsb.api.lock.ILockTerminal;
import hsb.api.lock.ILockable;
import hsb.config.Config;

public abstract class TileEntityHsb extends TileEntity
	implements IWrenchable, INetworkDataProvider, INetworkUpdateListener, INetworkClientTileEntityEventListener, ILockable
{
	public short facing = 1;
	protected short prevFacing;
	
	protected boolean init = false;
	
	public  boolean locked = false;
	public  boolean prevLocked = false;
	
	private int port = 0;
	private String pass;

	private boolean removed = false;
	//Terminal coordinates
	public int xTer = 0;
	public int yTer = 0;
	public int zTer = 0;
	
	
	public TileEntityHsb() {
		super();
		facing = 1;
		prevFacing = 1;
		init = false;
		locked = false;
		port = 0;
		pass = "password";

	}
	
	public ILockTerminal getConnectedTerminal() {
		TileEntity te = this.worldObj.getBlockTileEntity(xTer, yTer, zTer);
		if(te instanceof ILockTerminal)
			return (ILockTerminal) te;
		return null;
	}
	
	@Override
	public boolean connectsTo(int side) {return true;}

	
	@Override
	public short getFacing() {
		return facing;
	}
	
	@Override
	public List<String> getNetworkedFields() {
		List<String> list = new Vector<String>(4);
	    list.add("facing");
	    list.add("port");
	    list.add("pass");
	    list.add("locked");
	    
	    return list;
	}
	
	protected void initData()
    {
        onInventoryChanged();
		if(worldObj.isRemote)
        {
    		NetworkManager.requestInitialData(this);
        }
        init = true;
    }
	
	@Override
	public void onNetworkUpdate(String field) {
		 if (field.equals("facing") && prevFacing != facing)
	     {
	         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	         prevFacing = facing;
	     }
		 if (field.equals("locked") && prevLocked != locked)
	     {
	         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
        xTer = nbttagcompound.getInteger("xTer");
        yTer = nbttagcompound.getInteger("yTer");
        zTer = nbttagcompound.getInteger("zTer");
    	this.onInventoryChanged();
    }
	
	@Override
	public void setFacing(short facing) {
        this.facing = facing;
        if (prevFacing != facing && !Config.ECLIPSE)
        {
            NetworkManager.updateTileEntityField(this, "facing");
        }
        this.prevFacing = facing;

	}
	
	public void onRemove(World world, int x, int y, int z, int par5, int par6) {
		//only on server
		if(this.worldObj.isRemote)
		{
			return;
		}
		//unlocking
		this.locked = false;
		//set removed mark
		this.removed = true;
		//this.transferSignal_do(null, false, this.pass, this.port, 6);
		
		//transfersignal (from this, no terminal, unlocking, pass, port, all sides)
		LockManager.tranferSignal(this, null, false, pass, port, 6);
	}

	@Override
	public boolean receiveSignal(int side, ILockTerminal te, boolean value, String pass, int port) {
		//only simulated on server
		if(this.worldObj.isRemote)
			return true;
		//checks if the tile is going to be destroyed
		if(this.removed)
		{
			Config.logDebug("this is removed!");
			return false;
		}
		//check if Lock is the same, or if port is not the same
		if (value == this.locked || port != this.port) {
			Config.logDebug("transfer failed! this: " + this.locked + " lock: " + value + " this.port: " + this.port + " port: " + port);
			return false;
		}
		
		//Unlocking
		if(!value)
		{
			//checks if the connected terminal is valid (if not unlocking will succeed)
			if( (this.getConnectedTerminal() != null) && (!this.getConnectedTerminal().isDestroyed()) )
			{
				//checks if the password is right
				if(!this.pass.equals(pass))
				{
					Config.logDebug("transfer failed:false password ! |" + this.pass + "|" + pass + "|");
					return false;
				}
			}
			
		}
		//locking
		if(value)
		{
			//setting terminal coordinates
			this.xTer = te.getTileEntity().xCoord;
			this.yTer = te.getTileEntity().yCoord;
			this.zTer = te.getTileEntity().zCoord;
			//adding block to the terminal
			te.addBlockToTileEntity(this);
			//setting password
			this.pass = pass;
		}
		//setting new properties
		Config.logDebug("transfer in progress: side: " + side + " te: " + te + " value: " + value + " pass: " + pass + " port: " + port);
		this.locked = value;
		
		//TODO rewrite
		//continue sending the signal
		//this.transferSignal_do(te, value, pass, port, side);
		LockManager.tranferSignal(this, getConnectedTerminal(), value, pass, port, side);
		
		//updating
		NetworkManager.updateTileEntityField(this, "locked");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return true;		
	}
//	/**
//	 * transfers a locksignal to all blocks, but the side specified
//	 * 
//	 * @param te the TileEntity the Signal is from, null if it is broken
//	 * @param lock wether to lock or unlock
//	 * @param pass password
//	 * @param port port
//	 * @param ignoreSide Side no signal shoudd be send to
//	 * @return succes
//	 */
//	protected boolean transferSignal_do(ILockTerminal te, boolean lock, String pass, int port, int ignoreSide) {
//		for(int i = 0; i<6; i++)
//		{
//			if(i == ignoreSide)
//			{
//				Config.logDebug("ignored Side: " + i);
//				continue;
//			}
//			TileEntity tile = this.worldObj.getBlockTileEntity(xCoord+Facing.offsetsXForSide[i], yCoord+Facing.offsetsYForSide[i], zCoord+Facing.offsetsZForSide[i]);
//			if(tile != null && tile instanceof ILockReceiver)
//			{
//				((ILockReceiver) tile).receiveSignal(Facing.faceToSide[i], te, lock, pass, port);
//			}
//		}
//		return true;
//	}

	@Override
    public void updateEntity()
    {
        super.updateEntity();
		if (!init)
        {
            initData();
    		onInventoryChanged();

        }  



    }
	
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("facing", this.facing);
        nbttagcompound.setBoolean("locked", locked);
        nbttagcompound.setInteger("port", port);
        nbttagcompound.setString("pass", pass);
        nbttagcompound.setInteger("xTer", xTer);
        nbttagcompound.setInteger("yTer", yTer);
        nbttagcompound.setInteger("zTer", zTer);
    }
    
	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {
		event = event - 2;
		if(event > 99 || event < 0)
		{
			Config.logError("Unexpected event!! " + event);
			return;
		}
		this.port = event;
	}
	
	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getPass() {
		return pass;
	}
	
	@Override
	public boolean isDestroyed() {
		return removed;
	}
	
	@Override
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override 
	public void setPass(String pass) {
		this.pass = pass;
	}
}
