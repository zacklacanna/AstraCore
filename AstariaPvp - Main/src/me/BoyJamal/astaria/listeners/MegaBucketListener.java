package me.BoyJamal.astaria.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.utils.MainUtils;

public class MegaBucketListener implements Listener {
	
	@EventHandler
	public void onPlace(PlayerInteractEvent evt)
	{
		if (evt.isCancelled())
		{
			return;
		}
		
		if (evt.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return;
		}
		
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		if (inHand != null)
		{
			int left = getUsesLeft(inHand);
			if (left == -1)
			{
				return;
			}
			
			
			if (left <= 0)
			{
				p.setItemInHand(new ItemStack(Material.AIR));
				p.sendMessage(MainUtils.chatColor("&c&lNotice! &7&oYour MegaBucket has ran out of uses!"));
				return;
			} else {
				ItemStack newItem = setUsesLeft(inHand);
				new BukkitRunnable()
				{
					public void run()
					{
						if (!(p.isDead()))
						{
							p.getInventory().setItemInHand(newItem);
						}
					}
				}.runTaskLater(Main.getInstance(),1);
				return;
			}
		} else {
			return;
		}
	}
	
	public ItemStack setUsesLeft(ItemStack item)
	{
		if (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET)
		{
			if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore())
			{
				if (!(MainUtils.chatColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(MainUtils.getMegaBucket("water", 50).getItemMeta().getDisplayName())))
				{
					return item;
				}
				
				List<String> newLore = new ArrayList<>();
				for (String each : item.getItemMeta().getLore())
				{
					String[] words = ChatColor.stripColor(each).split(" ");
					try {
						if (words[0].equalsIgnoreCase("(*)"))
						{
							int val = Integer.parseInt(words[2].split("/")[0]);
							val--;
							newLore.add(MainUtils.chatColor("&7&l(&f&l*&7&l) &bUses: &f" + val + "/" + words[2].split("/")[1]));
						} else {
							newLore.add(each);
						}
					} catch (Exception exc) {
						newLore.add(each);
					}
				}
				ItemMeta im = item.getItemMeta();
				im.setLore(newLore);
				item.setItemMeta(im);
				return item;
			} else {
				return item;
			}
		} else {
			return item;
		}
	}
	
	public int getUsesLeft(ItemStack item)
	{
		if (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET)
		{
			if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore())
			{
				if (!(MainUtils.chatColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(MainUtils.getMegaBucket("water", 50).getItemMeta().getDisplayName())))
				{
					return -1;
				}
				for (String each : item.getItemMeta().getLore())
				{
					String[] words = ChatColor.stripColor(each).split(" ");
					try {
						if (words[0].equalsIgnoreCase("(*)"))
						{
							int val = Integer.parseInt(words[2].split("/")[0]);
							return val;
						}
					} catch (Exception exc) {
						continue;
					}
				}
				return -1;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

}
