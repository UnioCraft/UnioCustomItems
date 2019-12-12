package me.uniodex.uniocustomitems.hooks;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Hook {
    default boolean canBuild(Player player, Location location) {
        return true;
    }

    default boolean canUseFixWand(Player player, Location location) { return true; }

    default boolean canUseSellWand(Player player, Location location) { return true; }

    default boolean canUseBossEgg(Player player, Location location) { return true; }

    default boolean canTransactMoney(Player player) { return true; }

    String getDenyMessage();
}
