package hsb.core.util;

import buildcraft.api.power.PowerHandler;

public class EnergyHelper {
	
	/**
	 * adds energy
	 * 
	 * @return returns energy used
	 */
	public static float addEnergy(float energyStored, float maxEnergyStored, float amount) {
		energyStored += amount;
		//to much energy?
		if(energyStored > maxEnergyStored) {
			//return used energy
			amount -= (energyStored - maxEnergyStored); //--> amount - leftover
			energyStored = maxEnergyStored;
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
