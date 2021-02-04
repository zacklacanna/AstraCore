package me.BoyJamal.astaria.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.vk2gpz.tokenenchant.TokenEnchant;

import me.BoyJamal.astaria.utils.GuiItem;
import me.BoyJamal.astaria.utils.GuiManager;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.StorageManager;

public class TokenShopListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		Inventory inv = evt.getInventory();
		Player p = (Player)evt.getWhoClicked();
		GuiManager guiInfo = null;
		for (GuiManager mang : StorageManager.activeGuis)
		{
			if (mang.getType().equalsIgnoreCase("tokenshop"))
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
						
						double playerTokens = TokenEnchant.getInstance().getTokens(p);
						if (playerTokens >= item.getCost())
						{
							TokenEnchant.getInstance().setTokens(p, (playerTokens-item.getCost()));
							for (String each : item.getActions())
							{
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), each.replaceAll("%player%", p.getName()));
							}
							p.sendMessage(MainUtils.chatColor("&a&lCongrats! &7You have bought item for &a&n" + item.getCost() + "&7 tokens!"));
							return;
						} else {
							p.closeInventory();
							p.sendMessage(MainUtils.chatColor("&c&lError! &7You do not have enough tokens for that item!"));
							return;
						}
					}
				}
			}
		}
	}
	
}
