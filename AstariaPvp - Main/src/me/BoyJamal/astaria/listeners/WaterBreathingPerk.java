package me.BoyJamal.astaria.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.BoyJamal.astaria.utils.MainUtils;

public class WaterBreathingPerk implements Listener {
	
	@EventHandler
	public void onWater(PlayerMoveEvent evt)
	{
		Player p = (Player)evt.getPlayer();
		if (p.hasPermission(MainUtils.getPermission("water")))
		{
			if (p.getRemainingAir() < p.getMaximumAir())
			{
				p.setRemainingAir(p.getMaximumAir());
			}
		}
	}

}
