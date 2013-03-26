package hsb.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import hsb.api.upgrade.IHsbUpgrade;
import hsb.config.HsbItems;
import hsb.network.packet.PacketHsb;
import hsb.tileentitys.TileEntityLockTerminal;

public class UpgradeTesla implements IHsbUpgrade{
	
	@Override
	public void updateUpgrade(TileEntityLockTerminal te) {
		int index = te.getUpgradeId(this.getUniqueId());
		if(te.upgradeCount[index] > 32)
			te.upgradeCount[index]  = 32;
		if(te.upgradeActive[index])
			te.energyUse = te.energyUse + 0.25 * te.upgradeCount[index];
		
	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player, int button) {

		boolean active = te.upgradeActive[te.getUpgradeId(this.getUniqueId())];
		if(active)
		{
			if(!te.worldObj.isRemote)
			{
				player.sendChatToPlayer("Tesla Upgrade disabled!");
			}
			te.upgradeActive[te.getUpgradeId(this.getUniqueId())] = false;
		} else {
			if(!te.worldObj.isRemote)
			{
				player.sendChatToPlayer("Tesla Upgrade enabled!");
			}
			te.upgradeActive[te.getUpgradeId(this.getUniqueId())] = true;
		}
		
	}

	@Override
	public String getButtonName() {
		return "Tesla";
	}
	@Override
	public String getUniqueId() {
		return "tesla";
	}

	@Override
	public void onTileSave(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {}

	@Override
	public void onTileLoad(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {}

	@Override
	public void onGuiOpen(TileEntityLockTerminal te) {}

	@Override
	public ItemStack getItem() {
		return new ItemStack(HsbItems.itemHsbUpgrade, 1, 0);
	}

	@Override
	public boolean isEnabledByDefault() {
		return false;
	}

	@Override
	public void handlePacket(PacketHsb packet, TileEntityLockTerminal te) {}

}
