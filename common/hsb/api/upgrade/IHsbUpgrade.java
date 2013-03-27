package hsb.api.upgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.network.packet.PacketHsb;
import hsb.tileentitys.TileEntityLockTerminal;

/**
 * interface that has to be implemented by a class to be an Upgrade for the LockTerminal
 * @author Jonas
 *
 */
public interface IHsbUpgrade {
	
	/**
	 * called whenever an upgrade has changed
	 * @param te
	 */
	public void updateUpgrade(TileEntityLockTerminal te);
	/**
	 * called when player clicks the upgrade-button
	 * @param te the terminal the Upgrade is installed
	 * @param player who clicked the button
	 * @param button id?
	 */
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player, int button);
	/**
	 * 
	 * @return the button display name
	 */
	public String getButtonName();
	/**
	 * 
	 * @return the Item this Upgrade depends on
	 */
	public ItemStack getItem();
	/**
	 * 
	 * @return unique String for this upgrade type
	 */
	public String getUniqueId();
	/**
	 * called in te: writeToNBTTag()
	 * @param nbttagcompound
	 * @param te
	 */
	public void onTileSave(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
	/**
	 * called in te: readFromNBTTag()
	 * @param nbttagcompound
	 * @param te
	 */
	public void onTileLoad(NBTTagCompound nbttagcompound, TileEntityLockTerminal te);
	/**
	 * unused TODO: use to do action before terminal is accessed
	 * @param te
	 */
	public void onGuiOpen(TileEntityLockTerminal te);
	/**
	 * 
	 * @return is the Upgrade enabled when installed?
	 */
	public boolean isEnabledByDefault();
	
	//TODO inventory syncing
	//TODO maybe new network code
	public void handlePacket(PacketHsb packet, TileEntityLockTerminal te);
}
