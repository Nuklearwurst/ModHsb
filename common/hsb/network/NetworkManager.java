package hsb.network;

import hsb.ModHsb;
import hsb.configuration.Settings;
import hsb.core.helper.HsbLog;
import hsb.network.packet.tileentity.PacketTileFieldUpdate;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.NetworkHelper;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;


public class NetworkManager {
	
	
	public static NetworkManager getInstance() {
		return ModHsb.network_manager;
	}
	
	public void initiateClientTileEntityEvent(TileEntity te, int ev) {}

	public void requestInitialData(INetworkDataProvider te) {}
	
	@SuppressWarnings("unchecked")
	private void sendPacketToPlayers(int radius, TileEntity te, Packet packet)
	{
		try {
			
			AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
					te.xCoord - radius, te.yCoord - radius, te.zCoord - radius,
					te.xCoord + radius,	te.yCoord + radius, te.zCoord + radius);
			
			List<EntityPlayer> e = te.worldObj.getEntitiesWithinAABB(EntityPlayer.class, box);
//			HsbLog.logDebug(e.size() + " Players found!\n X,Y,Z Coords: " + te.xCoord + ", " + te.yCoord + ", " + te.zCoord);
			for( EntityPlayer player : e) {
				PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
			}
		} catch(Exception e) {
			HsbLog.severe("Error occured wehen sending event to Client!!!");
			e.printStackTrace();
		}
	}
	
	public void updateTileEntityField(TileEntity te, String s) {
		if(Settings.ic2Available)
		{
			NetworkHelper.updateTileEntityField(te, s);
		} else {
			//TODO tilefield update
			Object value = null;
			try {
				value = te.getClass().getField(s).get(te);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(value != null) {
				PacketTileFieldUpdate packet = new PacketTileFieldUpdate(te, s, value);
				sendPacketToPlayers(100, te, packet.getPacket());
			} else {
				HsbLog.severe("Empty Field to be updated: " + s);
			}
		}
	}
}
