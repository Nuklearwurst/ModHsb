package hsb.network;

public class PacketIds {

	public static final int TILE_UPDATE = 0;
	public static final int ITEM_NBTTAG_UPDATE = 1; //Client --> Server
	public static final int TILE_FIELD_UPDATE = 2; //Client --> Server
	public static final int TILE_TERMINAL_UPDATE = 3; //Client --> Server
	public static final int TILE_UPGRADECAMO = 4; //Server --> Client
	public static final int TILE_UPGRADEINV_UPDATE = 5; //Server --> Client
	public static final int TILE_CLIENT_EVENT = 6; //Server --> Client
	//public static final int TILE_FIELD_UPDATE = 7; //Server --> Client
	public static final int TILE_DATA_REQUEST = 8; //Client --> Server

	
	
	//Type Ids
	public static final short BOOLEAN = 0;
	public static final short INTEGER = 1;
	public static final short STRING = 2;
	public static final short SHORT = 3;
	public static final short ITEMSTACK = 4;

	public static final short BOOLEAN_ARRAY = 5;
	public static final short INTEGER_ARRAY = 6;
	
	public static final short ERROR = -1;

}
