package me.uniodex.uniocustomitems;

import io.lumine.xikage.mythicmobs.MythicMobs;
import me.uniodex.uniocustomitems.commands.MainCommands;
import me.uniodex.uniocustomitems.listeners.*;
import me.uniodex.uniocustomitems.managers.*;
import me.uniodex.uniocustomitems.objects.FixWand;
import me.uniodex.uniocustomitems.objects.SellChest;
import me.uniodex.uniocustomitems.objects.SellHopper;
import me.uniodex.uniocustomitems.objects.SellWand;
import me.uniodex.unioprotections.UnioProtections;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minelink.ctplus.CombatTagPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class CustomItems extends JavaPlugin {

    public SellChest sellChest;
    public SellHopper sellHopper;
    public SellWand sellWand;
    public FixWand fixWand;
    private static Economy economy = null;
    private static Permission permission = null;
    public ConfigManager configManager;
    public ItemManager itemManager;
    public ExtensionManager extensionManager;
    public PriceManager priceManager;
    public FlyManager flyManager;
    public XPBottleManager xpBottleManager;
    private int voidChestSaveTimer;
    public static String hataprefix = ChatColor.AQUA + "" + ChatColor.BOLD + "UNIOCRAFT " + ChatColor.DARK_GREEN + "->" + ChatColor.RED + " ";
    public static String dikkatprefix = ChatColor.AQUA + "" + ChatColor.BOLD + "UNIOCRAFT " + ChatColor.DARK_GREEN + "->" + ChatColor.GOLD + " ";
    public static String bilgiprefix = ChatColor.AQUA + "" + ChatColor.BOLD + "UNIOCRAFT " + ChatColor.DARK_GREEN + "->" + ChatColor.GREEN + " ";
    public Plugin lockettePro;
    public Plugin factions;
    public Plugin askyblock;
    public Plugin shopGui;
    public Plugin essPlugin;
    public Plugin unioMarket;
    public UnioProtections unioProtections;
    public CombatTagPlus ctplus;
    public Plugin tradeMe;
    public Random random = new Random();
    public MythicMobs mythicMobs;

    public static String nmsver;
    public static CustomItems instance;

    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            throw new RuntimeException("Could not find Vault! Plugin can not work without it!");
        }

        if ((shopGui = Bukkit.getPluginManager().getPlugin("ShopGUIPlus")) == null && (essPlugin = Bukkit.getPluginManager().getPlugin("Essentials")) == null) {
            throw new RuntimeException("Could not find ShopGUIPlus or Essentials!");
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
            throw new RuntimeException("Could not find WorldEdit! Plugin can not work without it!");
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            throw new RuntimeException("Could not find WorldGuard! Plugin can not work without it!");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("LockettePro")) {
            lockettePro = Bukkit.getPluginManager().getPlugin("LockettePro");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            factions = Bukkit.getPluginManager().getPlugin("Factions");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("ASkyBlock")) {
            askyblock = Bukkit.getPluginManager().getPlugin("ASkyBlock");
        }

        Bukkit.getScheduler().runTask(this, () -> {
            if (Bukkit.getPluginManager().isPluginEnabled("UnioMarket")) {
                unioMarket = Bukkit.getPluginManager().getPlugin("UnioMarket");
            }
        });

        if (Bukkit.getPluginManager().isPluginEnabled("CombatTagPlus")) {
            ctplus = (CombatTagPlus) Bukkit.getPluginManager().getPlugin("CombatTagPlus");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("TradeMe")) {
            tradeMe = Bukkit.getPluginManager().getPlugin("TradeMe");
        }

        Bukkit.getScheduler().runTask(this, () -> {
            if (Bukkit.getPluginManager().isPluginEnabled("UnioProtections")) {
                unioProtections = (UnioProtections) Bukkit.getPluginManager().getPlugin("UnioProtections");
            }
        });

        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            mythicMobs = (MythicMobs) Bukkit.getPluginManager().getPlugin("MythicMobs");
        }

        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
        instance = this;

        setupEconomy();
        setupPermissions();
        initializeData();
        itemManager = new ItemManager(this);
        extensionManager = new ExtensionManager(this);
        priceManager = new PriceManager(this);
        flyManager = new FlyManager(this);
        sellChest = new SellChest(this);
        sellHopper = new SellHopper(this);
        sellWand = new SellWand(this);
        fixWand = new FixWand(this);
        xpBottleManager = new XPBottleManager(this);
        Bukkit.getPluginManager().registerEvents(new BlockListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new InteractListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new FlyListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new MultiblockListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new SellChestListener(this), this);
        Bukkit.getPluginManager().registerEvents(new XPBottleListener(this), this);
        new MainCommands(this);
        setupTimer();
    }

    public void onDisable() {
        flyManager.onDisable();
        sellChest.saveChests();
        Bukkit.getScheduler().cancelTask(sellChest.voidChestTimer);
        Bukkit.getScheduler().cancelTask(voidChestSaveTimer);
    }

    private void initializeData() {
        configManager = new ConfigManager(this);
    }

    private void setupTimer() {
        voidChestSaveTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> sellChest.saveChests(), 6000L, 6000L);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public Economy getEconomy() {
        return economy;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public Permission getPermission() {
        return permission;
    }

    public Player getRandomUser() {
        if (Bukkit.getOnlinePlayers().size() < 1) {
            return null;
        }
        return Bukkit.getOnlinePlayers().iterator().next();
    }

    /* TODO
        VIP Kartlarına işlev getir
     */
}
