package me.BoyJamal.astaria.enchants;

public abstract class Enchant {

	private String name;
	private int maxLevel;
	private int cost;
	private boolean expodential;
	private String type;
	private String tier;
	private String description;
	
	public Enchant(String name, int maxLevel, int cost, boolean expodential, String type, String tier)
	{
		this.name = name;
		this.maxLevel = maxLevel;
		this.cost = cost;
		this.expodential = expodential;
		this.type = type;
		this.tier = tier;
	}
	
	public Enchant(String name, int maxLevel, int cost, boolean expodential, String type, String tier, String description)
	{
		this.name = name;
		this.maxLevel = maxLevel;
		this.cost = cost;
		this.expodential = expodential;
		this.type = type;
		this.tier = tier;
		this.description = description;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int maxLevel()
	{
		return maxLevel;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public boolean isExpodential()
	{
		return expodential;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getTier()
	{
		return tier;
	}
	
}
