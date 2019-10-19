package me.uniodex.uniocustomitems.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.uniodex.uniocustomitems.CustomItems;

public class FlyListeners implements Listener {

	private CustomItems plugin;

	public FlyListeners(CustomItems plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.flyManager.onJoin(player);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		plugin.flyManager.onQuit(player);
	}

	@EventHandler
	public void flyToggle(PlayerChangedWorldEvent event)  {
		Player player = event.getPlayer();

		if (!player.hasPermission("uci.fly") && plugin.flyManager.isFlying(player)) {
			plugin.flyManager.stopFly(player);
		}

	}
}
