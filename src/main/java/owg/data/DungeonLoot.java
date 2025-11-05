package owg.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class DungeonLoot
{
	public static Random rand;
	private static final List<WeightedLootEntry> lootTable = new ArrayList<WeightedLootEntry>();
	private static int totalWeight = 0;

	// A list for music discs to make them rare but possible
	private static final Item[] musicDiscs = {
			Items.record_13, Items.record_cat, Items.record_blocks, Items.record_chirp,
			Items.record_far, Items.record_mall, Items.record_mellohi, Items.record_stal,
			Items.record_strad, Items.record_ward, Items.record_11, Items.record_wait
	};

	private static class WeightedLootEntry {
		public final ItemStack item;
		public final int minCount;
		public final int maxCount;
		public final int weight;

		public WeightedLootEntry(ItemStack item, int min, int max, int weight) {
			this.item = item;
			this.minCount = min;
			this.maxCount = max;
			this.weight = weight;
		}

		public ItemStack generate(Random rand) {
			ItemStack stack = item.copy();
			if (maxCount > minCount) {
				stack.stackSize = rand.nextInt(maxCount - minCount + 1) + minCount;
			} else {
				stack.stackSize = minCount;
			}
			return stack;
		}
	}

	public static void init(long seed)
	{
		rand = new Random(seed);
		lootTable.clear();

		// Common Loot (Weight: 10)
		add(new ItemStack(Items.iron_ingot), 1, 4, 10);
		add(new ItemStack(Items.bread), 1, 1, 10);
		add(new ItemStack(Items.wheat), 1, 4, 10);
		add(new ItemStack(Items.gunpowder), 1, 4, 10);
		add(new ItemStack(Items.string), 1, 4, 10);
		add(new ItemStack(Items.bone), 1, 3, 10);
		add(new ItemStack(Items.rotten_flesh), 1, 3, 10);

		// Uncommon Loot (Weight: 5)
		add(new ItemStack(Items.bucket), 1, 1, 5);
		add(new ItemStack(Items.redstone), 1, 4, 5);
		add(new ItemStack(Items.saddle), 1, 1, 5);
		add(new ItemStack(Blocks.sponge), 1, 2, 5);
		add(new ItemStack(Items.slime_ball), 1, 4, 5);
		add(new ItemStack(Items.clay_ball), 1, 5, 5);
		add(new ItemStack(Items.gold_ingot), 1, 3, 5);
		add(new ItemStack(Items.arrow), 1, 5, 5);
		add(new ItemStack(Items.dye, 1, 4), 1, 3, 5); // Lapis

		// Rare Loot (Weight: 2)
		add(new ItemStack(Items.name_tag), 1, 1, 2);
		add(new ItemStack(Items.golden_apple), 1, 1, 2);
		add(new ItemStack(Items.experience_bottle), 1, 3, 2);
		// Special for all music discs
		lootTable.add(new WeightedLootEntry(new ItemStack(Items.record_13), 0, 0, 2)); // placeholder, will be replaced

		// Very Rare Loot (Weight: 1)
		add(new ItemStack(Items.emerald), 1, 1, 1);

		totalWeight = 0;
		for (WeightedLootEntry entry : lootTable) {
			totalWeight += entry.weight;
		}
	}

	private static void add(ItemStack item, int min, int max, int weight) {
		lootTable.add(new WeightedLootEntry(item, min, max, weight));
	}

	public static ItemStack pickItem()
	{
		if (rand.nextInt(4) == 0)
		{
			return ChestGenHooks.getOneItem(ChestGenHooks.DUNGEON_CHEST, rand);
		}
		else
		{
			int choice = rand.nextInt(totalWeight);

			for (WeightedLootEntry entry : lootTable)
			{
				choice -= entry.weight;
				if (choice < 0)
				{
					// Check for the music disc
					if (entry.item.getItem() == Items.record_13 && entry.minCount == 0) {
						Item disc = musicDiscs[rand.nextInt(musicDiscs.length)];
						return new ItemStack(disc, 1);
					}
					return entry.generate(rand);
				}
			}
			return null;
		}
	}
}
