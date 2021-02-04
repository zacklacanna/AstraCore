package me.BoyJamal.astaria.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.utils.MainUtils;

public class SmeltCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		
		if (p.hasPermission(MainUtils.getPermission("smelt")))
		{
			List<ItemStack> toAdd = new ArrayList<>();
			int blocks = 0;
			int count = 0;
			
			for (ItemStack each : p.getInventory().getContents())
			{
				if (each == null)
				{
					count++;
					continue;
				}
				
				if (getSmelt(each) != null)
				{
					toAdd.add(getSmelt(each));
					blocks+=each.getAmount();
					p.getInventory().setItem(count,new ItemStack(Material.AIR));
				}
				count++;
			}
			
			for (ItemStack each : toAdd)
			{
				p.getInventory().addItem(each);
			}
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 500, 500);
			
			if (blocks != 0)
			{
				p.sendMessage(MainUtils.chatColor("&a&lSuccess! &7&oYou converted &a&o" + blocks + " &7&oOres!"));
			} else {
				p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou don't have any ores to smelt!"));
			}
			return true;
		} else {
			p.sendMessage(MainUtils.noPermission("/smelt"));
			return true;
		}
	}
	
	public ItemStack getSmelt(ItemStack stack)
	{
		switch (stack.getType().toString().toLowerCase())
		{
			case("iron_ore"):
				return new ItemStack(Material.IRON_INGOT,stack.getAmount(),(short)0);
			case("coal_ore"):
				return new ItemStack(Material.COAL,stack.getAmount(),(short)0);
			case("gold_ore"):
				return new ItemStack(Material.GOLD_INGOT,stack.getAmount(),(short)0);
			case("lapis_ore"):
				return new ItemStack(Material.LAPIS_ORE,stack.getAmount(),(short)0);
			case("diamond_ore"):
				return new ItemStack(Material.DIAMOND,stack.getAmount(),(short)0);
			case("redstone_ore"):
				return new ItemStack(Material.REDSTONE,stack.getAmount(),(short)0);
			case("emerald_ore"):
				return new ItemStack(Material.EMERALD,stack.getAmount(),(short)0);
			default:
				return null;
		}
	}

}
