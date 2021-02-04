package me.BoyJamal.astaria.enchants.weapon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class GrindListener implements Listener {
	
	@EventHandler
	public void onKill(EntityDeathEvent evt)
	{
		if (!(evt.getEntity().getKiller() instanceof Player))
		{
			return;
		}
		
		Player damager = (Player)evt.getEntity().getKiller();
		ItemStack inHand = damager.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("grind");
		
		if (inHand != null && enchant != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				int level = ItemUtil.getEnchantLevel(enchant, inHand);
				evt.setDroppedExp(evt.getDroppedExp()*level);
			}
		}
	}

}
