package hsb.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.api.upgrade.IHsbUpgrade;
import hsb.api.upgrade.IItemHsbUpgrade;
import hsb.config.Config;
import hsb.tileentitys.TileEntityLockTerminal;
import hsb.upgrades.UpgradeCamoflage;
import hsb.upgrades.UpgradeDummy;
import hsb.upgrades.UpgradePassword;
import hsb.upgrades.UpgradeSecurity;
import hsb.upgrades.UpgradeTesla;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemHsbUpgrade extends Item implements IItemHsbUpgrade{

	public static final String[] upgradeNames = new String[] {"Tesla", "Password", "Security", "Camoflage"};
    public static final int[] texture = new int[] {5, 6, 7, 8};//TODO textures
    
    public static final String ID_UPGRADE_CAMO = "Camoflage";
    public static final String ID_UPGRADE_TESLA = "tesla";
    public static final String ID_UPGRADE_PASSWORD = "password";
    public static final String ID_UPGRADE_SECURITY = "security";

    

    public ItemHsbUpgrade(int id)
    {
        super(id);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabHsb.tabHsb);
    }

    @Override
	@SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int damage)
    {
    	if(damage < texture.length)
    	{
    		return texture[damage];
    	}
        return 0;
    }

    @Override
	public String getItemNameIS(ItemStack stack)
    {
    	int damage = stack.getItemDamage();
    	if(damage < upgradeNames.length)
    	{
    		return upgradeNames[damage];
    	}
        return "hsbUpgrade";
    }

    @Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int itemId, CreativeTabs creativeTab, List list)
    {
    	//TODO SubItems for Upgrades
        for (int i = 0; i < upgradeNames.length; ++i)
        {
            list.add(new ItemStack(itemId, 1, i));
        }
    	
    }

    @Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
	}

	@Override
	public String getUniqueId(int meta) {
		return this.getUpgrade(meta).getUniqueId();
	}
	
	@Override
	public IHsbUpgrade getUpgrade(int meta) {
		switch(meta)
		{
		//Tesla
		case 0:
			return new UpgradeTesla();
		//Password length
		case 1:
			return new UpgradePassword();
		//Security Level Upgrade
		case 2:
			return new UpgradeSecurity();
		case 3:
			return new UpgradeCamoflage();
		default:
			return new UpgradeDummy();
		}
	}

	/**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     * 
     * Used to auto-insert upgrade into LockTerminal
     */
    @Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
		TileEntity te = world.getBlockTileEntity(x, y, z); 
		if(te instanceof TileEntityLockTerminal && (!((TileEntityLockTerminal) te).locked))
		{
			((TileEntityLockTerminal)te).addToInventory(itemstack, 1);
			//TODO sound
			Config.logDebug("Upgrade installed!");
			itemstack.stackSize--;
			return true;
		}
		return false;
    }


}
