package me.BoyJamal.astaria.enchants.hoe;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.EnchantHandler;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.enchants.EnchantHandler.KeyChance;
import me.BoyJamal.astaria.enchants.EnchantHandler.SpawnerChance;
import me.BoyJamal.astaria.utils.MainUtils;

public class Miscreation extends Enchant {

	public Miscreation(String name, int maxLevel, int cost, boolean expodential, String type, String tier, String string) {
		super(name, maxLevel, cost, expodential, type, tier, string);
	}
	
	public static void runSpawnerChance(ItemStack inHand, Player p)
	{
		Random r = new Random();
		int val = r.nextInt(100)+1;
		if (val > 3)
		{
			return;
		}
		
		Enchant keyfinder = MainUtils.getEnchant("miscreation");
		if (keyfinder == null)
		{
			return;
		}
		
		if (ItemUtil.containsEnchant(keyfinder, inHand))
		{
			int level = ItemUtil.getEnchantLevel(keyfinder, inHand);
			EnchantHandler handler = new EnchantHandler();
			
			SpawnerChance run = handler.new SpawnerChance(level,p,keyfinder);
			run.run();
		} else {
			return;
		}
	}
	
}
