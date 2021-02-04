package me.BoyJamal.astaria.enchants.armor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.codingforcookies.armorequip.ArmorEquipEvent;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class BunniesListener implements Listener {
	
	@EventHandler
	public void joinEvent(PlayerJoinEvent evt)
	{
		boolean added = false;
		Player p = evt.getPlayer();
		Enchant enchant = MainUtils.getEnchant("bunnies");
		
		for (ItemStack each : p.getInventory().getArmorContents())
		{
			if (each != null)
			{
				if (ItemUtil.containsEnchant(enchant, each))
				{
					int level = ItemUtil.getEnchantLevel(enchant, each);
					if (!(p.hasPotionEffect(PotionEffectType.JUMP)))
					{
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,level-1));
						added = true;
					}
				}
			}
		}		
		
	}
	
	@EventHandler
	public void addSpeed(ArmorEquipEvent evt)
	{
		boolean remove = false;
		Player p = evt.getPlayer();
		Enchant enchant = MainUtils.getEnchant("bunnies");
		
		if(evt.getOldArmorPiece() != null && evt.getOldArmorPiece().getType() != Material.AIR)
		{
			if (ItemUtil.containsEnchant(enchant, evt.getOldArmorPiece()))
			{
				new BukkitRunnable()
				{
					boolean remove = false;
					public void run()
					{
						for (ItemStack remain : p.getInventory().getArmorContents())
						{
							if (remain != null)
							{
								if (ItemUtil.containsEnchant(enchant, remain))
								{
									remove = true;
									break;
								}
							}
						}
						
						if (!(remove))
						{
							if (p.hasPotionEffect(PotionEffectType.JUMP))
							{
								p.removePotionEffect(PotionEffectType.JUMP);
								return;
							}
						}
					}
				}.runTaskLater(Main.getInstance(),2L);
			}
		}
		
		if (ItemUtil.containsEnchant(enchant, evt.getNewArmorPiece()))
		{
			int level = ItemUtil.getEnchantLevel(enchant, evt.getNewArmorPiece());
			if (!(p.hasPotionEffect(PotionEffectType.JUMP)))
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,level-1));
			}
		}
	}

}
