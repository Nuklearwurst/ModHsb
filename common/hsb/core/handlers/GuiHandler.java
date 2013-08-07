package hsb.core.handlers;

import hsb.client.gui.GuiBlockBuilding;
import hsb.client.gui.GuiCamoUpgrade;
import hsb.client.gui.GuiHsbTerminal;
import hsb.client.gui.GuiHsbTerminalOptions;
import hsb.client.gui.GuiMultiTool;
import hsb.client.gui.GuiUnlocker;
import hsb.inventory.ContainerCamoUpgrade;
import hsb.inventory.ContainerTerminal;
import hsb.inventory.ContainerTerminalOptions;
import hsb.inventory.ContainerUnlocker;
import hsb.lib.GuiIds;
import hsb.tileentity.TileEntityHsb;
import hsb.tileentity.TileEntityHsbTerminal;
import hsb.tileentity.TileEntityUnlocker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler 
	implements IGuiHandler
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
		case GuiIds.GUI_BLOCKBUILDING:
			return new GuiBlockBuilding((TileEntityHsb) te, player);
		case GuiIds.GUI_LOCKTERMINAL:
			te.onInventoryChanged();
			return new GuiHsbTerminal((TileEntityHsbTerminal) te,(Container)this.getServerGuiElement(ID, player, world, x, y, z), player);
		case GuiIds.GUI_LOCKTERMINAL_OPTIONS:
			return new GuiHsbTerminalOptions((TileEntityHsbTerminal) te, new ContainerTerminalOptions((TileEntityHsbTerminal) te, player), player);
		case GuiIds.GUI_MULTI_TOOL:
			return new GuiMultiTool(player.getCurrentEquippedItem(), x, y, z, world, player);
		case GuiIds.GUI_UPGRADE_CAMOFLAGE:
			return new GuiCamoUpgrade((TileEntityHsbTerminal) te, new ContainerCamoUpgrade((TileEntityHsbTerminal) te, player), player);
		case GuiIds.GUI_UNLOCKER:
			return new GuiUnlocker((TileEntityUnlocker) te, new ContainerUnlocker((TileEntityUnlocker) te, player), player);
		default:
			return null;
				
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
		case GuiIds.GUI_BLOCKBUILDING:
			return null;
		case GuiIds.GUI_LOCKTERMINAL:
			return new ContainerTerminal((TileEntityHsbTerminal) te, player);
		case GuiIds.GUI_LOCKTERMINAL_OPTIONS:
			return new ContainerTerminalOptions((TileEntityHsbTerminal) te, player);
		case GuiIds.GUI_MULTI_TOOL:
			return null;
		case GuiIds.GUI_UPGRADE_CAMOFLAGE:
			return new ContainerCamoUpgrade((TileEntityHsbTerminal) te, player);
		case GuiIds.GUI_UNLOCKER:
			return new ContainerUnlocker((TileEntityUnlocker) te, player);
		default:
			return null;
		}
				

	}

}
