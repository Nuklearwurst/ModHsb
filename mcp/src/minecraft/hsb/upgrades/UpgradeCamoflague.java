package hsb.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import hsb.api.IHsbUpgrade;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityLockTerminal;

public class UpgradeCamoflague implements IHsbUpgrade {

	public ItemStack itemSlot = null;
	
	@Override
	public void updateUpgrade(TileEntityLockTerminal te) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onButtonClicked(TileEntityLockTerminal te, EntityPlayer player,
			int button) {
		//TODO openGui
		if(te.renderAs == -1)
		{
			te.renderAs = 1;
		} else {
			te.renderAs = -1;
		}
		
	}

	@Override
	public String getButtonName() {		
		return "Camoflague";
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(HsbItems.itemHsbUpgrade, 1, 3);
	}

	@Override
	public String getUniqueId() {
		return "Camoflague";
	}

	@Override
	public void onTileSave(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
		//Items
		if (this.itemSlot != null)
        {
            NBTTagCompound nbttag = new NBTTagCompound();
            this.itemSlot.writeToNBT(nbttag);
            nbttagcompound.setTag("Items", nbttag);
        }		
	}

	@Override
	public void onTileLoad(NBTTagCompound nbttagcompound,
			TileEntityLockTerminal te) {
		//Items
		NBTTagCompound nbttag = (NBTTagCompound) nbttagcompound.getTag("Items");
		if(nbttag != null)
			this.itemSlot = ItemStack.loadItemStackFromNBT(nbttag);		
	}

	@Override
	public void onGuiOpen(TileEntityLockTerminal te) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

}
