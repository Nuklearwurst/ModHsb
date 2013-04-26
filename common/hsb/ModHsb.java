package hsb;



import hsb.block.ModBlocks;
import hsb.configuration.Config;
import hsb.core.addons.PluginIC2;
import hsb.core.handlers.GuiHandler;
import hsb.core.handlers.LocalizationHandler;
import hsb.core.helper.HsbLog;
import hsb.core.proxy.CommonProxy;
import hsb.item.ModItems;
import hsb.lib.Reference;
import hsb.lib.Strings;
import hsb.network.NetworkManager;
import hsb.network.PacketHandler;
import hsb.recipes.HsbRecipes;
import hsb.tileentity.TileEntityDoorBase;
import hsb.tileentity.TileEntityGuiAccess;
import hsb.tileentity.TileEntityHsbBuilding;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Main Mod Class
 * 
 * @author Jonas
 *
 */

@Mod(	modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION_NUMBER,
		dependencies = Reference.DEPENDENCIES,
		certificateFingerprint = Reference.FINGERPRINT) 

@NetworkMod(	clientSideRequired = true,
				serverSideRequired=false, 
				channels={Reference.CHANNEL_NAME},
				packetHandler = PacketHandler.class)
public class ModHsb{
	
	@Instance
	public static ModHsb instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS) //TODO Bukkit
	public static CommonProxy proxy;
	
	@SidedProxy(clientSide = Reference.CLIENT_NETWORK_CLASS, serverSide = Reference.SERVER_NETWORK__CLASS) //TODO Bukkit
	public static NetworkManager network_manager;
	
    @FingerprintWarning
    public void invalidFingerprint(FMLFingerprintViolationEvent event) {
    	//TODO fingerprint
        //HsbLog.severe(Strings.INVALID_FINGERPRINT_MESSAGE);
    }
	
	@PreInit
	void preInit(FMLPreInitializationEvent evt) {
		
		//init Logger
		HsbLog.init();
		
		//Localization
		LocalizationHandler.loadLanguages();
		
		//TODO key bindings
		
		//TODO sounds
		
		//read Config
		Config.readConfig(evt);
		
		//registerBlocks
		ModBlocks.init();
		//register Items
		ModItems.init();
	}
	
	@Init
	void init(FMLInitializationEvent evt) {
		
		//register guihandler
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		//Register Events 
		//nothing yet
		
		//register TileEntities
		GameRegistry.registerTileEntity(TileEntityHsbBuilding.class, Strings.TILE_ENTITY_HSB_BUILDING);
		GameRegistry.registerTileEntity(TileEntityHsbTerminal.class, Strings.TILE_ENTITY_HSB_TERMINAL);
		GameRegistry.registerTileEntity(TileEntityDoorBase.class, Strings.TILE_ENTITY_HSB_DOOR_BASE);
		GameRegistry.registerTileEntity(TileEntityGuiAccess.class, Strings.TILE_ENTITY_HSB_GUI_ACCESS);
		
		//add Names (Done in localization)
//		Config.initNames();
		
		//init rendering
		proxy.initRendering();
		proxy.initSpecialRenderer();
		
		//check for IC2
		PluginIC2.initPluginIC2();
		
		//init Recipes
		HsbRecipes.initRecipes();

		
		//register upgrades
		UpgradeRegistry.initUpgrades();
	}
	
	@PostInit
	void postInit(FMLPostInitializationEvent evt) {
		
		HsbLog.info("Hsb Version: "+ Reference.VERSION_NUMBER + " loaded.");
	}

	


}
