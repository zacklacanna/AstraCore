package me.BoyJamal.astaria.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.utils.MainUtils;

public class EnchantBlocker implements Listener {
	
	@EventHandler
	public void onClick(PrepareItemEnchantEvent evt)
	{
		Player p = evt.getEnchanter();
		if (MainUtils.isSimilar(evt.getItem(), MainUtils.createHarvesterHoe()))
		{	
			evt.setCancelled(true);
			evt.getInventory().clear();
			p.getInventory().addItem(evt.getItem());
			p.closeInventory();
			p.sendMessage(MainUtils.chatColor("&c&lSorry! &7&oThis isn't any normal item. You must"));
			p.sendMessage(MainUtils.chatColor("           &7&ouse &c&o/upgrade &7&oto enchant this!"));
			p.playSound(p.getLocation(), Sound.FIZZ, 15, 1);
			return;
		}
	}


}
