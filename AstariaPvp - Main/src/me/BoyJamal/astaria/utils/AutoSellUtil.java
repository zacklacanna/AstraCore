package me.BoyJamal.astaria.utils;

import org.bukkit.entity.Player;

public class AutoSellUtil {

	private Player p;
	private double money;
	private int count;
	private Long time;
	
	public AutoSellUtil(Player p, Long time, double money, int count)
	{
		this.p = p;
		this.time = time;
		
		this.money = money;
		this.count = count;
	}
	
	public Player getPlayer()
	{
		return p;
	}
	
	public double getSold()
	{
		return money;
	}
	
	public void addSold(double added)
	{
		this.money += added;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void addCount(int added)
	{
		this.count += added;
	}
	
	public Long getTime()
	{
		return time;
	}
	
}
