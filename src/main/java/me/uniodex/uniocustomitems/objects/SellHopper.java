package me.uniodex.uniocustomitems.objects;

import me.Zrips.TradeMe.TradeMe;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class SellHopper {

    private CustomItems plugin;

    public SellHopper(CustomItems plugin) {
        this.plugin = plugin;
    }

    public void executeSell(UUID player, Location hopperLoc) {
        if (hopperLoc == null) return;

        Hopper hopper = null;

        if (hopperLoc.getBlock().getType() == Material.HOPPER) {
            hopper = (Hopper) hopperLoc.getBlock().getState();
        }

        if (hopper == null) return;

        if (plugin.getRandomUser() == null) {
            return;
        }

        OfflinePlayer sellPlayer = Bukkit.getOfflinePlayer(player);
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
        for (ItemStack ischest : hopper.getInventory()) {
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
            hopper.getInventory().setItem(s, new ItemStack(Material.AIR));
        }

        if (totalSale > 0) {
            plugin.getEconomy().depositPlayer(sellPlayer, totalSale);
        }
    }
}
