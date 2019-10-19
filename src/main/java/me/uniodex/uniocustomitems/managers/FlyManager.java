package me.uniodex.uniocustomitems.managers;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.ActionBarAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class FlyManager {

    private CustomItems plugin;
    private HashMap<Player, Long> flyStartTimes = new HashMap<>();
    public HashMap<Player, Long> flyTimes = new HashMap<>();
    private HashMap<String, Long> flyData = new HashMap<>();

    public FlyManager(CustomItems plugin) {
        this.plugin = plugin;

        loadData();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (flyStartTimes.containsKey(p) && flyTimes.containsKey(p)) {
                    if (!p.getAllowFlight()) {
                        stopFly(p);
                        continue;
                    }
                    long timeSpent = (System.currentTimeMillis() / 1000) - flyStartTimes.get(p);
                    long flyTime = (flyTimes.get(p) - timeSpent);
                    if (flyTime <= 0) {
                        stopFly(p);
                        takeFly(p.getName());
                        p.sendMessage(CustomItems.dikkatprefix + "Uçuş süreniz bitt!");
                    } else if (flyTime <= 10) {
                        ActionBarAPI.sendActionBar(p, ChatColor.GREEN + "Uçuş sürenizin bitmesine " + ChatColor.RED + ChatColor.BOLD + flyTime + ChatColor.GREEN + " saniye kaldı!");
                    }
                }
            }
        }, 20L, 20L);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> saveData(), 20L, 6000L);
    }

    private void loadData() {
        for (String player : plugin.configManager.getFlyData().getKeys(false)) {
            flyData.put(player, plugin.configManager.getFlyData().getLong(player));
        }
    }

    public void loadFly(Player p) {
        if (p != null) {
            if (flyData.containsKey(p.getName())) {
                Long l = flyData.get(p.getName());
                if (!Objects.isNull(l) && l > 0) {
                    flyTimes.put(p, l);
                }
            }
        }
    }

    public void saveFly(Player player) {
        if (flyTimes.containsKey(player)) {
            if (flyStartTimes.containsKey(player)) {
                long timeSpent = (System.currentTimeMillis() / 1000) - flyStartTimes.get(player);
                long flyTime = (flyTimes.get(player) - timeSpent);
                if (flyTime <= 0) {
                    stopFly(player);
                    takeFly(player.getName());
                    return;
                } else {
                    flyData.put(player.getName(), flyTimes.get(player));
					/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.configManager.getFlyData().set(player.getName(), flyTimes.get(player));
						}
					});*/
                }
            } else {
                flyData.put(player.getName(), flyTimes.get(player));
				/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.configManager.getFlyData().set(player.getName(), flyTimes.get(player));
					}
				});*/
            }
			/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					plugin.configManager.saveFlyConfig();
				}
			});*/
        }
    }

    public boolean startFly(Player player) {
        if (player.hasPermission("uci.fly")) {
            player.setAllowFlight(true);
            player.setFlying(true);
            return true;
        }

        loadFly(player);
        if (flyTimes.containsKey(player)) {
            if (flyTimes.get(player) > 0) {
                flyStartTimes.put(player, System.currentTimeMillis() / 1000);
                player.setAllowFlight(true);
                player.setFlying(true);
                return true;
            }
        }
        return false;
    }


    public void stopFly(Player player) {
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setFallDistance(0f);

        if (player.hasPermission("uci.fly")) {
            if (flyStartTimes.containsKey(player) || flyTimes.containsKey(player)) {
                takeFly(player.getName());
            }
            return;
        }

        if (flyStartTimes.containsKey(player) && flyTimes.containsKey(player)) {
            long timeSpent = (System.currentTimeMillis() / 1000) - flyStartTimes.get(player);
            long flyTime = (flyTimes.get(player) - timeSpent);
            if (flyTime > 0) {
                flyStartTimes.remove(player);
                flyTimes.remove(player);
                flyTimes.put(player, flyTime);

                flyData.put(player.getName(), flyTimes.get(player));

				/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.configManager.getFlyData().set(player.getName(), flyTimes.get(player));
						plugin.configManager.saveFlyConfig();
					}
				});*/
            }
        }
    }

    public void giveFly(String player, Long duration) {
        Player p = Bukkit.getPlayer(player);
        if (p != null && p.isOnline()) {
            loadFly(p);
            if (flyTimes.containsKey(p)) {
                long flyTime = flyTimes.get(p);
                flyTimes.put(p, flyTime + duration);

                flyData.put(player, flyTimes.get(p));
				/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.configManager.getFlyData().set(player, flyTimes.get(p));
					}
				});*/
            } else {
                flyTimes.put(p, duration);

                flyData.put(player, flyTimes.get(p));
				/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.configManager.getFlyData().set(player, flyTimes.get(p));
					}
				});*/
            }
        } else {
            Long l = plugin.configManager.getFlyData().getLong(player);
            if (l > 0) {
                flyData.put(player, l + duration);
				/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.configManager.getFlyData().set(player, l + duration);
					}
				});*/
            } else {
                flyData.put(player, duration);
				/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.configManager.getFlyData().set(player, duration);
					}
				});*/
            }
        }
		/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				plugin.configManager.saveFlyConfig();
			}
		});*/
    }

    public void giveUnlimitedFly(String player) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(player);
        plugin.getPermission().playerAdd("", p, "uci.fly");
        takeFly(player);
    }

    public void takeFly(String player) {
        Player p = Bukkit.getPlayer(player);
        if (p != null && p.isOnline()) {
            if (flyTimes.containsKey(p)) {
                flyTimes.remove(p);
            }
            if (flyStartTimes.containsKey(p)) {
                flyStartTimes.remove(p);
            }
        }

        if (flyData.containsKey(player)) {
            flyData.remove(player);
        }
		/*Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				plugin.configManager.getFlyData().set(player, null);
				plugin.configManager.saveFlyConfig();
			}
		});*/
    }

    public boolean hasFly(Player player) {
        loadFly(player);
        if (flyTimes.containsKey(player)) {
            if (flyTimes.get(player) > 0) {
                return true;
            }
        }
        return false;
    }

    public void onDisable() {
        for (Player p : flyStartTimes.keySet()) {
            stopFly(p);
        }
        for (Player p : flyTimes.keySet()) {
            saveFly(p);
        }
        saveData();
    }

    public void onJoin(Player p) {
        loadFly(p);
    }

    public void onQuit(Player p) {
        stopFly(p);
        if (flyTimes.containsKey(p)) {
            if (flyTimes.get(p) <= 1) {
                takeFly(p.getName());
                return;
            }
            flyTimes.remove(p);
        }
        if (flyStartTimes.containsKey(p)) {
            flyStartTimes.remove(p);
        }
    }

    private void saveData() {
        for (String player : plugin.configManager.getFlyData().getKeys(false)) {
            plugin.configManager.getFlyData().set(player, null);
        }

        for (String player : flyData.keySet()) {
            plugin.configManager.getFlyData().set(player, flyData.get(player));
        }

        plugin.configManager.saveFlyConfig();
    }

    public boolean isFlying(Player p) {
        return (flyStartTimes.containsKey(p) && flyTimes.containsKey(p));
    }

	/*public void stopFlySync(Player player) {
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setFallDistance(0f);

		if (player.hasPermission("uci.fly")) {
			if (flyStartTimes.containsKey(player) || flyTimes.containsKey(player)) {
				takeFly(player.getName());
			}
			return;
		}

		if (flyStartTimes.containsKey(player) && flyTimes.containsKey(player)) {
			long timeSpent = (System.currentTimeMillis()/1000) - flyStartTimes.get(player);
			long flyTime = (flyTimes.get(player) - timeSpent);
			if (flyTime > 0) {
				flyStartTimes.remove(player);
				flyTimes.remove(player);
				flyTimes.put(player, flyTime);
				plugin.configManager.getFlyData().set(player.getName(), flyTimes.get(player));
				plugin.configManager.saveFlyConfig();
			}
		}
	}

	public void saveFlySync(Player player) {
		if (flyTimes.containsKey(player)) {
			if (flyStartTimes.containsKey(player)) {
				long timeSpent = (System.currentTimeMillis()/1000) - flyStartTimes.get(player);
				long flyTime = (flyTimes.get(player) - timeSpent);
				if (flyTime <= 0) {
					stopFly(player);
					takeFly(player.getName());
					return;
				}else {
					plugin.configManager.getFlyData().set(player.getName(), flyTimes.get(player));
				}
			}else {
				plugin.configManager.getFlyData().set(player.getName(), flyTimes.get(player));
			}
			plugin.configManager.saveFlyConfig();
		}
	}*/
}
