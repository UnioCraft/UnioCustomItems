package me.uniodex.uniocustomitems.managers;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Bukkit;

@SuppressWarnings("deprecation")
public class ExtensionManager {

    private CustomItems plugin;

    public ExtensionManager(CustomItems plugin) {
        this.plugin = plugin;
    }

    /*
     * returns int result
     * 1 = success
     * 2 = no island
     * 3 = already big island
     * 4 = no skyblock
     */
    public int extendIsland(String player, int ext) {
        if (plugin.askyblock != null) {
            Island is = ASkyBlockAPI.getInstance().getIslandOwnedBy(Bukkit.getOfflinePlayer(player).getUniqueId());
            if (is == null) {
                return 2;
            }

            int size = is.getProtectionSize();
            if (size >= ext) {
                return 3;
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "asadmin setrange " + player + " " + ext);
            return 1;
        } else {
            return 4;
        }
    }
}


