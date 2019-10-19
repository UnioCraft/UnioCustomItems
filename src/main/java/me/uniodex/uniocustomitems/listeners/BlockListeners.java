package me.uniodex.uniocustomitems.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.managers.ItemManager.Items;
import me.uniodex.uniocustomitems.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class BlockListeners implements Listener {

	private CustomItems plugin;
	private BlockFace[] chestFaces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

	public BlockListeners(CustomItems plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) return;
		
		Block b = event.getBlock();
		Player p = event.getPlayer();

		if (b.getType() == Material.CHEST) {
			for (BlockFace face : chestFaces){
				if (b.getRelative(face).getType().equals(Material.CHEST)) {
					if (plugin.sellChest.getChests().containsKey(Utils.getStringLocation(b.getRelative(face).getLocation()))) {
						event.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Satış Sandığını başka sandıklarla birleştiremezsiniz!");
						return;
					}
				}
			}
		}

		if (b.getType() == Material.CHEST 
				&& event.getItemInHand().hasItemMeta() 
				&& event.getItemInHand().getItemMeta().getDisplayName() != null 
				&& event.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Satış Sandığı")) {

			for (BlockFace face : chestFaces){
				if (b.getRelative(face).getType().equals(Material.CHEST)) {
					event.setCancelled(true);
					p.sendMessage(ChatColor.RED + "Satış Sandığını başka sandıklarla birleştiremezsiniz!");
					return;
				}
			}
			plugin.sellChest.registerChest(p.getName(), (Chest) b.getState());
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (event.isCancelled()) return;
		
		Block b = event.getBlock();
		Player p = event.getPlayer();
		
		if (b.getType() == Material.CHEST) {
			String loc = Utils.getStringLocation(b.getLocation());
			if (plugin.sellChest.getChests().containsKey(loc)) {
				event.setCancelled(true);
				if(p.getInventory().firstEmpty() == -1) {
					p.sendMessage(CustomItems.hataprefix + "Envanterinizde yer olmadığı için satış sandığını kıramadınız!");
					return;
				}
				plugin.sellChest.unregisterChest(b.getLocation());
				b.setType(Material.AIR);
				p.getInventory().addItem(plugin.itemManager.getItem(Items.satisSandigi));
				p.updateInventory();
			}
		}
	}
}
