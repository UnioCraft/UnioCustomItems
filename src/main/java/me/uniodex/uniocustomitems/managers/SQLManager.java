package me.uniodex.uniocustomitems.managers;

import me.uniodex.uniocustomitems.CustomItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager {

    private ConnectionPoolManager pool;
    public String table;

    public SQLManager(CustomItems plugin) {
        pool = new ConnectionPoolManager(plugin, "UnioCustomItemsPool");
        this.table = plugin.factions != null ? "factionsv2" : "skyblock"; //TODO
    }

    public boolean updateSQL(String QUERY) {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            int count = statement.executeUpdate();
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer getAmount(String player, String item, String table, String playerNameColumn) {
        String QUERY = "SELECT `" + item + "` FROM `" + this.table + "`.`" + table + "` WHERE `" + playerNameColumn + "` = '" + player + "';";
        try (Connection connection = pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getInt(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean takeItem(String player, String item, String table, String playerNameColumn) {
        return updateSQL("UPDATE `" + this.table + "`.`" + table +
                "` SET `" + item + "` = '" + (getAmount(player, item, table, playerNameColumn) - 1) +
                "' WHERE `" + playerNameColumn + "` = '" + player + "';");
    }

    public boolean takeAll(String player, String item, String table, String playerNameColumn) {
        return updateSQL("UPDATE `" + this.table + "`.`" + table +
                "` SET `" + item + "` = '0' WHERE `" + playerNameColumn + "` = '" + player + "';");
    }

    public void onDisable() {
        pool.closePool();
    }

}