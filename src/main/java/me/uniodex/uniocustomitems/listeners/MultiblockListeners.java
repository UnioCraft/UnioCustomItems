package me.uniodex.uniocustomitems.listeners;

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
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.hooks.Hook;
import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MultiblockListeners implements Listener {

    private CustomItems plugin;
    private WorldEditPlugin wpl;

    private Set<Integer> disabledBlocks = new HashSet<>(Arrays.asList(
            BlockID.AIR,
            BlockID.BEDROCK,
            BlockID.MOB_SPAWNER,
            BlockID.CHEST,
            BlockID.TRAPPED_CHEST,
            BlockID.HOPPER,
            BlockID.DARK_OAK_DOOR,
            BlockID.ACACIA_DOOR,
            BlockID.SPRUCE_DOOR,
            BlockID.BIRCH_DOOR,
            BlockID.JUNGLE_DOOR,
            BlockID.WOODEN_DOOR,
            BlockID.IRON_DOOR,
            BlockID.TRAP_DOOR,
            BlockID.IRON_TRAP_DOOR
    ));

    public MultiblockListeners(CustomItems plugin) {
        this.plugin = plugin;
        wpl = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.useItemInHand() == Result.DENY) {
            return;
        }

        if (plugin.getItemManager().isItemNamed(event.getItem())) {
            return;
        }

        if (!plugin.getItemManager().isItSame(event.getItem(), plugin.getItemManager().getMultiblock())) {
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

            // At this time, only handle interaction from players
            if (actor != null) {
                LocalSession session = wpl.getWorldEdit().getSessionManager().get(actor);

                final BlockTool superPickaxe = session.getSuperPickaxe();
                if (superPickaxe != null) {
                    event.setCancelled(actPrimary(wpl.getWorldEdit().getPlatformManager().queryCapability(Capability.WORLD_EDITING), wpl.getWorldEdit().getPlatformManager().getConfiguration(), player, session, pos));
                }
            }
        }
    }

    private boolean actPrimary(Platform server, LocalConfiguration config, Player player, LocalSession session, com.sk89q.worldedit.util.Location clicked) {
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
                    main:
                    for (int z = oz - 1; z <= oz + 1; ++z) {
                        Vector pos = new Vector(x, y, z);
                        org.bukkit.World world = Bukkit.getWorld(player.getWorld().getName());
                        org.bukkit.Location location = new org.bukkit.Location(world, pos.getX(), pos.getY(), pos.getZ());
                        org.bukkit.block.Block block = world.getBlockAt(location);
                        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.getName());

                        if (disabledBlocks.contains(editSession.getBlockType(pos))) continue;

                        if (editSession.getBlockType(pos) == BlockID.END_PORTAL_FRAME) {
                            if (plugin.getAskyblock() != null) {
                                ItemStack endFrame = new ItemStack(Material.ENDER_PORTAL_FRAME);
                                if (ASkyBlockAPI.getInstance().locationIsOnIsland(bukkitPlayer, location)) {
                                    if (block.getType().equals(Material.ENDER_PORTAL_FRAME)) {
                                        if (bukkitPlayer.getInventory().firstEmpty() != -1) {
                                            block.setType(Material.AIR);
                                            bukkitPlayer.getInventory().addItem(endFrame);
                                        } else {
                                            block.setType(Material.AIR);
                                            location.setY(location.getY() + 0.80);
                                            block.getWorld().dropItemNaturally(location, endFrame);
                                        }
                                    }
                                }
                            }
                            continue;
                        }

                        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, bukkitPlayer);
                        Bukkit.getPluginManager().callEvent(blockBreakEvent);
                        if (blockBreakEvent.isCancelled()) {
                            continue;
                        }

                        for (Hook hook : plugin.getHookManager().getHooks()) {
                            if (!hook.canBuild(bukkitPlayer, location)) {
                                continue main;
                            }
                        }

                        // why nms? because otherwise it just breaks block and doesn't give metadata
                        ((CraftPlayer) bukkitPlayer).getHandle().playerInteractManager.breakBlock(new BlockPosition(pos.getX(), pos.getY(), pos.getZ()));
                    }
                }
            }
        } finally {
            editSession.flushQueue();
            session.remember(editSession);
        }

        return true;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onItemDamage(PlayerItemDamageEvent event) {
        if (plugin.getItemManager().isItSame(event.getItem(), plugin.getItemManager().getMultiblock())) {
            event.setCancelled(true);
        }
    }
}