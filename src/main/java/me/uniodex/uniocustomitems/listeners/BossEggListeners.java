package me.uniodex.uniocustomitems.listeners;

import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.hooks.Hook;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BossEggListeners implements Listener {

    private CustomItems plugin;

    public BossEggListeners(CustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBossEgg(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Location location = event.getClickedBlock().getLocation();

        if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getInventory().getItemInMainHand() == null) return;
            if (!player.getInventory().getItemInMainHand().getType().equals(Material.MONSTER_EGG)) return;
            if (!plugin.getItemManager().isItemNamed(player.getInventory().getItemInMainHand())) return;

            for (Hook hook : plugin.getHookManager().getHooks()) {
                if (!hook.canUseBossEgg(player, location)) {
                    player.sendMessage(hook.getDenyMessage());
                    return;
                }
            }

            String bossName = null;

            for (String name : plugin.getItemManager().getBossEggs().keySet()) {
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(name)) {
                    bossName = plugin.getItemManager().getBossEggs().get(name);
                    break;
                }
            }

            if (bossName == null) return;

            if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            } else {
                player.getInventory().removeItem(player.getInventory().getItemInMainHand());
            }

            Location spawnLoc = location.clone();
            spawnLoc.add(0, 1, 0);
            ActiveMob mob = plugin.getMythicMobs().getMobManager().spawnMob(bossName, spawnLoc);
            plugin.getUnioProtections().getCheckManager().getBossOwnerManager().addBossOwner(player.getName(), mob.getLivingEntity().getUniqueId());
            event.setCancelled(true);
        }
    }
}
