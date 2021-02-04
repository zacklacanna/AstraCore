package me.BoyJamal.astaria;

import de.dustplanet.silkspawners.SilkSpawners;
import de.dustplanet.util.SilkUtil;
import me.lucko.luckperms.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.BoyJamal.astaria.commands.AnvilGui;
import me.BoyJamal.astaria.commands.BlocksCommand;
import me.BoyJamal.astaria.commands.CustomItem;
import me.BoyJamal.astaria.commands.EnchanterMain;
import me.BoyJamal.astaria.commands.Kits;
import me.BoyJamal.astaria.commands.MainGui;
import me.BoyJamal.astaria.commands.SmeltCommand;
import me.BoyJamal.astaria.commands.TokenShop;
import me.BoyJamal.astaria.enchants.EnchantHandler;
import me.BoyJamal.astaria.enchants.hoe.AutoSellListener;
import me.BoyJamal.astaria.enchants.hoe.KeyFinder;
import me.BoyJamal.astaria.enchants.hoe.Lucky;
import me.BoyJamal.astaria.enchants.SugarCanBreak;
import me.BoyJamal.astaria.enchants.armor.AgilityListener;
import me.BoyJamal.astaria.enchants.armor.AquifierListener;
import me.BoyJamal.astaria.enchants.armor.BunniesListener;
import me.BoyJamal.astaria.enchants.armor.RebornListener;
import me.BoyJamal.astaria.enchants.armor.ReforgeListener;
import me.BoyJamal.astaria.enchants.bow.HeadshotListener;
import me.BoyJamal.astaria.enchants.hoe.TokenFinder;
import me.BoyJamal.astaria.enchants.pickaxe.ExcavationListener;
import me.BoyJamal.astaria.enchants.pickaxe.ForcefulListener;
import me.BoyJamal.astaria.enchants.pickaxe.HasteListener;
import me.BoyJamal.astaria.enchants.pickaxe.KeyFinderListener;
import me.BoyJamal.astaria.enchants.pickaxe.SpawnerListener;
import me.BoyJamal.astaria.enchants.pickaxe.TokenFinderListener;
import me.BoyJamal.astaria.enchants.weapon.EnrageListener;
import me.BoyJamal.astaria.enchants.weapon.GrindListener;
import me.BoyJamal.astaria.enchants.weapon.HellforgerListener;
import me.BoyJamal.astaria.enchants.weapon.ShieldListener;
import me.BoyJamal.astaria.listeners.CraftingBlocker;
import me.BoyJamal.astaria.listeners.EnchantBlocker;
import me.BoyJamal.astaria.listeners.EnchanterListener;
import me.BoyJamal.astaria.listeners.JellyLegs;
import me.BoyJamal.astaria.listeners.KitGuiListener;
import me.BoyJamal.astaria.listeners.LightningWandEvent;
import me.BoyJamal.astaria.listeners.MainEnchanterListener;
import me.BoyJamal.astaria.listeners.MainGuiListener;
import me.BoyJamal.astaria.listeners.MegaBucketListener;
import me.BoyJamal.astaria.listeners.NightVisionListener;
import me.BoyJamal.astaria.listeners.NoHungerPerk;
import me.BoyJamal.astaria.listeners.SandWandListener;
import me.BoyJamal.astaria.listeners.SellWandListener;
import me.BoyJamal.astaria.listeners.TokenShopListener;
import me.BoyJamal.astaria.listeners.UpgradeListener;
import me.BoyJamal.astaria.listeners.WaterBreathingPerk;
import me.BoyJamal.astaria.utils.MainUtils;
import me.BoyJamal.astaria.utils.StorageManager;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static Economy econ;
	private static LuckPerms api;
	private static SilkSpawners spawners;
	
	public void onEnable()
	{
		instance = this;
		
		if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider != null) {
			api = provider.getProvider();
		}

		SilkUtil.hookIntoSilkSpanwers();

		StorageManager.loadFiles();
		MainUtils.loadEnchants();
		registerCommands();
		registerListeners();
	}
	
	public void onDisable()
	{
		StorageManager mang = new StorageManager();
	}
	
	public void registerCommands()
	{
		getCommand("tokenshop2").setExecutor(new TokenShop());
		getCommand("anvil").setExecutor(new AnvilGui());
		getCommand("customitem").setExecutor(new CustomItem());
		getCommand("upgrade").setExecutor(new MainGui());
		getCommand("kits2").setExecutor(new Kits());
		getCommand("enchanter").setExecutor(new EnchanterMain());
		getCommand("blocks").setExecutor(new BlocksCommand());
		getCommand("smelt").setExecutor(new SmeltCommand());
	}
	
	public void registerListeners()
	{
		Bukkit.getPluginManager().registerEvents(new TokenShopListener(), this);
		Bukkit.getPluginManager().registerEvents(new NoHungerPerk(), this);
		Bukkit.getPluginManager().registerEvents(new WaterBreathingPerk(), this);
		Bukkit.getPluginManager().registerEvents(new JellyLegs(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantBlocker(), this);
		Bukkit.getPluginManager().registerEvents(new KitGuiListener(), this);
		Bukkit.getPluginManager().registerEvents(new UpgradeListener(),this);
		Bukkit.getPluginManager().registerEvents(new SugarCanBreak(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantHandler(), this);
		Bukkit.getPluginManager().registerEvents(new CraftingBlocker(),this);
		Bukkit.getPluginManager().registerEvents(new MainGuiListener(),this);
		Bukkit.getPluginManager().registerEvents(new SellWandListener(),this);
		Bukkit.getPluginManager().registerEvents(new NightVisionListener(), this);
		Bukkit.getPluginManager().registerEvents(new EnchanterListener(), this);
		Bukkit.getPluginManager().registerEvents(new MainEnchanterListener(), this);
		Bukkit.getPluginManager().registerEvents(new MegaBucketListener(), this);
		Bukkit.getPluginManager().registerEvents(new SandWandListener(), this);
		Bukkit.getPluginManager().registerEvents(new LightningWandEvent(), this);
		registerEnchants();
	}
	
	public void registerEnchants()
	{
		//armor
		Bukkit.getPluginManager().registerEvents(new AgilityListener(), this);
		Bukkit.getPluginManager().registerEvents(new AquifierListener(), this);
		Bukkit.getPluginManager().registerEvents(new BunniesListener(), this);
		Bukkit.getPluginManager().registerEvents(new RebornListener(), this);
		Bukkit.getPluginManager().registerEvents(new ReforgeListener(), this);
		
		//hoe
		Bukkit.getPluginManager().registerEvents(new AutoSellListener(), this);
		
		//bow
		Bukkit.getPluginManager().registerEvents(new HeadshotListener(), this);
		
		//pickaxe
		Bukkit.getPluginManager().registerEvents(new ForcefulListener(), this);
		Bukkit.getPluginManager().registerEvents(new HasteListener(), this);
		Bukkit.getPluginManager().registerEvents(new ExcavationListener(), this);
		Bukkit.getPluginManager().registerEvents(new TokenFinderListener(), this);
		Bukkit.getPluginManager().registerEvents(new KeyFinderListener(), this);
		Bukkit.getPluginManager().registerEvents(new SpawnerListener(), this);
		
		//weapon
		Bukkit.getPluginManager().registerEvents(new ShieldListener(), this);
		Bukkit.getPluginManager().registerEvents(new HellforgerListener(), this);
		Bukkit.getPluginManager().registerEvents(new EnrageListener(), this);
		Bukkit.getPluginManager().registerEvents(new GrindListener(), this);
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public static Main getInstance()
	{
		return instance;
	}
	
	public static Economy getEco()
	{
		return econ;
	}
	

	public static LuckPerms getPerms()
	{
		return api;
	}

}
