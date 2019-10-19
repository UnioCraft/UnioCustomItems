package me.uniodex.uniocustomitems.objects;

import me.Zrips.TradeMe.TradeMe;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@SuppressWarnings("deprecation")
public class SellChest {

    private CustomItems plugin;
    private Map<String, String> chests; // loc, player
    private FileConfiguration fc;
    public int voidChestTimer;

    public SellChest(CustomItems plugin) {
        this.plugin = plugin;
        fc = plugin.configManager.getChestsData();
        loadChests();

        voidChestTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (String chestLoc : new HashSet<>(chests.keySet())) {
                executeSell(Utils.getLocationStringWithoutChunkLoad(chestLoc));
            }
        }, 100L, 30L);
    }

    private void loadChests() {
        chests = new HashMap<String, String>();
        for (String str : fc.getKeys(false)) {
            chests.put(str, fc.getString(str));
        }
    }

    public void saveChests() {
        for (String str : fc.getKeys(false)) {
            fc.set(str, null);
        }

        for (String loc : chests.keySet()) {
            fc.set(loc, chests.get(loc));
        }

        plugin.configManager.saveChestsConfig();
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
        if (chests.containsKey(loc)) {
            chests.remove(loc);
        }
    }

    public void executeSell(Location chestLoc) {
        if (chestLoc == null) {
            return;
        }

        if (chestLoc.getWorld() == null) {
            return;
        }

        Chunk chunk = chestLoc.getWorld().getChunkAt(chestLoc.getBlockX() >> 4, chestLoc.getBlockZ() >> 4);

        if (chunk == null || !chunk.isLoaded()) {
            return;
        }

        Chest chest = null;

        if (chestLoc.getBlock().getType() == Material.CHEST) {
            chest = (Chest) chestLoc.getBlock().getState();
        } else {
            unregisterChest(chestLoc);
            return;
        }

        if (chest == null) {
            unregisterChest(chestLoc);
            return;
        }

        if (plugin.getRandomUser() == null) {
            return;
        }

        OfflinePlayer sellPlayer = Bukkit.getOfflinePlayer(getOwner(chest));
        if (plugin.tradeMe != null) {
            Player onlinePlayer = null;
            if (sellPlayer.isOnline() && (onlinePlayer = sellPlayer.getPlayer()) != null) {
                if (TradeMe.getInstance().getUtil().isTrading(onlinePlayer)) {
                    return;
                }
            }
        }

        double totalSale = 0.0;
        int slot = 0;
        ArrayList<Integer> slots = new ArrayList<>();
        for (ItemStack ischest : chest.getInventory()) {
            if (ischest == null || ischest.getType().equals(Material.AIR) || ischest.getType() == null) {
                slot++;
                continue;
            }

            double price = plugin.priceManager.getPrice(ischest);

            if (price <= 0) {
                slot++;
                continue;
            }

            slots.add(slot);
            totalSale += (price);
            slot++;
        }

        for (int s : slots) {
            chest.getInventory().setItem(s, new ItemStack(Material.AIR));
        }

        if (totalSale > 0) {
            plugin.getEconomy().depositPlayer(sellPlayer, totalSale);
        }
    }

    public boolean isVoidChest(Location location) {
        String loc = Utils.getStringLocation(location);
        if (chests.containsKey(loc)) {
            return true;
        }
        return false;
    }
}
