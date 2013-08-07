package hsb.network.packet;


import hsb.ModHsb;
import hsb.lib.PacketIds;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.Player;

public class PacketItemUpdate extends PacketHsb{
	
	public String itemclass;
	public String tag;
	public Object value;
	public short type;
	
	public PacketItemUpdate(){}
	
	public PacketItemUpdate(Item item, String tag, Object value) {
		if(value instanceof Integer)
		{
			this.type = PacketIds.INTEGER;
		}
		else if(value instanceof String)
		{
			this.type = PacketIds.STRING;
		}
		else if(value instanceof Boolean)
		{
			this.type = PacketIds.BOOLEAN;
		} else {
			ModHsb.logger.severe("Hsb Packet Error: Invalid Type!!");
			this.type = PacketIds.ERROR;
		}
		try
		{
			//Item Class name
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
	public void onPacketData(DataInputStream data, Player player) throws IOException {
		ItemStack stack = ((EntityPlayer)player).getCurrentEquippedItem();
		this.readData(data);
//		if(stack.getItem().getClass().getName() != this.itemclass) {
//			HsbLog.severe("False ItemClass! (maybe lag?)");
//			return;
//		}
		if(stack.getTagCompound().hasKey(this.tag))
		{
			switch(this.type)
			{
			case PacketIds.BOOLEAN:
				stack.getTagCompound().setBoolean(this.tag, (Boolean) this.value);
				break;
			case PacketIds.INTEGER:
				stack.getTagCompound().setInteger(this.tag, (Integer) this.value);
				break;
			case PacketIds.STRING:
				stack.getTagCompound().setString(this.tag, (String) this.value);
				break;
			default:
				ModHsb.logger.severe("PacketHandler: unexpected type!!");
				break;
			}
		} else {
			ModHsb.logger.severe("PacketHandler: False NBTTAG key!!!!");
		}
		
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		itemclass = data.readUTF();
		type = data.readShort();
		tag = data.readUTF();
		if(type == PacketIds.INTEGER)
		{
			value = data.readInt();
		}
		else if(type == PacketIds.STRING)
		{
			value = data.readUTF();
		}
		else if(type == PacketIds.BOOLEAN)
		{
			value = data.readBoolean();
		} else if(type == PacketIds.ERROR)
		{
			ModHsb.logger.severe("Hsb Packet reading Error: Invalid Type Id!!");
		}
		
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeUTF(itemclass); //The Class Name
		data.writeShort(type); //the type of the nbttag
		data.writeUTF(tag); //the tag name
		//The value...
		if(type == PacketIds.INTEGER)
		{
			data.writeInt((Integer) value);
		}
		else if(type == PacketIds.STRING)
		{
			data.writeUTF((String) value);
		}
		else if(type == PacketIds.BOOLEAN)
		{
			data.writeBoolean((Boolean) value);
		} else if(type == PacketIds.ERROR){
			ModHsb.logger.severe("Hsb Packetwriting Error: Invalid Type Id!");
		}
		
	}

}
