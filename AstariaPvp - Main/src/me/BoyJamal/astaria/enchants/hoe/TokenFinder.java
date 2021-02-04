package me.BoyJamal.astaria.enchants.hoe;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.vk2gpz.tokenenchant.TokenEnchant;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class TokenFinder extends Enchant {

	public TokenFinder(String name, int maxLevel, int cost, boolean expodential, String type, String tier, String string) {
		super(name, maxLevel, cost, expodential, type, tier, string);
	}
	
	public static ItemStack runTokens(Player p, ItemStack itemInHand)
	{	
		Random r = new Random();
		int val = r.nextInt(100)+1;
		if (val <= 35)
		{
			if (MainUtils.getEnchant("tokenfinder") != null)
			{
				TokenFinder enchant = (TokenFinder) MainUtils.getEnchant("tokenfinder");
				if (ItemUtil.containsEnchant(enchant, itemInHand))
				{
					int level = ItemUtil.getEnchantLevel(enchant, itemInHand);
					TokenEnchant.getInstance().addTokens(p, (level/4));
					return ItemUtil.updateLore(itemInHand, "tokenupdate",(level/4));
				} else {
					TokenEnchant.getInstance().addTokens(p, 1);
					return ItemUtil.updateLore(itemInHand, "tokenupdate",1);
				}
			} else {
				return itemInHand;
			}
		} else {
			return itemInHand;
		}
	}
	
}
