package me.BoyJamal.astaria.enchants.pickaxe;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class HasteListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("haste");
		
		if (inHand != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				int level = ItemUtil.getEnchantLevel(enchant, inHand);
				if (!(p.hasPotionEffect(PotionEffectType.FAST_DIGGING)))
				{
					p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,20*5,level-1));
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("haste");
		
		if (inHand != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				int level = ItemUtil.getEnchantLevel(enchant, inHand);
				if (!(p.hasPotionEffect(PotionEffectType.FAST_DIGGING)))
				{
					p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,20*5,level-1));
				}
			}
		}
	}

}
