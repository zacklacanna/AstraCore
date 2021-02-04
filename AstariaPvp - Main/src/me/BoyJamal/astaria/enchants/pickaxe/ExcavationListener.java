package me.BoyJamal.astaria.enchants.pickaxe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.listeners.FactionsBlockListener;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class ExcavationListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("excavation");
		
		if (evt.isCancelled())
		{
			return;
		}
		
		if (inHand != null && enchant != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				int level = ItemUtil.getEnchantLevel(enchant, inHand);
				int radius = getRadius(level);
				int eachSide = radius/2;
				
				List<Block> breakSpots = new ArrayList<>();
				
				for (int x = (0-eachSide); x<=(0+eachSide);x++)
				{
					for (int y = (0-eachSide); y<=(0+eachSide);y++)
					{
						for (int z = (0-eachSide);z<=(0+eachSide);z++)
						{
							Block b = p.getWorld().getBlockAt(new Location(p.getWorld()
									,evt.getBlock().getX()+x
									,evt.getBlock().getY()+y
									,evt.getBlock().getZ()+z));
							
							Faction facAt = Board.getInstance().getFactionAt(new FLocation(b.getLocation()));
							FPlayer fme = FPlayers.getInstance().getByPlayer(evt.getPlayer());

							if (!(fme.getFaction() == facAt)) {
								
								continue;

							}
							
							if (fme.getFaction().isSystemFaction())
							{
								continue;
							}
							
							BlockBreakEvent fakeEvt = new BlockBreakEvent(b,p);
							if (!(fakeEvt.isCancelled()))
							{
								if (b.getType() == Material.BEDROCK ||
										b.getType() == Material.DRAGON_EGG || 
										b.getType() == Material.ENDER_PORTAL_FRAME ||
										b.getType() == Material.CHEST)
								{
									continue;
								}
								
								if (fakeEvt.getBlock().getType() != Material.AIR)
								{
									if (b.getType() == Material.STONE ||
										b.getType() == Material.DIRT ||
										b.getType() == Material.GRAVEL ||
										b.getType() == Material.GRASS || 
										b.getType() == Material.COBBLESTONE)
										
									{
										fakeEvt.getBlock().setType(Material.AIR);
									} else {
										fakeEvt.getBlock().breakNaturally();
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public int getRadius(int level)
	{
		switch(String.valueOf(level))
		{
			case("1"):
				return 3;
			case("2"):
				return 5;
			case("3"):
				return 7;
			case("4"):
				return 10;
			default:
				return -1;
		}
	}

}
