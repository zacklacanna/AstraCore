package me.BoyJamal.astaria.commands;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.utils.MainUtils;

public class CustomItem implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender.hasPermission(MainUtils.getPermission("admin")))
		{
			if (args.length == 0)
			{
				helpMessage(sender);
				return true;
			} else if (args.length == 1 || args.length == 2) {
				listItems(sender);
				return true;
			} else if (args.length == 3) {
				
				if (args[0].equalsIgnoreCase("rename"))
				{
					Player p;
					int amount;
					try {
						p = Bukkit.getPlayer(args[1]);
					} catch (Exception exc) {
						sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[1] + " is not online!"));
						return true;
					}
					
					try {
						
						if (args[2].equalsIgnoreCase("unlimited"))
						{
							User player = LuckPerms.getApi().getUserManager().getUser(p.getUniqueId());
							int formerAmount = MainUtils.getRenamesLeft(p);
							if (formerAmount != 0)
							{
								player.setPermission(LuckPerms.getApi().buildNode(MainUtils.getPermission("rename")+ "." + formerAmount).build());
							}
							player.setPermission(LuckPerms.getApi().buildNode(MainUtils.getPermission("rename")+ ".unlimited").build());
							p.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have been given &a&oUNLIMITED &7&o Harvester Hoe renames!"));
							return true;
							
						} else {
							amount = Integer.parseInt(args[2]);
						}
					}  catch (Exception exc) {
						sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[2] + " is not a valid number!"));
						return true;
					}
					
					int formerAmount = MainUtils.getRenamesLeft(p);
					if (!(formerAmount == -1))
					{
						User player = LuckPerms.getApi().getUserManager().getUser(p.getUniqueId());
						int newAmount = formerAmount+=amount;
						if (formerAmount != 0)
						{
							player.setPermission(LuckPerms.getApi().buildNode(MainUtils.getPermission("rename")+ "." + formerAmount).build());
						}
						player.setPermission(LuckPerms.getApi().buildNode(MainUtils.getPermission("rename")+ "." + newAmount).build());
						p.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have been given &a&o" + amount + "&7&o Harvester Hoe renames!"));
						return true;
					} else {
						return true;
					}
				}
				
				Player toGive;
				ItemStack item = null;
				String itemMessage = "";
				try {
					toGive = Bukkit.getPlayer(args[1]);
				} catch(Exception exc) {
					sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[1] + " is not online!"));
					return true;
				}
				
				switch(args[2].toLowerCase())
				{
					case("superpick"):
						item = MainUtils.createSuperPick(1);
						itemMessage = "TrenchPick";
						break;
					case("trenchpick"):
						item = MainUtils.createSuperPick(1);
						itemMessage = "TrenchPick";
						break;
					case("lightningwand"):
						item = MainUtils.getLightningWand(50);
						itemMessage = "LightningWand";
						break;
					case("sandwand"):
						item = MainUtils.getSandWand(50);
						itemMessage = "SandWand";
						break;
					case("megabucket"):
						item = MainUtils.getMegaBucket("water", 50);
						itemMessage = "MegaBucket";
						break;
					case("harvesterhoe"):
						item = MainUtils.createHarvesterHoe();
						itemMessage = "Harvester Hoe";
						break;
					case("hoe"):
						item = MainUtils.createHarvesterHoe();
						itemMessage = "Harvester Hoe";
						break;
					case("sellwand"):
						item = MainUtils.createSellWand(2.0, "50");
						itemMessage = "SellWand";
						break;
					default:
						sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[2] + " is not a valid item!"));
						return true;
				}
				
				if (item != null)
				{
					toGive.getInventory().addItem(item);
					toGive.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have been given a " + itemMessage));
					return true;
				}	
				
			} else if (args.length == 4) {
				Player toGive;
				ItemStack item = null;
				String itemMessage = "";
				try {
					toGive = Bukkit.getPlayer(args[1]);
				} catch(Exception exc) {
					sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[1] + " is not online!"));
					return true;
				}
				
				switch(args[2].toLowerCase())
				{
					case("superpick"):
						int level;
						try {
							level = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid level!"));
							return true;
						}
						item = MainUtils.createSuperPick(level);
						itemMessage = "TrenchPick";
						break;
					case("trenchpick"):
						int level1;
						try {
							level1 = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid level!"));
							return true;
						}
						item = MainUtils.createSuperPick(level1);
						itemMessage = "TrenchPick";
						break;
					case("lightningwand"):
						int val1; 
						try {
							val1 = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid amount of uses!"));
							return true;
						}
						item = MainUtils.getLightningWand(val1);
						itemMessage = "LightningWand";
						break;
					case("sandwand"):
						int val; 
						try {
							val = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid amount of uses!"));
							return true;
						}
						item = MainUtils.getSandWand(val);
						itemMessage = "SandWand";
						break;
					case("harvesterhoe"):
						item = MainUtils.createHarvesterHoe();
						itemMessage = "Harvester Hoe";
						break;
					case("hoe"):
						item = MainUtils.createHarvesterHoe();
						itemMessage = "Harvester Hoe";
						break;
					case("megabucket"):
						item = MainUtils.getMegaBucket(args[3], 50);
						if (item == null)
						{
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oInvalid Type (water,lava)"));
							return true;
						}
						itemMessage = "MegaBucket";
						break;	
					case("sellwand"):
						double multi;
						try {
							multi = Double.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oMust enter a valid double"));
							return true;
						}
						
						if (multi > 3.0)
						{
							multi = 3.0;
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou tried to create an illegal sell wand!"));
						}
						item = MainUtils.createSellWand(multi, "50");
						itemMessage = "SellWand";
						break;
					default:
						sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[2] + " is not a valid item!"));
						return true;
				}
				
				if (item != null)
				{
					toGive.getInventory().addItem(item);
					toGive.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have been given a " + itemMessage));
					return true;
				}	
				
			} else if (args.length == 5) {
				
				Player toGive;
				ItemStack item = null;
				String itemMessage = "";
				try {
					toGive = Bukkit.getPlayer(args[1]);
				} catch(Exception exc) {
					sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[1] + " is not online!"));
					return true;
				}
				
				switch(args[2].toLowerCase())
				{
					case("superpick"):
						int level;
						try {
							level = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid level!"));
							return true;
						}
						item = MainUtils.createSuperPick(level);
						itemMessage = "TrenchPick";
						break;
					case("trenchpick"):
						int level1;
						try {
							level1 = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid level!"));
							return true;
						}
						item = MainUtils.createSuperPick(level1);
						itemMessage = "TrenchPick";
						break;
					case("lightningwand"):
						int val1; 
						try {
							val1 = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid amount of uses!"));
							return true;
						}
					item = MainUtils.getLightningWand(val1);
					itemMessage = "LightningWand";
					break;
					case("sandwand"):
						int val; 
						try {
							val = Integer.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou entered an invalid amount of uses!"));
							return true;
						}
						item = MainUtils.getSandWand(val);
						itemMessage = "SandWand";
						break;
					case("megabucket"):
						int uses;
						try {
							uses = Integer.valueOf(args[4]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oMust enter a valid number!"));
							return true;
						}
						
						item = MainUtils.getMegaBucket(args[3], uses);
						if (item == null)
						{
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oInvalid Type (water,lava)"));
							return true;
						}
						itemMessage = "MegaBucket";
						break;
					case("harvesterhoe"):
						item = MainUtils.createHarvesterHoe();
						itemMessage = "Harvester Hoe";
						break;
					case("hoe"):
						item = MainUtils.createHarvesterHoe();
						itemMessage = "Harvester Hoe";
						break;
					case("sellwand"):
						double multi;
						try {
							multi = Double.valueOf(args[3]);
						} catch (Exception exc) {
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oMust enter a valid multiplier!"));
							return true;
						}
						
						if (multi > 3.0)
						{
							multi = 3.0;
							sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oYou tried to create an illegal sell wand!"));
						}
						
						if (!(args[4].equalsIgnoreCase("unlimited")))
						{
							Integer maxUses;
							try {
								maxUses = Integer.parseInt(args[4]);
							} catch (Exception exc) {
								sender.sendMessage(MainUtils.chatColor("&c&lError! &7&oMust enter a valid amount of uses1"));
								return true;
							}
						}
						
						item = MainUtils.createSellWand(multi, args[4]);
						itemMessage = "SellWand";
						break;
					default:
						sender.sendMessage(MainUtils.chatColor("&c&lError! &7&o" + args[2] + " is not a valid item!"));
						return true;
				}
				
				if (item != null)
				{
					toGive.getInventory().addItem(item);
					toGive.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou have been given a " + itemMessage));
					return true;
				}	
				
				
			} else {
				helpMessage(sender);
				return true;
			}
			return true;
		} else {
			sender.sendMessage(MainUtils.noPermission("/admin"));
			return true;
		}
	}
	
	private void helpMessage(CommandSender p)
	{
		p.sendMessage("");
		p.sendMessage(MainUtils.chatColor("&e&lCustomItems: &7&o(Coded for AstraPvp)"));
		p.sendMessage(MainUtils.chatColor(""));
		p.sendMessage(MainUtils.chatColor("&r  &7&o- /customitem give %player% %item%"));
		p.sendMessage(MainUtils.chatColor("&r  &7&o- /customitem give %player% %item% %type% %uses% (MegaBucket)"));
		p.sendMessage(MainUtils.chatColor("&r  &7&o- /customitem give %player% %item% %multi% (SellWand)"));
		p.sendMessage(MainUtils.chatColor("&r  &7&o- /customitem give %player% %item% %multi% %uses% (SellWand)"));
		p.sendMessage(MainUtils.chatColor("&r  &7&o- /customitem rename %player% %amount% (Renames)"));
		p.sendMessage(MainUtils.chatColor("&r  &7&o- /customitem list"));
		p.sendMessage("");
		p.sendMessage(MainUtils.chatColor("&e&nCoded By:&r &e&lBoyJamal"));
		p.sendMessage("");
	}
	
	private void listItems(CommandSender p)
	{
		p.sendMessage(MainUtils.chatColor("&e&lCustomItems: &7&o(Coded for AstraPvp)"));
		p.sendMessage("");
		p.sendMessage(MainUtils.chatColor("&r  &eItems:"));
		p.sendMessage(MainUtils.chatColor("&r    &7&o- harvesterhoe"));
		p.sendMessage(MainUtils.chatColor("&r    &7&o- sellwand"));
		p.sendMessage(MainUtils.chatColor("&r    &7&o- sandwand"));
		p.sendMessage(MainUtils.chatColor("&r    &7&o- megabucket (water/lava)"));
		p.sendMessage(MainUtils.chatColor("&r    &7&o- lightningwand"));
		p.sendMessage(MainUtils.chatColor("&r    &7&o- superpick"));
		p.sendMessage(MainUtils.chatColor("&r    &7&o- trenchpick"));
	}

}
