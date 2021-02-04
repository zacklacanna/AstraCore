package me.BoyJamal.astaria.utils;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class GuiItem {

	private ItemStack item;
	private ItemStack noPerm;
	private int cost;
	private int slot;
	private boolean placeholder;
	private boolean glow;
	private String permission = null;
	private List<String> actions;
	private List<String> leftClickActions;
	
	public GuiItem(ItemStack item, int cost, int slot, boolean glow, boolean placeholder,List<String> actions)
	{
		this.item = item;
		this.cost = cost;
		this.slot = slot;
		this.placeholder = placeholder;
		this.glow = glow;
		this.actions = actions;
	}
	
	public GuiItem(ItemStack item, ItemStack noPerm, String permission, int slot, boolean placeholder,List<String> actions, List<String> leftClick)
	{
		this.item = item;
		this.noPerm = noPerm;
		this.permission = permission;
		this.slot = slot;
		this.placeholder = placeholder;
		this.actions = actions;
		this.leftClickActions = leftClick;
	}
	
	public List<String> getLeftClick()
	{
		return leftClickActions;
	}
	
	public String getPermission()
	{
		return permission;
	}
	
	public ItemStack getNoPerm()
	{
		return noPerm;
	}
	
	public boolean isPlaceholder()
	{
		return placeholder;
	}
	
	public boolean hasGlow()
	{
		return glow;
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public List<String> getActions()
	{
		return actions;
	}
	
	public int getSlot()
	{
		return slot;
	}
}
