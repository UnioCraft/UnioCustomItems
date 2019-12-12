package me.uniodex.uniocustomitems.objects;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

public class SellWand {

    private CustomItems plugin;

    public SellWand(CustomItems plugin) {
        this.plugin = plugin;
    }

    public void executeSell(Block block, String player) {
        if (block.getState() instanceof Container) {
            plugin.getEconomyManager().executeSell(Bukkit.getOfflinePlayer(player), (Container) block.getState());
        }
    }
}
