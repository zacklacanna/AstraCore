package me.BoyJamal.astaria.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SellWand {

	private double multi;
	private String uses;
	private ItemStack item;
	private boolean valid;
	private Player p;
	
	public SellWand(ItemStack item, Player p)
	{
		this.item = item;
		this.p = p;
		if (item.hasItemMeta() && item.getItemMeta().hasLore())
		{
			for (String each : item.getItemMeta().getLore())
			{
				if (each.startsWith(MainUtils.chatColor("&7(&f*&7) &cUses Left:")))
				{
					String[] split = each.split(" ");
					try {
						uses = ChatColor.stripColor(split[3]);
					} catch (Exception exc) {
						valid = false;
						return;
					}
				}
				
				else if (each.startsWith(MainUtils.chatColor("&7(&f*&7) &eMultipler:")))
				{
					String[] split = each.split(" ");
					try {
						multi = Double.valueOf(ChatColor.stripColor(split[2].replaceAll("x", "")));
					} catch (Exception exc) {
						valid = false;
						return;
					}
				}
			}
			valid = true;
		} else {
			valid = false;
		}
	}
	
	public boolean isValid()
	{
		return valid;
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public ItemStack useItem()
	{
		if (item.hasItemMeta() && item.getItemMeta().hasLore())
		{
			List<String> newLore = new ArrayList<>();
			for (String each : item.getItemMeta().getLore())
			{
				if (each.startsWith(MainUtils.chatColor("&7(&f*&7) &cUses Left:")))
				{
					String[] split = each.split(" ");
					if (!(split[3].equalsIgnoreCase("unlimited")))
					{
						String[] currentUses = split[3].split("/");
						int current;
						try {
							current = Integer.valueOf(ChatColor.stripColor(currentUses[0]));
						} catch (Exception exc) {
							return item;
						}
						
						if (current <= 1)
						{
							item.setType(Material.AIR);
							continue;
						} else {
							current--;
							newLore.add(MainUtils.chatColor(split[0] + " " + split[1] + " " + split[2] + 
									" &f" + current+"/" + currentUses[1]));
							continue;
						}
					}
				}
				newLore.add(each);
			}
			
			ItemMeta im = item.getItemMeta();
			if (item.getType() != Material.AIR)
			{
				im.setLore(newLore);
			} else {
				p.sendMessage(MainUtils.chatColor("&c&lNotice! &7&oYour sell wand has run out! Purchase another one on /buy!"));
			}
			item.setItemMeta(im);
			return item;
		}
		return item;
	}
	
	public String getUses()
	{
		return uses;
	}
	
	public double getMulti()
	{
		return multi;
	}
	
}
