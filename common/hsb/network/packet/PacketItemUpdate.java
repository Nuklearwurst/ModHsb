package hsb.network.packet;

import hsb.network.PacketIds;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.Player;
//Mal sehen
public class PacketItemUpdate extends PacketHsb{
	
	public static final short BOOLEAN_KEY= 0;
	public static final short INTEGER_KEY= 1;
	public static final short STRING_KEY= 2;
	
	public static final short ERROR= -1;
	
	public String itemclass;
	public String tag;
	public Object value;
	public short type;
	
	public PacketItemUpdate(){}
	
	public PacketItemUpdate(Item item, String tag, Object value) {
		if(value instanceof Integer)
		{
			this.type = INTEGER_KEY;
		}
		else if(value instanceof String)
		{
			this.type = STRING_KEY;
		}
		else if(value instanceof Boolean)
		{
			this.type = BOOLEAN_KEY;
		} else {
			FMLLog.severe("Hsb Packet Error!!");
			this.type = ERROR;
		}
		try
		{
			this.itemclass = item.getClass().getName();
		} catch(Exception e){
			e.printStackTrace();
		}
		//The NBTTag
		this.tag = tag;
		//The Value of the NBTTag
		this.value = value;
		
	}
	@Override
	public int getID() {
		return PacketIds.ITEM_NBTTAG_UPDATE;
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		itemclass = data.readUTF();
		type = data.readShort();
		tag = data.readUTF();
		if(type == INTEGER_KEY)
		{
			value = data.readInt();
		}
		else if(type == STRING_KEY)
		{
			value = data.readUTF();
		}
		else if(type == BOOLEAN_KEY)
		{
			value = data.readBoolean();
		} else if(type == ERROR)
		{
			FMLLog.severe("Hsb Packet reading Error!!");
		}
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeUTF(itemclass); //The Class Name
		data.writeShort(type);
		data.writeUTF(tag);
		if(type == INTEGER_KEY)
		{
			data.writeInt((Integer) value);
		}
		else if(type == STRING_KEY)
		{
			data.writeUTF((String) value);
		}
		else if(type == BOOLEAN_KEY)
		{
			data.writeBoolean((Boolean) value);
		} else if(type == ERROR){
			FMLLog.severe("Hsb Packetwriting Error!");
		}
		
	}

	@Override
	public void onPacketData(DataInputStream data, Player player) throws IOException {
		ItemStack stack = ((EntityPlayer)player).getCurrentEquippedItem();
		this.readData(data);
		if(stack.getTagCompound().hasKey(this.tag))
		{
			switch(this.type)
			{
			case PacketItemUpdate.BOOLEAN_KEY:
				stack.getTagCompound().setBoolean(this.tag, (Boolean) this.value);
				break;
			case PacketItemUpdate.INTEGER_KEY:
				stack.getTagCompound().setInteger(this.tag, (Integer) this.value);
				break;
			case PacketItemUpdate.STRING_KEY:
				stack.getTagCompound().setString(this.tag, (String) this.value);
				break;
			default:
				FMLLog.severe("Hsb: PacketHandler: unexpected type!!");
				break;
			}
		} else {
			FMLLog.severe("Hsb: PacketHandler: False NBTTAG key!!!!");
		}
		
	}

}
