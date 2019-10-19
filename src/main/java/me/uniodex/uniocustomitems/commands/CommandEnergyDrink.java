package me.uniodex.uniocustomitems.commands;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandEnergyDrink implements CommandExecutor {

    private MainCommands main;

    public CommandEnergyDrink(MainCommands main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("uci.energydrink")) {
            player.sendMessage(CustomItems.hataprefix + "Bunun için izniniz yok!");
            return true;
        }
        if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            player.sendMessage(CustomItems.bilgiprefix + "Enerji içeceğinin etkisini geçirdiniz.");
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 86400 * 20, 1));
            player.sendMessage(CustomItems.bilgiprefix + "Bir enerji içeceği içtiniz. Artık çok daha hızlı kazı yapabilirsiniz.");
        }

        return true;
    }
}
