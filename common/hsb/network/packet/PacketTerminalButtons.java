package hsb.network.packet;

import hsb.core.helper.HsbLog;
import hsb.lib.PacketIds;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.types.IHsbUpgrade;
import hsb.upgrade.types.IUpgradeButton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;

/**
 * unused
 * @author Nuklearwurst
 *
 */
public class PacketTerminalButtons extends PacketPosition {
	
	List<String> buttons = new ArrayList<String>();

	@Override
	public int getID() {
		return PacketIds.TILE_TERMINAL_BUTTONS;
	}

	
	public PacketTerminalButtons(){}
	
	public PacketTerminalButtons(TileEntityHsbTerminal te) {
		super(te.xCoord, te.yCoord, te.zCoord);
		//getting the ids
		List<String> ids = te.buttons;
		if(ids != null)
		{
			//setting button text for client
			for(String s : ids)
			{
				IHsbUpgrade upgrade = te.getUpgrade(s);
				String name = "";
				if(upgrade != null && upgrade instanceof IUpgradeButton)
				{
					name = ((IUpgradeButton) upgrade).getButton();
				}
				buttons.add(name);
			}
//			HsbLog.debug("button number: " + ids.size());
		} else {
			HsbLog.severe("ids == null!");
		}
	}

	//on client
	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		int length = data.readShort();
		for (int i = 0; i < length; i++)
		{
			buttons.add(data.readUTF());
		}
	}

	//on server
	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeShort(buttons.size());
		for(String s: buttons) {
			data.writeUTF(s);
		}
	}


	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		readData(data);
		TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityHsbTerminal)
		{
			((TileEntityHsbTerminal) te).buttons = buttons;
		} else {
			HsbLog.severe("error when reading button update packet!");
		}
	}

}
