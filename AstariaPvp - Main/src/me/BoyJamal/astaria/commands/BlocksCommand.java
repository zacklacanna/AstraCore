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

public class BlocksCommand implements CommandExecutor {
	
	private class CondensdedBlocks {
		private ItemStack remainingItems;
		private ItemStack condensed;
		
		public CondensdedBlocks(ItemStack remain, ItemStack cond)
		{
			this.condensed = cond;
			this.remainingItems = remain;
		}
		
		public ItemStack getCondensded()
		{
			return this.condensed;
		}
		
		public ItemStack getRemain()
		{
			return this.remainingItems;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		if (p.hasPermission(MainUtils.getPermission("block")))
		{
			
			int count = 0;
			int ores = 0;
			int block = 0;
			
			List<CondensdedBlocks> condensdedBlocks = new ArrayList<>();
			
			//find material blocks
			for (ItemStack each : p.getInventory().getContents())
			{
				if (each == null)
				{
					count++;
					continue;
				}
				
				
				CondensdedBlocks ore = isMaterial(each);
				
				if (ore != null)
				{
					ores += each.getAmount();
					condensdedBlocks.add(ore);
					p.getInventory().setItem(count,new ItemStack(Material.AIR));
				}
				count++;
			}
			
			
			for (CondensdedBlocks blocks : condensdedBlocks)
			{
				block += blocks.getCondensded().getAmount();
				if (blocks.getCondensded().getAmount() != 0)
				{
					p.getInventory().addItem(blocks.getCondensded());
				}
				
				if (blocks.getRemain().getAmount() != 0)
				{
					p.getInventory().addItem(blocks.getRemain());
				}
			}
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 500, 500);
			
			if (block != 0)
			{
				p.sendMessage(MainUtils.chatColor("&a&lSuccess! &7&oYou have condensed &a&o" + block*9 + "&7&o ores into&a&o " + block + " blocks!"));
			} else {
				p.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou did not have enough ores to convert!"));
			}
		} else {
			p.sendMessage(MainUtils.noPermission("/block"));
			return true;
		}
		return true;
	}
	
	public CondensdedBlocks isMaterial(ItemStack item)
	{
		
		int blocks = item.getAmount()/9;
		int remain = item.getAmount()%9;
		
		switch (item.getType().toString().toLowerCase())
		{
			case("diamond"):
				return new CondensdedBlocks(new ItemStack(Material.DIAMOND,remain,(short)0),new ItemStack(Material.DIAMOND_BLOCK,blocks,(short)0));
			case("redstone"):
				return new CondensdedBlocks(new ItemStack(Material.REDSTONE,remain,(short)0),new ItemStack(Material.REDSTONE_BLOCK,blocks,(short)0));
			case("emerald"):
				return new CondensdedBlocks(new ItemStack(Material.EMERALD,remain,(short)0),new ItemStack(Material.EMERALD_BLOCK,blocks,(short)0));
			case("quartz"):
				return new CondensdedBlocks(new ItemStack(Material.QUARTZ,remain,(short)0),new ItemStack(Material.QUARTZ_BLOCK,blocks,(short)0));
			case("coal"):
				return new CondensdedBlocks(new ItemStack(Material.COAL,remain,(short)0),new ItemStack(Material.COAL_BLOCK,blocks,(short)0));
			case("iron_ingot"):
				return new CondensdedBlocks(new ItemStack(Material.IRON_INGOT,remain,(short)0),new ItemStack(Material.IRON_BLOCK,blocks,(short)0));
			case("gold_ingot"):
				return new CondensdedBlocks(new ItemStack(Material.GOLD_INGOT,remain,(short)0),new ItemStack(Material.GOLD_BLOCK,blocks,(short)0));
			case("ink_sac"):
				if (item.getDurability() == (short)4)
				{
					return new CondensdedBlocks(new ItemStack(Material.INK_SACK,remain,(short)4),new ItemStack(Material.LAPIS_BLOCK,blocks,(short)0));
				} else {
					return null;
				}
			default:
				return null;
		}
	}

}
