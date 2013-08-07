package hsb.core.util;

import hsb.configuration.Settings;
import hsb.core.plugin.ic2.PluginIC2;
import hsb.item.ItemHsbMultiTool;
import ic2.api.tile.IWrenchable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineUtils {

	/**
	 * can be called to react to wrenches etc. in onBlockActivated
	 * 
	 * @return false if onBlockActivated should return false. </br>
	 *  true if normal onBlockActivated Code should be used.
	 */
	public static boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer player, int side, float m, float n, float o) {
		//Items
    	if(player.getCurrentEquippedItem()!=null) {
    		//ic2 wrench
    		if(Settings.usePluginIC2)
    			if(player.getCurrentEquippedItem().itemID == PluginIC2.getIC2ItemId("wrench") || player.getCurrentEquippedItem().itemID == PluginIC2.getIC2ItemId("electricWrench"))
    				return false;
    		//hsb wrench
    		if(player.getCurrentEquippedItem().getItem() instanceof ItemHsbMultiTool)
    			return false;
    		//TODO BC and other wrenches
    	}
		return true;
	}
	
	/**
	 * rotates the Block when placed
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param player
	 * @param stack
	 */
	public static void onMachinePlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {

    	TileEntity te = world.getBlockTileEntity(x, y, z);
		//rotate block
        if (player != null && te instanceof IWrenchable) 
        {
            IWrenchable wrenchable = (IWrenchable)te;
            int rotationSegment = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
            if (player.rotationPitch >= 65) 
            {
                wrenchable.setFacing((short)1);
            } 
            else if (player.rotationPitch <= -65) 
            {
                wrenchable.setFacing((short)0);
            } 
            else 
            {
                switch (rotationSegment) 
                {
                case 0: wrenchable.setFacing((short)2); break;
                case 1: wrenchable.setFacing((short)5); break;
                case 2: wrenchable.setFacing((short)3); break;
                case 3: wrenchable.setFacing((short)4); break;
                default:
                    wrenchable.setFacing((short)0); break;
                }
            }
        }
	}
}
