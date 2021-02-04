package me.BoyJamal.astaria.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.StorageManager;

public class MainEnchanterListener implements Listener {
	
	public ItemStack notCorrectItem(String type)
	{
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(MainUtils.chatColor("&c&lIncorrect Item"));
		
		List<String> lore = new ArrayList<>();
		lore.add(MainUtils.chatColor("&7&oYou can only view"));
		lore.add(MainUtils.chatColor("&7&othese enchants as you were"));
		lore.add(MainUtils.chatColor("&7&onot holding the right item!"));
		lore.add("");
		lore.add(MainUtils.chatColor("&c&nCorrect Type:&f " + type));
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		Inventory inv = evt.getInventory();
		Player p = (Player)evt.getWhoClicked();
		if (MainUtils.chatColor(inv.getName()).equalsIgnoreCase(StorageManager.getMainEnchaner(p).getName()))
		{
			evt.setCancelled(true);
			ItemStack clicked = evt.getCurrentItem();
			ItemStack inHand = p.getItemInHand();
			String type = MainUtils.getItemType(inHand);
			
			if (clicked.isSimilar(MainUtils.getArmorItem()))
			{
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						EnchanterListener.inEnchanter.add(p);
						if (type == null || (!(type.equalsIgnoreCase("armor"))))
						{
							p.openInventory(StorageManager.getEnchanterGui(notCorrectItem("Armor"), p, "armor"));
						} else {
							p.openInventory(StorageManager.getEnchanterGui(p.getItemInHand(), p, "armor"));
						}
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 500, 500);
					}
				}.runTaskLater(Main.getInstance(), 1);
			} else if (clicked.isSimilar(MainUtils.getBowItem())) {
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						EnchanterListener.inEnchanter.add(p);
						if (type == null || (!(type.equalsIgnoreCase("bow"))))
						{
							p.openInventory(StorageManager.getEnchanterGui(notCorrectItem("Bow"), p, "bow"));
						} else {
							p.openInventory(StorageManager.getEnchanterGui(p.getItemInHand(), p, "bow"));
						}
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 500, 500);
					}
				}.runTaskLater(Main.getInstance(), 1);
			} else if (clicked.isSimilar(MainUtils.getHoeItem())) {
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						EnchanterListener.inEnchanter.add(p);
						if (!(MainUtils.isSimilar(inHand, MainUtils.createHarvesterHoe())))
						{
							p.openInventory(StorageManager.getUpgradeGui(notCorrectItem("Harvester Hoe"),"hoe"));
						} else {
							p.openInventory(StorageManager.getUpgradeGui(inHand, "hoe"));
						}
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 500, 500);
					}
				}.runTaskLater(Main.getInstance(), 1);
			} else if (clicked.isSimilar(MainUtils.getPickaxeItem())) {
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						EnchanterListener.inEnchanter.add(p);
						if (type == null || (!(type.equalsIgnoreCase("pickaxe"))))
						{
							p.openInventory(StorageManager.getEnchanterGui(notCorrectItem("Pickaxe"), p, "pickaxe"));
						} else {
							p.openInventory(StorageManager.getEnchanterGui(p.getItemInHand(), p, "pickaxe"));
						}
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 500, 500);
					}
				}.runTaskLater(Main.getInstance(), 1);
			} else if (clicked.isSimilar(MainUtils.getSwordItem())) {
				p.closeInventory();
				new BukkitRunnable()
				{
					public void run()
					{
						EnchanterListener.inEnchanter.add(p);
						if (type == null || (!(type.equalsIgnoreCase("weapon"))))
						{
							p.openInventory(StorageManager.getEnchanterGui(notCorrectItem("Sword"), p, "weapon"));
						} else {
							p.openInventory(StorageManager.getEnchanterGui(p.getItemInHand(), p, "weapon"));
						}
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 500, 500);
					}
				}.runTaskLater(Main.getInstance(), 1);
			} else {
				return;
			}
		}
	}

}
