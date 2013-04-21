package hsb.item;

import hsb.lib.Strings;
import hsb.lib.Textures;
import hsb.upgrade.UpgradeRegistry;
import hsb.upgrade.machine.UpgradeEnergyStorage;
import hsb.upgrade.types.IMachineUpgradeItem;
import hsb.upgrade.types.IUpgradeHsbMachine;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHsbMachineUpgrade extends ItemSimple
	implements IMachineUpgradeItem
{

    public static final Icon[] texture = new Icon[Textures.ITEM_UPGRADE_HSB_MACHINE.length];//TODO textures
    
	public ItemHsbMachineUpgrade(int id) {
		super(id);
	}

	@Override
	public String getUniqueId(int meta) {
		switch(meta)
		{
		case 0:
			return UpgradeRegistry.ID_UPGRADE_STORAGE;
		default:
			return UpgradeRegistry.ID_UPGRADE_STORAGE;
		}
	}
	
    @Override
	public String getUnlocalizedName(ItemStack stack)
    {
    	int damage = stack.getItemDamage();
    	if(damage < Strings.ITEM_HSB_MACHINE_UPGRADES.length)
    	{
    		return "item." + Strings.ITEM_HSB_MACHINE_UPGRADES[damage];
    	}
        return "item.upgradeDummy";
    }

	@Override
	public IUpgradeHsbMachine getUpgrade(int meta) {
		switch(meta)
		{
		case 0:
			return new UpgradeEnergyStorage();
		default:
			return new UpgradeEnergyStorage();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister reg) {
    	int i = 0;
    	for(String s : Textures.ITEM_UPGRADE_HSB_MACHINE)
    	{
    		ItemHsbMachineUpgrade.texture[i++] = reg.registerIcon(s);
    	}
	}

}
