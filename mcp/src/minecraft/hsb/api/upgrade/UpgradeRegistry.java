package hsb.api.upgrade;


import java.util.HashMap;
import java.util.Map;

/**
 * unused
 * @author Jonas
 *
 */
public class UpgradeRegistry {

	static public Map<String, IHsbUpgrade> upgrades = new HashMap<String, IHsbUpgrade>();
	
	static public boolean addUpgrade(IHsbUpgrade upgrade) {
		upgrades.put(upgrade.getUniqueId(), upgrade);
		
		return true;
		
	}
}
