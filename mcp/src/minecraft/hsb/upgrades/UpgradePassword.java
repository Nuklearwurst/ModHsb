package hsb.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.api.IHsbUpgrade;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityLockTerminal;

public class UpgradePassword implements IHsbUpgrade {

	@Override
	public void updateUpgrade(TileEntityLockTerminal te) {
		int index = te.upgradeCount[te.getUpgradeId(getUniqueId())];
		te.extraPassLength = index;

	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player,
			int button) {
		if(!te.worldObj.isRemote){
			player.sendChatToPlayer("Current password length: " + (te.defaultPassLength + te.extraPassLength));
		}

	}

	@Override
	public String getButtonName() {
		return "Passw.";
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(HsbItems.itemHsbUpgrade, 1, 1);
	}

	@Override
	public String getUniqueId() {
		return "password";
	}

	@Override
	public void onTileSave(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
	}

	@Override
	public void onTileLoad(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
	}

	@Override
	public void onGuiOpen(TileEntityLockTerminal te) {
	}

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

}
