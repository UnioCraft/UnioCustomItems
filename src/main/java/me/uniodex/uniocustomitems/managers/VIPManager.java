package me.uniodex.uniocustomitems.managers;

import me.uniodex.uniocustomitems.CustomItems;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class VIPManager {

    private CustomItems plugin;

    public VIPManager(CustomItems plugin) {
        this.plugin = plugin;
    }
    
    public boolean giveVIP(String player, String vipType) {
        try {
            System.setProperty("http.agent", "Chrome");

            URL url = new URL("***REMOVED***");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            Map<String, String> params = new HashMap<>();
            params.put("action", "1");
            params.put("agreement", "true");
            params.put("receiver", player);
            params.put("adminkey", "***REMOVED***");
            params.put("buyer", "VIP Kartı");
            params.put("server", "Factions");
            params.put("viptype", vipType);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            connection.setDoOutput(true);
            try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                writer.write(postDataBytes);
                writer.flush();
                writer.close();

                StringBuilder content;

                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    content = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        content.append(line);
                    }
                }
                if (content.toString().equals("1")) {
                    return true;
                } else {
                    plugin.getLogManager().log("[SQUID] Squid result other than 1. Result: " + content.toString(), "error");
                    return false;
                }
            } finally {
                connection.disconnect();
            }
        } catch (IOException e) {
            plugin.getLogManager().log("[SQUID] Can't connect to squid. IOException: " + e.toString(), "error");
            return false;
        }
    }
}
