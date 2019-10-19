package me.uniodex.uniocustomitems.commands;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandFly implements CommandExecutor {

    private MainCommands main;

    public CommandFly(MainCommands main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("uci.fly") || main.plugin.flyManager.hasFly(p)) {
                    if (p.getAllowFlight()) {
                        main.plugin.flyManager.stopFly(p);
                        Long flyTime = main.plugin.flyManager.flyTimes.get(p);
                        if (!Objects.isNull(flyTime) && !p.hasPermission("uci.fly")) {
                            p.sendMessage(CustomItems.bilgiprefix + "Uçuş modu kapatıldı. Kalan uçuş süresi: " + ChatColor.GREEN + Utils.secondsToString(flyTime));
                        } else {
                            p.sendMessage(CustomItems.bilgiprefix + "Uçuş modu kapatıldı.");
                        }
                    } else {
                        if (main.plugin.flyManager.startFly(p)) {
                            Long flyTime = main.plugin.flyManager.flyTimes.get(p);
                            if (!Objects.isNull(flyTime) && !p.hasPermission("uci.fly")) {
                                p.sendMessage(CustomItems.bilgiprefix + "Uçuş modu açıldı. Kalan uçuş süresi: " + ChatColor.GREEN + Utils.secondsToString(flyTime));
                            } else {
                                p.sendMessage(CustomItems.bilgiprefix + "Uçuş modu açıldı.");
                            }
                        } else {
                            p.sendMessage(CustomItems.hataprefix + "Uçuş süreniz olmadığı için uçma açılamadı!");
                        }
                    }
                    return true;
                } else {
                    p.sendMessage(CustomItems.hataprefix + "Uçuş süreniz olmadığı için uçma açılamadı!");
                    return true;
                }
            }
        }
        return false;
    }

}
