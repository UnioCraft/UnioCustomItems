package me.uniodex.uniocustomitems.utils;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

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

    public static World getWorldString(final String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            return w;
        }
        return null;
    }

    public static int getXString(final String s) {
        if (s == null || s.trim() == "") {
            return -1;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final int x = Integer.parseInt(parts[1]);
            return x;
        }
        return -1;
    }

    public static int getZString(final String s) {
        if (s == null || s.trim() == "") {
            return -1;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final int z = Integer.parseInt(parts[3]);
            return z;
        }
        return -1;
    }

    public static String secondsToString(Long seconds) {
        long second = seconds % 60;
        long minute = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long day = seconds / 86400;

        if (day == 0 && minute == 0 && hours == 0) {
            return second + " saniye";
        }

        if (day == 0 && hours == 0) {
            return minute + " dakika, " + second + " saniye";
        }

        if (day == 0) {
            return hours + " saat, " + minute + " dakika, " + second + " saniye";
        }

        return day + " gün, " + hours + " saat, " + minute + " dakika, " + second + " saniye";
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
}
