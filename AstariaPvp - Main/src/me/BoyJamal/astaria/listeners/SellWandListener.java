package me.BoyJamal.astaria.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.SellWand;
import net.brcdev.shopgui.ShopGuiPlusApi;

public class SellWandListener implements Listener {

	private List<Player> inCooldown = new ArrayList<>();
	
	@EventHandler
	public void onClick(PlayerInteractEvent evt)
	{
		Player p = evt.getPlayer();
		if (evt.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			if (evt.getClickedBlock().getType() == Material.CHEST && p.getInventory().getItemInHand() != null)
			{
				if (evt.isCancelled())
				{
					p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou can't use SellWand here!"));
					return;
				}
				SellWand wand = new SellWand(p.getInventory().getItemInHand(),p);
				if (wand.isValid())
				{
					if (inCooldown.contains(p))
					{
						return;
					}
					inCooldown.add(p);
					DoubleChest chestBlock = null;
					Chest normalChest = null;
					if (evt.getClickedBlock().getState() instanceof DoubleChest)
					{
						chestBlock = (DoubleChest)evt.getClickedBlock().getState();
					} else {
						normalChest = (Chest)evt.getClickedBlock().getState();
					}
					
					Inventory items = null;
					if (chestBlock != null)
					{
						items = chestBlock.getInventory();
					} else {
						if (normalChest != null)
						{
							items = normalChest.getInventory();
						}
					}
					
					if (items == null)
					{
						return;
					}
					
					
					int currentSlot = 0;
					
					double sumSold = 0;
					int itemsSold = 0;
					while (currentSlot < items.getSize())
					{
						ItemStack sellItems = items.getItem(currentSlot);
						if (ShopGuiPlusApi.getItemStackPriceSell(sellItems) > 0)
						{
							double price = ShopGuiPlusApi.getItemStackPriceSell(sellItems);
							price *=wand.getMulti();
							Main.getEco().depositPlayer(p, price);
							items.setItem(currentSlot,new ItemStack(Material.AIR,1));
							sumSold += price;
							itemsSold += sellItems.getAmount();
						}
						currentSlot++;
					}
					
					if (itemsSold == 0)
					{
						p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou can't use a SellWand on an empty chest!"));
						inCooldown.remove(p);
						return;
					}
					
					if (!(p.isDead()))
					{
						p.getInventory().setItemInHand(wand.getItem());
					}
					p.sendMessage(MainUtils.chatColor(""));
					p.sendMessage(MainUtils.chatColor("           &f&l◆ &b&lSell Information &f&l◆"));
					p.sendMessage("");
					p.sendMessage(MainUtils.chatColor("&r  &b&nItems Sold:&f " + itemsSold));
					p.sendMessage("");
					p.sendMessage(MainUtils.chatColor("&r  &b&nMoney Made:&f $" + sumSold));
					p.sendMessage("");
					
					new BukkitRunnable()
					{
						public void run()
						{
							if (inCooldown.contains(p))
							{
								inCooldown.remove(p);
							}
						}
					}.runTaskLater(Main.getInstance(), 5);
				}
			}
		}
	}
	
}
