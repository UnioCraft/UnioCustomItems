package me.uniodex.uniocustomitems.managers;

import me.uniodex.uniocustomitems.CustomItems;
import me.uniodex.uniocustomitems.utils.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogManager {

    private CustomItems plugin;

    public LogManager(CustomItems plugin) {
        this.plugin = plugin;
    }

    /**
     * Logs.
     *
     * @param message - Message to log.
     * @param type    -  Can be: info or error.
     */
    public void log(String message, String type) {
        String messageToLog = "[" + Utils.getTimeAsHours() + "] " + message;

        try {
            File file = new File(plugin.getDataFolder().getAbsolutePath() + "/logs/" + Utils.getTimeAsYearMonthDay() + "." + type + ".log");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(messageToLog);
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
