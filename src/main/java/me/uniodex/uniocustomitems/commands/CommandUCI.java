package me.uniodex.uniocustomitems.commands;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandUCI implements CommandExecutor {

    private CustomItems plugin;

    public CommandUCI(CustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("uci.admin")) {
            return true;
        }

        if (args.length >= 3) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("give")) {
                String playerName = args[1];
                Player player = Bukkit.getPlayerExact(playerName);
                String itemName = args[2];
                int amount = 1;
                if (args.length == 4) {
                    amount = Integer.parseInt(args[3]);
                }

                ItemStack item = plugin.getItemManager().getItem(itemName);
                if (player == null) {
                    sender.sendMessage(plugin.getMessage("commands.uci.give.playerNotFound"));
                    return true;
                }

                if (item == null) {
                    sender.sendMessage(plugin.getMessage("commands.uci.give.itemNotFound"));
                    return true;
                }

                if (!Utils.isThereEnoughSpace(item, player.getInventory())) {
                    sender.sendMessage(plugin.getMessage("commands.uci.give.inventoryIsFull"));
                    return true;
                }

                item.setAmount(amount);
                player.getInventory().addItem(item);
                player.updateInventory();
                sender.sendMessage(plugin.getMessage("commands.uci.give.success"));
                return true;
            }
        }
        sender.sendMessage(plugin.getMessage("commands.wrongCommand"));
        return true;
    }
}
