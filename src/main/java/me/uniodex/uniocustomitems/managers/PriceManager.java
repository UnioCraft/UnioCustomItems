package me.uniodex.uniocustomitems.managers;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IEssentials;
import me.uniodex.uniocustomitems.CustomItems;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public class PriceManager {

    private CustomItems plugin;

    public PriceManager(CustomItems plugin) {
        this.plugin = plugin;
    }

    public double getPrice(ItemStack item) {
        if (plugin.shopGui != null) {
            return ShopGuiPlusApi.getItemStackPriceSell(plugin.getRandomUser(), item);
        } else if (plugin.essPlugin != null) {
            BigDecimal worth = ((Essentials) plugin.essPlugin).getWorth().getPrice((IEssentials) plugin.essPlugin, item);
            if (worth != null) {
                return worth.doubleValue() * item.getAmount();
            }
        }
        return -1;
    }
}
