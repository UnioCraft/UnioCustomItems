package me.uniodex.uniocustomitems.listeners;

import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.managers.ItemManager.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListeners implements Listener {

    private CustomItems plugin;

    public InteractListeners(CustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBossEgg(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (p.getInventory().getItemInMainHand() == null) return;
            if (!p.getInventory().getItemInMainHand().getType().equals(Material.MONSTER_EGG)) return;
            String bossName = null;
            if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand())) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.pembepenceYumurta).getItemMeta().getDisplayName())) {
                    bossName = "Pembepence";
                } else if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.kemikkiranYumurta).getItemMeta().getDisplayName())) {
                    bossName = "Kemikkiran";
                } else if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.vahsiavciYumurta).getItemMeta().getDisplayName())) {
                    bossName = "VahsiAvci";
                } else if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.yarimbeyinYumurta).getItemMeta().getDisplayName())) {
                    bossName = "Yarimbeyin";
                } else if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.karacellatYumurta).getItemMeta().getDisplayName())) {
                    bossName = "Karacellat";
                } else if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.demirgovdeYumurta).getItemMeta().getDisplayName())) {
                    bossName = "Demirgovde";
                } else if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.cehennemtazisiYumurta).getItemMeta().getDisplayName())) {
                    bossName = "Cehennemtazisi";
                } else {
                    return;
                }
                Location loc = event.getClickedBlock().getLocation();
                if (p.getInventory().getItemInMainHand().getAmount() > 1) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                } else {
                    p.getInventory().removeItem(p.getInventory().getItemInMainHand());
                }
                Location spawnLoc = loc.clone();
                spawnLoc.add(0, 1, 0);
                ActiveMob mob = plugin.mythicMobs.getMobManager().spawnMob(bossName, spawnLoc);
                plugin.unioProtections.getCheckManager().getBossOwnerManager().addBossOwner(p.getName(), mob.getLivingEntity().getUniqueId());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.isCancelled()) return;
            Block b = event.getClickedBlock();
            if (b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST) || b.getType().equals(Material.HOPPER)) {
                if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                        p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_HOE) &&
                        p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.satisAsasi).getItemMeta().getDisplayName())) {
                    if (b.getState() instanceof Chest || b.getState() instanceof Hopper) {
                        event.setCancelled(true);
                        if (plugin.sellWand.canUseWand(p, b)) {
                            plugin.sellWand.executeSell(b, p.getName());
                        } else {
                            p.sendMessage(CustomItems.hataprefix + "Satış işlemi başarısız! Bloğa erişiminiz yok!");
                        }
                    }
                } else if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                        p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_AXE) &&
                        p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.tamirAsasi).getItemMeta().getDisplayName())) {
                    if (b.getState() instanceof Chest) {
                        event.setCancelled(true);
                        if (plugin.fixWand.canUseWand(p, b)) {
                            plugin.fixWand.executeFix(b);
                        } else {
                            p.sendMessage(CustomItems.hataprefix + "Tamir işlemi başarısız! Bloğa erişiminiz yok ya da savaştasınız.");
                        }
                    }
                }
            } else if (b.getType().equals(Material.DIRT) || (b.getType().equals(Material.GRASS))) {
                if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                        p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_HOE) &&
                        p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.satisAsasi).getItemMeta().getDisplayName())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (action.equals(Action.RIGHT_CLICK_AIR)) {
            if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                    p.getInventory().getItemInMainHand().getType().equals(Material.PAPER) &&
                    p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.x200).getItemMeta().getDisplayName())) {

                if (plugin.extensionManager.extendIsland(p.getName(), 200) == 1) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.bilgiprefix + "Adanız başarıyla 200x200 olarak genişletildi.");
                    p.sendMessage(CustomItems.bilgiprefix + "Değişikliklerin etkinleştirilmesi için oyundan çıkıp tekrar girin.");
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.updateInventory();
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 200) == 2) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız olmadığı için genişletme başarısız!");
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 200) == 3) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız zaten genişletmek istediğiniz boyuttan daha büyük olduğu için genişletme başarısız!");
                    return;
                }
            } else if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                    p.getInventory().getItemInMainHand().getType().equals(Material.PAPER) &&
                    p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.x300).getItemMeta().getDisplayName())) {
                if (plugin.extensionManager.extendIsland(p.getName(), 300) == 1) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.bilgiprefix + "Adanız başarıyla 300x300 olarak genişletildi.");
                    p.sendMessage(CustomItems.bilgiprefix + "Değişikliklerin etkinleştirilmesi için oyundan çıkıp tekrar girin.");
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.updateInventory();
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 300) == 2) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız olmadığı için genişletme başarısız!");
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 300) == 3) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız zaten genişletmek istediğiniz boyuttan daha büyük olduğu için genişletme başarısız!");
                    return;
                }
            } else if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                    p.getInventory().getItemInMainHand().getType().equals(Material.PAPER) &&
                    p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.x400).getItemMeta().getDisplayName())) {
                if (plugin.extensionManager.extendIsland(p.getName(), 400) == 1) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.bilgiprefix + "Adanız başarıyla 400x400 olarak genişletildi.");
                    p.sendMessage(CustomItems.bilgiprefix + "Değişikliklerin etkinleştirilmesi için oyundan çıkıp tekrar girin.");
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.updateInventory();
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 400) == 2) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız olmadığı için genişletme başarısız!");
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 400) == 3) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız zaten genişletmek istediğiniz boyuttan daha büyük olduğu için genişletme başarısız!");
                    return;
                }
            } else if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                    p.getInventory().getItemInMainHand().getType().equals(Material.PAPER) &&
                    p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.x500).getItemMeta().getDisplayName())) {
                if (plugin.extensionManager.extendIsland(p.getName(), 500) == 1) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.bilgiprefix + "Adanız başarıyla 500x500 olarak genişletildi.");
                    p.sendMessage(CustomItems.bilgiprefix + "Değişikliklerin etkinleştirilmesi için oyundan çıkıp tekrar girin.");
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.updateInventory();
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 500) == 2) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız olmadığı için genişletme başarısız!");
                    return;
                }

                if (plugin.extensionManager.extendIsland(p.getName(), 500) == 3) {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Adanız zaten genişletmek istediğiniz boyuttan daha büyük olduğu için genişletme başarısız!");
                    return;
                }
            } else if (plugin.itemManager.isItemNamed(p.getInventory().getItemInMainHand()) &&
                    p.getInventory().getItemInMainHand().getType().equals(Material.PAPER) &&
                    p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.itemManager.getItem(Items.ucusKarti).getItemMeta().getDisplayName())) {
                if (!p.hasPermission("uci.fly")) {
                    event.setCancelled(true);
                    plugin.flyManager.giveUnlimitedFly(p.getName());
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.updateInventory();
                    p.sendMessage(CustomItems.bilgiprefix + "Sınırsız uçuş hakkı etkinleştirildi. /fly komutuyla uçabilirsin!");
                } else {
                    event.setCancelled(true);
                    p.sendMessage(CustomItems.hataprefix + "Zaten uçuş hakkınız olduğundan eşya kullanılamadı!");
                }
            }
        }
    }

}
