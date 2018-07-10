package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String newCommand = command;
            if (command.contains(entry.getKey())) {
                newCommand = newCommand.replace(entry.getKey(), entry.getValue());
            }
            return newCommand;
        }
        return command;
    }

}
