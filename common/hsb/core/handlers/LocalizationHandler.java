package hsb.core.handlers;

import hsb.lib.Localizations;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class LocalizationHandler {

    /***
     * Loads in all the localization files from the Localizations library class
     */
    public static void loadLanguages() {

        // For every file specified in the Localization library class, load them into the Language Registry
        for (String localizationFile : Localizations.localeFiles) {
            LanguageRegistry.instance().loadLocalization(localizationFile, getLocaleFromFileName(localizationFile), isXMLLanguageFile(localizationFile));
        }
    }
    
    /***
     * Simple test to determine if a specified file name represents a XML file
     * or not
     * 
     * @param fileName
     *            String representing the file name of the file in question
     * @return True if the file name represents a XML file, false otherwise
     */
    private static boolean isXMLLanguageFile(String fileName) {

        return fileName.endsWith(".xml");
    }

    /***
     * Returns the locale from file name
     * 
     * @param fileName
     *            String representing the file name of the file in question
     * @return String representation of the locale snipped from the file name
     */
    private static String getLocaleFromFileName(String fileName) {

        return fileName.substring(fileName.lastIndexOf('/') + 1, fileName.lastIndexOf('.'));
    }

    public static String getLocalizedString(String key) {

        return LanguageRegistry.instance().getStringLocalization(key);
    }
}
