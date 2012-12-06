package hsb;

import hsb.config.Items;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemDebugTool extends Item {

	public ItemDebugTool(int id) {
		super(id);
		 this.setCreativeTab(CreativeTabs.tabTools);
		 this.setIconIndex(1);//TODO
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.TEXTURE_ITEMS;
		
	}
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
    {
    	if(player == null)
    		return false;
    	TileEntity te = world.getBlockTileEntity(x, y, z); 
    	player.sendChatToPlayer("TileEntity:" + te + "\n");
    	if(te instanceof TileEntityHsb)
    	{
    		if(player.isSneaking() && te instanceof TileEntityLockTerminal) {
    			TileEntityLockTerminal ter = (TileEntityLockTerminal) te; 
    			String upgrades = "";
    			for(int i = 0; i<10;i++)
    			{
    				upgrades = upgrades + ";" + ter.getUpgrade(i);
    			}
    			String inv = "";
    			for(int i = 0; i<10;i++)
    			{
    				inv = inv + ";" + ter.getStackInSlot(i);
    			}
    			String count = "";
    			for(int i = 0; i<10;i++)
    			{
    				count = count + ";" + ter.upgradeCount[i];
    			}
    			String active = "";
    			for(int i = 0; i<10;i++)
    			{
    				active = active + ";" + ter.upgradeActive[i];
    			}
    			player.sendChatToPlayer("upgrade list: " + upgrades + " \ninventory: " + inv + " \nupgradeCount: " + count + " \nactive: " + active + "\ninit: " + ter.init + " \nIC2 upgrades (maxInput, transformer, extraenergy, energy, overclock) : " + ter.maxInput + ", " + ter.transformerUpgrades + ", " + ter.extraStorage + ", " + ter.storageUpgrades + ", " + ter.overclockerUpgrades);
    		}else{
    			player.sendChatToPlayer("Port: " + String.valueOf(((TileEntityHsb)te).port) + " Pass: |" + ((TileEntityHsb)te).pass + "|\nFacing: " + String.valueOf(((TileEntityHsb) te).getFacing()) + " Textur der Side " + String.valueOf(side) + " ist: " + String.valueOf(Items.blockHsb.getBlockTexture(world, x, y, z, side)) + "\nLocked: " + ((TileEntityHsb) te).locked);
    		}
    		
    		return true;
    	}
        return false;
    }

	
}
