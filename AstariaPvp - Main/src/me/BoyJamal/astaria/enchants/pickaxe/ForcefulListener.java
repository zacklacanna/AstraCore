package me.BoyJamal.astaria.enchants.pickaxe;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.MainUtils;

public class ForcefulListener implements Listener {
	
	@EventHandler
	public void onGlass(PlayerInteractEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("forceful");
		
		if (inHand != null && enchant != null && (!(evt.isCancelled())))
		{
			if (evt.getAction() == Action.LEFT_CLICK_BLOCK)
			{
				if (ItemUtil.containsEnchant(enchant, inHand))
				{
					if (MainUtils.isGlass(evt.getClickedBlock()))
					{
						evt.getClickedBlock().setType(Material.AIR);
					}
				}
			}
		}
	}

}
