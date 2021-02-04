package me.BoyJamal.astaria.enchants;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.vk2gpz.tokenenchant.TokenEnchant;

import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.enchants.hoe.KeyFinder;
import me.BoyJamal.astaria.enchants.hoe.Lucky;
import me.BoyJamal.astaria.enchants.hoe.Miscreation;
import me.BoyJamal.astaria.enchants.hoe.TokenFinder;
import me.BoyJamal.astaria.utils.MainUtils;

public class SugarCanBreak implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent evt)
	{
		int count = 0;
		int yVal = 0;
		if (evt.getBlock().getType() == Material.SUGAR_CANE_BLOCK)
		{
			Player p = evt.getPlayer();
			if (p.getInventory().getItemInHand() != null)
			{
				ItemStack itemInHand = p.getInventory().getItemInHand();
				if (MainUtils.isSimilar(itemInHand, MainUtils.createHarvesterHoe()))
				{
					//enchants
					int luckyAmount = Lucky.getLuckyNumber(itemInHand);
					KeyFinder.runKeyFinder(itemInHand, p);
					Miscreation.runSpawnerChance(itemInHand, p);
					p.setItemInHand(TokenFinder.runTokens(p, itemInHand));
					
					
					Block check = evt.getBlock();
					boolean tokensGiven = false;
					if (check.getRelative(BlockFace.UP).getType() != Material.SUGAR_CANE_BLOCK && check.getRelative(BlockFace.DOWN).getType() != Material.SUGAR_CANE_BLOCK)
					{
						evt.setCancelled(true);
						check.setType(Material.AIR);
						if (p.getInventory().firstEmpty() != -1)
						{
							p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE,1+luckyAmount));
						} else {
							p.getWorld().dropItem(check.getLocation(), new ItemStack(Material.SUGAR_CANE,1+luckyAmount));
						}
						return;
					} else {
						evt.setCancelled(true);
					
						while (check.getType() == Material.SUGAR_CANE_BLOCK)
						{
							if (count == 0)
							{
								yVal = check.getY();
							}
						
							if (tokensGiven)
							{
								p.playSound(p.getLocation(), Sound.ORB_PICKUP, 15, 1);
							}
						
							p.updateInventory();
							
							if (count != 0)
							{
								if (check.getRelative(BlockFace.DOWN).getType() == Material.SUGAR_CANE_BLOCK)
								{
									if (p.getInventory().firstEmpty() != -1)
									{
										p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE,1+luckyAmount));
									} else {
										p.getWorld().dropItem(check.getLocation(), new ItemStack(Material.SUGAR_CANE,1+luckyAmount));
									}
								}
								check = check.getRelative(BlockFace.UP);
							}
							count++;
						}
					
						check = check.getRelative(BlockFace.DOWN);
						
						while (check.getType() == Material.SUGAR_CANE_BLOCK)
						{
							if (check.getRelative(BlockFace.DOWN).getType() == Material.SUGAR_CANE_BLOCK)
							{
								int amount = 0;
								for (ItemStack each : check.getDrops())
								{
									if (each != null && (each.getType() == Material.SUGAR_CANE || each.getType() == Material.SUGAR_CANE_BLOCK))
									{
										amount = each.getAmount();
									}
								}
								
								if (amount == 0)
								{
									amount++;
								}
								
								if (check.getY() < yVal)
								{
									p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE,amount));
								}
								check.setType(Material.AIR);
							}
							check = check.getRelative(BlockFace.DOWN);
						}
					}
				}
			}
		}
	}

}
