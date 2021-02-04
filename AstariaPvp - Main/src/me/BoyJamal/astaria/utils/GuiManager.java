package me.BoyJamal.astaria.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiManager {

	private String name;
	private List<GuiItem> items;
	private int slots;
	private String type;
	
	public GuiManager(String name, List<GuiItem> items, int slots, String type)
	{
		this.name = name;
		this.items = items;
		this.slots = slots;
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<GuiItem> getItems()
	{
		return items;
	}
	
	public int getSlots()
	{
		return slots;
	}
	
	public Inventory toInventory()
	{
		Inventory inv = Bukkit.createInventory(null, slots,name);
		for (GuiItem each : items)
		{
			inv.setItem(each.getSlot(), each.getItem());
		}
		return inv;
	}
	
	public Inventory permissionInventory(Player p)
	{
		Inventory inv = Bukkit.createInventory(null, slots,name);
		for (GuiItem each : items)
		{
			if (each.getPermission() == null)
			{
				inv.setItem(each.getSlot(), each.getItem());
			} else {
				if (p.hasPermission(each.getPermission()))
				{
					inv.setItem(each.getSlot(), each.getItem());
				} else {
					inv.setItem(each.getSlot(), each.getNoPerm());
				}
			}
		}
		return inv;
	}
}
