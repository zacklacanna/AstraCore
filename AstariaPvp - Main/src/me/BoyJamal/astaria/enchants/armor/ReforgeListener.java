package me.BoyJamal.astaria.enchants.armor;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class ReforgeListener implements Listener {
	
	@EventHandler
	public void onWalk(PlayerMoveEvent evt)
	{
		Player p = evt.getPlayer();
		Enchant enchant = MainUtils.getEnchant("reforge");
		
		Random r = new Random();
		int val = r.nextInt(100)+1;
		if (val >= 25)
		{
			return;
		}
		
		for (ItemStack each : p.getInventory().getArmorContents())
		{
			if (each != null)
			{
				if (ItemUtil.containsEnchant(enchant, each))
				{
					int level = ItemUtil.getEnchantLevel(enchant, each);
					if (!(each.getDurability()>= each.getType().getMaxDurability()))
					{
						each.setDurability((short)(each.getDurability()+level));
					}
				}
			}
		}
	}

}
