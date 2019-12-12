package me.uniodex.uniocustomitems.managers;

import lombok.Getter;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.hooks.*;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;

public class HookManager {

    public CustomItems plugin;
    @Getter
    private Set<Hook> hooks = new HashSet<>();

    public HookManager(CustomItems plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        if (Bukkit.getPluginManager().isPluginEnabled("ASkyBlock")) {
            hooks.add(new HookASkyBlock());
        }

        if (Bukkit.getPluginManager().isPluginEnabled("CombatLogX")) {
            hooks.add(new HookCombatLogX());
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            hooks.add(new HookFactions());
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Lockette")) {
            hooks.add(new HookLockette());
        }

        if (Bukkit.getPluginManager().isPluginEnabled("TradeMe")) {
            hooks.add(new HookTradeMe());
        }

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            hooks.add(new HookWorldGuard());
        }
    }


}
