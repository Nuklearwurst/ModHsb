package hsb.network.packet.tileentity;

import hsb.core.helper.HsbLog;
import hsb.lib.PacketIds;
import hsb.network.packet.PacketPosition;
import ic2.api.network.INetworkUpdateListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;

public class PacketTileFieldUpdate extends PacketPosition{


	//value of the field
	Object value;
	//name of the field
	String name;
	//type of the field see: PacketIds
	short type;

	

	public PacketTileFieldUpdate() {}
	
	public PacketTileFieldUpdate(TileEntity te, String name, Object value) {
		super(te.xCoord, te.yCoord, te.zCoord);
		this.value = value;
		this.name = name;
		
		if(value instanceof Boolean) {
			this.type = PacketIds.BOOLEAN;
		} else if(value instanceof Integer) {
			this.type = PacketIds.INTEGER;
		} else if(value instanceof String) {
			this.type = PacketIds.STRING;
		} else if(value instanceof Short) {
			this.type = PacketIds.SHORT;
		} else if(value instanceof ItemStack) {
			this.type = PacketIds.ITEMSTACK;
		} else if(value instanceof boolean[]) {
			this.type = PacketIds.BOOLEAN_ARRAY;
		} else if(value instanceof int[]) {
			this.type = PacketIds.INTEGER_ARRAY;
		} else{
			HsbLog.severe("Invalid type! (PacketTileFieldUpdate, constructor) : " + value);
			this.type = PacketIds.ERROR;
		}
	}

	@Override
	public int getID() {
		return PacketIds.TILE_FIELD_UPDATE;
	}

	@Override
	public void onPacketData(DataInputStream data, Player player)
			throws IOException {
		this.readData(data);
		
		TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		if(te == null) //Lag
			return;
		try {
			//try to get field and set value
			te.getClass().getField(name).set(te, value);
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
		//updates tileentity
		if(te instanceof INetworkUpdateListener) {
			((INetworkUpdateListener) te).onNetworkUpdate(name);
		} else {
			HsbLog.debug("Fieldupdated but no updatelistener found");
		}

	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		
		type = data.readShort();
		
		name = data.readUTF();
		
		switch(type) {
		case PacketIds.BOOLEAN:
			value = data.readBoolean();
			break;
		case PacketIds.INTEGER:
			value = data.readInt();
			break;
		case PacketIds.STRING:
			value = data.readUTF();
			break;
		case PacketIds.SHORT:
			value = data.readShort();
			break;
		case PacketIds.ITEMSTACK:
			value = Packet.readItemStack(data);
			break;
		case PacketIds.BOOLEAN_ARRAY:
		{
			short length = data.readShort();
			boolean array[] = new boolean[length];
			for(int i = 0; i < length; i++) {
				array[i] = data.readBoolean();
			}
			value = array;
			break;
		}
		case PacketIds.INTEGER_ARRAY:
		{
			short length = data.readShort();
			int array[] = new int[length];
			for(int i = 0; i < length; i++) {
				array[i] = data.readInt();
			}
			value = array;
			break;
		}
		default:
			value = null;
			HsbLog.severe("invalid type (PacketTileFieldUpdate, readData): " + type);
			break;
		}
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		
		data.writeShort(type);
		
		data.writeUTF(name);
		
		switch(type) {
		case PacketIds.BOOLEAN:
			data.writeBoolean((Boolean) value);
			break;
		case PacketIds.INTEGER:
			data.writeInt((Integer) value);
			break;
		case PacketIds.STRING:
			data.writeUTF((String) value);
			break;
		case PacketIds.SHORT:
			data.writeShort((Short) value);
			break;
		case PacketIds.ITEMSTACK:
			Packet.writeItemStack((ItemStack) value, data);
			break;
		case PacketIds.BOOLEAN_ARRAY:
		{
			boolean array[] = (boolean[]) value;
			data.writeShort(array.length);
			for(int i = 0; i < array.length; i++) {
				data.writeBoolean(array[i]);
			}
			break;
		}
		case PacketIds.INTEGER_ARRAY:
		{
			int array[] = (int[]) value;
			data.writeShort(array.length);
			for(int i = 0; i < array.length; i++) {
				data.writeInt(array[i]);
			}
			break;
		}
		default:
			HsbLog.severe("invalid type (PacketTileFieldUpdate, writeData)");
			break;
		}
	}
}
