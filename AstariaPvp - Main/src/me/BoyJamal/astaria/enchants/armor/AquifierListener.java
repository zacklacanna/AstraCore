package me.BoyJamal.astaria.enchants.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class AquifierListener implements Listener {
	
	@EventHandler
	public void onWater(EntityDamageEvent evt)
	{
		if (!(evt.getCause() == DamageCause.DROWNING))
		{
			return;
		}
		
		if (!(evt.getEntity() instanceof Player))
		{
			return;
		}
		
		Player p = (Player)evt.getEntity();
		Enchant enchant = MainUtils.getEnchant("aquifier");
		
		for (ItemStack each : p.getInventory().getArmorContents())
		{
			if (each != null)
			{
				if (ItemUtil.containsEnchant(enchant, each))
				{
					evt.setCancelled(true);
					p.setRemainingAir(p.getMaximumAir());	
					return;
				}
			}
		}
	}

}
