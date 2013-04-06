package hsb.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import hsb.CommonProxy;
import hsb.CreativeTabHsb;
import hsb.HsbInfo;
import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityHsb;
import hsb.tileentitys.TileEntityLockTerminal;

public class ItemDebugTool extends Item {

	public ItemDebugTool(int id) {
		super(id);
		 this.setCreativeTab(CreativeTabHsb.tabHsb);
		 
	}
    @Override
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
    				upgrades = upgrades + ";"/* + ter.buttonNumber[i]*/;
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
    			player.sendChatToPlayer("upgrade list: " + upgrades + " \nupgradeCount: " + count + " \nactive: " + active + " \nIC2 upgrades (maxInput, transformer, extraenergy, energy, overclock) : " + ter.maxInput + ", " + ter.transformerUpgrades + ", " + ter.extraStorage + ", " + ter.storageUpgrades + ", " + ter.overclockerUpgrades);
    		}else{
    			player.sendChatToPlayer("Port: " + String.valueOf(((TileEntityHsb)te).getPort()) + " Pass: |" + ((TileEntityHsb)te).getPass() + "|\nFacing: " + String.valueOf(((TileEntityHsb) te).getFacing()) + " Textur der Side " + String.valueOf(side) + " ist: " + String.valueOf(HsbItems.blockHsb.getBlockTexture(world, x, y, z, side)) + "\nLocked: " + ((TileEntityHsb) te).locked);
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
    public void updateIcons(IconRegister reg)
    {
    	this.iconIndex = reg.registerIcon(HsbInfo.modId.toLowerCase() + ":" + "debugTool");
    }

	
}
