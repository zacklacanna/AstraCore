package me.BoyJamal.astaria.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.enchants.armor.Agility;
import me.BoyJamal.astaria.enchants.armor.Aquifier;
import me.BoyJamal.astaria.enchants.armor.Bunnies;
import me.BoyJamal.astaria.enchants.armor.Reborn;
import me.BoyJamal.astaria.enchants.armor.Reforge;
import me.BoyJamal.astaria.enchants.bow.Headshot;
import me.BoyJamal.astaria.enchants.hoe.AutoSell;
import me.BoyJamal.astaria.enchants.hoe.Carnage;
import me.BoyJamal.astaria.enchants.hoe.KeyFinder;
import me.BoyJamal.astaria.enchants.hoe.Lucky;
import me.BoyJamal.astaria.enchants.hoe.Miscreation;
import me.BoyJamal.astaria.enchants.hoe.TokenFinder;
import me.BoyJamal.astaria.enchants.pickaxe.Excavation;
import me.BoyJamal.astaria.enchants.pickaxe.Forceful;
import me.BoyJamal.astaria.enchants.pickaxe.Haste;
import me.BoyJamal.astaria.enchants.weapon.Enrage;
import me.BoyJamal.astaria.enchants.weapon.Grind;
import me.BoyJamal.astaria.enchants.weapon.Hellforger;
import me.BoyJamal.astaria.enchants.weapon.Shield;
import net.md_5.bungee.api.ChatColor;

public class MainUtils {

	public static HashMap<String,Enchant> loadedEnchants = new HashMap<>();
	
	public static Enchant getEnchant(String name)
	{
		if (loadedEnchants.containsKey(name.toLowerCase()))
		{
			return loadedEnchants.get(name);
		} else {
			return null;
		}
	}
	
	public static ItemStack createSuperPick(int level)
	{
		if (level > 4)
		{
			level = 4;
		}
		
		if (level <= 0)
		{
			level = 1;
		}
		
		
		Enchant enchant = MainUtils.getEnchant("excavation");
		if (enchant == null)
		{
			return null;
		}
		
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&f&k&lI&r &b&n&lTrench Pick&r &f&k&lI"));
		
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(MainUtils.chatColor("&7&oExcavate the blocks around the"));
		lore.add(MainUtils.chatColor("&7&oblock you originally mined!"));
		lore.add(MainUtils.chatColor("&f&o(Must be in your claim)"));
		lore.add("");
		lore.add(MainUtils.chatColor("&cUnbreakable I"));
		lore.add(MainUtils.chatColor(enchant.getName() + " " + MainUtils.numToRom(level)));
		
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		im.spigot().setUnbreakable(true);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		return item;
	}
	
	public static boolean isGlass(Block block)
	{
		switch(block.getType().toString().toLowerCase())
		{
			case("glass"):
				return true;
			case("stained_glass"):
				return true;
			default:
				return false;
		}
	}
	
