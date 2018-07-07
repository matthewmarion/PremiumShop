package com.moojm.premiumshop.command;

import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class ShopCategoryCommand extends ShopCommandExecutor {

    public ShopCategoryCommand() {
        setSubCommand("category");
        setPermission("pshop.admin");
        setUsage("/pshop category <new|remove> <name>");
        setBoth();
        setLength(3);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String name = args[2];
        Category category = Category.getCategoryByName(name);

        if (args[1].equalsIgnoreCase("new")) {
            if (category != null) {
                MessageUtils.tell(sender, MessageUtils.EXISTING_CATEGORY, null, null);
                return;
            }
            Category newCategory = new Category(name);
            MessageUtils.tell(sender, MessageUtils.NEW_CATEGORY, "{name}", name);
        }

        if (args[1].equalsIgnoreCase("remove")) {
            if (category == null) {
                MessageUtils.tell(sender, MessageUtils.NULL_CATEGORY, null, null);
                return;
            }
            ConfigManager.getInventoryConfig().set(name, null);
            Category.remove(category);
            MessageUtils.tell(sender, MessageUtils.REMOVED_CATEGORY, "{name}", name);
        }

    }
}
