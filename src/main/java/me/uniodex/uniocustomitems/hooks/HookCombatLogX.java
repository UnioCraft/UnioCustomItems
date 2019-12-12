package me.uniodex.uniocustomitems.hooks;

import com.SirBlobman.combatlogx.utility.CombatUtil;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HookCombatLogX implements Hook {

    @Override
    public boolean canUseFixWand(Player player, Location location) {
        return !CombatUtil.isInCombat(player);
    }

    @Override
    public boolean canUseBossEgg(Player player, Location location) {
        return !CombatUtil.isInCombat(player);
    }

    @Override
    public String getDenyMessage() {
        return CustomItems.getInstance().getMessage("hooks.denyMessages.combatlogx");
    }
}
