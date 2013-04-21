package hsb.core.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import hsb.configuration.Settings;
import hsb.lib.Reference;
import cpw.mods.fml.common.FMLLog;


public class HsbLog {

    private static Logger hsbLogger = Logger.getLogger(Reference.MOD_ID);

    public static void init() {

    	hsbLogger.setParent(FMLLog.getLogger());
    }

    public static void log(Level logLevel, String message) {

    	hsbLogger.log(logLevel, message);
    }
    
    public static void debug(String s) {
    	if(Settings.DEBUG) {
    		log(Level.INFO, s);
    	}
    }
    public static void severe(String s) {
		log(Level.SEVERE, s);
    }
    public static void info(String s) {
		log(Level.INFO, s);
    }
}
