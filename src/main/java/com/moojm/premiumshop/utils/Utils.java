package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
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

        if (newCommand == null) {
            return "";
        }

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (command.contains(pair.getKey().toString())) {
                newCommand =  newCommand.replace(pair.getKey().toString(), pair.getValue().toString());
            }
        }
        return newCommand;
    }

    public static String convertMoney(double amt){
        DecimalFormat df = new DecimalFormat("#.###");
        String s;
        Double thou = new Double("1000");
        Double mill = new Double("1000000");
        Double bill = new Double("1000000000");
        Double tril = new Double("1000000000000");
        Double quad = new Double("1000000000000000");
        Double quin = new Double("1000000000000000000");
        if(amt>=quin){
            amt = amt/quin;
            s = df.format(amt) + "quin";
        }
        else if(amt>=quad){
            amt = amt/quad;
            s = df.format(amt) + "quad";
        }
        else if(amt>=tril){
            amt = amt/tril;
            s = df.format(amt) + "tril";
        }
        else if(amt>=bill){
            amt = amt/bill;
            s = df.format(amt) + "bil";
        }
        else if(amt>=mill){
            amt = amt/mill;
            s = df.format(amt) + "mil";
        }
        else if(amt>=thou){
            amt = amt/thou;
            s = df.format(amt) + "k";
        }
        else{
            s = df.format(amt) + "";
        }
        return s;
    }

}
