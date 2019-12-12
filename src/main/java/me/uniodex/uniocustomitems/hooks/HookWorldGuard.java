package me.uniodex.uniocustomitems.hooks;

import com.sk89q.worldguard.bukkit.WGBukkit;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HookWorldGuard implements Hook {

    @Override
    public boolean canBuild(Player player, Location location) {
        return WGBukkit.getPlugin().canBuild(player, location.getBlock());
    }

    @Override
    public boolean canUseFixWand(Player player, Location location) {
        return this.canBuild(player, location);
    }

    @Override
    public boolean canUseSellWand(Player player, Location location) {
        return this.canBuild(player, location);
    }

    @Override
    public String getDenyMessage() {
        return CustomItems.getInstance().getMessage("hooks.denyMessages.factions");
    }
}
