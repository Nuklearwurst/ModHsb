package hsb.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.network.NetworkManager;
import ic2.api.IWrenchable;

public class TileEntityGuiAccess extends TileEntityHsb
	implements IWrenchable
{

	public short facing = 3;
	private short prevFacing = 3;
	
	@Override
	public void onNetworkUpdate(String field) {
		super.onNetworkUpdate(field);
		 if (field.equals("facing") && prevFacing != facing)
	     {
	         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	         prevFacing = facing;
	     }
	}
	
	@Override
    public void readFromNBT(NBTTagCompound tag)
    {
		super.readFromNBT(tag);
		//facing
		this.facing = tag.getShort("facing");
		prevFacing = this.facing;	
    }
	@Override
    public void writeToNBT(NBTTagCompound tag)
    {
		super.writeToNBT(tag);
		
		tag.setShort("facing", this.getFacing());
    }
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return false;
	}

	@Override
	public short getFacing() {
		return facing;
	}

	@Override
	public void setFacing(short facing) {
		this.facing = facing;
        if (this.prevFacing != this.facing)
        {
            NetworkManager.getInstance().updateTileEntityField(this, "facing");
        }
        this.prevFacing = this.facing;
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return false;
	}

	@Override
	public float getWrenchDropRate() {
		return 0;
	}
	
	@Override
	public List<String> getNetworkedFields() {
		List<String> list = super.getNetworkedFields();
	    list.add("facing");
	    return list;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return null;
	}

}
