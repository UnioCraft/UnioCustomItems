package me.uniodex.uniocustomitems.utils;

import me.uniodex.uniocustomitems.CustomItems;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * Converts a location to a simple string representation
     * If location is null, returns empty string
     *
     * @param l
     * @return
     */
    static public String getStringLocation(final Location l) {
        if (l == null) {
            return "";
        }
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    /**
     * Converts a serialized location to a Location. Returns null if string is empty
     *
     * @param s - serialized location in format "world:x:y:z"
     * @return Location
     */
    static public Location getLocationString(final String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }

    static public Location getLocationStringWithoutChunkLoad(final String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            if (w == null) {
                return null;
            }
            if (w.isChunkLoaded(x >> 4, z >> 4)) {
                return new Location(w, x, y, z);
            }
        }
        return null;
    }

    public static ItemStack addGlow(ItemStack item) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }

    public static boolean isArmor(ItemStack item) {
        return item.getType().name().toUpperCase().contains("CHESTPLATE")
                || item.getType().name().toUpperCase().contains("LEGGINGS")
                || item.getType().name().toUpperCase().contains("BOOTS")
                || item.getType().name().toUpperCase().contains("HELMET");
    }

    public static boolean isProtection5Book(ItemStack item) {
        if (!item.getType().equals(Material.ENCHANTED_BOOK)) return false;
        if (!item.hasItemMeta()) return false;
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        if (!meta.hasStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) return false;
        return (meta.getStoredEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 5);
    }

    public static Player getRandomUser() {
        if (Bukkit.getOnlinePlayers().size() < 1) {
            return null;
        }
        return Bukkit.getOnlinePlayers().iterator().next();
    }

    public static String colorizeMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%hataPrefix%", CustomItems.hataPrefix).replaceAll("%bilgiPrefix%", CustomItems.bilgiPrefix).replaceAll("%dikkatPrefix%", CustomItems.dikkatPrefix).replaceAll("%prefix%", CustomItems.bilgiPrefix));
    }

    public static List<String> colorizeMessages(List<String> messages) {
        List<String> newList = new ArrayList<>();
        for (String msg : messages) {
            newList.add(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%hataPrefix%", CustomItems.hataPrefix).replaceAll("%bilgiPrefix%", CustomItems.bilgiPrefix).replaceAll("%dikkatPrefix%", CustomItems.dikkatPrefix).replaceAll("%prefix%", CustomItems.bilgiPrefix)));
        }
        return newList;
    }

    public static boolean isThereEnoughSpace(ItemStack item, Inventory inventory) {
        int freeSpace = 0;
        for (ItemStack i : inventory) {
            if (i == null) {
                freeSpace += item.getType().getMaxStackSize();
            } else if (i.getType() == item.getType()) {
                freeSpace += i.getType().getMaxStackSize() - i.getAmount();
            }
        }
        return item.getAmount() <= freeSpace;
    }

    public static boolean isThereEnoughSpace(List<ItemStack> items, Inventory inventory) {
        for (ItemStack item : items) {
            if (!isThereEnoughSpace(item, inventory)) {
                return false;
            }
        }
        return true;
    }

    public static String getTimeAsHours() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static String getTimeAsYearMonthDay() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
    }
}
