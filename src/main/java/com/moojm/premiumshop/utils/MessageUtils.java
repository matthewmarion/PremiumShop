package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MessageUtils {

    public static void tell(CommandSender sender, String message, String placeholder, String value) {
        if (placeholder != null && value != null) {
            message = replacePlaceholders(message, placeholder, value);
        }
        message = replaceColorCodes(message);
        sender.sendMessage(message);
    }

    public static void tellList(CommandSender sender, List<String> messages) {
        if (messages == null) {
            return;
        }
        for (String message : messages) {
            message = replaceColorCodes(message);
            sender.sendMessage(message);
        }
    }

    private static String replacePlaceholders(String message, String placeholder, String value) {
        return message = message.replace(placeholder, value);
    }

    private static String replaceColorCodes(String message) {
        return message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public static final List<String> HELP = Utils.getMessageList("help");
    public static final String NO_PERMISSION = Utils.getMessage("no-permission");
    public static final String ONLY_CONSOLE = Utils.getMessage("only-console");
    public static final String ONLY_PLAYER = Utils.getMessage("only-player");
    public static final String ERROR = Utils.getMessage("error");
    public static final String CREATE_SHOP = Utils.getMessage("create-shop");
    public static final String NULL_CATEGORY = Utils.getMessage("null-category");
    public static final String EXISTING_CATEGORY = Utils.getMessage("existing-category");
    public static final String NEW_CATEGORY = Utils.getMessage("new-category");
    public static final String REMOVED_CATEGORY = Utils.getMessage("removed-category");
    public static final String EXISTING_PRODUCT = Utils.getMessage("existing-product");
    public static final String NULL_PRODUCT = Utils.getMessage("null-product");
    public static final String NEW_PRODUCT = Utils.getMessage("new-product");
    public static final String REMOVED_PRODUCT = Utils.getMessage("removed-product");
    public static final String CREATE_CMD = Utils.getMessage("create-cmd");

}
