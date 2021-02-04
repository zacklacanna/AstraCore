package me.BoyJamal.astaria.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.codingforcookies.armorequip.ArmorEquipEvent;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.StorageManager;

public class EnchanterListener implements Listener {
	
	public static List<Player> inEnchanter = new ArrayList<>();
	
	@EventHandler
	public void armorAdd(ArmorEquipEvent evt)
	{
		Player p = evt.getPlayer();
		if (inEnchanter.contains(p))
		{
			if(evt.getNewArmorPiece() != null && evt.getNewArmorPiece().getType() != Material.AIR)
			{
				evt.setCancelled(true);
			}
		}
	}
	
	
	@EventHandler
	public void onEnchanterClose(InventoryCloseEvent evt)
	{
		Player p = (Player)evt.getPlayer();
		ItemStack itemInHand = p.getItemInHand();
		if (itemInHand != null)
		{
			try {
				String[] type = ChatColor.stripColor(evt.getInventory().getName()).split(" ");
				if (MainUtils.chatColor(StorageManager.getEnchanterGui(itemInHand, p, type[0]).getName()).equalsIgnoreCase(evt.getInventory().getName()))
				{
					if (inEnchanter.contains(p))
					{
						inEnchanter.remove(p);
					}
					
					new BukkitRunnable()
					{
						public void run()
						{
							p.openInventory(StorageManager.getMainEnchaner(p));
						}
					}.runTaskLater(Main.getInstance(), 1);
				}
			} catch (Exception exc) {
				return;
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		Player p = (Player)evt.getWhoClicked();
		ItemStack itemInHand = p.getItemInHand();
		
		if (itemInHand != null)
		{
			String[] type;
			try {
				type = ChatColor.stripColor(evt.getInventory().getName()).split(" ");
				if (MainUtils.chatColor(StorageManager.getEnchanterGui(itemInHand, p, type[0]).getName()).equalsIgnoreCase(evt.getInventory().getName()))
				{
					evt.setCancelled(true);
				} else {
					return;
				}
			} catch (Exception exc) {
				return;
			}
			
			String actualType = type[0];
			Enchant enchant = ItemUtil.getEnchant(evt.getCurrentItem());
			if (enchant != null)
			{
				if (MainUtils.getItemType(itemInHand).equalsIgnoreCase(actualType) || MainUtils.isSimilar(itemInHand, MainUtils.createHarvesterHoe()))
				{
					ItemStack newItem = ItemUtil.enchantItem(enchant, itemInHand, p);
					p.getInventory().setItemInHand(newItem);
					p.closeInventory();
					
					new BukkitRunnable()
					{
						public void run()
						{
							p.openInventory(MainUtils.getEnchanterType(p, newItem, actualType));
						}
					}.runTaskLater(Main.getInstance(), 1);
					
				} else {
					return;
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}

}
