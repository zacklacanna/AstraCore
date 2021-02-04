package me.BoyJamal.astaria.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.BoyJamal.astaria.utils.MainUtils;

public class NoHungerPerk implements Listener {

	@EventHandler
	public void hungerEvent(FoodLevelChangeEvent evt)
	{
		if (evt.getEntity() instanceof Player)
		{
			Player p = (Player)evt.getEntity();
			if (p.hasPermission(MainUtils.getPermission("nohunger")))
			{
				p.setFoodLevel(20);
				evt.setCancelled(true);
			}
		}
	}
	
}
