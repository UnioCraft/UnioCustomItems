package me.uniodex.uniocustomitems.listeners;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.hooks.Hook;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WandListeners implements Listener {

    private CustomItems plugin;

    public WandListeners(CustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.isCancelled()) return;
            Block b = event.getClickedBlock();
            if (b.getState() instanceof Container) {
                if (plugin.getItemManager().isItemNamed(player.getInventory().getItemInMainHand()) &&
                        player.getInventory().getItemInMainHand().getType().equals(Material.GOLD_HOE) &&
                        player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.getItemManager().getSellWand().getItemMeta().getDisplayName())) {

                    event.setCancelled(true);

                    for (Hook hook : plugin.getHookManager().getHooks()) {
                        if (!hook.canUseSellWand(player, b.getLocation())) {
                            player.sendMessage(hook.getDenyMessage());
                            return;
                        }
                    }

                    plugin.getSellWand().executeSell(b, player.getName());

                } else if (plugin.getItemManager().isItemNamed(player.getInventory().getItemInMainHand()) &&
                        player.getInventory().getItemInMainHand().getType().equals(Material.GOLD_AXE) &&
                        player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.getItemManager().getFixWand().getItemMeta().getDisplayName())) {

                    event.setCancelled(true);

                    for (Hook hook : plugin.getHookManager().getHooks()) {
                        if (!hook.canUseFixWand(player, b.getLocation())) {
                            player.sendMessage(hook.getDenyMessage());
                            return;
                        }
                    }
                    plugin.getFixWand().executeFix(b);

                }
            } else if (b.getType().equals(Material.DIRT) || (b.getType().equals(Material.GRASS))) {
                if (plugin.getItemManager().isItemNamed(player.getInventory().getItemInMainHand()) &&
                        player.getInventory().getItemInMainHand().getType().equals(Material.GOLD_HOE) &&
                        player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.getItemManager().getSellWand().getItemMeta().getDisplayName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
