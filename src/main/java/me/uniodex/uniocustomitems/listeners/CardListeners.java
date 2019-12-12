package me.uniodex.uniocustomitems.listeners;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class CardListeners implements Listener {

    private CustomItems plugin;

    private Map<String, String> confirmation = new HashMap<>(); // Player, Card Type

    public CardListeners(CustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        final String playerName = player.getName();

        if (action.equals(Action.RIGHT_CLICK_AIR)) {
            if (plugin.getItemManager().isItemNamed(player.getInventory().getItemInMainHand()) &&
                    player.getInventory().getItemInMainHand().getType().equals(Material.PAPER)) {

                // Fly Perk
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.getItemManager().getFlyperk().getItemMeta().getDisplayName())) {
                    if (!player.hasPermission(plugin.getConfig().getString("flyPerm"))) {
                        event.setCancelled(true);
                        if (!confirmation.containsKey(player.getName()) || !confirmation.get(player.getName()).equals("flyPerk")) {
                            confirmation.put(player.getName(), "flyPerk");
                            player.sendMessage(plugin.getMessage("flyPerk.confirmation"));
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                if (confirmation.get(playerName).equals("flyPerk")) {
                                    confirmation.remove(playerName);
                                }
                            }, 200L);
                            return;
                        }
                        plugin.getPermission().playerAdd(null, player, plugin.getConfig().getString("flyPerm"));
                        if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                        } else {
                            player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                        }
                        player.updateInventory();
                        player.sendMessage(plugin.getMessage("flyPerk.activated"));
                        confirmation.remove(playerName);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(plugin.getMessage("flyPerk.alreadyActivated"));
                    }
                }

                // VIP card
                String vipType = null;

                for (String name : plugin.getItemManager().getVipCards().keySet()) {
                    if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(name)) {
                        vipType = plugin.getItemManager().getVipCards().get(name);
                        break;
                    }
                }

                if (vipType == null) return;
                final String finalVipType = vipType;

                if (!confirmation.containsKey(player.getName()) || !confirmation.get(player.getName()).equals(vipType)) {
                    confirmation.put(player.getName(), vipType);
                    player.sendMessage(plugin.getMessage("vipCard.confirmation"));
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        if (confirmation.get(playerName).equals(finalVipType)) {
                            confirmation.remove(playerName);
                        }
                    }, 200L);
                    return;
                }

                if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                } else {
                    player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                }
                player.updateInventory();

                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    if (plugin.getVipManager().giveVIP(player.getName(), finalVipType)) {
                        player.sendMessage(plugin.getMessage("vipCard.vipActivated"));
                    } else {
                        player.sendMessage(plugin.getMessage("vipCard.vipNotActivated"));
                    }
                });
                confirmation.remove(playerName);
            }
        }
    }
}
