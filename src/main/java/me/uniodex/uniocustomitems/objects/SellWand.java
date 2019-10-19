package me.uniodex.uniocustomitems.objects;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.Zrips.TradeMe.TradeMe;
import me.crafter.mc.lockettepro.LocketteProAPI;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.FactionsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class SellWand {

    private CustomItems plugin;

    public SellWand(CustomItems plugin) {
        this.plugin = plugin;
    }

    public void executeSell(Block block, String player) {
        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            if (!chest.getChunk().isLoaded()) {
                return;
            }
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

            plugin.getEconomy().depositPlayer(sellPlayer, totalSale);
        } else if (block.getState() instanceof Hopper) {
            Hopper hopper = (Hopper) block.getState();
            if (!hopper.getChunk().isLoaded()) {
                return;
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

            OfflinePlayer sellPlayer = Bukkit.getOfflinePlayer(player);

            if (totalSale > 0) {
                plugin.getEconomy().depositPlayer(sellPlayer, totalSale);
            }
        }
    }

    public boolean canUseWand(Player player, Block blck) {

        if (!WGBukkit.getPlugin().canBuild(player, blck)) {
            return false;
        }

        if (plugin.lockettePro != null) {
            if (LocketteProAPI.isProtected(blck)) {
                if (LocketteProAPI.isLocked(blck) && !LocketteProAPI.isUser(blck, player)) {
                    return false;
                }
            }
        }

        if (plugin.factions != null) {
            if (!FactionsUtils.canPlayerUseWandFactions(player, blck, true)) {
				/* TODO if (!LocketteProFeatures.canPlayerInteractFactions(player, blck)) {
					return false;
				}*/
            }
        }

        if (plugin.askyblock != null) {
            if (!ASkyBlockAPI.getInstance().playerIsOnIsland(player)) {
                return false;
            }
        }

        return true;
    }
}
