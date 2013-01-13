package hsb.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.tileentitys.TileEntityLockTerminal;

public interface IHsbUpgrade {
	
	public void updateUpgrade(TileEntityLockTerminal te);
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player, int button);
	public String getButtonName();
	public ItemStack getItem();
	public String getUniqueId();
	public void onTileSave(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
	public void onTileLoad(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
	public void onGuiOpen(TileEntityLockTerminal te);
	public boolean isEnabledByDefault();

}
