package me.uniodex.uniocustomitems.managers;

import com.earth2me.essentials.craftbukkit.SetExpFix;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class XPBottleManager {

    private CustomItems plugin;

    public XPBottleManager(CustomItems plugin) {
        this.plugin = plugin;
    }

    public String giveXPBottle(Player player, Integer amount) {
        if (player.getInventory().firstEmpty() == -1) {
            return plugin.getMessage("commands.xpbottle.notEnoughSpace");
        }
        if (SetExpFix.getTotalExperience(player) < amount) {
            return plugin.getMessage("commands.xpbottle.notEnoughXP");
        }
        SetExpFix.setTotalExperience(player, SetExpFix.getTotalExperience(player) - amount);
        player.getInventory().addItem(getXPBottle(player.getName(), amount));
        return plugin.getMessage("commands.xpbottle.success");
    }

    private ItemStack getXPBottle(String player, Integer amount) {
        ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta meta = xpBottle.getItemMeta();
        meta.setDisplayName(plugin.getMessage("xpBottle.name"));
        List<String> lore = plugin.getMessages("xpBottle.lore");
        lore.replaceAll(l -> l.replaceAll("%amount%", String.valueOf(amount)).replaceAll("%player%", player));
        meta.setLore(lore);
        xpBottle.setItemMeta(meta);

        return Utils.addGlow(xpBottle);
    }

    public int getXPFromBottle(ItemStack item) {
        if (!isXPBottle(item)) return -1;

        String amountLore = ChatColor.stripColor(item.getItemMeta().getLore().get(1));

        return Integer.parseInt(amountLore.substring(amountLore.lastIndexOf(" ") + 1));
    }

    public boolean isXPBottle(ItemStack item) {
        if (!item.getType().equals(Material.EXP_BOTTLE)) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().hasDisplayName()) return false;
        if (!item.getItemMeta().hasLore()) return false;

        if (!item.getItemMeta().getDisplayName().equals(plugin.getMessage("xpBottle.name"))) return false;
        return true;
    }

}
