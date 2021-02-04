package me.BoyJamal.astaria.enchants.weapon;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class ShieldListener implements Listener {
	
	@EventHandler 
	public void onKill(EntityDeathEvent evt)
	{
		if (!(evt.getEntity() instanceof Player && evt.getEntity().getKiller() instanceof Player))
		{
			return;
		}
		
		Player killer = (Player)evt.getEntity().getKiller();
		ItemStack inHand = killer.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("shield");
		
		if (inHand != null && enchant != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				Random r = new Random();
				int chance = r.nextInt(100)+1;
				if (chance <= 25)
				{
					if (!(killer.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)))
					{
						killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*60*2,1));
						killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 500, 500);
					}
				}
			}
		}
	}

}
