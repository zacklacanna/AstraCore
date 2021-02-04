package me.BoyJamal.astaria.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class StorageManager {

	private File tokenFile = new File(Main.getInstance().getDataFolder(), "gui.yml");
	//private File itemDataFile = new File(Main.getInstance().getDataFolder(), "itemData.yml");
	
	public static YamlConfiguration tokenYML;
	//public static YamlConfiguration itemYML;
	
	public static List<GuiManager> activeGuis = new ArrayList<>();
	
	public static void loadFiles()
	{
		StorageManager mang = new StorageManager();
		if (!(Main.getInstance().getDataFolder().exists()))
    	{
    		Main.getInstance().getDataFolder().mkdir();
    	}
		if (!(mang.tokenFile.exists()))
		{
			try {
				mang.tokenFile.createNewFile();
				copy(Main.getInstance().getResource("gui.yml"),mang.tokenFile);
			} catch (IOException e) {
				for (int i = 0; i < 4; i++)
					System.out.println(MainUtils.chatColor("&c&lERROR &7Could not create &c&ngui.yml&c!!!"));
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
				return;
			}
			
			tokenYML = YamlConfiguration.loadConfiguration(mang.tokenFile);
			System.out.println(MainUtils.chatColor("&a&lSUCCESS &7&oAstraMain has loaded &a&ogui.yml&7&o correctly!"));
			
			mang.loadTokenShop();
			mang.loadKits();
		} else {
			tokenYML = YamlConfiguration.loadConfiguration(mang.tokenFile);
			System.out.println(MainUtils.chatColor("&a&lSUCCESS &7&oAstraMain has loaded &a&ogui.yml&7&o correctly!"));
			
			mang.loadTokenShop();
			mang.loadKits();
		}
		
	}
	
	private static void copy(InputStream in, File file)
    {
    	try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private void loadTokenShop()
	{
		ConfigurationSection tokenShop = tokenYML.getConfigurationSection("guis.tokenshop");
		if (tokenShop != null)
		{
			ConfigurationSection items = tokenShop.getConfigurationSection("items");
			if (items != null)
			{
				List<GuiItem> guiItems = new ArrayList<>();
				for (String key : items.getKeys(false))
				{
					int itemID = items.getInt(key+".itemID");
					int itemData = items.getInt(key+".itemData");
					String name = items.getString(key+".name");
					boolean placeholder = items.getBoolean(key+".placeholder");
					boolean glow = items.getBoolean(key + ".glow");
					int cost = items.getInt(key + ".cost");
					List<String> lore = items.getStringList(key + ".lore");
					List<String> actions = items.getStringList(key + ".actions");
					
					ItemStack item = new ItemStack(Material.getMaterial(itemID),1,(short)itemData);
					ItemMeta im = item.getItemMeta();
					im.setDisplayName(MainUtils.chatColor(name));
					im.setLore(MainUtils.colorList(lore));
					im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					item.setItemMeta(im);
					if (glow)
					{
						item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					}
					guiItems.add(new GuiItem(item,cost,Integer.valueOf(key),glow,placeholder,actions));
				}
				activeGuis.add(new GuiManager(MainUtils.chatColor(tokenShop.getString("name")),guiItems,tokenShop.getInt("slots"),"tokenshop"));
			}
		}
	}
	
	public static ItemStack getBlueGlass()
	{
		ItemStack blueGlass = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)3);
		ItemMeta blueMeta = blueGlass.getItemMeta();
		blueMeta.setDisplayName(" ");
		blueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		blueMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		blueGlass.setItemMeta(blueMeta);
		blueGlass.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return blueGlass;
	}
	
	public static ItemStack getWhiteGlass()
	{
		ItemStack blueGlass = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)0);
		ItemMeta blueMeta = blueGlass.getItemMeta();
		blueMeta.setDisplayName(" ");
		blueMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		blueMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		blueGlass.setItemMeta(blueMeta);
		blueGlass.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return blueGlass;
	}
	
	public static Inventory getMainEnchaner(Player p)
	{
		Inventory inv = Bukkit.createInventory(null,45,MainUtils.chatColor("&f&l◆ &b&lEnchant Central&f&l ◆"));
		
		//first Row
		for (int i = 0; i <= 8; i++)
		{
			if (i == 2 || i == 6)
			{
				inv.setItem(i, getWhiteGlass());
			} else {
				inv.setItem(i, getBlueGlass());
			}
		}
		
		//second Row
		inv.setItem(9, getBlueGlass());
		inv.setItem(10, getWhiteGlass());
		
		inv.setItem(13, MainUtils.getPickaxeItem());
		
		inv.setItem(16, getWhiteGlass());
		inv.setItem(17, getBlueGlass());
		
		//third Row
		inv.setItem(18, getWhiteGlass());
		
		inv.setItem(21, MainUtils.getSwordItem());
		inv.setItem(22, MainUtils.getArmorItem());
		inv.setItem(23, MainUtils.getBowItem());
		
		inv.setItem(26, getWhiteGlass());
		
		//forth Row
		inv.setItem(27, getBlueGlass());
		inv.setItem(28, getWhiteGlass());
		
		inv.setItem(31, MainUtils.getHoeItem());
		
		inv.setItem(34, getWhiteGlass());
		inv.setItem(35, getBlueGlass());
		
		//fifth Row
		for (int i = 36; i <= 44; i++)
		{
			if (i == 38 || i == 42)
			{
				inv.setItem(i, getWhiteGlass());
			} else {
				inv.setItem(i, getBlueGlass());
			}
		}
		
		return inv;
	}
	
	public static Inventory getEnchanterGui(ItemStack item, Player p, String type)
	{
		Inventory inv = Bukkit.createInventory(null,9,MainUtils.chatColor("&0&n" + type.substring(0,1).toUpperCase() + type.substring(1) + "&0 Enchanter"));
		
		ItemStack purpleGlassItem = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)3);
		ItemMeta im = purpleGlassItem.getItemMeta();
		im.setDisplayName(" ");
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		purpleGlassItem.setItemMeta(im);
		
		
		int count = 0;
		for (Enchant enchants : MainUtils.loadedEnchants.values())
		{
			if (enchants.getType().equalsIgnoreCase(type))
			{
				ItemStack enchantItem = new ItemStack(Material.ENCHANTED_BOOK);
				ItemMeta enchantMeta = enchantItem.getItemMeta();
				String chatColor = MainUtils.getColorByTier(enchants.getTier());
				enchantMeta.setDisplayName(MainUtils.chatColor(chatColor + ChatColor.stripColor(MainUtils.chatColor(enchants.getName()))));
				List<String> lore = new ArrayList<>();
				
				int level = ItemUtil.getEnchantLevel(enchants, item);
				int maxLevel = enchants.maxLevel();
				
				int maxShowLevel = 20;
				int progress = (level*maxShowLevel)/maxLevel;
				String start = "&7&l["+chatColor;
				for (int i = 1; i<=progress;i++)
				{
					start += "|";
				}
				start+="&7&l";
				for (int end = progress+1; end <=maxShowLevel;end++)
				{
					start+="|";
				}
				start+="]";
				
				lore.add(MainUtils.chatColor(start));
				lore.add(MainUtils.chatColor(enchants.getDescription()));
				lore.add("");
				String tierFormated = enchants.getTier().substring(0,1).toUpperCase() + enchants.getTier().substring(1).toLowerCase();
				lore.add(MainUtils.chatColor("&7(&f*&7) &cTier: &f" + tierFormated));
				lore.add(MainUtils.chatColor("&7(&f*&7) &bProgress: &f" + level + "/" + maxLevel));
				lore.add(MainUtils.chatColor("&7(&f*&7) &eCost: &f" + NumberFormat.getIntegerInstance().format(enchants.getCost()) + " Tokens"));
				enchantMeta.setLore(lore);
				enchantItem.setItemMeta(enchantMeta);
				inv.setItem(count, enchantItem);
				count++;
			}
		}
		
		for (int i = count; i <= 7; i++)
		{
			inv.setItem(i, purpleGlassItem);
		}
		
		inv.setItem(8, item);
		return inv;
	}
	
	public static Inventory getMainGui(ItemStack item, Player p)
	{
		Inventory inv = Bukkit.createInventory(null,InventoryType.HOPPER,MainUtils.chatColor("&f&l◆ &b&lDisplay&f&l ◆"));
		inv.setItem(0, item);
		
		ItemStack glassItem = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)3);
		ItemMeta im = glassItem.getItemMeta();
		im.setDisplayName(" ");
		glassItem.setItemMeta(im);
		inv.setItem(1, glassItem);
		inv.setItem(4, glassItem);
		
		ItemStack enchantItem = new ItemStack(Material.ENCHANTED_BOOK);
		List<String> enchantLore = new ArrayList<>();
		ItemMeta enchantMeta = enchantItem.getItemMeta();
		enchantMeta.setDisplayName(MainUtils.chatColor("&f&l◆ &b&lUpgrade&f&l ◆"));
		enchantLore.add(MainUtils.chatColor("&7&oClick to go to Upgrade Menu!"));
		enchantMeta.setLore(enchantLore);
		enchantMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		enchantItem.setItemMeta(enchantMeta);
		inv.setItem(3,enchantItem);
		
		ItemStack renameItem = new ItemStack(Material.EMPTY_MAP);
		List<String> renameLore = new ArrayList<>();
		ItemMeta renameMeta = renameItem.getItemMeta();
		renameMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		renameMeta.setDisplayName(MainUtils.chatColor("&f&l◆ &b&lRename&f&l ◆"));
		
		int renamesLeft = MainUtils.getRenamesLeft(p);
		String renameToString = "&f";
		if (renamesLeft == -1)
		{
			renameToString += "Unlimited";
		} else {
			renameToString += renamesLeft;
		}
		
		renameLore.add(MainUtils.chatColor("&7&oRename your Harvester Hoe!"));
		renameLore.add("");
		renameLore.add(MainUtils.chatColor("&7(&f*&7) &bRenames Left: " + renameToString));
		renameMeta.setLore(renameLore);
		renameItem.setItemMeta(renameMeta);
		inv.setItem(2, renameItem);
		return inv;
	}
	
	public static Inventory getUpgradeGui(ItemStack item, String type)
	{
		Inventory inv = Bukkit.createInventory(null,36,MainUtils.chatColor("&f&l◆ &b&lUpgrade&f&l ◆"));
		
		ItemStack purpleGlassItem = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)3);
		ItemMeta im = purpleGlassItem.getItemMeta();
		im.setDisplayName(" ");
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		purpleGlassItem.setItemMeta(im);
		
		ItemStack whiteGlassItem = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)3);
		ItemMeta imW = whiteGlassItem.getItemMeta();
		imW.setDisplayName(" ");
		imW.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imW.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		whiteGlassItem.setItemMeta(imW);
		whiteGlassItem.addUnsafeEnchantment(Enchantment.DURABILITY,1);
		
		//first row
		inv.setItem(0, purpleGlassItem);
		inv.setItem(1, purpleGlassItem);
		for (int i = 2; i <= 6; i++)
		{
			if (i == 4)
			{
				continue;
			} else {
				inv.setItem(i, whiteGlassItem);
			}
		}
		
		inv.setItem(7, purpleGlassItem);
		inv.setItem(8, purpleGlassItem);
		
		//second row
		inv.setItem(9, purpleGlassItem);
		inv.setItem(10, purpleGlassItem);
		
		int count = 4;
		for (Enchant enchants : MainUtils.loadedEnchants.values())
		{
			if (enchants.getType().equalsIgnoreCase(type))
			{
				ItemStack enchantItem = new ItemStack(Material.ENCHANTED_BOOK);
				ItemMeta enchantMeta = enchantItem.getItemMeta();
				String chatColor = MainUtils.getColorByTier(enchants.getTier());
				enchantMeta.setDisplayName(MainUtils.chatColor(chatColor + ChatColor.stripColor(MainUtils.chatColor(enchants.getName()))));
				List<String> lore = new ArrayList<>();
			
				int level = ItemUtil.getEnchantLevel(enchants, item);
				int maxLevel = enchants.maxLevel();
			
				int maxShowLevel = 20;
				int progress = (level*maxShowLevel)/maxLevel;
				String start = "&7&l["+chatColor;
				for (int i = 1; i<=progress;i++)
				{
					start += "|";
				}
				start+="&7&l";
				for (int end = progress+1; end <=maxShowLevel;end++)
				{
					start+="|";
				}
				start+="]";
			
				lore.add(MainUtils.chatColor(start));
			
				switch(ChatColor.stripColor(MainUtils.chatColor(enchants.getName())).toLowerCase())
				{
					case("autosell"):
						lore.add(MainUtils.chatColor("&7&oAutosell your cane everytime you mine!"));
						break;
					case("keyfinder"):
						lore.add(MainUtils.chatColor("&7&oChance to find keys when harvesting cane!"));
						lore.add("");
						lore.add(MainUtils.chatColor(chatColor+"Upgrade Info:"));
						lore.add(MainUtils.chatColor("&7- &7Better Keys"));
						lore.add(MainUtils.chatColor("&7- &7Higher Chance of Activation"));
						break;
					case("tokenfinder"):
						lore.add(MainUtils.chatColor("&7&oGain tokens at an expodential rate!"));
						lore.add("");
						lore.add(MainUtils.chatColor(chatColor+"Upgrade Info:"));
						lore.add(MainUtils.chatColor("&7- &7More Tokens per Harvest"));
						break;
					case("carnage"):
						lore.add(MainUtils.chatColor("&7&oGain a haste boost, allowing you to harvest cane faster!"));
						lore.add("");
						lore.add(MainUtils.chatColor(chatColor+"Upgrade Info:"));
						lore.add(MainUtils.chatColor("&7- &7Higher Level Haste Ability"));
						break;
					case("miscreation"):
						lore.add(MainUtils.chatColor("&7&oChance to find spawners while harvesting cane!"));
						lore.add(" ");
						lore.add(MainUtils.chatColor(chatColor+"Upgrade Info:"));
						lore.add(MainUtils.chatColor("&7- &7Better Spawners"));
						lore.add(MainUtils.chatColor("&7- &7Higher Chance of Activation"));
						break;
					case("lucky"):
						lore.add(MainUtils.chatColor("&7&oIncrease the amount of cane you get per harvest!"));
						lore.add("");
						lore.add(MainUtils.chatColor(chatColor+"Upgrade Info:"));
						lore.add(MainUtils.chatColor("&7- &7More Cane per Harvest"));
						break;
				}
				lore.add(" ");
				String tierFormated = enchants.getTier().substring(0,1).toUpperCase() + enchants.getTier().substring(1).toLowerCase();
				lore.add(MainUtils.chatColor("&7(&f*&7) &cTier: &f" + tierFormated));
				lore.add(MainUtils.chatColor("&7(&f*&7) &bProgress: &f" + level + "/" + maxLevel));
				lore.add(MainUtils.chatColor("&7(&f*&7) &eCost: &f" + NumberFormat.getIntegerInstance().format(enchants.getCost()) + " Tokens"));
				enchantMeta.setLore(lore);
				enchantItem.setItemMeta(enchantMeta);
				inv.setItem(count, enchantItem);
				if (count == 4)
				{
					count+=7;
				} else {
					count+=1;
				}
			}
			inv.setItem(16, purpleGlassItem);
			inv.setItem(17, purpleGlassItem);
		
		//third row
			inv.setItem(18, purpleGlassItem);
			inv.setItem(19, purpleGlassItem);
			inv.setItem(20, purpleGlassItem);
			inv.setItem(21, whiteGlassItem);
			inv.setItem(22, item);
			inv.setItem(23, whiteGlassItem);
			inv.setItem(24, purpleGlassItem);
			inv.setItem(25, purpleGlassItem);
			inv.setItem(26, purpleGlassItem);
		
		//forth row
			inv.setItem(27, purpleGlassItem);
			inv.setItem(28, purpleGlassItem);
			for (int i = 29; i <= 33; i++)
			{
				if (i == 31)
				{
					inv.setItem(i, whiteGlassItem);
				} else {
					inv.setItem(i, purpleGlassItem);
				}
			}
		
			inv.setItem(34, purpleGlassItem);
			inv.setItem(35, purpleGlassItem);
		}
		
		return inv;
	}
	
	private void loadKits()
	{
		ConfigurationSection tokenShop = tokenYML.getConfigurationSection("guis.kits");
		if (tokenShop != null)
		{
			ConfigurationSection items = tokenShop.getConfigurationSection("items");
			if (items != null)
			{
				List<GuiItem> guiItems = new ArrayList<>();
				for (String key : items.getKeys(false))
				{
					ConfigurationSection defaultItem = items.getConfigurationSection(key + ".hasPerm");
					ConfigurationSection noPermItem = items.getConfigurationSection(key + ".noPerm");
					
					ItemStack hasPerm = null;
					ItemStack noPerm = null;
					if (defaultItem != null)
					{
						int itemID = defaultItem.getInt("itemID");
						int itemData = defaultItem.getInt("itemData");
						String name = defaultItem.getString("name");
						boolean glow = defaultItem.getBoolean("glow");
						List<String> lore = defaultItem.getStringList("lore");
						hasPerm = new ItemStack(Material.getMaterial(itemID),1,(short)itemData);
						ItemMeta im = hasPerm.getItemMeta();
						im.setDisplayName(MainUtils.chatColor(name));
						im.setLore(MainUtils.colorList(lore));
						im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						hasPerm.setItemMeta(im);
						if (glow)
						{
							hasPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
						}
					}
					
					if (noPermItem != null)
					{
						int itemID = noPermItem.getInt("itemID");
						int itemData = noPermItem.getInt("itemData");
						String name = noPermItem.getString("name");
						boolean glow = noPermItem.getBoolean("glow");
						List<String> lore = noPermItem.getStringList("lore");
						noPerm = new ItemStack(Material.getMaterial(itemID),1,(short)itemData);
						ItemMeta im = noPerm.getItemMeta();
						im.setDisplayName(MainUtils.chatColor(name));
						im.setLore(MainUtils.colorList(lore));
						im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						noPerm.setItemMeta(im);
						if (glow)
						{
							noPerm.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
						}
					}
					String permission = items.getString(key + ".permission");
					List<String> actions = items.getStringList(key + ".rightClick");
					List<String> leftClick = items.getStringList(key+".leftClick");
					boolean placeholder = items.getBoolean(key+".placeholder");
					guiItems.add(new GuiItem(hasPerm,noPerm,permission,Integer.valueOf(key),placeholder,actions,leftClick));
				}
				activeGuis.add(new GuiManager(MainUtils.chatColor(tokenShop.getString("name")),guiItems,tokenShop.getInt("slots"),"kits"));
			}
		}
	}
}
