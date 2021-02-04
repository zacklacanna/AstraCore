package me.BoyJamal.astaria.enchants;

import java.util.Random;

import de.dustplanet.silkspawners.SilkSpawners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.enchants.hoe.Carnage;
import me.BoyJamal.astaria.enchants.hoe.KeyFinder;
import me.BoyJamal.astaria.enchants.hoe.Lucky;
import me.BoyJamal.astaria.enchants.hoe.Miscreation;
import me.BoyJamal.astaria.utils.MainUtils;

public class EnchantHandler implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack itemInHand = p.getItemInHand();
		if (itemInHand != null)
		{
			if (MainUtils.isSimilar(itemInHand, MainUtils.createHarvesterHoe()))
			{
				if (MainUtils.getEnchant("carnage") != null)
				{
					Carnage enchant = (Carnage) MainUtils.getEnchant("carnage");
					if (ItemUtil.containsEnchant(enchant, itemInHand))
					{
						int level = ItemUtil.getEnchantLevel(enchant, itemInHand);
						if (!(p.hasPotionEffect(PotionEffectType.FAST_DIGGING)))
						{
							p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,20*5,level-1));
						}
						return;
					}
				}
			}
		}
	}
	
	public class KeyChance
	{
		private int level;
		private Player player;
		private Enchant enchant;
		private int val;
		public KeyChance(int level, Player p, Enchant enchant)
		{
			this.level = level;
			this.player = p;
			this.enchant = enchant;
			
			Random r = new Random();
			val = r.nextInt(100)+1;
		}
		
		private void highTier()
		{
			if (val < 5)
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Galactic 1");
				return;
			} else if (val <= 20) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Star 1");
				return;
			} else if (val <= 60) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Crater 1");
				return;
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Vote 1");
				return;
			}
		}
		
		private void midTier()
		{
			if (val <= 1)
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Galactic 1");
				return;
			} else if (val <= 10) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Star 1");
				return;
			} else if (val <= 40) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Crater 1");
				return;
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Vote 1");
				return;
			}
		}
		
		private void lowTier()
		{
			if (val < 5) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Star 1");
				return;
			} else if (val < 20) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Crater 1");
				return;
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "amc give " + player.getName() + " Vote 1");
				return;
			}
		}
		
		public void run()
		{
			if (level == enchant.maxLevel())
			{
				highTier();
				return;
			} else if (level > (enchant.maxLevel()/2)+1) {
				midTier();
				return;
			} else {
				lowTier();
				return;
			}
		}
	}
	
	public class SpawnerChance
	{
		private int level;
		private Player player;
		private Enchant enchant;
		public SpawnerChance(int level,Player p, Enchant enchant)
		{
			this.level = level;
			this.player = p;
			this.enchant = enchant;
		}
		
		private void highTier()
		{
			Random r = new Random();
			int val = r.nextInt(100)+1;
			
			if (val <= 1)
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " iron_golem 1");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
						MainUtils.chatColor("bc &b&lCongrats! &b&o" + player.getName() + " &f&ohas found a &b&oIron Golem &f&oSpawner with Miscreation X"));
				return;
			} else if (val <= 5) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " enderman 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oEnderman&7&o Spawner!"));
				return;
			} else if (val <= 10) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " creeper 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oCreeper&7&o Spawner!"));
				return;
			} else if (val <= 30) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " blaze 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oBlaze&7&o Spawner!"));
				return;
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " skeleton 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oSkeleton&7&o Spawner!"));
				return;
			}
		}
		
		private void middleTier()
		{
			Random r = new Random();
			int val = r.nextInt(100)+1;
			
			if (val <= 2)
			{
				///epicspawners give boyjamal pig 1
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " enderman 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oEnderman&7&o Spawner!"));
				return;
			} else if (val <= 6) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " creeper 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oCreeper&7&o Spawner!"));
				return;
			} else if (val <= 12) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " blaze 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oBlaze&7&o Spawner!"));
				return;
			} else if (val <= 20) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " skeleton 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oSkeleton&7&o Spawner!"));
				return;
			} else if (val <= 50) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " zombie 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oZombie&7&o Spawner!"));
				return;
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " cow 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oCow&7&o Spawner!"));
				return;
			}
		}
		
		private void lowTier()
		{
			Random r = new Random();
			int val = r.nextInt(100)+1;
			
			if (val <= 5)
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " creeper 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oCreeper&7&o Spawner!"));
				return;
			} else if (val <= 10) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " blaze 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oBlaze&7&o Spawner!"));
				return;
			} else if (val <= 25) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " skeleton 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oSkeleton&7&o Spawner!"));
				return;
			} else if (val <= 40) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " zombie 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oZombie&7&o Spawner!"));
				return;
			} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawner give " + player.getName() + " cow 1");
				player.sendMessage(MainUtils.chatColor("&a&lCongrats! &7&oYou found a &a&oCow&7&o Spawner!"));
				return;
			}
		}
		
		public void run()
		{
			if (level == enchant.maxLevel())
			{
				highTier();
				return;
			} else if (level > (enchant.maxLevel()/2)+1) {
				middleTier();
			} else {
				lowTier();
			}
		}
	}
}
