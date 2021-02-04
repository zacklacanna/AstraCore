package me.BoyJamal.astaria.enchants.pickaxe;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.EnchantHandler;
import me.BoyJamal.astaria.enchants.EnchantHandler.KeyChance;
import me.BoyJamal.astaria.enchants.EnchantHandler.SpawnerChance;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class KeyFinderListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("keyfinder");
		
		if (inHand != null && enchant != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				if (MainUtils.getItemType(inHand) == null || (!(MainUtils.getItemType(inHand).equalsIgnoreCase("pickaxe"))))
				{
					return;
				}
				
				int level = ItemUtil.getEnchantLevel(enchant, inHand);
				EnchantHandler handler = new EnchantHandler();
				
				Random r = new Random();
				int val = r.nextInt(100)+1;
				if (val > 3)
				{
					return;
				}
				
				KeyChance chance = handler.new KeyChance(level,p,enchant);
				chance.run();
			}
		}
	}

}