	public static ItemStack getMegaBucket(String type, int uses)
	{
		ItemStack item = null;
		switch (type.toLowerCase())
		{
			case("water"):
				item = new ItemStack(Material.WATER_BUCKET);
				break;
			case("lava"):
				item = new ItemStack(Material.LAVA_BUCKET);
				break;
			default:
				return null;
		}
		
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&r     &f&k&lI&r &b&nMegaBucket&r &f&k&lI"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7&oMagical bucket that allows"));
		lore.add(MainUtils.chatColor("&7&oyou to place more buckets!"));
		lore.add("");
		lore.add(MainUtils.chatColor("&7&l(&f&l*&7&l) &bUses: &f" + uses + "/" + uses));
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return item;
	}
	
	public static ItemStack getSandWand(int uses)
	{
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&r      &f&k&lI&r &b&nSandWand&r &f&k&lI"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7&oRight click stack of sand"));
		lore.add(MainUtils.chatColor("&7&oto break the entire stack!"));
		lore.add(MainUtils.chatColor("&f&o(Must be in your claim)"));
		lore.add("");
		lore.add(MainUtils.chatColor("&7&l(&f&l*&7&l) &bUses: &f" + uses + "/" + uses));
		
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
		return item;
	}
	
	public static ItemStack getLightningWand(int uses)
	{
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&r   &f&k&lI&r &b&nLightningWand&r &f&k&lI"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7&o   Left click a"));
		lore.add(MainUtils.chatColor("&7&ocreeper to charge it!"));
		lore.add("");
		lore.add(MainUtils.chatColor("&7&l(&f&l*&7&l) &bUses: &f" + uses + "/" + uses));
		
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
		return item;
	}
	
	public static ItemStack getPickaxeItem()
	{
		ItemStack pickaxeEnchant = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta pickaxeMeta = pickaxeEnchant.getItemMeta();
		pickaxeMeta.setDisplayName(MainUtils.chatColor("&a&lPickaxe Enchants"));
		
		List<String> pickaxeLore = new ArrayList<>();
		pickaxeLore.add(MainUtils.chatColor("&f&l* &7Click to enchant your pickaxe!"));
		pickaxeLore.add("");
		pickaxeLore.add(MainUtils.chatColor("&a&nEnchants:"));
		pickaxeLore.add("");
		for (Enchant each : MainUtils.loadedEnchants.values())
		{
			if (each.getType().equalsIgnoreCase("pickaxe"))
			{
				pickaxeLore.add(MainUtils.chatColor("&f* &7" + ChatColor.stripColor(MainUtils.chatColor(each.getName()))));
			}
		}
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		pickaxeMeta.setLore(pickaxeLore);
		pickaxeEnchant.setItemMeta(pickaxeMeta);
		pickaxeEnchant.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return pickaxeEnchant;
	}
	
	public static ItemStack getSwordItem()
	{
		ItemStack pickaxeEnchant = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta pickaxeMeta = pickaxeEnchant.getItemMeta();
		pickaxeMeta.setDisplayName(MainUtils.chatColor("&b&lSword Enchants"));
		
		List<String> pickaxeLore = new ArrayList<>();
		pickaxeLore.add(MainUtils.chatColor("&f&l* &7Click to enchant your sword!"));
		pickaxeLore.add("");
		pickaxeLore.add(MainUtils.chatColor("&b&nEnchants:"));
		pickaxeLore.add("");
		for (Enchant each : MainUtils.loadedEnchants.values())
		{
			if (each.getType().equalsIgnoreCase("weapon"))
			{
				pickaxeLore.add(MainUtils.chatColor("&f* &7" + ChatColor.stripColor(MainUtils.chatColor(each.getName()))));
			}
		}
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		pickaxeMeta.setLore(pickaxeLore);
		pickaxeEnchant.setItemMeta(pickaxeMeta);
		pickaxeEnchant.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return pickaxeEnchant;
	}
	
	public static ItemStack getBowItem()
	{
		ItemStack pickaxeEnchant = new ItemStack(Material.BOW);
		ItemMeta pickaxeMeta = pickaxeEnchant.getItemMeta();
		pickaxeMeta.setDisplayName(MainUtils.chatColor("&d&lBow Enchants"));
		
		List<String> pickaxeLore = new ArrayList<>();
		pickaxeLore.add(MainUtils.chatColor("&f&l* &7Click to enchant your bow!"));
		pickaxeLore.add("");
		pickaxeLore.add(MainUtils.chatColor("&d&nEnchants:"));
		pickaxeLore.add("");
		for (Enchant each : MainUtils.loadedEnchants.values())
		{
			if (each.getType().equalsIgnoreCase("bow"))
			{
				pickaxeLore.add(MainUtils.chatColor("&f* &7" + ChatColor.stripColor(MainUtils.chatColor(each.getName()))));
			}
		}
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		pickaxeMeta.setLore(pickaxeLore);
		pickaxeEnchant.setItemMeta(pickaxeMeta);
		pickaxeEnchant.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return pickaxeEnchant;
	}
	
	public static ItemStack getHoeItem()
	{
		ItemStack pickaxeEnchant = new ItemStack(Material.DIAMOND_HOE);
		ItemMeta pickaxeMeta = pickaxeEnchant.getItemMeta();
		pickaxeMeta.setDisplayName(MainUtils.chatColor("&e&lHarvesterHoe Enchants"));
		
		List<String> pickaxeLore = new ArrayList<>();
		pickaxeLore.add(MainUtils.chatColor("&f&l* &7Click to enchant your hoe!"));
		pickaxeLore.add("");
		pickaxeLore.add(MainUtils.chatColor("&e&nEnchants:"));
		pickaxeLore.add("");
		for (Enchant each : MainUtils.loadedEnchants.values())
		{
			if (each.getType().equalsIgnoreCase("hoe"))
			{
				pickaxeLore.add(MainUtils.chatColor("&f* &7" + ChatColor.stripColor(MainUtils.chatColor(each.getName()))));
			}
		}
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		pickaxeMeta.setLore(pickaxeLore);
		pickaxeEnchant.setItemMeta(pickaxeMeta);
		pickaxeEnchant.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return pickaxeEnchant;
	}
	
	public static ItemStack getArmorItem()
	{
		ItemStack pickaxeEnchant = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta pickaxeMeta = pickaxeEnchant.getItemMeta();
		pickaxeMeta.setDisplayName(MainUtils.chatColor("&c&lArmor Enchants"));
		
		List<String> pickaxeLore = new ArrayList<>();
		pickaxeLore.add(MainUtils.chatColor("&f&l* &7Click to enchant your armor!"));
		pickaxeLore.add("");
		pickaxeLore.add(MainUtils.chatColor("&c&nEnchants:"));
		pickaxeLore.add("");
		for (Enchant each : MainUtils.loadedEnchants.values())
		{
			if (each.getType().equalsIgnoreCase("armor"))
			{
				pickaxeLore.add(MainUtils.chatColor("&f* &7" + ChatColor.stripColor(MainUtils.chatColor(each.getName()))));
			}
		}
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		pickaxeMeta.setLore(pickaxeLore);
		pickaxeEnchant.setItemMeta(pickaxeMeta);
		pickaxeEnchant.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return pickaxeEnchant;
	}
	
	public static String getItemType(ItemStack item)
	{	
		switch (item.getType().toString().toLowerCase())
		{
			//armor
			case("diamond_helmet"):
				return "armor";
			case("diamond_chestplate"):
				return "armor";
			case("diamond_leggings"):
				return "armor";
			case("diamond_boots"):
				return "armor";
			case("iron_helmet"):
				return "armor";
			case("iron_chestplate"):
				return "armor";
			case("iron_leggings"):
				return "armor";
			case("iron_boots"):
				return "armor";
			
			//weapons
			case("diamond_sword"):
				return "weapon";
			case("iron_sword"):
				return "weapon";
			case("stone_sword"):
				return "weapon";
			case("wooden_sword"):
				return "weapon";
			
			//bow
			case("bow"):
				return "bow";
			
			//pickaxe
			case("diamond_pickaxe"):
				return "pickaxe";
			case("iron_pickaxe"):
				return "pickaxe";
			case("stone_pickaxe"):
				return "pickaxe";
			case("wooden_pickaxe"):
				return "pickaxe";
			
			default:
				return null;
		}
	}
	
	public static Inventory getEnchanterType(Player p, ItemStack item, String type)
	{
		if (type == null)
		{
			return null;
		}
		
		switch(type.toLowerCase())
		{
			case("weapon"):
				return StorageManager.getEnchanterGui(item, p, type);
			case("bow"):
				return StorageManager.getEnchanterGui(item, p, type);
			case("pickaxe"):
				return StorageManager.getEnchanterGui(item, p, type);
			case("armor"):
				return StorageManager.getEnchanterGui(item, p, type);
			default:
				return null;
		}
	}
	
	public static int romanToNum(String val)
	{
		switch(val.toLowerCase())
		{
			case("i"):
				return 1;
			case("ii"):
				return 2;
			case("iii"):
				return 3;
			case("iv"):
				return 4;
			case("v"):
				return 5;
			case("vi"):
				return 6;
			case("vii"):
				return 7;
			case("viii"):
				return 8;
			case("ix"):
				return 9;
			case("x"):
				return 10;
			case("xi"):
				return 11;
			case("xii"):
				return 12;
			case("xiii"):
				return 13;
			case("xiv"):
				return 14;
			case("xv"):
				return 15;
			case("xvi"):
				return 16;
			case("xvii"):
				return 17;
			case("xviii"):
				return 18;
			case("xix"):
				return 19;
			case("xx"):
				return 20;
			default:
				return 0;
		}
	}
	
	public static String numToRom(int num)
	{
		switch(String.valueOf(num))
		{
			case("1"):
				return "I";
			case("2"):
				return "II";
			case("3"):
				return "III";
			case("4"):
				return "IV";
			case("5"):
				return "V";
			case("6"):
				return "VI";
			case("7"):
				return "VII";
			case("8"):
				return "VIII";
			case("9"):
				return "IX";
			case("10"):
				return "X";
			case("11"):
				return "XI";
			case("12"):
				return "XII";
			case("13"):
				return "XIII";
			case("14"):
				return "XIV";
			case("15"):
				return "XV";
			case("16"):
				return "XVI";
			case("17"):
				return "XVII";
			case("18"):
				return "XVIII";
			case("19"):
				return "XIX";
			case("20"):
				return "XX";
			default:
				return null;
		}
	}
	
	public static void loadEnchants()
	{
		//hoe enchants
		loadedEnchants.put("keyfinder", new KeyFinder("&cKeyfinder",10,7500,false,"hoe","epic","a"));
		loadedEnchants.put("tokenfinder", new TokenFinder("&bTokenFinder",20,6000,false,"hoe","rare", "a"));
		loadedEnchants.put("carnage", new Carnage("&aCarnage",15,4000,false,"hoe","common", "a"));
		loadedEnchants.put("miscreation", new Miscreation("&cMiscreation",10,10000,false,"hoe","epic", "a"));
		loadedEnchants.put("lucky", new Lucky("&bLucky",15,5000,false,"hoe","rare", "a"));
		loadedEnchants.put("autosell", new AutoSell("&cAutoSell",1,12500,false,"hoe","epic","a"));
		
		//weapon enchants
		loadedEnchants.put("grind", new Grind("&aGrind",4,2500,false,"weapon","common","&7&oMultiply xp level on mob death"));
		loadedEnchants.put("enrage", new Enrage("&cEnrage",1,5000,false,"weapon","epic","&7&oThe lower your current health the more damage you will deal"));
		loadedEnchants.put("shield", new Shield("&cShield",1,5000,false,"weapon","epic", "&7&oChance to get Resistance 2 after a kill for 5min"));
		loadedEnchants.put("hellforger", new Hellforger("&bHellforger",2,3500,false,"weapon","rare", "&7&oPermanent Strength"));
		
		//pickaxe enchants
		loadedEnchants.put("forceful", new Forceful("&bForceful",1,5000,false,"pickaxe","rare", "&7&oInstantly destroy glass "));
		loadedEnchants.put("haste", new Haste("&aHaste",2,3000,false,"pickaxe","common", "&7&oPermanent Haste"));
		loadedEnchants.put("excavation", new Excavation("&cExcavation",3,7000,false,"pickaxe","epic", "&7&oDestroy blocks in a bigger radius"));
		loadedEnchants.put("keyfinderPickaxe", new KeyFinder("&cKeyfinder",10,7500,false,"pickaxe","epic", "&7&oChance to find keys when breaking blocks"));
		loadedEnchants.put("tokenfinderPickaxe", new TokenFinder("&bTokenFinder",20,3000,false,"pickaxe","rare", "&7&oGain tokens at an expodential rate"));
		loadedEnchants.put("miscreationPickaxe", new Miscreation("&cMiscreation",10,10000,false,"pickaxe","epic", "&7&oChance to find spawners while breaking blocks"));
		
		//armor enchants
		loadedEnchants.put("agility", new Agility("&bAgility",3,3000,false,"armor","rare", "&7&oPermanent speed"));
		loadedEnchants.put("aquifier", new Aquifier("&aAquifier",1,5000,false,"armor","common","&7&oPermanent water breathing"));
		loadedEnchants.put("bunnies", new Bunnies("&bBunnies",2,3500,false,"armor","rare","&7&oPermanent Jump Boost"));
		loadedEnchants.put("reborn", new Reborn("&cReborn",3,5000,false,"armor","epic","&7&oAttempt to heal yourself at low health"));
		loadedEnchants.put("reforge", new Reforge("&cReforge",3,6000,false,"armor","epic","&7&oRepair armour as you walk"));
		
		//bow enchants
		loadedEnchants.put("headshot", new Headshot("&bHeadshot",2,3000,false,"bow","rare","&7&oDeal more damage if you headshot a player!"));
		
	}
	
	public static String chatColor(String message) 
	{
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String noPermission(String command)
	{
		return chatColor("&c&lError! &7&oYou dont have permission to use &c&o&n" + command+"!");
	}
	
	public static String getPermission(String type)
	{
		switch (type)
		{
			case("smelt"):
				return "astrapvp.command.smelt";
			case("block"):
				return "astrapvp.command.block";
			case("rename"):
				return "astrapvp.rename";
			case("admin"):
				return "astrapvp.admin";
			case("jellylegs"):
				return "astrapvp.perk.jellylegs";
			case("water"):
				return "astrapvp.perk.waterbreathing";
			case("nohunger"):
				return "astrapvp.perk.nohunger";
			case("anvil"):
				return "astrapvp.perk.anvilgui";
			default:
				return "";
		}
	}
	
	public static int getRenamesLeft(Player p)
	{
		if (p.hasPermission(MainUtils.getPermission("rename") + ".unlimted"))
		{
			return -1;
		} else {
			for (int i = 0; i <= 100; i++)
			{
				if (p.hasPermission(MainUtils.getPermission("rename") + "." + i))
				{
					return i;
				}
			}
			return 0;
		}
	}
	
	public static String getColorByTier(String tier)
	{
		switch(tier.toLowerCase())
		{
			case("common"):
				return "&a&l";
			case("rare"):
				return "&b&l";
			case("epic"):
				return "&c&l";
			default:
				return "&a&l";
		}
	}
	
	public static List<String> colorList(List<String> lore)
	{
		List<String> newLore = new ArrayList<>();
		for (String each : lore)
		{
			newLore.add(chatColor(each));
		}
		return newLore;
	}
	
	public static ItemStack createSellWand(double amount, String maxUses)
	{
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(MainUtils.chatColor("&f&l!&e&n Sell Wand &f&l!"));
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7&oMagical item allows you to left"));
		lore.add(MainUtils.chatColor("&7&oclick a chest and sell with a big boost!"));
		lore.add("");
		lore.add(MainUtils.chatColor("&7(&f*&7) &eMultipler: &f" + amount + "x"));
		if (maxUses.equalsIgnoreCase("Unlimited"))
		{
			lore.add(MainUtils.chatColor("&7(&f*&7) &cUses Left: &fUnlimited"));
		} else {
			lore.add(MainUtils.chatColor("&7(&f*&7) &cUses Left: &f" + maxUses + "/" + maxUses));
		}
		im.setLore(lore);
		im.spigot().setUnbreakable(true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		return item;
	}
	
	public static ItemStack createHarvesterHoe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_HOE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(chatColor("&f&l! &e&nThe Lost Hoe&f&l !"));
		List<String> lore = new ArrayList<>();
		lore.add(chatColor("&7&oItem that was found lost in space"));
		lore.add(chatColor("&7&ogives the ability to earn &e&ospecial rewards!"));
		lore.add("");
		lore.add(chatColor("&7(&f*&7) &aReed Harvested: &f0"));
		lore.add(chatColor("&7(&f*&7) &dTokens Collected: &f0"));
		lore.add("");
		lore.add(chatColor("&r&7Tracker I"));
		lore.add(chatColor("&r&7Unbreakable I"));
		im.setLore(lore);
		im.spigot().setUnbreakable(true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(im);
		item.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
		return item;
	}
	
	public static boolean isSimilar(ItemStack item, ItemStack baseItem)
	{
		if (!(item.getType() == baseItem.getType()))
		{
			return false;
		}
		
		if (!(item.hasItemMeta() && item.getItemMeta().hasDisplayName()))
		{
			return false;
		}
		
		ItemMeta im = item.getItemMeta();
		
		if (im.getItemFlags().equals(baseItem.getItemMeta().getItemFlags()))
		{
			return true;
		} else {
			return false;
		}
	}
}
