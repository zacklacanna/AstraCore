package me.BoyJamal.astaria.enchants.pickaxe;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.vk2gpz.tokenenchant.TokenEnchant;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.enchants.hoe.TokenFinder;
import me.BoyJamal.astaria.utils.MainUtils;

public class TokenFinderListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("tokenfinder");
		
		if (enchant == null || inHand == null)
		{
			return;
		}
		
		Random r = new Random();
		int val = r.nextInt(100)+1;
		if (val <= 35)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				if (MainUtils.getItemType(inHand) != null && MainUtils.getItemType(inHand).equalsIgnoreCase("pickaxe"))
				{
					int level = ItemUtil.getEnchantLevel(enchant, inHand);
					TokenEnchant.getInstance().addTokens(p, level/4);
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 500, 500);
				}
			}
		}
	}

}
