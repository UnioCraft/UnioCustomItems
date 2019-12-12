package me.uniodex.uniocustomitems.managers;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.hooks.Hook;
import me.uniodex.uniocustomitems.utils.Utils;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EconomyManager {

    private CustomItems plugin;

    public EconomyManager(CustomItems plugin) {
        this.plugin = plugin;
    }

    public void executeSell(OfflinePlayer player, Container container) {
        if (container == null) return;
        if (player == null) return;
        if (Utils.getRandomUser() == null) return;

        if (player.getPlayer() != null) {
            for (Hook hook : plugin.getHookManager().getHooks()) {
                if (!hook.canTransactMoney(player.getPlayer())) {
                    return;
                }
            }
        }

        double totalSale = 0.0;
        int slot = 0;
        ArrayList<Integer> slots = new ArrayList<>();
        for (ItemStack containerItem : container.getInventory()) {
            if (containerItem == null || containerItem.getType().equals(Material.AIR) || containerItem.getType() == null) {
                slot++;
                continue;
            }

            double price = plugin.getEconomyManager().getPrice(containerItem);

            if (price <= 0) {
                slot++;
                continue;
            }

            slots.add(slot);
            totalSale += (price);
            slot++;
        }

        for (int s : slots) {
            container.getInventory().setItem(s, new ItemStack(Material.AIR));
        }

        if (totalSale > 0) {
            plugin.getEconomy().depositPlayer(player, totalSale);
        }
    }

    public double getPrice(ItemStack item) {
        if (plugin.getShopGui() != null) {
            return ShopGuiPlusApi.getItemStackPriceSell(Utils.getRandomUser(), item);
        } else if (plugin.getEssentials() != null) {
            BigDecimal worth = plugin.getEssentials().getWorth().getPrice(plugin.getEssentials(), item);
            if (worth != null) {
                return worth.doubleValue() * item.getAmount();
            }
        }
        return -1;
    }
}
