package me.uniodex.uniocustomitems.objects;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.managers.ConfigManager.Config;
import me.uniodex.uniocustomitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SellChest {

    private CustomItems plugin;
    private Map<String, String> chests; // loc, player
    private FileConfiguration config;
    public int voidChestTimer;

    public SellChest(CustomItems plugin) {
        this.plugin = plugin;
        config = plugin.getConfigManager().getConfig(Config.CHESTS);
        loadChests();

        voidChestTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (String chestLoc : new HashSet<>(chests.keySet())) {
                executeSell(Utils.getLocationStringWithoutChunkLoad(chestLoc));
            }
        }, 100L, 30L);
    }

    private void loadChests() {
        chests = new HashMap<>();
        for (String str : config.getKeys(false)) {
            chests.put(str, config.getString(str));
        }
    }

    public void saveChests() {
        for (String str : config.getKeys(false)) {
            config.set(str, null);
        }

        for (String loc : chests.keySet()) {
            config.set(loc, chests.get(loc));
        }

        plugin.getConfigManager().saveConfig(Config.CHESTS);
    }

    public Map<String, String> getChests() {
        return chests;
    }

    private String getOwner(Chest chest) {
        String loc = Utils.getStringLocation(chest.getLocation());
        return chests.get(loc);
    }

    public void registerChest(String player, Chest chest) {
        String loc = Utils.getStringLocation(chest.getLocation());
        chests.put(loc, player);
    }

    public void unregisterChest(Location location) {
        String loc = Utils.getStringLocation(location);
        chests.remove(loc);
    }

    public void executeSell(Location chestLoc) {
        if (chestLoc == null) return;
        if (!chestLoc.getWorld().isChunkLoaded(chestLoc.getBlockX() >> 4, chestLoc.getBlockZ() >> 4)) return;

        Chest chest = null;
        if (chestLoc.getBlock().getState() instanceof Chest) {
            chest = (Chest) chestLoc.getBlock().getState();
        }

        if (chest == null) {
            plugin.getSellChest().unregisterChest(chestLoc);
            return;
        }

        plugin.getEconomyManager().executeSell(Bukkit.getOfflinePlayer(getOwner(chest)), chest);
    }

    public boolean isVoidChest(Location location) {
        String loc = Utils.getStringLocation(location);
        return chests.containsKey(loc);
    }
}
