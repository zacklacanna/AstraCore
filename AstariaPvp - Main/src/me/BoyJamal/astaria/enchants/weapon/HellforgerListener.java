package me.BoyJamal.astaria.enchants.weapon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codingforcookies.armorequip.ArmorEquipEvent;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class HellforgerListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt)
	{
		Player p = evt.getPlayer();
		Enchant enchant = MainUtils.getEnchant("hellforger");
		ItemStack inHand = p.getInventory().getItemInHand();
		
		if (inHand != null && enchant != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				int level = ItemUtil.getEnchantLevel(enchant, inHand);
				if (!(p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)))
				{
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*5,level-1));
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt)
	{
		Player p = evt.getPlayer();
		Enchant enchant = MainUtils.getEnchant("hellforger");
		ItemStack inHand = p.getInventory().getItemInHand();
		boolean added = false;
		
		if (inHand != null && enchant != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				int level = ItemUtil.getEnchantLevel(enchant, inHand);
				if (!(p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)))
				{
					added = true;
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*5,level-1));
				}
			} else {
				if (!(added))
				{
					if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
					{
						p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					}
				}
			}
		} 
	}

}
