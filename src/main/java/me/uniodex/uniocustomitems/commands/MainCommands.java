package me.uniodex.uniocustomitems.commands;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.managers.ItemManager;
import me.uniodex.uniocustomitems.managers.ItemManager.Items;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class MainCommands implements CommandExecutor {

    public CustomItems plugin;

    public MainCommands(CustomItems plugin) {
        this.plugin = plugin;
        plugin.getCommand("uci").setExecutor(this);
        plugin.getCommand("fly").setExecutor(new CommandFly(this));
        plugin.getCommand("xpbottle").setExecutor(new CommandXPBottle(this));
        plugin.getCommand("catvision").setExecutor(new CommandCatVision(this));
        plugin.getCommand("energydrink").setExecutor(new CommandEnergyDrink(this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("uci.admin") || !sender.isOp()) {
            return false;
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                Player p = Bukkit.getPlayerExact(args[2]);
                if (p != null) {
                    if (args[1].equalsIgnoreCase("blokhunisix3")) {
                        plugin.itemManager.giveHopper(p, "3");
                        plugin.itemManager.giveHopper(p, "3");
                        plugin.itemManager.giveHopper(p, "3");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("satishunisix5")) {
                        plugin.itemManager.giveHopper(p, "2");
                        plugin.itemManager.giveHopper(p, "2");
                        plugin.itemManager.giveHopper(p, "2");
                        plugin.itemManager.giveHopper(p, "2");
                        plugin.itemManager.giveHopper(p, "2");
                        return true;
                    }
                }

                Items it = null;
                for (Items i : ItemManager.Items.values()) {
                    if (i.name().equalsIgnoreCase(args[1])) {
                        it = i;
                    }
                }

                if (it == null) {
                    sender.sendMessage(CustomItems.hataprefix + "Böyle bir eşya yok!");
                    return false;
                }

                ItemStack item = plugin.itemManager.getItem(it);
                if (p != null) {
                    if (p.getInventory().firstEmpty() == -1) {
                        sender.sendMessage(CustomItems.hataprefix + "Oyuncunun envanterinde boş yer yok!");
                        return false;
                    }
                    p.getInventory().addItem(item);
                    p.updateInventory();
                    sender.sendMessage(CustomItems.bilgiprefix + "Eşya başarıyla verildi!");
                    return true;
                } else {
                    sender.sendMessage(CustomItems.hataprefix + "Oyuncu bulunamadı!");
                    return false;
                }
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("fly")) {
                if (Bukkit.getOfflinePlayer(args[2]) != null) {
                    String playerName = args[2];
                    long duration = 0;
                    try {
                        duration = Long.valueOf(args[3]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        sender.sendMessage(CustomItems.hataprefix + "Hatalı bir komut girdiniz!");
                        return false;
                    }
                    plugin.flyManager.giveFly(playerName, duration);
                    sender.sendMessage(CustomItems.bilgiprefix + "Fly başarıyla verildi!");
                    return true;
                }
            }
        }
        if (args.length >= 4) {
            if (args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("spawner")) {
                if (Bukkit.getOfflinePlayer(args[2]) != null) {
                    String playerName = args[2];
                    String spawner = StringUtils.join(args, ' ', 3, args.length);
                    Player p = Bukkit.getPlayerExact(playerName);
                    if (p == null) {
                        sender.sendMessage(CustomItems.hataprefix + "Oyuncu bulunamadı!");
                        return false;
                    }
                    if (p.getInventory().firstEmpty() == -1) {
                        sender.sendMessage(CustomItems.hataprefix + "Oyuncunun envanterinde boş yer yok!");
                        return false;
                    }
                    plugin.itemManager.giveSpawner(p, spawner);
                    sender.sendMessage(CustomItems.bilgiprefix + "Spawner başarıyla verildi!");
                    return true;
                }
            }
        }
        if (args.length >= 3) {
            if (args[0].equalsIgnoreCase("sendmessage")) {
                Player oyuncu = Bukkit.getPlayerExact(args[1]);
                if (oyuncu == null) return false;
                String mesaj = StringUtils.join(args, ' ', 2, args.length);
                oyuncu.sendMessage(CustomItems.bilgiprefix + ChatColor.translateAlternateColorCodes('&', mesaj));
                return true;
            }
        }

        sender.sendMessage(CustomItems.hataprefix + "Hatalı bir komut girdiniz!");
        return false;
    }

}
