package me.uniodex.uniocustomitems.commands;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandCatVision implements CommandExecutor {

    private MainCommands main;

    public CommandCatVision(MainCommands main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("uci.catvision")) {
            player.sendMessage(CustomItems.hataprefix + "Bunun için izniniz yok!");
            return true;
        }
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(CustomItems.bilgiprefix + "Kedi gözü özelliğiniz kapatıldı. Artık karanlıkta göremezsiniz.");
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 86400 * 20, 1));
            player.sendMessage(CustomItems.bilgiprefix + "Kedi gözü özelliğiniz açıldı. Artık karanlıkta görebilirsiniz.");
        }

        return true;
    }
}
