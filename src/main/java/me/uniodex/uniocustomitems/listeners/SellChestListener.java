package me.uniodex.uniocustomitems.listeners;

import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import me.uniodex.uniocustomitems.CustomItems;

public class SellChestListener implements Listener {

	private CustomItems plugin;

	public SellChestListener(CustomItems plugin) {
		this.plugin = plugin;
	}

	/*
	 * Potential dupe fix
	 */
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onInventoryMoveItemLowest(final InventoryMoveItemEvent event) {
		if (event.getSource().getHolder() instanceof Chest && plugin.sellChest.isVoidChest(((Chest) event.getSource().getHolder()).getLocation())) {
			event.setCancelled(true);
		}
	}

	/*@EventHandler(priority=EventPriority.HIGH)
	public void onInventoryClick(final InventoryClickEvent event) {
		if (event.isCancelled()) return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (event.getView().getTopInventory().getType().equals(InventoryType.CHEST) && event.getView().getTopInventory().getHolder() instanceof Chest) {
					Chest chest = (Chest) event.getView().getTopInventory().getHolder();
					if (chest != null) {
						if (plugin.sellChest.isVoidChest(chest.getLocation())) {
							plugin.sellChest.executeSell(chest.getLocation());
						}
					}
				}
			}
		}, 1L);
	}*/
}
