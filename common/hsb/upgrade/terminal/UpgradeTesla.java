package hsb.upgrade.terminal;

import hsb.core.util.Utils;
import hsb.lib.Strings;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.INBTUpgrade;
import hsb.upgrade.types.IUpgradeButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class UpgradeTesla extends UpgradeHsbTerminal
	implements IUpgradeButton, INBTUpgrade
{

	public boolean active = false;
	@Override
	public void updateUpgrade(TileEntityHsbTerminal te) {
		if(active)
		{
			te.tesla = this.count;//for now
		} else {
			te.tesla = 0;
		}
	}

	@Override
	public String getUniqueId() {
		return UpgradeRegistry.ID_UPGRADE_TESLA;
	}

	@Override
	public void addInformation(IHsbUpgrade upgrade) {
		if(upgrade instanceof UpgradeTesla) {
			this.active = ((UpgradeTesla) upgrade).active;
		}
	}

	@Override
	public void onButtonClicked(EntityPlayer player, TileEntityHsbTerminal te) {
		if(!te.isLocked()) {
			active = !active;
			player.sendChatToPlayer(Utils.getChatMessage(Strings.translate(active ? Strings.CHAT_ACTIVATED : Strings.CHAT_DISABLED)));
			this.updateUpgrade(te);
		} else {
			
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		tag.getBoolean("upgradeTeslaActive");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setBoolean("upgradeTelsaActive", active);
	}

}
