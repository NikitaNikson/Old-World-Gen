// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package owg.deco;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

// Referenced classes of package net.minecraft.src:
//            WorldGenerator, World, Block

public class OldGenLiquids extends WorldGenerator
{
    private Block liquidBlockId;

    public OldGenLiquids(Block i)
    {
        liquidBlockId = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        if(world.getBlock(i, j + 1, k) != Blocks.stone)
        {
            return false;
        }
        if(world.getBlock(i, j - 1, k) != Blocks.stone)
        {
            return false;
        }
        if(world.getBlock(i, j, k) != Blocks.air && world.getBlock(i, j, k) != Blocks.stone)
        {
            return false;
        }
        int l = 0;
        if(world.getBlock(i - 1, j, k) == Blocks.stone)
        {
            l++;
        }
        if(world.getBlock(i + 1, j, k) == Blocks.stone)
        {
            l++;
        }
        if(world.getBlock(i, j, k - 1) == Blocks.stone)
        {
            l++;
        }
        if(world.getBlock(i, j, k + 1) == Blocks.stone)
        {
            l++;
        }
        int i1 = 0;
        if(world.isAirBlock(i - 1, j, k))
        {
            i1++;
        }
        if(world.isAirBlock(i + 1, j, k))
        {
            i1++;
        }
        if(world.isAirBlock(i, j, k - 1))
        {
            i1++;
        }
        if(world.isAirBlock(i, j, k + 1))
        {
            i1++;
        }
        if(l == 3 && i1 == 1)
        {
            world.setBlock(i, j, k, liquidBlockId);

            // Schedule a block update to make the liquid flow.
            // This is the proper way to ensure liquids start flowing after world generation.
            world.scheduleBlockUpdate(i, j, k, liquidBlockId, random.nextInt(4) + 2);
        }
        return true;
    }
}
