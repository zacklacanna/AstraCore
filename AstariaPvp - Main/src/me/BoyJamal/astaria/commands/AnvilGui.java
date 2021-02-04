package me.BoyJamal.astaria.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import me.BoyJamal.astaria.utils.MainUtils;

public class AnvilGui implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player)sender;
		if (p.hasPermission(MainUtils.getPermission("anvil")))
		{
			p.openInventory(Bukkit.createInventory(null, InventoryType.ANVIL));
			return true;
		} else {
			p.sendMessage(MainUtils.noPermission("/anvil"));
			return true;
		}
	}

}
