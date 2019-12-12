package me.uniodex.uniocustomitems.listeners;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class EnchantmentBookListeners implements Listener {

    private CustomItems plugin;

    public EnchantmentBookListeners(CustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack leftItem = inventory.getItem(0);
        ItemStack rightItem = inventory.getItem(1);
        if (leftItem == null) return;
        if (rightItem == null) return;
        ItemStack result = event.getResult();

        if (Utils.isArmor(leftItem) && Utils.isProtection5Book(rightItem)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) result.getItemMeta();
            meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            result.setItemMeta(meta);
            event.setResult(result);
        }
    }
}
