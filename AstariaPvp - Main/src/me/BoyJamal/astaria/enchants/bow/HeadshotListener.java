package me.BoyJamal.astaria.enchants.bow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class HeadshotListener implements Listener {
	
	private HashMap<UUID,Integer> headShotArrows = new HashMap<>();
	
	@EventHandler
	public void onFire(EntityShootBowEvent evt)
	{
		if (!(evt.getEntity() instanceof Player))
		{
			return;
		}
		
		Enchant enchant = MainUtils.getEnchant("headshot");
		if (ItemUtil.containsEnchant(enchant, evt.getBow()))
		{
			int level = ItemUtil.getEnchantLevel(enchant, evt.getBow());
			headShotArrows.put(evt.getProjectile().getUniqueId(),level);
			
			new BukkitRunnable()
			{
				public void run()
				{
					if (headShotArrows.containsKey(evt.getProjectile().getUniqueId()))
					{
						headShotArrows.remove(evt.getProjectile().getUniqueId());
					}
				}
			}.runTaskLater(Main.getInstance(),20*8);
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent evt)
	{
		if (!(evt.getDamager() instanceof Arrow))
		{
			return;
		}
		
		if (isHeadshot((Arrow)evt.getDamager(),evt.getEntity()))
		{
			if (headShotArrows.containsKey(evt.getDamager().getUniqueId()))
			{
				int level = headShotArrows.get(evt.getDamager().getUniqueId());
				evt.setDamage(evt.getDamage()+(level)/2);
				headShotArrows.remove(evt.getDamager().getUniqueId());
			}
		}
	}
	
	public boolean isHeadshot(Arrow a, Entity e) 
	{	 
	     double y = a.getLocation().getY();
	     double y2 = ((LivingEntity) e).getEyeLocation().getY();
	     double distance = Math.abs(y - y2);
	     
	     return distance <= 0.5D;
	}

}
