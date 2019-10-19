package me.uniodex.uniocustomitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.uniodex.uniocustomitems.CustomItems;

public class CommandXPBottle implements CommandExecutor {

	private MainCommands main;

	public CommandXPBottle(MainCommands main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return true;
		
		Player player = (Player) sender;
		if (args.length == 1) {
			sender.sendMessage(main.plugin.xpBottleManager.giveXPBottle(player, Integer.valueOf(args[0])));
		}else {
			sender.sendMessage(CustomItems.bilgiprefix + "Kullanım: /xpbottle <miktar>");
		}
		return true;
	}
}
