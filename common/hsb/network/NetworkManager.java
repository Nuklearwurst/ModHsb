package hsb.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;


import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import hsb.ModHsb;
import hsb.config.Config;
import hsb.network.packet.PacketClientTileEvent;
import hsb.network.packet.PacketTileFieldUpdate;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.NetworkHelper;

/**
 * for network-code
 * currently only used for ic2
 * @author Nuklearwurst
 *
 */
public class NetworkManager {
	
	public final int event_range = 20;

	  private int maxNetworkedFieldsToUpdate = 4000;
	
	/**
	 * workaround if ic2 is not installed
	 * @param te
	 */
	public void requestInitialData(INetworkDataProvider te) {}
	
	public void updateTileEntityField(TileEntity te, String s) {
		if(Config.ic2Available)
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
				Config.logError("Empty Field to be updated!");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void sendPacketToPlayers(int radius, TileEntity te, Packet packet)
	{
		try {
			
			AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
					te.xCoord - radius, te.yCoord - radius, te.zCoord - radius,
					te.xCoord + radius,	te.yCoord + radius, te.zCoord + radius);
			
			List<EntityPlayer> e = te.worldObj.getEntitiesWithinAABB(EntityPlayer.class, box);
//			te.worldObj.playerEntities.iterator();
			Config.logDebug(e.size() + " Players found!\n X,Y,Z Coords: " + te.xCoord + ", " + te.yCoord + ", " + te.zCoord);
			for( EntityPlayer player : e) {
				PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
			}
		} catch(Exception e) {
			Config.logError("Error occured wehen sending event to Client!!!");
			e.printStackTrace();
		}
	}
	public void initiateClientTileEntityEvent(TileEntity te, int ev) {}
	
	public static NetworkManager getInstance() {
		return ModHsb.network_manager;
	}
	
}
