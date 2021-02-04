package me.BoyJamal.astaria.enchants.armor;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class RebornListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(EntityDamageEvent evt)
	{
		if (!(evt.getEntity() instanceof Player))
		{
			return;
		}
		
		Player p = (Player)evt.getEntity();
		Enchant enchant = MainUtils.getEnchant("reborn");
		Random r = new Random();
		int chance = r.nextInt(100)+1;
		if (chance > 10)
		{
			return;
		}
		
		
		for (ItemStack each : p.getInventory().getArmorContents())
		{
			if (each != null)
			{
				if (ItemUtil.containsEnchant(enchant, each))
				{
					int level = ItemUtil.getEnchantLevel(enchant, each);
					if (p.getHealth()/p.getMaxHealth() <= .25)
					{
						p.setHealth(p.getHealth()+(level*2));
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 500, 500);
						break;
					}
				}
			}
		}
	}

}
