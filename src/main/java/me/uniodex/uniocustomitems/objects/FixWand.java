package me.uniodex.uniocustomitems.objects;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.crafter.mc.lockettepro.LocketteProAPI;
import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.FactionsUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FixWand {

    private CustomItems plugin;

    public FixWand(CustomItems plugin) {
        this.plugin = plugin;
    }

    public void executeFix(Block block) {
        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            if (!chest.getChunk().isLoaded()) {
                return;
            }

            for (ItemStack ischest : chest.getInventory()) {
                if (ischest == null || ischest.getType().equals(Material.AIR) || ischest.getType() == null || ischest.getType().isBlock() || ischest.getType().getMaxDurability() < 1) {
                    continue;
                }

                if (ischest.getDurability() > 0) {
                    ischest.setDurability((short) 0);
                }
            }
        }
    }

    public boolean canUseWand(Player player, Block blck) {

        if (plugin.ctplus != null) {
            if (plugin.ctplus.getTagManager().isTagged(player.getUniqueId())) {
                return false;
            }
        }

        if (!WGBukkit.getPlugin().canBuild(player, blck)) {
            return false;
        }

        if (plugin.lockettePro != null) {
            if (LocketteProAPI.isProtected(blck)) {
                if (LocketteProAPI.isLocked(blck) && !LocketteProAPI.isUser(blck, player)) {
                    return false;
                }
            }
        }

        if (plugin.factions != null) {
            if (!FactionsUtils.canPlayerUseWandFactions(player, blck, true)) {
				/*TODO if (!LocketteProFeatures.canPlayerInteractFactions(player, blck)) {
					return false;
				}*/
            }
        }

        if (plugin.askyblock != null) {
            if (!ASkyBlockAPI.getInstance().playerIsOnIsland(player)) {
                return false;
            }
        }

        return true;
    }
}
