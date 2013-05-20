package hsb.lib;

public class Reference {

    /* General Mod related constants */
    public static final String MOD_ID = "HSB";
    public static final String MOD_NAME = "High Security Blocks";
    public static final String VERSION_NUMBER = "@VERSION@ (build @BUILD_NUMBER@)";
    public static final String CHANNEL_NAME = MOD_ID;
    public static final String DEPENDENCIES = "after:ic2";
    public static final String FINGERPRINT = "@FINGERPRINT@";
    
    public static final int SECOND_IN_TICKS = 20;
    public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
    
    public static final String SERVER_PROXY_CLASS = "hsb.core.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "hsb.core.proxy.ClientProxy";
    
    public static final String SERVER_NETWORK__CLASS = "hsb.network.NetworkManager";
    public static final String CLIENT_NETWORK_CLASS = "hsb.network.NetworkManagerClient";
    
    public static final String LANG_RESOURCE_LOCATION = "/mods/hsb/lang/";
    public static String[] localeFiles = { LANG_RESOURCE_LOCATION + "en_US.xml"};
    public static final String DEFAULT_LANGUAGE = "en_US";
    //public static final int VERSION_CHECK_ATTEMPTS = 3;

}
