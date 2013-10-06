package hsb;



import hsb.block.ModBlocks;
import hsb.configuration.Config;
import hsb.configuration.Settings;
import hsb.core.handlers.GuiHandler;
import hsb.core.plugin.PluginManager;
import hsb.core.proxy.CommonProxy;
import hsb.item.ModItems;
import hsb.lib.Reference;
import hsb.lib.Strings;
import hsb.network.PacketHandler;
import hsb.recipes.HsbRecipes;
import hsb.tileentity.TileEntityDoorBase;
import hsb.tileentity.TileEntityGuiAccess;
import hsb.tileentity.TileEntityHsbBuilding;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.tileentity.TileEntityUnlocker;
import hsb.upgrade.UpgradeRegistry;
import nwcore.core.handler.LocalizationHandler;
import nwcore.core.helper.NwLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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
 * @author Nuklearwurst
 *
 */

@Mod(	modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION_NUMBER,
		dependencies = Reference.DEPENDENCIES
		/*certificateFingerprint = Reference.FINGERPRINT*/) 

@NetworkMod(	clientSideRequired = true,
				serverSideRequired=false, 
				channels={Reference.CHANNEL_NAME},
				packetHandler = PacketHandler.class)
public class ModHsb{
	
	@Instance(Reference.MOD_ID)
	public static ModHsb instance;
	
	public static NwLog logger;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS) //TODO Bukkit
	public static CommonProxy proxy;
	
	//moved
//	@SidedProxy(clientSide = Reference.CLIENT_NETWORK_CLASS, serverSide = Reference.SERVER_NETWORK__CLASS) //TODO Bukkit
//	public static NetworkManager network_manager;
	
    @EventHandler
    public void invalidFingerprint(FMLFingerprintViolationEvent event) {
    	//TODO fingerprint
        //HsbLog.severe(Strings.INVALID_FINGERPRINT_MESSAGE);
    }
	
    @EventHandler
	void preInit(FMLPreInitializationEvent evt) {
		
		//key bindings
		//nothing yet
		
		//sounds
		//nothing yet
		
		//read Config
		Config.readConfig(evt);
		
		//init Logger
    	logger = new NwLog(Reference.MOD_ID, Settings.DEBUG);
    	//moved
//		HsbLog.init();
    	
		//Localization
		LocalizationHandler.loadLanguages(Reference.localeFiles);
		//TODO switch to vanilla language files
		
		//registerBlocks
		ModBlocks.init();
		//register Items
		ModItems.init();
	}
	
    @EventHandler
	void init(FMLInitializationEvent evt) {
		
		//register guihandler
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		//Register Events 
		//nothing yet
		
		//register Entities
		//nothing yet
		
		//register TileEntities
		GameRegistry.registerTileEntity(TileEntityHsbBuilding.class, Strings.TILE_ENTITY_HSB_BUILDING);
		GameRegistry.registerTileEntity(TileEntityHsbTerminal.class, Strings.TILE_ENTITY_HSB_TERMINAL);
		GameRegistry.registerTileEntity(TileEntityDoorBase.class, Strings.TILE_ENTITY_HSB_DOOR_BASE);
		GameRegistry.registerTileEntity(TileEntityGuiAccess.class, Strings.TILE_ENTITY_HSB_GUI_ACCESS);
		GameRegistry.registerTileEntity(TileEntityUnlocker.class, Strings.TILE_ENTITY_UNLOCKER);
		
		//init rendering
		proxy.initRendering();
		proxy.initSpecialRenderer();
		
		//check for Plugins
		PluginManager.initPlugins();
		
		//init Recipes
		HsbRecipes.initRecipes();
		
		//register upgrades
		UpgradeRegistry.initUpgrades();
	}
	
    @EventHandler
	void postInit(FMLPostInitializationEvent evt) {
		
		logger.info("Hsb Version: "+ Reference.VERSION_NUMBER + " loaded.");
	}

	


}
