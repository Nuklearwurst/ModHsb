package hsb.item;


import hsb.ModHsb;
import hsb.creativetab.CreativeTabHsb;
import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.terminal.UpgradeCamoflage;
import hsb.upgrade.terminal.UpgradeDummy;
import hsb.upgrade.terminal.UpgradePassword;
import hsb.upgrade.terminal.UpgradeSecurity;
import hsb.upgrade.terminal.UpgradeTesla;
import hsb.upgrade.types.ITerminalUpgradeItem;
import hsb.upgrade.types.IUpgradeHsbTerminal;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUpgradeHsb extends ItemSimple
	implements ITerminalUpgradeItem
{
    public static final Icon[] texture = new Icon[Textures.ITEM_UPGRADE_HSB.length];
    
    public static final int metaTesla = 0;
    public static final int metaPassword = 1;
    public static final int metaSecurity = 2;
    public static final int metaCamo = 3;
    public static final int metaDummy = 4;
    public static final int metaEmpty = 5;
    

    public ItemUpgradeHsb(int id)
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
    public Icon getIconFromDamage(int damage)
    {
    	if(damage < texture.length)
    	{
    		return texture[damage];
    	}
        return texture[0];
    }

    @Override
	public String getUnlocalizedName(ItemStack stack)
    {
    	int damage = stack.getItemDamage();
    	if(damage < Strings.ITEM_HSB_UPGRADES.length)
    	{
    		return "item." + Strings.ITEM_HSB_UPGRADES[damage];
    	}
        return "item.upgradeDummy";
    }

    @Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int itemId, CreativeTabs creativeTab, List list)
    {
        for (int i = 0; i < Strings.ITEM_HSB_UPGRADES.length; ++i)
        {
        	if(i == metaDummy)
        		continue;
            list.add(new ItemStack(itemId, 1, i));
        }	
    }
	@Override
	public String getUniqueId(int meta) {
		switch(meta) {
			case 0:
				return UpgradeRegistry.ID_UPGRADE_TESLA;
			case 1:
				return UpgradeRegistry.ID_UPGRADE_PASSWORD;
			case 2:
				return UpgradeRegistry.ID_UPGRADE_SECURITY;
			case 3:
				return UpgradeRegistry.ID_UPGRADE_CAMO;
			case 4:
				return UpgradeRegistry.ID_UPGRADE_DUMMY;
			case 5:
				return UpgradeRegistry.ID_UPGRADE_DUMMY;
			default:
				return UpgradeRegistry.ID_UPGRADE_DUMMY;
		}
	}
	
	@Override
	public IUpgradeHsbTerminal getUpgrade(int meta) {
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
		case 4:
			return new UpgradeDummy();
		case 5:
			return new UpgradeDummy();
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
		if(te instanceof TileEntityHsbTerminal && (!((TileEntityHsbTerminal) te).locked))
		{
			if(!world.isRemote)
			{
				((TileEntityHsbTerminal)te).addToInventory(itemstack, 1);
				//TODO sound
				ModHsb.logger.debug("Upgrade installed!");
//				itemstack.stackSize--;
			}
			return true;
		}
		return false;
    }
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister reg)
    {
    	int i = 0;
    	for(String s : Textures.ITEM_UPGRADE_HSB)
    	{
    		ItemUpgradeHsb.texture[i++] = reg.registerIcon(s);
    	}
    	UpgradeRegistry.initUpgradeIcons(reg);
    }


}
