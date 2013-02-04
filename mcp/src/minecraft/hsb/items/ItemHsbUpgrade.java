package hsb.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hsb.ClientProxy;
import hsb.CreativeTabHsb;
import hsb.api.IHsbUpgrade;
import hsb.api.IItemHsbUpgrade;
import hsb.config.Config;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityLockTerminal;
import hsb.upgrades.UpgradeCamoflage;
import hsb.upgrades.UpgradeDummy;
import hsb.upgrades.UpgradePassword;
import hsb.upgrades.UpgradeSecurity;
import hsb.upgrades.UpgradeTesla;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemHsbUpgrade extends Item implements IItemHsbUpgrade{

	public static final String[] upgradeNames = new String[] {"Tesla", "Password", "Security", "Camoflage"};
    public static final int[] texture = new int[] {5, 6, 7, 8};//TODO textures

    public ItemHsbUpgrade(int id)
    {
        super(id);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabHsb.tabHsb);
    }

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

    public String getItemNameIS(ItemStack stack)
    {
    	int damage = stack.getItemDamage();
    	if(damage < upgradeNames.length)
    	{
    		return upgradeNames[damage];
    	}
        return "hsbUpgrade";
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     * 
     * Used to auto-insert upgrade into LockTerminal
     */
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
	
	@Override
	public String getTextureFile() {
		return ClientProxy.TEXTURE_ITEMS;
	}


}
