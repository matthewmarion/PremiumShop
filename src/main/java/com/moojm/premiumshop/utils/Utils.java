package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Utils {

    public static String getMessage(String node) {
        return ConfigManager.getMessagesConfig().getString(node);
    }

    public static List<String> getMessageList(String node) {
        return ConfigManager.getMessagesConfig().getStringList(node);
    }

    public static String toColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static double[] parseLocation(Location location) {
        double[] coords = new double[3];
        coords[0] = location.getX();
        coords[1] = location.getY();
        coords[2] = location.getZ();
        return coords;
    }

    public static String replaceCommandPlaceholders(Player player, String command) {
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("{player}", player.getName());
        Iterator it = placeholders.entrySet().iterator();
        String newCommand = command;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (command.contains(pair.getKey().toString())) {
                newCommand =  newCommand.replace(pair.getKey().toString(), pair.getValue().toString());
            }
        }
        return newCommand;
    }

}
