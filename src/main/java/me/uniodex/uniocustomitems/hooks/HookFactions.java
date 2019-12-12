package me.uniodex.uniocustomitems.hooks;

import com.massivecraft.factions.Conf;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.massivecraft.factions.listeners.FactionsPlayerListener;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HookFactions implements Hook {

    @Override
    public boolean canBuild(Player player, Location location) {
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, "destroy", true);
    }

    private boolean canUseWand(Player player, Location location) {
        boolean safeZoneDenyUseage = Conf.safeZoneDenyUseage;
        boolean warZoneDenyUseage = Conf.warZoneDenyUseage;

        Conf.safeZoneDenyUseage = true;
        Conf.warZoneDenyUseage = true;
        boolean result = FactionsPlayerListener.playerCanUseItemHere(player, location, Material.GOLD_AXE, true);
        Conf.safeZoneDenyUseage = safeZoneDenyUseage;
        Conf.warZoneDenyUseage = warZoneDenyUseage;

        return result;
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
    public boolean canUseBossEgg(Player player, Location location) {
        return this.canBuild(player, location);
    }

    @Override
    public String getDenyMessage() {
        return CustomItems.getInstance().getMessage("hooks.denyMessages.factions");
    }
}
