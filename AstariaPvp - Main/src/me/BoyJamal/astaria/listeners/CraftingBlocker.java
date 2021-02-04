package me.BoyJamal.astaria.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.utils.MainUtils;

public class CraftingBlocker implements Listener {

	@EventHandler
    public void craftItem(PrepareItemCraftEvent e) 
	{
        Material itemType = e.getRecipe().getResult().getType();
        Byte itemData = e.getRecipe().getResult().getData().getData();
        if(itemType==Material.ENDER_CHEST||itemType==Material.HOPPER|| itemType == Material.BEACON | (itemType==Material.GOLDEN_APPLE&&itemData==1)) 
        {
            e.getInventory().setResult(new ItemStack(Material.AIR));
            for(HumanEntity he:e.getViewers()) 
            {
                if(he instanceof Player) 
                {
                    ((Player)he).sendMessage(MainUtils.chatColor("&c&lSorry! &7&oYou can only get this item through &c&o/shop"));
                    ((Player)he).playSound(he.getLocation(), Sound.FIZZ, 500, 500);
                }
            }
        }
    }
	
	
}
