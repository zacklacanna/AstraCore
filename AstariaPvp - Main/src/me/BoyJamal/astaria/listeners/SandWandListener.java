package me.BoyJamal.astaria.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.utils.MainUtils;

public class SandWandListener implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent evt)
	{
		if (evt.isCancelled())
		{
			return;
		}
		
		if (evt.getAction() != Action.RIGHT_CLICK_BLOCK && evt.getAction() != Action.LEFT_CLICK_BLOCK)
		{
			return;
		}
		
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		if (inHand != null)
		{
			
			if (evt.getClickedBlock() == null || evt.getClickedBlock().getType() != Material.SAND)
			{
				return;
			}
			
			int left = getUsesLeft(inHand);
			
			if (left == -1)
			{
				return;
			}
			
			if (left <= 0)
			{
				p.setItemInHand(new ItemStack(Material.AIR));
				p.sendMessage(MainUtils.chatColor("&c&lNotice! &7&oYour SandWand has ran out of uses!"));
				return;
			} else {
				new BukkitRunnable()
				{
					Block block = evt.getClickedBlock();
					Faction facAt = Board.getInstance().getFactionAt(new FLocation(block.getLocation()));
					FPlayer fme = FPlayers.getInstance().getByPlayer(evt.getPlayer());
					
					int yVal = 255;
					public void run()
					{

						if (!(fme.getFaction() == facAt)) {
							this.cancel();
						}
						
						if (fme.getFaction().isSystemFaction())
						{
							this.cancel();
						}
						
						if (yVal <= 0)
						{
							this.cancel();
						}
						block = block.getWorld().getBlockAt(block.getX(),yVal,block.getZ());
						
						
						if (block.getType() != Material.SAND)
						{
							yVal--;
						} else {
							if (block != null)
							{
								BlockBreakEvent evt = new BlockBreakEvent(block,p);
								if (!(evt.isCancelled()))
								{
									block.setType(Material.AIR);
									p.getInventory().addItem(new ItemStack(Material.SAND));
									yVal--;
								} else {
									yVal--;
								}
							} else {
								yVal--;
							}
						}
					}
				}.runTaskTimer(Main.getInstance(),0,2);
				
				Block block = evt.getClickedBlock();
				Faction facAt = Board.getInstance().getFactionAt(new FLocation(block.getLocation()));
				FPlayer fme = FPlayers.getInstance().getByPlayer(evt.getPlayer());
				
				if (!(fme.getFaction() == facAt)) {
					return;
				}
				
				if (fme.getFaction().isSystemFaction())
				{
					return;
				}
				
				ItemStack newItem = setUsesLeft(inHand);
				if (!(p.isDead()))
				{
					p.getInventory().setItemInHand(newItem);
				}
				return;
			}
		}
	}
	
	public ItemStack setUsesLeft(ItemStack item)
	{
		if (item.getType() == Material.STICK)
		{
			if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore())
			{
				if (!(MainUtils.chatColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(MainUtils.getSandWand(50).getItemMeta().getDisplayName())))
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
		if (item.getType() == Material.STICK)
		{
			if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore())
			{
				if (!(MainUtils.chatColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(MainUtils.getSandWand(50).getItemMeta().getDisplayName())))
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
