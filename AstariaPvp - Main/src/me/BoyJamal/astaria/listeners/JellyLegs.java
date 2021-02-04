package me.BoyJamal.astaria.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.BoyJamal.astaria.utils.MainUtils;

public class JellyLegs implements Listener {
	
	@EventHandler
	public void onFallDamage(EntityDamageEvent evt)
	{
		if (evt.getCause() != DamageCause.FALL)
		{
			return;
		}
		
		
		if (evt.getEntity() instanceof Player)
		{
			Player p = (Player)evt.getEntity();
			if (p.hasPermission(MainUtils.getPermission("jellylegs")))
			{
				evt.setCancelled(true);
			}
		}
	}

}
