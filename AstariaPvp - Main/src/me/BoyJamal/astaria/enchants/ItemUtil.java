package me.BoyJamal.astaria.enchants;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vk2gpz.tokenenchant.TokenEnchant;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;

public class ItemUtil {
	
	public static Enchant getEnchant(ItemStack clicked)
	{
		for (Enchant each : MainUtils.loadedEnchants.values())
		{
			if (clicked.getItemMeta().hasDisplayName())
			{
				if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(MainUtils.chatColor(MainUtils.getColorByTier(each.getTier()) +ChatColor.stripColor(MainUtils.chatColor(each.getName())))))
				{
					return each;
				}
			}
		}
		return null;
	}
	
	public static boolean containsEnchant(Enchant enchant, ItemStack item)
	{
		String name = enchant.getName();
		if (item.hasItemMeta() && item.getItemMeta().hasLore())
		{
			for (String each : item.getItemMeta().getLore())
			{
				if (each.startsWith(MainUtils.chatColor(name)))
				{
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	public static int getEnchantLevel(Enchant enchant, ItemStack item)
	{
		if (item.hasItemMeta() && item.getItemMeta().hasLore())
		{
			for (String each : item.getItemMeta().getLore())
			{
				if (each.startsWith(MainUtils.chatColor(enchant.getName())))
				{
					String[] split = each.split(" ");
					int current;
					try {
						current = MainUtils.romanToNum(split[1]);
					} catch (Exception exc) {
						return 0;
					}
					return current;
				}
			}
			return 0;
		} else {
			return 0;
		}
	}
	
	public static ItemStack enchantItem(Enchant enchant, ItemStack item, Player p)
	{
			if (containsEnchant(enchant,item))
			{
				double balance = TokenEnchantAPI.getInstance().getTokens(p);
				if (enchant.getCost() <= balance)
				{
					List<String> newLore = new ArrayList<>();
					for (String each : item.getItemMeta().getLore())
					{
						if (each.startsWith(MainUtils.chatColor(enchant.getName())))
						{
							String[] split = each.split(" ");
							int current;
							try {
								current = MainUtils.romanToNum(split[1]);
							} catch (Exception exc) {
								return item;
							}
							current++;
							if (current > enchant.maxLevel())
							{
								p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou are already the max level!"));
								return item;
							}
							String numeral = MainUtils.numToRom(current);
							newLore.add(MainUtils.chatColor(split[0] + " " + numeral));
							p.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have upgraded " + ChatColor.stripColor(MainUtils.chatColor(enchant.getName())) 
								+ " to Level " + current));
							TokenEnchant.getInstance().removeTokens(p, enchant.getCost());
						} else {
							newLore.add(each);
						}
					}
					ItemMeta im = item.getItemMeta();
					im.setLore(newLore);
					item.setItemMeta(im);
					return item;
				} else {
					p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou can't afford that enchant!"));
					return item;
				}
			} else {
				double balance = TokenEnchantAPI.getInstance().getTokens(p);
				if (enchant.getCost() <= balance)
				{
					List<String> newLore = new ArrayList<>();
					if (item.getItemMeta().hasLore())
					{
						for (String each : item.getItemMeta().getLore())
						{
							newLore.add(each);
						}
					}
					newLore.add(MainUtils.chatColor(enchant.getName() + " " + MainUtils.numToRom(1)));
					p.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have upgraded " + ChatColor.stripColor(MainUtils.chatColor(enchant.getName())) 
					+ " to Level " + 1));
					TokenEnchant.getInstance().removeTokens(p, enchant.getCost());
					ItemMeta im = item.getItemMeta();
					im.setLore(newLore);
					item.setItemMeta(im);
					return item;
				} else {
					p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou can't afford that enchant!"));
					return item;
				}
			}
	}
	
	public static ItemStack enchantItem(Enchant enchant, ItemStack item, Player p, ItemStack baseItem)
	{
		if (MainUtils.isSimilar(item, baseItem))
		{
			if (containsEnchant(enchant,item))
			{
				double balance = TokenEnchantAPI.getInstance().getTokens(p);
				if (enchant.getCost() <= balance)
				{
					List<String> newLore = new ArrayList<>();
					for (String each : item.getItemMeta().getLore())
					{
						if (each.startsWith(MainUtils.chatColor(enchant.getName())))
						{
							String[] split = each.split(" ");
							int current;
							try {
								current = MainUtils.romanToNum(split[1]);
							} catch (Exception exc) {
								return item;
							}
							current++;
							if (current > enchant.maxLevel())
							{
								p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou are already the max level!"));
								return item;
							}
							String numeral = MainUtils.numToRom(current);
							newLore.add(MainUtils.chatColor(split[0] + " " + numeral));
							p.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have upgraded " + ChatColor.stripColor(MainUtils.chatColor(enchant.getName())) 
								+ " to Level " + current));
							TokenEnchant.getInstance().removeTokens(p, enchant.getCost());
						} else {
							newLore.add(each);
						}
					}
					ItemMeta im = item.getItemMeta();
					im.setLore(newLore);
					item.setItemMeta(im);
					return item;
				} else {
					p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou can't afford that enchant!"));
					return item;
				}
			} else {
				double balance = TokenEnchantAPI.getInstance().getTokens(p);
				if (enchant.getCost() <= balance)
				{
					List<String> newLore = new ArrayList<>();
					for (String each : item.getItemMeta().getLore())
					{
						newLore.add(each);
					}
					newLore.add(MainUtils.chatColor(enchant.getName() + " " + MainUtils.numToRom(1)));
					p.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have upgraded " + ChatColor.stripColor(MainUtils.chatColor(enchant.getName())) 
					+ " to Level " + 1));
					TokenEnchant.getInstance().removeTokens(p, enchant.getCost());
					ItemMeta im = item.getItemMeta();
					im.setLore(newLore);
					item.setItemMeta(im);
					return item;
				} else {
					p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou can't afford that enchant!"));
					return item;
				}
			}
		}
		return item;
	}
	
	public static ItemStack updateLore(ItemStack item, String type, int amount)
	{
		switch(type.toLowerCase())
		{
			case("canebreak"):
				if (item.hasItemMeta() && item.getItemMeta().hasLore())
				{
					ItemMeta im = item.getItemMeta();
					List<String> newLore = new ArrayList<>();
					for (String each : item.getItemMeta().getLore())
					{
						if (each.startsWith(MainUtils.chatColor("&7(&f*&7) &aReed")))
						{
							String[] split = each.split(" ");
							int current;
							try {
								current = Integer.valueOf(ChatColor.stripColor(split[3].replaceAll(",", "")));
							} catch (Exception exc) {
								return item;
							}
							current+=amount;
							newLore.add(MainUtils.chatColor(split[0] + " " + split[1] + " " + split[2] +  " &f" + NumberFormat.getIntegerInstance().format(current)));
						} else {
							newLore.add(MainUtils.chatColor(each));
						}
					}
					im.setLore(newLore);
					item.setItemMeta(im);
					return item;
				}
			case("tokenupdate"):
				if (item.hasItemMeta() && item.getItemMeta().hasLore())
				{
					ItemMeta im = item.getItemMeta();
					List<String> newLore = new ArrayList<>();
					for (String each : item.getItemMeta().getLore())
					{
						if (each.startsWith(MainUtils.chatColor("&7(&f*&7) &dToken")))
						{
							String[] split = each.split(" ");
							int current;
							try {
								current = Integer.valueOf(ChatColor.stripColor(split[3].replaceAll(",", "")));
							} catch (Exception exc) {
								return item;
							}
							current+=amount;
							newLore.add(MainUtils.chatColor(split[0] + " " + split[1] + " " + split[2] + " &f" + NumberFormat.getIntegerInstance().format(current)));
						} else {
							newLore.add(MainUtils.chatColor(each));
						}
					}
					im.setLore(newLore);
					item.setItemMeta(im);
					return item;
				}
		}
		
		return item;
	}
	
}
