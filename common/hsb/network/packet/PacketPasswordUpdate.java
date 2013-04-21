package hsb.network.packet;

import hsb.lib.PacketIds;
import hsb.tileentity.TileEntityHsbTerminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;

public class PacketPasswordUpdate extends PacketPosition{

	private String pass = "";
	
	@Override
	public int getID() {
		return PacketIds.GUI_PASSWORD_UPDATE;
	}
	
	public PacketPasswordUpdate(){}
	
	public PacketPasswordUpdate(TileEntity te, String pass) {
		super(te.xCoord, te.yCoord, te.zCoord);
		this.pass = pass;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		
		pass = data.readUTF();
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		
		data.writeUTF(pass);
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		readData(data);
		TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityHsbTerminal) {
			((TileEntityHsbTerminal) te).setPass(pass);
		}
	}

}
