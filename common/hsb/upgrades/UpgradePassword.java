package hsb.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.api.upgrade.IHsbUpgrade;
import hsb.config.HsbItems;
import hsb.items.ItemHsbUpgrade;
import hsb.network.packet.PacketHsb;
import hsb.tileentitys.TileEntityLockTerminal;

public class UpgradePassword implements IHsbUpgrade {

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
		return ItemHsbUpgrade.ID_UPGRADE_PASSWORD;
	}

	@Override
	public void handlePacket(PacketHsb packet, TileEntityLockTerminal te) {}

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player,
			int button) {
		if(!te.worldObj.isRemote){
			player.sendChatToPlayer("Current password length: " + (TileEntityLockTerminal.defaultPassLength + te.extraPassLength));
		}

	}

	@Override
	public void onGuiOpen(TileEntityLockTerminal te) {
	}

	@Override
	public void onTileLoad(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
	}

	@Override
	public void onTileSave(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
	}
	
	@Override
	public void updateUpgrade(TileEntityLockTerminal te) {
		int index = te.upgradeCount[te.getUpgradeId(getUniqueId())];
		te.extraPassLength = index;

	}

}
