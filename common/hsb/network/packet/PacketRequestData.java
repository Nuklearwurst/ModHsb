package hsb.network.packet;

import hsb.network.NetworkManager;
import hsb.network.PacketIds;
import ic2.api.network.INetworkDataProvider;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;

public class PacketRequestData extends PacketPosition{

	

	public PacketRequestData() {}
	
	public PacketRequestData(TileEntity te) {
		super(te.xCoord, te.yCoord, te.zCoord);
	}

	@Override
	public int getID() {
		return PacketIds.TILE_DATA_REQUEST;
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);

		TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if(te!=null && te instanceof INetworkDataProvider)
		{
			List<String> list = ((INetworkDataProvider)te).getNetworkedFields();
			for(String field : list ) {
				NetworkManager.getInstance().updateTileEntityField(te, field);
			}
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
