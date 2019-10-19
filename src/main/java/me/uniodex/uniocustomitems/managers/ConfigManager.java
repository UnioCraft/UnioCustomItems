package me.uniodex.uniocustomitems.managers;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class ConfigManager {

    private CustomItems plugin;
    private HashMap<String, FileConfiguration> configurations = new HashMap<String, FileConfiguration>();

    public ConfigManager(CustomItems plugin) {
        this.plugin = plugin;
        registerConfig("chests.yml");
        registerConfig("fly.yml");

        for (String fileName : configurations.keySet()) {
            reloadConfig(fileName);
            configurations.get(fileName).options().copyDefaults(true);
            saveConfig(fileName);
        }
    }

    // Get Configs

    public FileConfiguration getChestsData() {
        return configurations.get("chests.yml");
    }

    public FileConfiguration getFlyData() {
        return configurations.get("fly.yml");
    }

    public void saveChestsConfig() {
        saveConfig("chests.yml");
    }

    public void saveFlyConfig() {
        saveConfig("fly.yml");
    }

    private void registerConfig(String name) {
        configurations.put(name, YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name)));
    }

    private void reloadConfig(String fileName) {
        InputStream inputStream = plugin.getResource(fileName);
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
            configurations.get(fileName).setDefaults(defConfig);
            try {
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveConfig(String fileName) {
        try {
            configurations.get(fileName).save(new File(plugin.getDataFolder(), fileName));
        } catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage("Couldn't save " + fileName + "!");
        }
    }

    public void deleteFile(File path) {
        if (path.exists()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) deleteFile(f);
                else f.delete();
            }
        }
        path.delete();
    }

    public void copyFile(File source, File target) {
        try {
            if (source.isDirectory()) {
                if (!target.exists()) target.mkdirs();
                String files[] = source.list();
                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(target, file);
                    copyFile(srcFile, destFile);
                }

            } else {
                FileInputStream inputStream = new FileInputStream(source);
                FileOutputStream outputStream = new FileOutputStream(target);
                FileChannel inChannel = inputStream.getChannel();
                FileChannel outChannel = outputStream.getChannel();
                try {
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inChannel != null) inChannel.close();
                    if (outChannel != null) outChannel.close();
                    inputStream.close();
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Failed to copy files!");
            e.printStackTrace();
        }
    }

    public long getSize(File file) {
        long length = 0;
        if (file.isDirectory()) {
            for (String f : file.list()) length += getSize(new File(file, f));
        } else length = file.length();
        return length;
    }
}
