package hsb;

import org.lwjgl.opengl.GL11;

import hsb.config.HsbItems;
import hsb.tileentitys.TileEntityHsb;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class HsbBlockRenderer implements ISimpleBlockRenderingHandler {
	@Override
	public int getRenderId() {
		return ClientProxy.HSBRENDERER_ID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		Tessellator tes = Tessellator.instance;
		
        block.setBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        
        tes.startDrawingQuads();
        tes.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, metadata));
        tes.draw();

        tes.startDrawingQuads();
        tes.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, metadata));
        tes.draw();

        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, metadata));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, metadata));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, metadata));
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, metadata));
        tes.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		
		if(block == HsbItems.blockHsb)
		{
			TileEntity te = world.getBlockTileEntity(x, y, z);
		
			if(te instanceof TileEntityHsb && ((TileEntityHsb) te).getConnectedTerminal()!= null)
			{
				if( ((TileEntityHsb) te).getConnectedTerminal().getCamoBlockId() != -1 ) {
					//Render Camo-block
					int camoBlockId = ((TileEntityHsb) te).getConnectedTerminal().getCamoBlockId();
	//				int camoMeta = ((TileEntityHsb) te).getConnectedTerminal().getCamoMeta();
					Block camoBlock = Block.blocksList[camoBlockId];
					if(camoBlock != null)
					{
						renderer.renderBlockByRenderType(camoBlock, x, y, z);
						return true;
					}
//					renderer.renderStandardBlock(block, x, y, z);
				} else {
					//render normal Block
//					renderer.renderStandardBlock(block, x, y, z);
				}
			} else {
//				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}