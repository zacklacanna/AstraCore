package me.BoyJamal.astaria.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.utils.StorageManager;

public class EnchanterMain implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		
		Player p = (Player)sender;
		ItemStack itemInHand = p.getInventory().getItemInHand();
		
		//open main enchanter gui
		p.openInventory(StorageManager.getMainEnchaner(p));
		p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 500, 500);
		return true;
	}

}
