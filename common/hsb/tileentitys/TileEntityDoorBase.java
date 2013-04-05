package hsb.tileentitys;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityDoorBase extends TileEntityHsbBuilding {

	public boolean upgradePlayer = false;
	public String placer = "";
	
	@Override
	public List<String> getNetworkedFields() {
		List<String> list = super.getNetworkedFields();
	    list.add("placer");
	    list.add("upgradePlayer");
	    return list;
	}
	public void onDoorBreak(World world, int x, int y, int z) {
		world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0);
		int port = this.getPort();
		world.setBlockTileEntity(xCoord, yCoord, zCoord, new TileEntityHsbBuilding());
		((TileEntityHsbBuilding)world.getBlockTileEntity(xCoord, yCoord, zCoord)).setPort(port);
		world.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		placer = nbttagcompound.getString("placer");
		upgradePlayer = nbttagcompound.getBoolean("upgradePlayer");
		super.readFromNBT(nbttagcompound); 
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
		nbttagcompound.setString("placer", placer);
		nbttagcompound.setBoolean("upgradePlayer", upgradePlayer);
		super.writeToNBT(nbttagcompound);
    }
}
