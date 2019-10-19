package me.uniodex.uniocustomitems.managers;

import com.earth2me.essentials.craftbukkit.SetExpFix;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class XPBottleManager {

    private CustomItems plugin;
    private String displayName = ChatColor.YELLOW + "Tecrübe İksiri";

    public XPBottleManager(CustomItems plugin) {
        this.plugin = plugin;
    }

    public String giveXPBottle(Player player, Integer amount) {
        if (SetExpFix.getTotalExperience(player) < amount) {
            return CustomItems.hataprefix + "Yeterli XP'ye sahip olmadığınız için tecrübe iksiri yaratılamadı.";
        }
        SetExpFix.setTotalExperience(player, SetExpFix.getTotalExperience(player) - amount);
        player.getInventory().addItem(getXPBottle(player.getName(), amount));
        return CustomItems.bilgiprefix + "Tecrübenizi başarıyla şişelediniz.";
    }

    public ItemStack getXPBottle(String player, Integer amount) {
        ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta meta = xpBottle.getItemMeta();
        meta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add(ChatColor.GOLD + "XP Miktarı: " + ChatColor.GREEN + amount);
        lore.add(ChatColor.GOLD + "İksir Sahibi: " + ChatColor.GREEN + player);
        lore.add(" ");
        meta.setLore(lore);
        xpBottle.setItemMeta(meta);

        return Utils.addGlow(xpBottle);
    }

    public int getXPFromBottle(ItemStack item) {
        if (!isXPBottle(item)) return -1;

        String amountLore = ChatColor.stripColor(item.getItemMeta().getLore().get(1));

        return Integer.valueOf(amountLore.substring(amountLore.lastIndexOf(" ") + 1));
    }

    public boolean isXPBottle(ItemStack item) {
        if (!item.getType().equals(Material.EXP_BOTTLE)) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().hasDisplayName()) return false;
        if (!item.getItemMeta().hasLore()) return false;

        if (!item.getItemMeta().getDisplayName().equals(displayName)) return false;
        return true;
    }

}
