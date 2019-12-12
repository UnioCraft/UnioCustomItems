package me.uniodex.uniocustomitems.listeners;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class SellChestListeners implements Listener {

    private CustomItems plugin;
    private BlockFace[] chestFaces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

    public SellChestListeners(CustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) {
        Block b = event.getBlock();
        Player p = event.getPlayer();

        if (b.getType() == Material.CHEST) {
            for (BlockFace face : chestFaces) {
                if (b.getRelative(face).getType().equals(Material.CHEST)) {
                    if (plugin.getSellChest().getChests().containsKey(Utils.getStringLocation(b.getRelative(face).getLocation()))) {
                        event.setCancelled(true);
                        p.sendMessage(plugin.getMessage("sellChest.youCantMerge"));
                        return;
                    }
                }
            }
        }

        if (b.getType() == Material.CHEST
                && event.getItemInHand().hasItemMeta()
                && event.getItemInHand().getItemMeta().getDisplayName() != null
                && event.getItemInHand().getItemMeta().getDisplayName().equals(plugin.getItemManager().getSellChest().getItemMeta().getDisplayName())) {

            for (BlockFace face : chestFaces) {
                if (b.getRelative(face).getType().equals(Material.CHEST)) {
                    event.setCancelled(true);
                    p.sendMessage(plugin.getMessage("sellChest.youCantMerge"));
                    return;
                }
            }
            plugin.getSellChest().registerChest(p.getName(), (Chest) b.getState());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        Block b = event.getBlock();
        Player p = event.getPlayer();

        if (b.getType() == Material.CHEST) {
            String loc = Utils.getStringLocation(b.getLocation());
            if (plugin.getSellChest().getChests().containsKey(loc)) {
                event.setCancelled(true);
                if (p.getInventory().firstEmpty() == -1) {
                    p.sendMessage(plugin.getMessage("sellChest.noEmptySpace"));
                    return;
                }
                plugin.getSellChest().unregisterChest(b.getLocation());
                b.setType(Material.AIR);
                p.getInventory().addItem(plugin.getItemManager().getSellChest());
                p.updateInventory();
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onInventoryMoveItemLowest(final InventoryMoveItemEvent event) {
        if (event.getSource().getHolder() instanceof Chest && plugin.getSellChest().isVoidChest(((Chest) event.getSource().getHolder()).getLocation())) {
            event.setCancelled(true);
        }
    }
}
