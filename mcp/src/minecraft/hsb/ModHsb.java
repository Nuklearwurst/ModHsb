package hsb;

import hsb.config.Config;
import hsb.config.Defaults;
import hsb.gui.GuiHandler;
import hsb.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

/*
 * This Mod Part adds / plans to add:
 * 
 * block for building - done
 * door - in work (no Upgrades yet!)
 * fence - TODO add fence
 * block for control - done
 * 	--> Upgrade System - done
 * 		- 
 * block for interaction - TODO block to open gui behind
 * some upgrades
 * 	in progress:
 * 		Tesla TODO: Tesla onEntityColliding
 * 
 * 
 * ?? also for interaction
 * ???interaction cabeling/controlling
 * ?monitoring block
 * 
 * placer/removetool (electric) - done
 * adminbreaker ?
 * 
 * upgrades
 * 	tesla -in work
 * 
 */
/**
 * Main Mod Class
 * 
 * @author Jonas
 *
 */

@Mod(modid = "HSB", name = "High Security Blocks", version = ModHsb.version, useMetadata = true, dependencies = "after:IC2")//!!TODO Versioning Mod 
@NetworkMod(clientSideRequired = true, versionBounds = "0.1", serverSideRequired=false, 
channels={Defaults.NET_CHANNEL}, packetHandler = PacketHandler.class)
public class ModHsb{
	static final String version= "0.1";
	@PreInit
	void preInit(FMLPreInitializationEvent evt) {
		Config.preinit(evt);
	}
	
	@Init
	void init(FMLInitializationEvent evt) {
		Config.init(evt);
		proxy.initRendering();
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		proxy.initSpecialRenderer();
//		GameRegistry.registerWorldGenerator(new WorldGenZwergenfestung());
	}
	
	@PostInit
	void postInit(FMLPostInitializationEvent evt) {
		Config.initRecipes();
		Config.logInfo("Hsb Version: "+ version + " loaded.");
	}
	
	@Instance
	public static ModHsb instance;
	
	@SidedProxy(clientSide = "hsb.ClientProxy", serverSide = "hsb.CommonProxy") //TODO Bukkit
	public static CommonProxy proxy;

}
