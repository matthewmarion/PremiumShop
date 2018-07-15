package com.moojm.premiumshop.command.shop;

import com.moojm.premiumshop.command.PremiumCommandExecutor;
import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopCategoryCommand extends PremiumCommandExecutor {

    public ShopCategoryCommand() {
        setSubCommand("category");
        setPermission("pshop.admin");
        setUsage("/pshop category <new|remove> <name>");
        setPlayer(true);
        setLength(3);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String name = args[2];
        Category category = Category.getCategoryByName(name);

        if (args[1].equalsIgnoreCase("new")) {
            if (category != null) {
                MessageUtils.tell(sender, MessageUtils.EXISTING_CATEGORY, null, null);
                return;
            }
            ItemStack item = player.getItemInHand();
            if (item == null || item.getType() == Material.AIR) {
                MessageUtils.tell(sender, "&cItem cannot be null", null, null);
                return;
            }
            Category newCategory = new Category(name, item);
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
