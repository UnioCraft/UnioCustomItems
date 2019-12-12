package me.uniodex.uniocustomitems;

import com.SirBlobman.combatlogx.CombatLogX;
import com.earth2me.essentials.Essentials;
import io.lumine.xikage.mythicmobs.MythicMobs;
import lombok.Getter;
import me.uniodex.uniocustomitems.commands.CommandUCI;
import me.uniodex.uniocustomitems.commands.CommandXPBottle;
import me.uniodex.uniocustomitems.listeners.*;
import me.uniodex.uniocustomitems.managers.*;
import me.uniodex.uniocustomitems.managers.ConfigManager.Config;
import me.uniodex.uniocustomitems.objects.FixWand;
import me.uniodex.uniocustomitems.objects.SellChest;
import me.uniodex.uniocustomitems.objects.SellWand;
import me.uniodex.uniocustomitems.utils.Utils;
import me.uniodex.unioprotections.UnioProtections;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class CustomItems extends JavaPlugin {

    public static String hataPrefix;
    public static String dikkatPrefix;
    public static String bilgiPrefix;
    public static String consolePrefix;

    @Getter
    private static CustomItems instance;

    @Getter
    private ConfigManager configManager;
    @Getter
    private ItemManager itemManager;
    @Getter
    private EconomyManager economyManager;
    @Getter
    private XPBottleManager xpBottleManager;
    @Getter
    private VIPManager vipManager;
    @Getter
    private HookManager hookManager;
    @Getter
    private LogManager logManager;

    @Getter
    private Economy economy;
    @Getter
    private Permission permission;

    @Getter
    private Plugin lockettePro;
    @Getter
    private Plugin factions;
    @Getter
    private Plugin askyblock;
    @Getter
    private Plugin shopGui;
    @Getter
    private Essentials essentials;
    @Getter
    private Plugin unioMarket;
    @Getter
    private UnioProtections unioProtections;
    @Getter
    private CombatLogX combatLogX;
    @Getter
    private Plugin tradeMe;
    @Getter
    private MythicMobs mythicMobs;

    @Getter
    private Random random = new Random();

    @Getter
    private SellChest sellChest;
    @Getter
    private SellWand sellWand;
    @Getter
    private FixWand fixWand;

    private int voidChestSaveTimer;

    public void onEnable() {
        instance = this;
        configManager = new ConfigManager(this);
        initializePrefixes();

        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        shopGui = Bukkit.getPluginManager().getPlugin("ShopGUIPlus");
        lockettePro = Bukkit.getPluginManager().getPlugin("LockettePro");
        factions = Bukkit.getPluginManager().getPlugin("Factions");
        askyblock = Bukkit.getPluginManager().getPlugin("ASkyBlock");
        tradeMe = Bukkit.getPluginManager().getPlugin("TradeMe");

        if (Bukkit.getPluginManager().isPluginEnabled("CombatLogX")) {
            combatLogX = (CombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            mythicMobs = (MythicMobs) Bukkit.getPluginManager().getPlugin("MythicMobs");
        }

        Bukkit.getScheduler().runTask(this, () -> {
            unioMarket = Bukkit.getPluginManager().getPlugin("UnioMarket");
            if (Bukkit.getPluginManager().isPluginEnabled("UnioProtections")) {
                unioProtections = (UnioProtections) Bukkit.getPluginManager().getPlugin("UnioProtections");
            }
        });

        setupVault();

        hookManager = new HookManager(this);
        itemManager = new ItemManager(this);
        economyManager = new EconomyManager(this);
        xpBottleManager = new XPBottleManager(this);
        vipManager = new VIPManager(this);
        logManager = new LogManager(this);

        sellChest = new SellChest(this);
        sellWand = new SellWand(this);
        fixWand = new FixWand(this);

        Bukkit.getPluginManager().registerEvents(new BossEggListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new CardListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new EnchantmentBookListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new MultiblockListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new SellChestListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new WandListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new XPBottleListeners(this), this);

        getCommand("uci").setExecutor(new CommandUCI(this));
        getCommand("xpbottle").setExecutor(new CommandXPBottle(this));

        setupTimer();
    }

    public void onDisable() {
        sellChest.saveChests();
        Bukkit.getScheduler().cancelTask(sellChest.voidChestTimer);
        Bukkit.getScheduler().cancelTask(voidChestSaveTimer);
    }

    private void setupVault() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        economy = economyProvider.getProvider();
    }

    private void initializePrefixes() {
        bilgiPrefix = getMessage("prefix.bilgiPrefix");
        dikkatPrefix = getMessage("prefix.dikkatPrefix");
        hataPrefix = getMessage("prefix.hataPrefix");
        consolePrefix = getMessage("prefix.consolePrefix");
    }

    public String getMessage(String configSection) {
        if (configManager.getConfig(Config.LANG).getString(configSection) == null) return null;

        return Utils.colorizeMessage(configManager.getConfig(Config.LANG).getString(configSection));
    }

    public List<String> getMessages(String configSection) {
        if (configManager.getConfig(Config.LANG).getStringList(configSection) == null) return null;

        return Utils.colorizeMessages(configManager.getConfig(Config.LANG).getStringList(configSection));
    }

    private void setupTimer() {
        voidChestSaveTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> sellChest.saveChests(), 6000L, 6000L);
    }
}
