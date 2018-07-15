package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.shop.Product;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public static String replacePlaceholders(String message, String placeholder, String value) {
        return message = message.replace(placeholder, value);
    }

    private static String replaceColorCodes(String message) {
        return message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void tellPurchase(Player player, Product product) {
        String itemName = ChatColor.stripColor(product.getItem().getItemMeta().getDisplayName());
        String purchaseMessage = MessageUtils.replacePlaceholders(MessageUtils.PURCHASED, "{gold}", String.valueOf(product.getPrice()));
        MessageUtils.tell(player, purchaseMessage, "{product}", itemName);
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
    public static final String NOT_ENOUGH_GOLD = Utils.getMessage("not-enough-gold");
    public static final String PURCHASED = Utils.getMessage("purchased");
    public static final String GOLD_BALANCE = Utils.getMessage("gold-balance");
    public static final String ADD_GOLD = Utils.getMessage("add-gold");
    public static final String REMOVE_GOLD = Utils.getMessage("remove-gold");
    public static final String SET_GOLD = Utils.getMessage("set-gold");

}
