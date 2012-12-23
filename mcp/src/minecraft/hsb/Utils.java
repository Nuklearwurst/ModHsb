package hsb;

import net.minecraft.entity.player.InventoryPlayer;

public class Utils {

	public static boolean removeFromInventory(int itemId, int itemDamage, InventoryPlayer inventory) {
		for(int i = 0; i < inventory.mainInventory.length; i++)
		{
			if(inventory.mainInventory[i]!=null && inventory.mainInventory[i].itemID == itemId && inventory.mainInventory[i].getItemDamage() == itemDamage)
			{
	            if (--inventory.mainInventory[i].stackSize <= 0)
	            {
	                inventory.mainInventory[i] = null;
	            }
	            return true;
			}
		}
		return false;	
	}
	

}
