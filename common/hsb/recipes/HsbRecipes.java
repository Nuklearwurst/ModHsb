package hsb.recipes;

import hsb.configuration.Settings;
import ic2.api.Ic2Recipes;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class HsbRecipes {
	
	public static void initRecipes()
	{
		//init ic2 dependent recipes
		if(Settings.ic2Available)
			initIC2Recipes();
		else
			initVanillaRecipes();
		//init recipes
		//TODO recipes
		
	}
	
	private static void initIC2Recipes()
	{
		
	}
	
	private static void initVanillaRecipes() 
	{
		
	}
	
	/**
	 * shaped recipe
	 * @param result
	 * @param args
	 */
	private static void addCraftingRecipe(ItemStack result, Object... args) {
		if(Settings.ic2Available) {
			Ic2Recipes.addCraftingRecipe(result, args);
		} else {
			GameRegistry.addShapedRecipe(result, args);
		}
	}
	
	/**
	 * shapeless recipe
	 * @param result
	 * @param args
	 */
	private static void addShapelessCraftingRecipe(ItemStack result, Object... args) {
		if(Settings.ic2Available) {
			Ic2Recipes.addShapelessCraftingRecipe(result, args);
		} else {
			GameRegistry.addShapelessRecipe(result, args);
		}
	}

}
