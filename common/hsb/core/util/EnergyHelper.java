package hsb.core.util;

import buildcraft.api.power.PowerHandler;

public class EnergyHelper {
	
	/**
	 * adds energy without loss
	 */
	public static float addEnergy(PowerHandler power, float amount, boolean addEnergy) {
		float stored = power.getEnergyStored();
		stored += amount;
		//to much energy?
		if(stored > power.getMaxEnergyStored()) {
			//return used energy
			amount -= (stored - power.getMaxEnergyStored()); //--> amount - leftover
			stored = power.getMaxEnergyStored();
		}
		//add energy?
		if(addEnergy) {
			power.setEnergy(stored);
		}
		return amount;
	}
	
	/**
	 * extracts energy without loss
	 */
	public static float useEnergy(PowerHandler power, float min, float max, boolean doUse) {
		float result = 0;
		if (power.getEnergyStored() >= min) {
			if (power.getEnergyStored() <= max) {
				result = power.getEnergyStored();
				if (doUse) {
					power.setEnergy(0);
				}
			} else {
				result = max;
				if (doUse) {
					power.setEnergy(power.getEnergyStored() - max);
				}
			}
		}
		return result;
	}

}
