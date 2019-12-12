package me.uniodex.uniocustomitems.managers;

import lombok.Getter;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    private CustomItems plugin;

    @Getter
    private Map<String, ItemStack> items = new HashMap<>(); // Item Name / ItemStack
    @Getter
    private Map<String, String> bossEggs = new HashMap<>(); // Item Name / Boss Type
    @Getter
    private Map<String, String> vipCards = new HashMap<>(); // Item Name / VIP Type

    @Getter
    private ItemStack sellChest;
    @Getter
    private ItemStack fixWand;
    @Getter
    private ItemStack sellWand;
    @Getter
    private ItemStack multiblock;
    @Getter
    private ItemStack flyperk;

    public ItemManager(CustomItems plugin) {
        this.plugin = plugin;
        initItems();
        initSpecialItems();
        initBossEggs();
        initVIPCards();
    }

    private void initItems() {
        for (String item : plugin.getConfig().getConfigurationSection("items").getKeys(false)) {
            String type = plugin.getConfig().getString("items." + item + ".type");
            String name = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("items." + item + ".name"));
            List<String> lore = plugin.getConfig().getStringList("items." + item + ".lore");
            lore.replaceAll(l -> ChatColor.translateAlternateColorCodes('&', l));
            List<String> flags = plugin.getConfig().getStringList("items." + item + ".flags");
            List<String> enchants = plugin.getConfig().getStringList("items." + item + ".enchants");
            List<String> bookenchants = plugin.getConfig().getStringList("items." + item + ".bookenchants");

            ItemStack itemStack = new ItemStack(Material.getMaterial(type));

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(lore);

            // Flags
            for (String flag : flags) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }

            // Enchants
            for (String enchant : enchants) {
                meta.addEnchant(Enchantment.getByName(enchant.split(":")[0]), Integer.parseInt(enchant.split(":")[1]), true);
            }

            itemStack.setItemMeta(meta);

            // Book Enchants
            if (itemStack.getType().equals(Material.ENCHANTED_BOOK)) {
                EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                for (String bookenchant : bookenchants) {
                    bookMeta.addStoredEnchant(Enchantment.getByName(bookenchant.split(":")[0]), Integer.parseInt(bookenchant.split(":")[1]), true);
                }
                itemStack.setItemMeta(bookMeta);
            }

            items.put(item, itemStack);
        }
    }

    private void initSpecialItems() {
        this.sellChest = items.get(plugin.getConfig().getString("specialItems.sellchest"));
        this.fixWand = items.get(plugin.getConfig().getString("specialItems.fixwand"));
        this.sellWand = items.get(plugin.getConfig().getString("specialItems.sellwand"));
        this.multiblock = items.get(plugin.getConfig().getString("specialItems.multiblock"));
        this.flyperk = items.get(plugin.getConfig().getString("specialItems.flyperk"));
    }

    private void initBossEggs() {
        for (String itemName : plugin.getConfig().getConfigurationSection("bossEggs").getKeys(false)) {
            bossEggs.put(itemName, plugin.getConfig().getString(itemName));
        }
    }

    private void initVIPCards() {
        for (String itemName : plugin.getConfig().getConfigurationSection("vipCards").getKeys(false)) {
            vipCards.put(itemName, plugin.getConfig().getString(itemName));
        }
    }

    public boolean isItemNamed(ItemStack item) {
        if (item == null) return false;
        if (item.getItemMeta() == null) return false;
        if (item.getItemMeta().getDisplayName() == null) return false;
        return true;
    }

    /*
        WARNING! This will only work for UCI items. Don't use it with Minecraft items.
     */
    public boolean isItSame(ItemStack item1, ItemStack item2) {
        if (isItemNamed(item1) && isItemNamed(item2)) {
            return item1.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName());
        }
        return false;
    }

    public ItemStack getItem(String name) {
        return items.get(name);
    }
}
