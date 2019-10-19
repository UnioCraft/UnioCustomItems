package me.uniodex.uniocustomitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandVote implements CommandExecutor {

    @SuppressWarnings("unused")
    private MainCommands main;

    public CommandVote(MainCommands main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(" ");
        sender.sendMessage("§6§lOy Verme Linkleri:");
        sender.sendMessage("§4➸  §bwww.uniocraft.com/vote1");
        sender.sendMessage("§4➸  §bwww.uniocraft.com/vote2");
        sender.sendMessage("§4➸  §bwww.uniocraft.com/vote3");
        sender.sendMessage("§4➸  §bwww.uniocraft.com/vote4");
        sender.sendMessage(" ");
        sender.sendMessage("§aOy vererek geçici uçuş süresi ve /puan kazanırsın!");
        sender.sendMessage(" ");
        return false;
    }
}
