package me.BoyJamal.astaria.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.StorageManager;

public class UpgradeListener implements Listener {
	
	@EventHandler
	public void onClose(InventoryCloseEvent evt)
	{
		Player p = (Player)evt.getPlayer();
		Inventory hoeInv = StorageManager.getUpgradeGui(p.getItemInHand(),"hoe");
		ItemStack itemInHand = p.getInventory().getItemInHand();
		
		if (itemInHand != null)
		{
			if (MainUtils.chatColor(hoeInv.getName()).equalsIgnoreCase(evt.getInventory().getName()))
			{
				new BukkitRunnable()
				{
					public void run()
					{
						p.openInventory(StorageManager.getMainEnchaner(p));
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		if (evt.getWhoClicked() instanceof Player)
		{
			Player p = (Player)evt.getWhoClicked();
			ItemStack upgradeItem = p.getItemInHand();
			if (upgradeItem != null && MainUtils.isSimilar(upgradeItem, MainUtils.createHarvesterHoe()))
			{
				Inventory inv = StorageManager.getUpgradeGui(upgradeItem,"hoe");
				if (MainUtils.chatColor(inv.getName()).equalsIgnoreCase(evt.getInventory().getName()))
				{
					evt.setCancelled(true);
					ItemStack clicked = evt.getCurrentItem();
					Enchant enchant = ItemUtil.getEnchant(clicked);
					if (enchant != null)
					{
						ItemStack upgradedItem = ItemUtil.enchantItem(enchant, upgradeItem, p, MainUtils.createHarvesterHoe());
						p.setItemInHand(upgradedItem);
						
						p.closeInventory();
						new BukkitRunnable()
						{
							public void run()
							{
								p.openInventory(StorageManager.getUpgradeGui(upgradedItem,"hoe"));
								p.playSound(p.getLocation(), Sound.ORB_PICKUP, 15, 1);
							}
						}.runTaskLater(Main.getInstance(), 2);
					}
				}
			} else {
				Inventory inv = StorageManager.getUpgradeGui(upgradeItem,"hoe");
				if (MainUtils.chatColor(inv.getName()).equalsIgnoreCase(evt.getInventory().getName()))
				{
					evt.setCancelled(true);
				}
			}
		}
	}

}
