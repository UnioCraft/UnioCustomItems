package me.uniodex.uniocustomitems.commands;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandXPBottle implements CommandExecutor {

    private CustomItems plugin;

    public CommandXPBottle(CustomItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        if (args.length == 1) {
            sender.sendMessage(plugin.getXpBottleManager().giveXPBottle(player, Integer.valueOf(args[0])));
        } else {
            sender.sendMessage(plugin.getMessage("commands.xpbottle.usage"));
        }
        return true;
    }
}
