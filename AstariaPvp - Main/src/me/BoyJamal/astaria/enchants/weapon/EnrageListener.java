package me.BoyJamal.astaria.enchants.weapon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class EnrageListener implements Listener {
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent evt)
	{
		if (!(evt.getDamager() instanceof Player))
		{
			return;
		}
		
		Player damager = (Player)evt.getDamager();
		ItemStack inHand = damager.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("enrage");
		
		if (inHand != null && enchant != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				double health = damager.getHealth();
				if (health < 5)
				{
					evt.setDamage(evt.getDamage()*(5-health));
				}
			}
		}
	}

}
