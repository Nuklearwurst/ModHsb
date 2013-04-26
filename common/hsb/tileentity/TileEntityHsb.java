package hsb.tileentity;

import hsb.configuration.Settings;
import hsb.core.helper.HsbLog;
import hsb.lock.ILockTerminal;
import hsb.lock.ILockable;
import hsb.lock.LockManager;
import hsb.network.NetworkManager;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileEntityHsb extends TileEntitySimple 
	implements ILockable, INetworkClientTileEntityEventListener, INetworkDataProvider, INetworkUpdateListener
{
	
	private int xTer = 0;
	private int yTer = 0;
	private int zTer = 0;
	
	public boolean locked = false;
	
	private boolean prevLocked = false; 
	
	private String pass = "";
	private int port = 0;
	
	private boolean isDestroyed = false;
	
	//time until next signal has to be send, if <0 no lock signal needs to be send
	private int nextSignal = -1;
	private boolean init;
	
	@Override
	public boolean connectsTo(int side) {
		return true;
	}
	
	@Override
	public ILockTerminal getConnectedTerminal() {
		TileEntity te = this.worldObj.getBlockTileEntity(xTer, yTer, zTer);
		if(te instanceof ILockTerminal && locked) {
			return (ILockTerminal) te;
		} else {
			return null;
		}
	}
	
	@Override
	public List<String> getNetworkedFields() {
		List<String> list  = new ArrayList<String>(1);
		list.add("locked");
	    return list;
	}
	
	@Override
	public String getPass() {
		return pass;
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
	protected void initData() {
		if(worldObj.isRemote)
        {
    		NetworkManager.getInstance().requestInitialData(this);
        }
        init = true;
	}
	
	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
	@Override
	public boolean isLocked() { return locked; }
	
	@Override
	public void onNetworkEvent(EntityPlayer player, int event) {

		//port update
		if(event > Settings.maxPort || event < 0)
		{
			HsbLog.severe("Unexpected event!! " + event);
			return;
		}
		this.port = event;
		
	}

	@Override
	public void onNetworkUpdate(String field) {
		 if (field.equals("locked") && prevLocked != locked)
	     {
	         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	         prevLocked = locked;
	     }	
	}
	
	public void onRemove(World world, int x, int y, int z, int par5, int par6) {
		//only on server
		if(this.worldObj.isRemote)
		{
			return;
		}
		if(!this.isLocked())
		{
			return;
		}
		//unlocking
		this.locked = false;
		//set removed mark
		this.isDestroyed = true;
		
		//transfersignal (from this, no terminal, unlocking, pass, port, all sides)
		LockManager.tranferSignal(this, null, false, pass, port, 6);

	}

	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

        locked = nbttagcompound.getBoolean("locked");
        prevLocked = locked;
        
        port = nbttagcompound.getInteger("port");
        pass = nbttagcompound.getString("pass");
        
        xTer = nbttagcompound.getInteger("xTer");
        yTer = nbttagcompound.getInteger("yTer");
        zTer = nbttagcompound.getInteger("zTer");
        
        nextSignal = nbttagcompound.getInteger("nextSignal");
        isDestroyed = nbttagcompound.getBoolean("destroyed");
    }
	
	@Override
	public boolean receiveSignal(int side, ILockTerminal te, boolean lockSignal,
			String pass, int port) {
		//only simulated on server (shoudn't be called)
		if(this.worldObj.isRemote) {
			HsbLog.debug("BUG lock signal send on client!!!");
			return true;
		}
		//checks if the tile is going to be destroyed
		if(this.isDestroyed)
		{
			HsbLog.debug("this is removed!");
			return false;
		}
		//check if Lock is the same, or if port is not the same
		if (lockSignal == this.locked || port != this.port) {
			return false;
		}
		
		//Unlocking (checks condition to return)
		if(!lockSignal)
		{
			//checks if the connected terminal is valid (if not unlocking will succeed)
			if( (this.getConnectedTerminal() != null) && (!this.getConnectedTerminal().isDestroyed()) )
			{
				//checks if the password is right
				if(!this.pass.equals(pass))
				{
					HsbLog.debug("transfer failed:false password ! |" + this.pass + "|" + pass + "|");
					return false;
				}
			}
			
		}
		//locking
		if(lockSignal && te != null)//
		{
			//adding block to the terminal
			te.addBlockToTileEntity(this);
			//setting password
			this.pass = pass;
		}
		if(te != null) {
			//setting terminal coordinates
			this.xTer = ((TileEntity)te).xCoord;
			this.yTer = ((TileEntity)te).yCoord;
			this.zTer = ((TileEntity)te).zCoord;
		}
		
		//setting new lock status
		this.setLocked(lockSignal);
		
		//continue sending the signal
		this.nextSignal = 5;
		return true;
	}
	
	@Override
	public void setLocked(boolean lock) {
		this.locked = lock;
		//updating fields
		NetworkManager.getInstance().updateTileEntityField(this, "locked");
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}
    @Override
    public void updateEntity()
    {
        super.updateEntity();
		if (!init)
        {
            initData();
        }
		if(nextSignal == 0)
		{
			//transfer
			LockManager.tranferSignal(this, getConnectedTerminal(), locked, getPass(), getPort(), 6);
		}
		if (nextSignal >= 0) {
			nextSignal--;
		}
    }
	@Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        
        nbttagcompound.setBoolean("locked", locked);
        
        nbttagcompound.setInteger("port", port);
        nbttagcompound.setString("pass", pass);
        
        nbttagcompound.setInteger("xTer", xTer);
        nbttagcompound.setInteger("yTer", yTer);
        nbttagcompound.setInteger("zTer", zTer);
        
        nbttagcompound.setInteger("nextSignal", nextSignal);
        nbttagcompound.setBoolean("destroyed", isDestroyed);
    }
}
