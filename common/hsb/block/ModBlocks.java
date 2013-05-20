package hsb.block;

import cpw.mods.fml.common.registry.GameRegistry;
import hsb.item.ItemBlockHsb;
import hsb.item.ItemBlockMachine;
import hsb.lib.BlockIds;
import hsb.lib.Strings;
import net.minecraft.block.Block;

public class ModBlocks {
	
	public static Block blockHsb;
	public static Block blockHeavyStone;
	public static Block blockHsbDoor;
	public static Block blockMachine;
	
	public static void init() {
		
		blockHsb = new BlockHsb(BlockIds.HSB);
		blockHeavyStone = new BlockHeavyStone(BlockIds.HEAVY_STONE);
		blockHsbDoor = new BlockHsbDoor(BlockIds.HSB_DOOR);
		blockMachine = new BlockMachine(BlockIds.MACHINE);
		
		//register Blocks
		GameRegistry.registerBlock(blockHsb, ItemBlockHsb.class, Strings.BLOCK_HSB);
		GameRegistry.registerBlock(blockHeavyStone, Strings.BLOCK_HEAVY_STONE);
		GameRegistry.registerBlock(blockHsbDoor, Strings.BLOCK_HSB_DOOR);
		GameRegistry.registerBlock(blockMachine, ItemBlockMachine.class, Strings.BLOCK_MACHINE);
	}
}
