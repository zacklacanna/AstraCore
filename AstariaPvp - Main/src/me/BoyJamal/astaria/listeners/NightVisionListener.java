package me.BoyJamal.astaria.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt)
	{
		Player p = evt.getPlayer();
		if (!(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)))
		{
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,1));
		}
	}

	@EventHandler
	public void onJoin(PlayerMoveEvent evt)
	{
		Player p = evt.getPlayer();
		if (!(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)))
		{
			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,1));
		}
	}
}
