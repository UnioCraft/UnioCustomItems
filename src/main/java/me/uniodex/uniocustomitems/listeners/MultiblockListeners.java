package me.uniodex.uniocustomitems.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.command.tool.BlockTool;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extension.platform.Capability;
import com.sk89q.worldedit.extension.platform.Platform;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.managers.ItemManager.Items;
import me.crafter.mc.lockettepro.LocketteProAPI;
import net.minecraft.server.v1_12_R1.BlockPosition;

public class MultiblockListeners implements Listener {

	private CustomItems plugin;
	private WorldEditPlugin wpl;

	public MultiblockListeners(CustomItems plugin) {
		this.plugin = plugin;
		wpl = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit"); 
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.isCancelled()) return;

		if (event.useItemInHand() == Result.DENY) {
			return;
		}

		if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null) {
			return;
		}

		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.itemManager.getItem(Items.buyuluAlanKazmasi).getItemMeta().getDisplayName())) {
			return;
		}

		final Player player = wpl.wrapPlayer(event.getPlayer());
		final World world = player.getWorld();

		Action action = event.getAction();
		if (action == Action.LEFT_CLICK_BLOCK) {
			final Block clickedBlock = event.getClickedBlock();
			final Location pos = new Location(world, clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ());

			// Create a proxy actor with a potentially different world for
			// making changes to the world
			Actor actor = wpl.getWorldEdit().getPlatformManager().createProxyActor(player);

			Location location = pos;

			// At this time, only handle interaction from players
			if (actor instanceof Player) {
				LocalSession session = wpl.getWorldEdit().getSessionManager().get(actor);


				final BlockTool superPickaxe = session.getSuperPickaxe();
				if (superPickaxe != null) {
					event.setCancelled(actPrimary(wpl.getWorldEdit().getPlatformManager().queryCapability(Capability.WORLD_EDITING), wpl.getWorldEdit().getPlatformManager().getConfiguration(), player, session, location));
					return;
				}


			}
		}
	}

	@SuppressWarnings("deprecation")
	public boolean actPrimary(Platform server, LocalConfiguration config, Player player, LocalSession session, com.sk89q.worldedit.util.Location clicked) {
		int ox = clicked.getBlockX();
		int oy = clicked.getBlockY();
		int oz = clicked.getBlockZ();
		int initialType = ((World) clicked.getExtent()).getBlockType(clicked.toVector());

		if (initialType == 0) {
			return true;
		}

		EditSession editSession = session.createEditSession(player);
		editSession.getSurvivalExtent().setToolUse(config.superPickaxeManyDrop);

		try {
			for (int x = ox - 1; x <= ox + 1; ++x) {
				for (int y = oy - 1; y <= oy + 1; ++y) {
					for (int z = oz - 1; z <= oz + 1; ++z) {
						Vector pos = new Vector(x, y, z);

						if (editSession.getBlockType(pos) == 0) {
							continue;
						}

						if (editSession.getBlockType(pos) == BlockID.BEDROCK) {
							continue;
						}

						if (editSession.getBlockType(pos) == BlockID.MOB_SPAWNER) {
							continue;
						}

						if (editSession.getBlockType(pos) == BlockID.CHEST) {
							continue;
						}

						if (editSession.getBlockType(pos) == BlockID.HOPPER) {
							continue;
						}

						if (editSession.getBlockType(pos) == BlockID.TRAPPED_CHEST) {
							continue;
						}

						if (editSession.getBlockType(pos) == BlockID.END_PORTAL_FRAME) {
							if (plugin.askyblock != null) {
								org.bukkit.World wrld =  Bukkit.getWorld(player.getWorld().getName());
								org.bukkit.Location loc = new org.bukkit.Location(wrld, pos.getX(), pos.getY(), pos.getZ()); 
								org.bukkit.block.Block b = wrld.getBlockAt(loc);
								org.bukkit.entity.Player p = Bukkit.getPlayer(player.getName());
								ItemStack endFrame = new ItemStack(Material.ENDER_PORTAL_FRAME);

								if (ASkyBlockAPI.getInstance().locationIsOnIsland(p, loc)) {
									if (b.getType().equals(Material.ENDER_PORTAL_FRAME)) {
										if (p.getInventory().firstEmpty() != -1) {
											b.setType(Material.AIR);
											p.getInventory().addItem(endFrame);
										}else {
											b.setType(Material.AIR);
											loc.setY(loc.getY() + 0.80);
											b.getWorld().dropItemNaturally(loc, endFrame);
										}
									}
								}
							}
							continue;
						}

						if (editSession.getBlockType(pos) == BlockID.ACACIA_DOOR || editSession.getBlockType(pos) == BlockID.BIRCH_DOOR || editSession.getBlockType(pos) == BlockID.DARK_OAK_DOOR || editSession.getBlockType(pos) == BlockID.JUNGLE_DOOR || editSession.getBlockType(pos) == BlockID.IRON_DOOR || editSession.getBlockType(pos) == BlockID.SPRUCE_DOOR || editSession.getBlockType(pos) == BlockID.WOODEN_DOOR) {
							continue;
						}

						org.bukkit.World wrld =  Bukkit.getWorld(player.getWorld().getName());
						org.bukkit.Location loc = new org.bukkit.Location(wrld, pos.getX(), pos.getY(), pos.getZ()); 
						org.bukkit.block.Block blck = wrld.getBlockAt(loc);
						org.bukkit.entity.Player plyr = Bukkit.getPlayer(player.getName());
						if (!WGBukkit.getPlugin().canBuild(Bukkit.getPlayer(player.getName()), blck))
						{
							continue;
						}
						BlockBreakEvent blckbreak = new BlockBreakEvent(blck, plyr);
						Bukkit.getPluginManager().callEvent(blckbreak);
						if (blckbreak.isCancelled()) {
							continue;
						}

						if (plugin.lockettePro != null) {
							if (LocketteProAPI.isProtected(blck)) {
								continue;
							}
						}

						if (plugin.factions != null) {
							if (!FactionsBlockListener.playerCanBuildDestroyBlock(plyr, blck.getLocation(), "destroy", false))
							{
								continue;
							}
						}

						// why nms? because otherwise it just breaks block and doesn't give metadata
						((CraftPlayer) plyr).getHandle().playerInteractManager.breakBlock(new BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
					}
				}
			}
		} finally {
			editSession.flushQueue();
			session.remember(editSession);
		}

		return true;
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e) {
		if (e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.buyuluAlanKazmasi).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
		}
	}
}