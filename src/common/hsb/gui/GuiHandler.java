package hsb.gui;

import hsb.ContainerLockTerminal;
import hsb.ContainerLockTerminalOptions;
import hsb.TileEntityLockTerminal;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler 
	implements IGuiHandler
{
	//Gui Ids:
	public static final int GUI_BLOCKPLACER = 1;
	public static final int GUI_LOCKTERMINAL = 2;
	public static final int GUI_BLOCKBUILDING = 3;
	public static final int GUI_LOCKTERMINAL_OPTIONS = 4;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch(ID)
		{
		case GUI_BLOCKPLACER:
			return null;
		case GUI_LOCKTERMINAL:
			return new ContainerLockTerminal((TileEntityLockTerminal) world.getBlockTileEntity(x, y, z), player);
		case GUI_LOCKTERMINAL_OPTIONS :
			return new ContainerLockTerminal((TileEntityLockTerminal) world.getBlockTileEntity(x, y, z), player);
		default:
			return null;
		}
				

	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch(ID)
		{
		case GUI_BLOCKPLACER:
			return new GuiBlockPlacer(player.getCurrentEquippedItem(), x, y, z, world, player);
		case GUI_LOCKTERMINAL:
		{
			TileEntityLockTerminal te = (TileEntityLockTerminal) world.getBlockTileEntity(x, y, z);
			return new GuiLockTerminal(te , new ContainerLockTerminal(te, player), player);
		}
		case GUI_LOCKTERMINAL_OPTIONS:
		{
			TileEntityLockTerminal te = (TileEntityLockTerminal) world.getBlockTileEntity(x, y, z);
			return new GuiLockTerminalOptions(te, new ContainerLockTerminalOptions(te, player), player);
		}
		case GUI_BLOCKBUILDING:
			return new GuiBlockPlacer(world.getBlockTileEntity(x, y, z), x, y, z, world, player);
		default:
			return null;
				
		}
	}

}