package me.uniodex.uniocustomitems.listeners;

import com.earth2me.essentials.craftbukkit.SetExpFix;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class XPBottleListeners implements Listener {

    private CustomItems plugin;

    public XPBottleListeners(CustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onUse(PlayerInteractEvent event) {
        if (event.isCancelled() && !event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }
        Player player = event.getPlayer();
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if (event.getItem() == null) return;

        if (!plugin.getXpBottleManager().isXPBottle(event.getItem())) return;

        event.setCancelled(true);

        ItemStack xpBottle = event.getItem();
        Integer xpAmount = plugin.getXpBottleManager().getXPFromBottle(xpBottle);

        if (event.getItem().getAmount() > 1) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        } else {
            player.getInventory().remove(event.getItem());
        }

        SetExpFix.setTotalExperience(player, SetExpFix.getTotalExperience(player) + xpAmount);
        player.sendMessage(plugin.getMessage("xpBottle.used").replaceAll("%xpAmount%", String.valueOf(xpAmount)));
    }

}
