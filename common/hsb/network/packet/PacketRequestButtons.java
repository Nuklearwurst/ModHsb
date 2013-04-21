package hsb.network.packet;

import hsb.core.helper.HsbLog;
import hsb.lib.PacketIds;
import hsb.tileentity.TileEntityHsbTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketRequestButtons extends PacketPosition{

	@Override
	public int getID() {
		return PacketIds.TILE_REQUEST_BUTTON_UPDATE;
	}

	public PacketRequestButtons() {}
	
	public PacketRequestButtons(TileEntity te) {
		super(te.xCoord, te.yCoord, te.zCoord);
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);

		TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if(te!=null && te instanceof TileEntityHsbTerminal)
		{
			PacketTerminalButtons packet = new PacketTerminalButtons((TileEntityHsbTerminal) te);
			PacketDispatcher.sendPacketToPlayer(packet.getPacket(), player);
		} else {
			HsbLog.severe("TE not valid for button update!");
		}

	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);

	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		
	}

}
