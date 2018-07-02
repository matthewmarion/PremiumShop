package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.Location;

public class Utils {

    public static String getMessage(String node) {
        return ConfigManager.getMessagesConfig().getString(node);
    }

    public static double[] parseLocation(Location location) {
        double[] coords = new double[3];
        coords[0] = location.getX();
        coords[1] = location.getY();
        coords[2] = location.getZ();
        return coords;
    }

}
