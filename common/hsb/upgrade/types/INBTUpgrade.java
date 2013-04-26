package hsb.upgrade.types;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTUpgrade extends IHsbUpgrade{

    public void readFromNBT(NBTTagCompound tag);
    public void writeToNBT(NBTTagCompound tag);
}
