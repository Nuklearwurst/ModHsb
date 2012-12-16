package hsb;

import java.util.List;
import java.util.Vector;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class TileEntityDoorBase extends TileEntityHsbBuilding {

	public boolean upgradePlayer = false;
	public String placer = "";
	
	public void onDoorBreak(World world, int x, int y, int z) {
		upgradePlayer = false;
		placer = "";
	}
	@Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
		nbttagcompound.setString("placer", placer);
		nbttagcompound.setBoolean("upgradePlayer", upgradePlayer);
		super.writeToNBT(nbttagcompound);
    }
	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		placer = nbttagcompound.getString("placer");
		upgradePlayer = nbttagcompound.getBoolean("upgradePlayer");
		super.readFromNBT(nbttagcompound); 
    }
	
	@Override
	public List<String> getNetworkedFields() {
		List list = super.getNetworkedFields();
	    list.add("placer");
	    list.add("upgradePlayer");
	    return list;
	}
}
