package me.uniodex.uniocustomitems.hooks;

import me.crafter.mc.lockettepro.LocketteProAPI;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HookLockette implements Hook {

    @Override
    public boolean canBuild(Player player, Location location) {
        return !LocketteProAPI.isProtected(location.getBlock());
    }

    private boolean canUseWand(Player player, Location location) {
        return !LocketteProAPI.isProtected(location.getBlock()) || !LocketteProAPI.isLocked(location.getBlock()) || LocketteProAPI.isUser(location.getBlock(), player);
    }

    @Override
    public boolean canUseFixWand(Player player, Location location) {
        return canUseWand(player, location);
    }

    @Override
    public boolean canUseSellWand(Player player, Location location) {
        return canUseWand(player, location);
    }

    @Override
    public String getDenyMessage() {
        return CustomItems.getInstance().getMessage("hooks.denyMessages.lockettepro");
    }
}
