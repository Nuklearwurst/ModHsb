package hsb;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Material;
import hsb.config.Config;
import hsb.config.Defaults;
import hsb.config.Items;
import hsb.gui.GuiHandler;
import hsb.network.PacketHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Block;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Item;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/*
 * This Mod Part adds / plans to add:
 * 
 * block for building - done
 * door - (no Upgrades yet!)
 * fence - TODO
 * block for control - done
 * 	--> Upgrade System
 * 		- 
 * block for interaction - TODO
 * some upgrades
 * 	in progress:
 * 		Tesla
 * 
 * some light versions of the fence - TODO
 * 
 * ?? also for interaction
 * ???interaction cabeling/controlling
 * ?monitoring block
 * 
 * placer/removetool (electric) - done
 * adminbreaker ?
 * 
 * upgrades
 * 	tesla
 * Added:
 * DebugTool
 * 
 */
/**
 * Main Mod Class
 * 
 * @author Jonas
 *
 */
@Mod(modid = "Hsb|Core", name = "Hsb Core", version = "0.1")
@NetworkMod(clientSideRequired = true, versionBounds = "0.1", serverSideRequired=false, 
channels={Defaults.NET_CHANNEL}, packetHandler = PacketHandler.class)
public class ModHsbCore {
	public static Side side;
	
	@PreInit
	void preInit(FMLPreInitializationEvent evt) {
		Config.preinit(evt);
	}
	
	@Init
	void init(FMLInitializationEvent evt) {
		Config.init(evt);
		proxy.initRendering();
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		side = FMLCommonHandler.instance().getEffectiveSide();
	}
	
	@PostInit
	void postInit(FMLPostInitializationEvent evt) {
		side = FMLCommonHandler.instance().getEffectiveSide();
		System.out.println("Hsb Version: 0.1 loaded.");
	}
	
	@Instance
	public static ModHsbCore instance;
	
	@SidedProxy(clientSide = "hsb.ClientProxy", serverSide = "hsb.CommonProxy") //TODO Bukkit
	public static CommonProxy proxy;

}
