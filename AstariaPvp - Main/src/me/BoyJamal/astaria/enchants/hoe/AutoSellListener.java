package me.BoyJamal.astaria.enchants.hoe;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.BoyJamal.astaria.Main;
import me.BoyJamal.astaria.enchants.Enchant;
import me.BoyJamal.astaria.enchants.ItemUtil;
import me.BoyJamal.astaria.utils.AutoSellUtil;
import me.BoyJamal.astaria.utils.MainUtils;
import net.brcdev.shopgui.ShopGuiPlusApi;

public class AutoSellListener implements Listener {
	
	private HashMap<String,AutoSellUtil> autoSellMessages = new HashMap<>();
	
	@EventHandler
	public void onBreak(BlockBreakEvent evt)
	{
		Player p = evt.getPlayer();
		ItemStack inHand = p.getInventory().getItemInHand();
		Enchant enchant = MainUtils.getEnchant("autosell");
		
		if (enchant != null & inHand != null)
		{
			if (ItemUtil.containsEnchant(enchant, inHand))
			{
				double money = 0;
				int count = 0;
				int countInv = 0;
				
				for (ItemStack items : p.getInventory().getContents())
				{
					if (items == null)
					{
						continue;
					}
					
					if (items.getType() == Material.SUGAR_CANE)
					{
						if (ShopGuiPlusApi.getItemStackPriceSell(items) > 0)
						{
							count += items.getAmount();
							money += ShopGuiPlusApi.getItemStackPriceSell(items);
							p.getInventory().setItem(countInv,new ItemStack(Material.AIR,1));
						}
					}
					countInv++;
				}
				
				if (autoSellMessages.containsKey(p.getUniqueId().toString()))
				{
					AutoSellUtil util = autoSellMessages.get(p.getUniqueId().toString());
					long remain = (util.getTime() + TimeUnit.SECONDS.toMillis(120)) - System.currentTimeMillis();
					
					if (remain <= 0)
					{
						double finalVal = util.getSold()+money;
						int finalCount = util.getCount()+count;
						p.sendMessage(MainUtils.chatColor("&f&n&lAutoSell Progress"));
						p.sendMessage("");
						p.sendMessage(MainUtils.chatColor("&r &7&l(&f&l*&7&l) &bSold Items: " + finalCount));
						p.sendMessage(MainUtils.chatColor("&r &7&l(&f&l*&7&l) &fMoney Earned: $" + NumberFormat.getIntegerInstance().format(finalVal)));
						p.sendMessage("");
						autoSellMessages.remove(p.getUniqueId().toString());
					} else {
						util.addCount(count);
						util.addSold(money);
					}
				} else {
					autoSellMessages.put(p.getUniqueId().toString(), new AutoSellUtil(p,System.currentTimeMillis(),money,count));
				}
				Main.getEco().depositPlayer(p, money);
			}
		}
	}

}
