package hsb;

import java.util.Random;
import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenZwergenfestung extends WorldGenerator implements IWorldGenerator
{
  public WorldGenZwergenfestung()
  {
    
  }
  @Override
public void generate(Random random, int chunkX, int chunkZ, World world,
		IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
	this.generate(world, random, chunkX, 64, chunkZ);
	
}
@Override
  public boolean generate(World world, Random rand, int x, int y, int z)
  {
     if(world.getBlockId(x, y, z)!= Block.grass.blockID || world.getBlockId(x, y + 1, z)!= 0)
     {
        return false;
     }
	  
	  
               int Stein = Block.stone.blockID;
				int brick = Block.stoneBrick.blockID;
				int schoenerbrick = Block.wood.blockID;
				int slab = Block.stoneSingleSlab.blockID;
				int doppelterSlab = Block.stoneDoubleSlab.blockID;
				int eisen = Block.blockSteel.blockID;
				int treppe = Block.dirt.blockID;
				int gold = Block.blockGold.blockID;
              
                //Weg vor der Festung
				 
					world.setBlockWithNotify(x, y, z + 8, brick);
					world.setBlockWithNotify(x, y, z + 9, brick);
					world.setBlockWithNotify(x, y, z + 10, brick);
				
					world.setBlockWithNotify(x+1, y, z + 8, brick);
					world.setBlockWithNotify(x+1, y, z + 9, brick);
					world.setBlockWithNotify(x+1, y, z + 10, brick);
					
					world.setBlockWithNotify(x+2, y, z + 8, brick);
					world.setBlockWithNotify(x+2, y, z + 9, brick);
					world.setBlockWithNotify(x+2, y, z + 10, brick);
					
					world.setBlockWithNotify(x+3, y, z + 8, brick);
					world.setBlockWithNotify(x+3, y, z + 9, brick);
					world.setBlockWithNotify(x+3, y,z + 10, brick);
					
					world.setBlockWithNotify(x+4, y, z + 8, brick);
					world.setBlockWithNotify(x+4, y, z + 9, brick);
					world.setBlockWithNotify(x+4, y, z + 10, brick);
					
				//Boden der Festung
				
					world.setBlockWithNotify(x+5, y, z + 8, brick);
					world.setBlockWithNotify(x+5, y, z + 9, brick);
					world.setBlockWithNotify(x+5, y, z + 10, brick);
					
					world.setBlockWithNotify(x+6, y, z + 8, brick);
					world.setBlockWithNotify(x+6, y, z + 9, brick);
					world.setBlockWithNotify(x+6, y, z + 10, brick);

					world.setBlockWithNotify(x+7, y, z + 8, brick);
					world.setBlockWithNotify(x+7, y, z + 9, brick);
					world.setBlockWithNotify(x+7, y, z + 10,brick);

					world.setBlockWithNotify(x+8, y, z + 7, brick);
					world.setBlockWithNotify(x+8, y, z + 8, brick);
					world.setBlockWithNotify(x+8, y, z + 9, brick);
					world.setBlockWithNotify(x+8, y, z + 10, brick);
					world.setBlockWithNotify(x+8, y, z + 11, brick);
					world.setBlockWithNotify(x+8, y, z + 12, brick);
					world.setBlockWithNotify(x+8, y, z + 13, brick);
					world.setBlockWithNotify(x+8, y, z + 14, brick);
			
					world.setBlockWithNotify(x+9, y, z + 7, brick);
					world.setBlockWithNotify(x+9, y, z + 8, brick);
					world.setBlockWithNotify(x+9, y, z + 9, brick);
					world.setBlockWithNotify(x+9, y, z + 10, brick);
					world.setBlockWithNotify(x+9, y, z + 11, brick);
					world.setBlockWithNotify(x+9, y, z + 12, brick);
					world.setBlockWithNotify(x+9, y, z + 13, brick);
					world.setBlockWithNotify(x+9, y, z + 14, brick);
				
					world.setBlockWithNotify(x+10, y, z + 7, brick);
					world.setBlockWithNotify(x+10, y, z + 8, brick);
					world.setBlockWithNotify(x+10, y, z + 9, brick);
					world.setBlockWithNotify(x+10, y, z + 10, brick);
					world.setBlockWithNotify(x+10, y, z + 11, brick);
					world.setBlockWithNotify(x+10, y, z + 12, brick);
					world.setBlockWithNotify(x+10, y, z + 13, brick);
					world.setBlockWithNotify(x+10, y, z + 14, brick);
					world.setBlockWithNotify(x+10, y, z + 15, brick);
					
					world.setBlockWithNotify(x+11, y, z + 7, brick);
					world.setBlockWithNotify(x+11, y, z + 8, brick);
					world.setBlockWithNotify(x+11, y, z + 9, brick);
					world.setBlockWithNotify(x+11, y, z + 10, brick);
					world.setBlockWithNotify(x+11, y, z + 11, brick);
					world.setBlockWithNotify(x+11, y, z + 12, brick);
					world.setBlockWithNotify(x+11, y, z + 13, brick);
					world.setBlockWithNotify(x+11, y, z + 14, brick);
					world.setBlockWithNotify(x+11, y, z + 15, brick);
					
					
					world.setBlockWithNotify(x+12, y, z + 5, brick);
					world.setBlockWithNotify(x+12, y, z + 6, brick);
					world.setBlockWithNotify(x+12, y, z + 7, brick);
					world.setBlockWithNotify(x+12, y, z + 8, brick);
					world.setBlockWithNotify(x+12, y, z + 9, gold);
					world.setBlockWithNotify(x+12, y, z + 10, brick);
					world.setBlockWithNotify(x+12, y, z + 11, brick);
					world.setBlockWithNotify(x+12, y, z + 12, brick);
					world.setBlockWithNotify(x+12, y, z + 13, brick);
					world.setBlockWithNotify(x+12, y, z + 14, brick);
					world.setBlockWithNotify(x+12, y, z + 15, brick);
					
					world.setBlockWithNotify(x+13, y, z + 5, brick);
					world.setBlockWithNotify(x+13, y, z + 6, brick);
					world.setBlockWithNotify(x+13, y, z + 7, brick);
					world.setBlockWithNotify(x+13, y, z + 8, brick);
					world.setBlockWithNotify(x+13, y, z + 9, brick);
					world.setBlockWithNotify(x+13, y, z + 10, brick);
					world.setBlockWithNotify(x+13, y, z + 11, brick);
					world.setBlockWithNotify(x+13, y, z + 12, brick);
					world.setBlockWithNotify(x+13, y, z + 13, brick);
					world.setBlockWithNotify(x+13, y, z + 14, brick);
					
					world.setBlockWithNotify(x+14, y, z + 5, brick);
					world.setBlockWithNotify(x+14, y, z + 6, brick);
					world.setBlockWithNotify(x+14, y, z + 7, brick);
					world.setBlockWithNotify(x+14, y, z + 8, brick);
					world.setBlockWithNotify(x+14, y, z + 9, brick);
					world.setBlockWithNotify(x+14, y, z + 10, brick);
					world.setBlockWithNotify(x+14, y, z + 11, brick);
					world.setBlockWithNotify(x+14, y, z + 12, brick);
					world.setBlockWithNotify(x+14, y, z + 13, brick);
					world.setBlockWithNotify(x+14, y, z + 14, brick);
					
					world.setBlockWithNotify(x+15, y, z + 5, brick);
					world.setBlockWithNotify(x+15, y, z + 6, brick);
					world.setBlockWithNotify(x+15, y, z + 7, brick);
					world.setBlockWithNotify(x+15, y, z + 8, brick);
					world.setBlockWithNotify(x+15, y, z + 9, brick);
					world.setBlockWithNotify(x+15, y, z + 10, brick);
					world.setBlockWithNotify(x+15, y, z + 11, brick);
					world.setBlockWithNotify(x+15, y, z + 12, brick);
					world.setBlockWithNotify(x+15, y, z + 13, brick);
					world.setBlockWithNotify(x+15, y, z + 14, brick);
					
					world.setBlockWithNotify(x+16, y, z + 5, brick);
					world.setBlockWithNotify(x+16, y, z + 6, brick);
					world.setBlockWithNotify(x+16, y, z + 7, brick);
					world.setBlockWithNotify(x+16, y, z + 8, brick);
					world.setBlockWithNotify(x+16, y, z + 9, brick);
					world.setBlockWithNotify(x+16, y, z + 10, brick);
					world.setBlockWithNotify(x+16, y, z + 11, brick);
					world.setBlockWithNotify(x+16, y, z + 12, brick);
					world.setBlockWithNotify(x+16, y, z + 13, brick);
					world.setBlockWithNotify(x+16, y, z + 14, brick);
					
				//Mauer Ebene 1
				
					world.setBlockWithNotify(x + 1, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 1, y + 1, z + 2, doppelterSlab);
					world.setBlockWithNotify(x + 1, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 1, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 2, y + 1, z + 0, doppelterSlab);
					world.setBlockWithNotify(x + 2, y + 1, z + 3, doppelterSlab);
					world.setBlockWithNotify(x + 2, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 2, y + 1, z + 18, doppelterSlab);
					
					world.setBlockWithNotify(x + 3, y + 1, z + 0, doppelterSlab);
					world.setBlockWithNotify(x + 3, y + 1, z + 3, doppelterSlab);
					world.setBlockWithNotify(x + 3, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 3, y + 1, z + 18, doppelterSlab);
				
					world.setBlockWithNotify(x + 4, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 2, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 3, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 5, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 6, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 8, eisen);
					world.setBlockWithNotify(x + 4, y + 1, z + 10, eisen);
					world.setBlockWithNotify(x + 4, y + 1, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 12, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 13, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 14, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 5, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 5, y + 1, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 5, y + 1, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 5, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 6, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 6, y + 1, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 6, y + 1, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 6, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 7, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 2, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 3, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 5, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 6, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 12, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 13, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 14, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 7, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 8, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 8, y + 1, z + 5, brick);
					world.setBlockWithNotify(x + 8, y + 1, z + 6, treppe);
					world.setBlockWithNotify(x + 8, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 8, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 8, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 9, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 9, y + 1, z + 5, brick);
					world.setBlockWithNotify(x + 9, y + 1, z + 6, brick);
					world.setBlockWithNotify(x + 9, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 9, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 10, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 10, y + 1, z + 5, brick);
					world.setBlockWithNotify(x + 10, y + 1, z + 6, brick);
					world.setBlockWithNotify(x + 10, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 10, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 11, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 11, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 11, y + 1, z + 5, doppelterSlab);
					world.setBlockWithNotify(x + 11, y + 1, z + 6, doppelterSlab);
					world.setBlockWithNotify(x + 11, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 11, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 12, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 12, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 12, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 12, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 13, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 13, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 13, y + 1, z + 15, treppe);
					world.setBlockWithNotify(x + 13, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 13, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 14, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 14, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 14, y + 1, z + 15, brick);
					world.setBlockWithNotify(x + 14, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 14, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 15, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 15, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 15, y + 1, z + 15, brick);
					world.setBlockWithNotify(x + 15, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 15, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 16, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 16, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 16, y + 1, z + 15, brick);
					world.setBlockWithNotify(x + 16, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 16, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 17, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 5, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 6, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 8, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 9, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 10, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 12, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 13, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 14, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 17, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 18, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 18, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 18, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 18, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 19, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 19, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 19, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 19, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 20, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 2, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 3, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 4, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 5, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 6, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 8, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 9, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 10, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 12, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 13, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 14, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 20, y + 1, z + 17, doppelterSlab);
					
					world.setBlockWithNotify(x + 21, y + 1, z + 0, doppelterSlab);
					world.setBlockWithNotify(x + 21, y + 1, z + 3, doppelterSlab);
					world.setBlockWithNotify(x + 21, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 21, y + 1, z + 18, doppelterSlab);
				
					world.setBlockWithNotify(x + 22, y + 1, z + 0, doppelterSlab);
					world.setBlockWithNotify(x + 22, y + 1, z + 3, doppelterSlab);
					world.setBlockWithNotify(x + 22, y + 1, z + 15, doppelterSlab);
					world.setBlockWithNotify(x + 22, y + 1, z + 18, doppelterSlab);
					
					world.setBlockWithNotify(x + 23, y + 1, z + 1, doppelterSlab);
					world.setBlockWithNotify(x + 23, y + 1, z + 2, doppelterSlab);
					world.setBlockWithNotify(x + 23, y + 1, z + 16, doppelterSlab);
					world.setBlockWithNotify(x + 23, y + 1, z + 17, doppelterSlab);
					
				//Mauer Ebene 2
				
					world.setBlockWithNotify(x + 1, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 1, y + 2, z + 2, Stein);
					world.setBlockWithNotify(x + 1, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 1, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 2, y + 2, z + 0, Stein);
					world.setBlockWithNotify(x + 2, y + 2, z + 3, Stein);
					world.setBlockWithNotify(x + 2, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 2, y + 2, z + 18, Stein);
					
					world.setBlockWithNotify(x + 3, y + 2, z + 0, Stein);
					world.setBlockWithNotify(x + 3, y + 2, z + 3, Stein);
					world.setBlockWithNotify(x + 3, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 3, y + 2, z + 18, Stein);
				
					world.setBlockWithNotify(x + 4, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 2, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 3, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 5, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 6, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 2, z + 8, eisen);
					world.setBlockWithNotify(x + 4, y + 2, z + 10, eisen);
					world.setBlockWithNotify(x + 4, y + 2, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 2, z + 12, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 13, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 14, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 4, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 5, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 5, y + 2, z + 7, Stein);
					world.setBlockWithNotify(x + 5, y + 2, z + 11, Stein);
					world.setBlockWithNotify(x + 5, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 6, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 6, y + 2, z + 7, Stein);
					world.setBlockWithNotify(x + 6, y + 2, z + 11, Stein);
					world.setBlockWithNotify(x + 6, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 7, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 2, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 3, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 5, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 6, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 7, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 11, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 12, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 13, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 14, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 7, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 8, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 8, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 8, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 8, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 8, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 9, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 9, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 9, y + 2, z + 5, treppe);
					world.setBlockWithNotify(x + 9, y + 2, z + 6, brick);
					world.setBlockWithNotify(x + 9, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 9, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 10, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 10, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 10, y + 2, z + 5, brick);
					world.setBlockWithNotify(x + 10, y + 2, z + 6, brick);
					world.setBlockWithNotify(x + 10, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 10, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 11, y + 2, z + 1, 	Stein);
					world.setBlockWithNotify(x + 11, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 11, y + 2, z + 5, Stein);
					world.setBlockWithNotify(x + 11, y + 2, z + 6, Stein);
					world.setBlockWithNotify(x + 11, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 11, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 12, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 12, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 12, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 12, y + 2, z + 17, Stein);
					 
					world.setBlockWithNotify(x + 13, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 13, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 13, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 13, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 14, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 14, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 14, y + 2, z + 15, treppe);
					world.setBlockWithNotify(x + 14, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 14, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 15, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 15, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 15, y + 2, z + 15, brick);
					world.setBlockWithNotify(x + 15, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 15, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 16, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 16, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 16, y + 2, z + 15, brick);
					world.setBlockWithNotify(x + 16, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 16, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 17, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 5, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 6, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 7, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 8, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 9, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 10, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 11, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 12, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 13, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 14, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 17, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 18, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 18, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 18, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 18, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 19, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 19, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 19, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 19, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 20, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 2, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 3, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 4, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 5, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 6, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 7, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 8, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 9, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 10, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 11, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 12, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 13, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 14, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 20, y + 2, z + 17, Stein);
					
					world.setBlockWithNotify(x + 21, y + 2, z + 0, Stein);
					world.setBlockWithNotify(x + 21, y + 2, z + 3, Stein);
					world.setBlockWithNotify(x + 21, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 21, y + 2, z + 18, Stein);
				
					world.setBlockWithNotify(x + 22, y + 2, z + 0, Stein);
					world.setBlockWithNotify(x + 22, y + 2, z + 3, Stein);
					world.setBlockWithNotify(x + 22, y + 2, z + 15, Stein);
					world.setBlockWithNotify(x + 22, y + 2, z + 18, Stein);
					
					world.setBlockWithNotify(x + 23, y + 2, z + 1, Stein);
					world.setBlockWithNotify(x + 23, y + 2, z + 2, Stein);
					world.setBlockWithNotify(x + 23, y + 2, z + 16, Stein);
					world.setBlockWithNotify(x + 23, y + 2, z + 17, Stein);
					
				//Mauer Ebene 3
				
					world.setBlockWithNotify(x + 1, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 1, y + 3, z + 2, schoenerbrick);
					world.setBlockWithNotify(x + 1, y + 3, z + 16, schoenerbrick);
					world.setBlockWithNotify(x + 1, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 2, y + 3, z + 0, schoenerbrick);
					world.setBlockWithNotify(x + 2, y + 3, z + 3, schoenerbrick);
					world.setBlockWithNotify(x + 2, y + 3, z + 15, schoenerbrick);
					world.setBlockWithNotify(x + 2, y + 3, z + 18, schoenerbrick);
					
					world.setBlockWithNotify(x + 3, y + 3, z + 0, schoenerbrick);
					world.setBlockWithNotify(x + 3, y + 3, z + 3, schoenerbrick);
					world.setBlockWithNotify(x + 3, y + 3, z + 15, schoenerbrick);
					world.setBlockWithNotify(x + 3, y + 3, z + 18, schoenerbrick);
				
					world.setBlockWithNotify(x + 4, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 2, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 3, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 4, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 5, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 6, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 7, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 3, z + 8, eisen);
					world.setBlockWithNotify(x + 4, y + 3, z + 9, eisen);
					world.setBlockWithNotify(x + 4, y + 3, z + 10, eisen);
					world.setBlockWithNotify(x + 4, y + 3, z + 11, doppelterSlab);
					world.setBlockWithNotify(x + 4, y + 3, z + 12, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 13, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 14, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 15, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 16, schoenerbrick);
					world.setBlockWithNotify(x + 4, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 5, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 5, y + 3, z + 7, Stein);
					world.setBlockWithNotify(x + 5, y + 3, z + 11, Stein);
					world.setBlockWithNotify(x + 5, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 6, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 6, y + 3, z + 7, Stein);
					world.setBlockWithNotify(x + 6, y + 3, z + 11, Stein);
					world.setBlockWithNotify(x + 6, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 7, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 7, y + 3, z + 2, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 3, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 5, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 6, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 7, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 11, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 12, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 13, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 14, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 15, Stein);
					world.setBlockWithNotify(x + 7, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 8, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 8, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 8, y + 3, z + 15, Stein);
					world.setBlockWithNotify(x + 8, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 8, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 9, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 9, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 9, y + 3, z + 6, brick);
					world.setBlockWithNotify(x + 9, y + 3, z + 15, Stein);
					world.setBlockWithNotify(x + 9, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 10, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 10, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 10, y + 3, z + 6, treppe);
					world.setBlockWithNotify(x + 10, y + 3, z + 6, brick);
					world.setBlockWithNotify(x + 10, y + 3, z + 15, Stein);
					world.setBlockWithNotify(x + 10, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 11, y + 3, z + 1, 	schoenerbrick);
					world.setBlockWithNotify(x + 11, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 11, y + 3, z + 5, Stein);
					world.setBlockWithNotify(x + 11, y + 3, z + 6, Stein);
					world.setBlockWithNotify(x + 11, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 11, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 12, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 12, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 12, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 12, y + 3, z + 17, schoenerbrick);
					 
					world.setBlockWithNotify(x + 13, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 13, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 13, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 13, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 14, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 14, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 14, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 14, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 15, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 15, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 15, y + 3, z + 15, treppe);
					world.setBlockWithNotify(x + 15, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 15, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 16, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 16, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 16, y + 3, z + 15, brick);
					world.setBlockWithNotify(x + 16, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 16, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 17, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 17, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 5, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 6, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 7, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 8, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 9, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 10, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 11, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 12, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 13, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 14, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 15, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 17, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 18, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 18, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 18, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 18, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 19, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 19, y + 3, z + 4, Stein);
					world.setBlockWithNotify(x + 19, y + 3, z + 16, Stein);
					world.setBlockWithNotify(x + 19, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 20, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 2, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 3, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 4, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 5, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 6, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 7, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 8, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 9, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 10, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 11, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 12, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 13, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 14, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 15, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 16, schoenerbrick);
					world.setBlockWithNotify(x + 20, y + 3, z + 17, schoenerbrick);
					
					world.setBlockWithNotify(x + 21, y + 3, z + 0, schoenerbrick);
					world.setBlockWithNotify(x + 21, y + 3, z + 3, schoenerbrick);
					world.setBlockWithNotify(x + 21, y + 3, z + 15, schoenerbrick);
					world.setBlockWithNotify(x + 21, y + 3, z + 18, schoenerbrick);
				
					world.setBlockWithNotify(x + 22, y + 3, z + 0, schoenerbrick);
					world.setBlockWithNotify(x + 22, y + 3, z + 3, schoenerbrick);
					world.setBlockWithNotify(x + 22, y + 3, z + 15, schoenerbrick);
					world.setBlockWithNotify(x + 22, y + 3, z + 18, schoenerbrick);
					
					world.setBlockWithNotify(x + 23, y + 3, z + 1, schoenerbrick);
					world.setBlockWithNotify(x + 23, y + 3, z + 2, schoenerbrick);
					world.setBlockWithNotify(x + 23, y + 3, z + 16, schoenerbrick);
					world.setBlockWithNotify(x + 23, y + 3, z + 17, schoenerbrick);
					
				//Wamd Ebene 4 Plattform 1
				
					//Turm
				
						world.setBlockWithNotify(x + 1, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 1, y + 4, z + 2, Stein);
						world.setBlockWithNotify(x + 1, y + 4, z + 16, Stein);
						world.setBlockWithNotify(x + 1, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 2, y + 4, z + 0, Stein);
						world.setBlockWithNotify(x + 2, y + 4, z + 3, Stein);
						world.setBlockWithNotify(x + 2, y + 4, z + 15, Stein);
						world.setBlockWithNotify(x + 2, y + 4, z + 18, Stein);
						
						world.setBlockWithNotify(x + 3, y + 4, z + 0, Stein);
						world.setBlockWithNotify(x + 3, y + 4, z + 3, Stein);
						world.setBlockWithNotify(x + 3, y + 4, z + 15, Stein);
						world.setBlockWithNotify(x + 3, y + 4, z + 18, Stein);
						
						world.setBlockWithNotify(x + 4, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 2, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 3, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 4, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 5, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 6, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 7, doppelterSlab);
						world.setBlockWithNotify(x + 4, y + 4, z + 8, doppelterSlab);
						world.setBlockWithNotify(x + 4, y + 4, z + 9, doppelterSlab);
						world.setBlockWithNotify(x + 4, y + 4, z + 10, doppelterSlab);
						world.setBlockWithNotify(x + 4, y + 4, z + 11, doppelterSlab);
						world.setBlockWithNotify(x + 4, y + 4, z + 12, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 13, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 14, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 15, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 16, Stein);
						world.setBlockWithNotify(x + 4, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 5, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 5, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 5, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 6, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 6, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 6, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 7, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 7, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 7, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 8, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 8, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 8, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 9, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 9, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 9, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 10, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 10, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 7, treppe);
						world.setBlockWithNotify(x + 10, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 10, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 11, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 11, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 11, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 12, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 12, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 12, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 13, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 13, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 13, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 14, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 14, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 14, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 15, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 15, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 15, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 16, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 16, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 15, treppe);
						world.setBlockWithNotify(x + 16, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 16, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 17, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 17, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 17, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 18, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 18, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 18, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 19, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 19, y + 4, z + 2, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 3, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 4, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 5, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 6, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 7, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 8, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 9, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 10, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 11, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 12, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 13, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 14, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 15, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 16, brick);
						world.setBlockWithNotify(x + 19, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 20, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 2, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 3, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 4, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 5, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 6, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 7, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 8, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 9, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 10, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 11, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 12, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 13, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 14, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 15, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 16, Stein);
						world.setBlockWithNotify(x + 20, y + 4, z + 17, Stein);
						
						world.setBlockWithNotify(x + 21, y + 4, z + 0, Stein);
						world.setBlockWithNotify(x + 21, y + 4, z + 3, Stein);
						world.setBlockWithNotify(x + 21, y + 4, z + 15, Stein);
						world.setBlockWithNotify(x + 21, y + 4, z + 18, Stein);
						
						world.setBlockWithNotify(x + 22, y + 4, z + 0, Stein);
						world.setBlockWithNotify(x + 22, y + 4, z + 3, Stein);
						world.setBlockWithNotify(x + 22, y + 4, z + 15, Stein);
						world.setBlockWithNotify(x + 22, y + 4, z + 18, Stein);
						
						world.setBlockWithNotify(x + 23, y + 4, z + 1, Stein);
						world.setBlockWithNotify(x + 23, y + 4, z + 2, Stein);
						world.setBlockWithNotify(x + 23, y + 4, z + 16, Stein);
						world.setBlockWithNotify(x + 23, y + 4, z + 17, Stein);
					
			// Wand Ebene 5
				
				world.setBlockWithNotify(x + 1, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 1, y + 5, z + 2, doppelterSlab);
				world.setBlockWithNotify(x + 1, y + 5, z + 16, doppelterSlab);
				world.setBlockWithNotify(x + 1, y + 5, z + 17, doppelterSlab);
						
				world.setBlockWithNotify(x + 2, y + 5, z + 0, doppelterSlab);
				world.setBlockWithNotify(x + 2, y + 5, z + 1, brick);
				world.setBlockWithNotify(x + 2, y + 5, z + 2, brick);
				world.setBlockWithNotify(x + 2, y + 5, z + 3, doppelterSlab);
				world.setBlockWithNotify(x + 2, y + 5, z + 15, doppelterSlab);
				world.setBlockWithNotify(x + 2, y + 5, z + 16, brick);
				world.setBlockWithNotify(x + 2, y + 5, z + 17, brick);
				world.setBlockWithNotify(x + 2, y + 5, z + 18, doppelterSlab);
						
				world.setBlockWithNotify(x + 3, y + 5, z + 0, doppelterSlab);
				world.setBlockWithNotify(x + 3, y + 5, z + 1, brick);
				world.setBlockWithNotify(x + 3, y + 5, z + 2, brick);
				world.setBlockWithNotify(x + 3, y + 5, z + 3, doppelterSlab);
				world.setBlockWithNotify(x + 3, y + 5, z + 15, doppelterSlab);
				world.setBlockWithNotify(x + 3, y + 5, z + 16, brick);
				world.setBlockWithNotify(x + 3, y + 5, z + 17, brick);
				world.setBlockWithNotify(x + 3, y + 5, z + 18, doppelterSlab);
				
				world.setBlockWithNotify(x + 4, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 2, brick);
				world.setBlockWithNotify(x + 4, y + 5, z + 3, brick);
				world.setBlockWithNotify(x + 4, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 6, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 7, slab);
				world.setBlockWithNotify(x + 4, y + 5, z + 8, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 9, gold);
				world.setBlockWithNotify(x + 4, y + 5, z + 10, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 11, slab);
				world.setBlockWithNotify(x + 4, y + 5, z + 12, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 16, doppelterSlab);
				world.setBlockWithNotify(x + 4, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 5, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 5, y + 5, z + 7, treppe);
				world.setBlockWithNotify(x + 5, y + 5, z + 8, brick);
				world.setBlockWithNotify(x + 5, y + 5, z + 9, brick);
				world.setBlockWithNotify(x + 5, y + 5, z + 1, treppe);
				world.setBlockWithNotify(x + 5, y + 5, z + 1, doppelterSlab);
				
				world.setBlockWithNotify(x + 6, y + 5, z + 7, treppe);
				world.setBlockWithNotify(x + 6, y + 5, z + 8, brick);
				world.setBlockWithNotify(x + 6, y + 5, z + 9, brick);
				world.setBlockWithNotify(x + 6, y + 5, z + 1, treppe);
				
				world.setBlockWithNotify(x + 7, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 5, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 6, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 7, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 8, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 9, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 10, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 11, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 12, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 13, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 7, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 8, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 8, y + 5, z + 6, brick);
				world.setBlockWithNotify(x + 8, y + 5, z + 7, brick);
				world.setBlockWithNotify(x + 8, y + 5, z + 8, brick);
				world.setBlockWithNotify(x + 8, y + 5, z + 9, treppe);
				world.setBlockWithNotify(x + 8, y + 5, z + 14, doppelterSlab);
						
				world.setBlockWithNotify(x + 9, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 9, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 9, y + 5, z + 6, brick);
				world.setBlockWithNotify(x + 9, y + 5, z + 7, brick);
				world.setBlockWithNotify(x + 9, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 9, y + 5, z + 17, doppelterSlab);
					
				world.setBlockWithNotify(x + 10, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 10, y + 5, z + 14, doppelterSlab);
				
				world.setBlockWithNotify(x + 11, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 11, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 11, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 11, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 13, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 13, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 13, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 13, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 14, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 14, y + 5, z + 14, doppelterSlab);
				
				world.setBlockWithNotify(x + 15, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 15, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 15, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 15, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 16, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 16, y + 5, z + 14, doppelterSlab);
				
				world.setBlockWithNotify(x + 17, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 5, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 6, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 7, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 9, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 11, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 12, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 13, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 17, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 18, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 18, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 19, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 2, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 4, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 6, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 8, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 10, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 12, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 14, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 16, doppelterSlab);
				world.setBlockWithNotify(x + 19, y + 5, z + 17, doppelterSlab);
				
				world.setBlockWithNotify(x + 20, y + 5, z + 0, doppelterSlab);
				world.setBlockWithNotify(x + 20, y + 5, z + 1, brick);
				world.setBlockWithNotify(x + 20, y + 5, z + 2, brick);
				world.setBlockWithNotify(x + 20, y + 5, z + 3, doppelterSlab);
				world.setBlockWithNotify(x + 20, y + 5, z + 15, doppelterSlab);
				world.setBlockWithNotify(x + 20, y + 5, z + 16, brick);
				world.setBlockWithNotify(x + 20, y + 5, z + 17, brick);
				world.setBlockWithNotify(x + 20, y + 5, z + 18, doppelterSlab);
						
				world.setBlockWithNotify(x + 21, y + 5, z + 0, doppelterSlab);
				world.setBlockWithNotify(x + 21, y + 5, z + 1, brick);
				world.setBlockWithNotify(x + 21, y + 5, z + 2, brick);
				world.setBlockWithNotify(x + 21, y + 5, z + 3, doppelterSlab);
				world.setBlockWithNotify(x + 21, y + 5, z + 15, doppelterSlab);
				world.setBlockWithNotify(x + 21, y + 5, z + 16, brick);
				world.setBlockWithNotify(x + 21, y + 5, z + 17, brick);
				world.setBlockWithNotify(x + 21, y + 5, z + 18, doppelterSlab);
				
				world.setBlockWithNotify(x + 22, y + 5, z + 1, doppelterSlab);
				world.setBlockWithNotify(x + 22, y + 5, z + 2, doppelterSlab);
				world.setBlockWithNotify(x + 22, y + 5, z + 16, doppelterSlab);
				world.setBlockWithNotify(x + 22, y + 5, z + 17, doppelterSlab);
			
			//Wand Ebene 6
				
				world.setBlockWithNotify(x + 1, y + 6, z + 16, Stein);	
				world.setBlockWithNotify(x + 1, y + 6, z + 17, Stein);
				
				world.setBlockWithNotify(x + 2, y + 6, z + 15, Stein);
				world.setBlockWithNotify(x + 2, y + 6, z + 18, Stein);
				
				world.setBlockWithNotify(x + 3, y + 6, z + 4, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 5, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 6, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 7, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 8, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 9, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 10, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 12, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 13, Stein);
				world.setBlockWithNotify(x + 3, y + 6, z + 14, Stein);
				
				world.setBlockWithNotify(x + 4, y + 6, z + 4, Stein);
				
				
				
				
				
               return true;
  }

 
}
