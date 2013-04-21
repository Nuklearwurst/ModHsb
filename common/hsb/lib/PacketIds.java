package hsb.lib;

public class PacketIds {


	public static final int TILE_FIELD_UPDATE = 0; //Server --> Client
	public static final int TILE_CLIENT_EVENT = 1; //Client --> Server
	public static final int TILE_DATA_REQUEST = 2; //Client --> Server
	
	public static final int GUI_PASSWORD_UPDATE = 3; // Client --> Server || Server --> Client
	
	public static final int ITEM_NBTTAG_UPDATE = 4;

	
	
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
