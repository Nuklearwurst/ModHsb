package hsb.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.api.upgrade.IHsbUpgrade;
import hsb.config.HsbItems;
import hsb.network.packet.PacketHsb;
import hsb.tileentitys.TileEntityLockTerminal;

public class UpgradeSecurity implements IHsbUpgrade {

	@Override
	public void updateUpgrade(TileEntityLockTerminal te) {
		int index = te.upgradeCount[te.getUpgradeId(getUniqueId())];
		te.securityLevel = index;

	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player,
			int button) {
		if(!te.worldObj.isRemote){
			player.sendChatToPlayer("Current security level: " + te.securityLevel);
		}
	}

	@Override
	public String getButtonName() {
		return "Security";
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(HsbItems.itemHsbUpgrade, 1, 2);
	}

	@Override
	public String getUniqueId() {
		return "security";
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

	@Override
	public void handlePacket(PacketHsb packet, TileEntityLockTerminal te) {}
}
