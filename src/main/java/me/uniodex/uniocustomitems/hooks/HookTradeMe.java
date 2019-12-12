package me.uniodex.uniocustomitems.hooks;

import me.Zrips.TradeMe.TradeMe;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.entity.Player;

public class HookTradeMe implements Hook {

    @Override
    public boolean canTransactMoney(Player player) {
        return !TradeMe.getInstance().getUtil().isTrading(player.getPlayer());
    }

    @Override
    public String getDenyMessage() {
        return CustomItems.getInstance().getMessage("hooks.denyMessages.trademe");
    }
}
