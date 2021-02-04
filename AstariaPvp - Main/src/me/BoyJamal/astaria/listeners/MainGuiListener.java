package me.BoyJamal.astaria.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.NodeFactory;
import me.lucko.luckperms.api.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.StorageManager;
import me.lucko.luckperms.common.api.LuckPermsApiProvider;

public class MainGuiListener implements Listener {
	
	private static List<String> inRename = new ArrayList<>();
	
	public static void removePlayer(String user,boolean expired)
	{
		if (inRename.contains(user))
		{
			inRename.remove(user);
		} else {
			return;
		}
		
		if (expired)
		{
			Player p = Bukkit.getPlayer(UUID.fromString(user));
			p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou ran out of time. Please rename your item quicker!"));
			p.sendMessage(MainUtils.chatColor("&7&o(You have not been charged a rename"));
			return;
		} else {
			Player p = Bukkit.getPlayer(UUID.fromString(user));
			p.sendMessage(MainUtils.chatColor("&a&lSuccess! &7&oYou have used a rename!"));
			return;
		}
	}
	
	@EventHandler
	public void onRename(AsyncPlayerChatEvent evt)
	{
		Player p = evt.getPlayer();
		if (inRename.contains(p.getUniqueId().toString()))
		{
			ItemStack item = p.getInventory().getItemInHand();
			if (item != null)
			{
				if (MainUtils.isSimilar(item, MainUtils.createHarvesterHoe()))
				{	
					evt.setCancelled(true);
					String rename = evt.getMessage();
					if (rename.startsWith("/"))
					{
						System.out.println(MainUtils.chatColor("&c&lError! &7&oYou must enter a rename first!"));
						return;
					}
					
					int renamesLeft = MainUtils.getRenamesLeft(p);
					if (renamesLeft > 0)
					{
						User player = LuckPerms.getApi().getUserManager().getUser(p.getUniqueId());
						Node node = LuckPerms.getApi().buildNode(MainUtils.getPermission("rename")+ "." + renamesLeft).build();
						player.unsetPermission(node);
						//player.data().remove(Node.builder(MainUtils.getPermission("rename")+ "." + renamesLeft).build());
						renamesLeft--;
						node = LuckPerms.getApi().buildNode(MainUtils.getPermission("rename")+ "." + renamesLeft).build();
						player.setPermission(node);
					}
					ItemMeta im = item.getItemMeta();
					im.setDisplayName(MainUtils.chatColor(rename));
					item.setItemMeta(im);
					p.getInventory().setItemInHand(item);
					p.updateInventory();
					removePlayer(p.getUniqueId().toString(),false);
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 15, 1);
					return;
				} else {
					evt.setCancelled(true);
					p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou must be holding a harvester hoe!"));
					p.sendMessage(MainUtils.chatColor("&7&o(You have not been charged a rename"));
					inRename.remove(p.getUniqueId().toString());
					p.playSound(p.getLocation(), Sound.FIZZ, 15, 1);
					return;
				}
			} else {
				evt.setCancelled(true);
				p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou must be holding a harvester hoe!"));
				p.sendMessage(MainUtils.chatColor("&7&o(You have not been charged a rename"));
				inRename.remove(p.getUniqueId().toString());
				p.playSound(p.getLocation(), Sound.FIZZ, 15, 1);
				return;
			}
		} else {
			return;
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent evt)
	{
		if (evt.getWhoClicked() instanceof Player)
		{
			Player p = (Player)evt.getWhoClicked();
			ItemStack itemInHand = p.getItemInHand();
			if (itemInHand != null)
			{
				Inventory inv = StorageManager.getMainGui(itemInHand, p);
				if (inv == null)
				{
					return;
				}
				
				if (evt.getInventory().getName().equalsIgnoreCase(inv.getName()))
				{
					evt.setCancelled(true);
					if (evt.getSlot() == 2)
					{
						int renamesLeft = MainUtils.getRenamesLeft(p);
						if (renamesLeft > 0 || renamesLeft == -1)
						{
							p.closeInventory();
							p.sendMessage(MainUtils.chatColor("&b&lRename: &7&oEnter your rename into chat (Can use Color Codes)"));
							inRename.add(p.getUniqueId().toString());
							
							new BukkitRunnable()
							{
								public void run()
								{
									removePlayer(p.getUniqueId().toString(),true);
								}
							}.runTaskLater(Main.getInstance(), 20*10);
							return;
						} else {
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.FIZZ, 15, 1);
							p.sendMessage(MainUtils.chatColor("&c&lSorry! &7&oYou don't have any renames left."));
							p.sendMessage(MainUtils.chatColor("&r         &7&oPurchase more with &c&o/buy!"));
							return;
						}
					} else if (evt.getSlot() == 3) {
						p.closeInventory();
						new BukkitRunnable()
						{
							public void run()
							{
								p.openInventory(StorageManager.getUpgradeGui(itemInHand,"hoe"));
								p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 15, 1);
								return;
							}
						}.runTaskLater(Main.getInstance(), 2);
					}
				}
			}
		}
	}

}
