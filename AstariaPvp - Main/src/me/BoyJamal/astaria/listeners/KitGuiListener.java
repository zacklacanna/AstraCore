package me.BoyJamal.astaria.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import com.vk2gpz.tokenenchant.TokenEnchant;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.utils.GuiItem;
import me.BoyJamal.astaria.utils.GuiManager;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.StorageManager;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.VaultEco;

public class KitGuiListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		Inventory inv = evt.getInventory();
		Player p = (Player)evt.getWhoClicked();
		GuiManager guiInfo = null;
		for (GuiManager mang : StorageManager.activeGuis)
		{
			if (mang.getType().equalsIgnoreCase("kits"))
			{
				guiInfo = mang;
			}
		}
		
		if (guiInfo != null)
		{
			if (MainUtils.chatColor(inv.getName()).equalsIgnoreCase(guiInfo.getName()))
			{
				evt.setCancelled(true);
				for (GuiItem item : guiInfo.getItems())
				{
					if (item.getSlot() == evt.getSlot())
					{
						if (item.isPlaceholder())
						{
							return;
						}
						
						if (evt.getClick() == ClickType.LEFT)
						{
							for (String each : item.getLeftClick())
							{
								String[] type = each.split(":");
								if (type[0].equalsIgnoreCase("console"))
								{
									if (type[1].equalsIgnoreCase("closeInventory"))
									{
										p.closeInventory();
										continue;
									}
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), type[1].replaceAll("%player%", p.getName()));
								} else if (type[0].equalsIgnoreCase("player")) {
									Bukkit.dispatchCommand(p, type[1].replaceAll("%player%", p.getName()));
								}
							}
							return;
						}
						
						if (p.hasPermission(item.getPermission()))
						{
							if (evt.getClick() == ClickType.RIGHT)
							{
								for (String each : item.getActions())
								{
									String[] type = each.split(":");
									if (type[0].equalsIgnoreCase("console"))
									{
										if (type[1].equalsIgnoreCase("closeInventory"))
										{
											p.closeInventory();
											continue;
										}
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), type[1].replaceAll("%player%", p.getName()));
									} else if (type[0].equalsIgnoreCase("player")) {
										Bukkit.dispatchCommand(p, type[1].replaceAll("%player%", p.getName()));
									}
								}
								return;
							}
						} else {
							p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou dont have permission to access this!"));
							p.sendMessage(MainUtils.chatColor("&7&oUse &c&o/buy &7&oto purchase this!"));
							return;
						}
					}
				}
			}
		}
	}

}
