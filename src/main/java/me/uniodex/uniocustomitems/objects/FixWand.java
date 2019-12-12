package me.uniodex.uniocustomitems.objects;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;

public class FixWand {

    private CustomItems plugin;

    public FixWand(CustomItems plugin) {
        this.plugin = plugin;
    }

    public void executeFix(Block block) {
        if (block.getState() instanceof Container) {
            Container container = (Container) block.getState();

            for (ItemStack item : container.getInventory()) {
                if (item == null ||
                        item.getType().equals(Material.AIR) ||
                        item.getType().isBlock() ||
                        item.getType().getMaxDurability() < 1) {
                    continue;
                }

                if (item.getDurability() > 0) {
                    item.setDurability((short) 0);
                }
            }
        }
    }
}
