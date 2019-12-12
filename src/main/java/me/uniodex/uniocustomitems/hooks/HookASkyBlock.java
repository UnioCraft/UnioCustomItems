package me.uniodex.uniocustomitems.hooks;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HookASkyBlock implements Hook {

    @Override
    public boolean canBuild(Player player, Location location) {
        return ASkyBlockAPI.getInstance().playerIsOnIsland(player);
    }

    @Override
    public boolean canUseFixWand(Player player, Location location) {
        return canBuild(player, location);
    }

    @Override
    public boolean canUseSellWand(Player player, Location location) {
        return canBuild(player, location);
    }

    @Override
    public boolean canUseBossEgg(Player player, Location location) {
        // Change this if you make a special place for killing boss.
        return canBuild(player, location);
    }

    @Override
    public String getDenyMessage() {
        return CustomItems.getInstance().getMessage("hooks.denyMessages.askyblock");
    }
}
