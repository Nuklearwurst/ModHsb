package hsb.recipes;

import hsb.block.BlockHsb;
import hsb.block.BlockMachine;
import hsb.block.ModBlocks;
import hsb.configuration.Settings;
import hsb.core.addons.PluginIC2;
import hsb.core.helper.HsbLog;
import hsb.item.ItemUpgradeHsb;
import hsb.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class HsbRecipes {
	
	public static void initRecipes()
	{
		/////////////////////////
		// init normal recipes //
		/////////////////////////
		
		//shaped
		
		//Hsb Building Block
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockHsb, 8, BlockHsb.META_BUILDING ), new Object[] 
				{ 
					"sss", "srs","sss" ,
					Character.valueOf('s'), new ItemStack(ModBlocks.blockHeavyStone, 1, 0),
					Character.valueOf('r'), new ItemStack(Item.redstone, 1, 0)
				});
		//hsb gui access
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockHsb, 1, BlockHsb.META_GUI_ACCESS ), new Object[] 
				{ 
					" b ", " s "," b " ,
					Character.valueOf('s'), new ItemStack(ModBlocks.blockHsb, 1, BlockHsb.META_BUILDING),
					Character.valueOf('b'), new ItemStack(Block.blockRedstone, 1, 0)
				});
		//heavy Stone
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockHeavyStone, 8, 0), new Object[] 
				{ 
					"sss", "sis","sss" ,
					Character.valueOf('s'), new ItemStack(Block.stone, 1, 0),
					Character.valueOf('i'), new ItemStack(Block.blockIron, 1, 0)
				});
		//Empty Upgrade
		GameRegistry.addRecipe(new ItemStack(ModItems.itemUpgradeHsb, 8, ItemUpgradeHsb.metaEmpty), new Object[] 
				{ 
					"sss", "sis","sss" ,
					Character.valueOf('s'), new ItemStack(Block.stone, 1, 0),
					Character.valueOf('i'), new ItemStack(Block.blockRedstone, 1, 0)
				});
		//unshaped
		
		//tesla upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaTesla), new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaEmpty), new ItemStack(Block.blockRedstone));
		//camo upgrade
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaCamo), new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaEmpty), new ItemStack(Block.blockRedstone), new ItemStack(Block.blockClay));
		//password
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaPassword), new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaEmpty), new ItemStack(Item.ingotIron));
		//security
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaSecurity), new ItemStack(ModItems.itemUpgradeHsb, 1, ItemUpgradeHsb.metaEmpty), new ItemStack(Item.ingotGold));
		
		////////////////////////////////
		// init ic2 dependent recipes //
		////////////////////////////////
		if(Settings.ic2Available)
			initIC2Recipes();
		else 
			initVanillaRecipes();		
	}

	/**
	 * inits Vanilla Recipes, needs to provide a recipe for all recipes added in initIC2Recipes
	 */
	private static void initVanillaRecipes() 
	{
		//Hsb Terminal
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockHsb, 8, BlockHsb.META_TERMINAL), new Object[] 
				{ 
					"bhr",
					Character.valueOf('b'), new ItemStack(Block.blockRedstone, 1, 0),
					Character.valueOf('h'), new ItemStack(ModBlocks.blockHsb, 1, BlockHsb.META_BUILDING),
					Character.valueOf('r'), new ItemStack(Block.redstoneComparatorIdle, 1, 0),
				});
		//machine
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMachine, 1, BlockMachine.META_MACHINE), new Object[] 
				{ 
					"grg", "rir","grg" ,
					Character.valueOf('r'), new ItemStack(Item.redstone , 1, 0),
					Character.valueOf('g'), new ItemStack(Item.lightStoneDust, 1, 0),
					Character.valueOf('i'), new ItemStack(Block.blockIron, 1, 0)	
				});
		//unlocker
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMachine, 1, BlockMachine.META_UNLOCKER), new Object[] 
				{ 
					"shs", "rmr","sts" ,
					Character.valueOf('s'), new ItemStack(Block.stone, 1, 0),
					Character.valueOf('r'), new ItemStack(Item.redstone, 1, 0),
					Character.valueOf('h'), new ItemStack(ModItems.itemLockHacker, 1, 0),
					Character.valueOf('m'), new ItemStack(ModBlocks.blockMachine, 1, BlockMachine.META_MACHINE),
					Character.valueOf('t'), new ItemStack(ModBlocks.blockHsb, 1, BlockHsb.META_TERMINAL),
				});
	}
	/**
	 * inits IC2 Recipes, needs to provide a recipe for all recipes added in initVanillaRecipes
	 */
	private static void initIC2Recipes() {
		if(!PluginIC2.getInstance().isAvailable())
		{
			HsbLog.severe("Error: IC2 recipes could not be loaded!");
			return;
		}
		//Hsb Terminal
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockHsb, 8, BlockHsb.META_TERMINAL), new Object[] 
				{ 
					"bhr",
					Character.valueOf('b'), new ItemStack(Block.blockRedstone, 1, 0),
					Character.valueOf('h'), new ItemStack(ModBlocks.blockHsb, 1, BlockHsb.META_BUILDING),
					Character.valueOf('r'), PluginIC2.getIC2Item("electronicCircuit"),
				});
		//machine
		//using ic2 machineblock
		
		//unlocker
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockMachine, 1, BlockMachine.META_UNLOCKER), new Object[] 
				{ 
					"shs", "rmr","sts" ,
					Character.valueOf('s'), new ItemStack(Block.stone, 1, 0),
					Character.valueOf('r'), PluginIC2.getIC2Item("goldCableItem"),
					Character.valueOf('h'), new ItemStack(ModItems.itemLockHacker, 1, 0),
					Character.valueOf('m'), PluginIC2.getIC2Item("machine"),
					Character.valueOf('t'), new ItemStack(ModBlocks.blockHsb, 1, BlockHsb.META_TERMINAL),
				});
		
	}
}
