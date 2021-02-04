package me.BoyJamal.astaria.enchants.hoe;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class Lucky extends Enchant {

	public Lucky(String name, int maxLevel, int cost, boolean expodential, String type, String tier, String string) {
		super(name, maxLevel, cost, expodential, type, tier, string);
	}
	
	public static Integer getLuckyNumber(ItemStack item)
	{
		
		Random r = new Random();
		int val = r.nextInt(10)+1;
		if (val > 4)
		{
			return 0;
		}
		
		Enchant lucky = MainUtils.getEnchant("lucky");
		if (lucky == null)
		{
			return 0;
		}
		
		
		if (ItemUtil.containsEnchant(lucky, item))
		{
			int level = ItemUtil.getEnchantLevel(lucky, item);
			return level/4;
		} else {
			return 0;
		}
	}
}
