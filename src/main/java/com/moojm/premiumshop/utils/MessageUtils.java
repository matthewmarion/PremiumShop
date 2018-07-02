package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageUtils {

    public static void tell(CommandSender sender, String message, String placeholder, String value) {
        if (placeholder != null && value != null) {
            message = replacePlaceholders(message, placeholder, value);
        }
        message = replaceColorCodes(message);
        sender.sendMessage(message);
    }

    private static String replacePlaceholders(String message, String placeholder, String value) {
        return message = message.replace(placeholder, value);
    }

    private static String replaceColorCodes(String message) {
        return message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public static final String NO_PERMISSION = Utils.getMessage("no-permission");
    public static final String ONLY_CONSOLE = Utils.getMessage("only-console");
    public static final String ONLY_PLAYER = Utils.getMessage("only-player");
    public static final String CREATE_SHOP = Utils.getMessage("create-shop");

}
