package com.moojm.premiumshop.command;

import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopProductCommand extends ShopCommandExecutor {

    public ShopProductCommand() {
        setSubCommand("product");
        setPermission("pshop.admin");
        setUsage("/pshop product <new|remove> <name> (category) (price)");
        setPlayer(true);
        setLength(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String name = args[2];
        String categoryName = args[3];
        Category category = Category.getCategoryByName(categoryName);

        if (args[1].equalsIgnoreCase("new")) {
            if (args.length != 5) {
                player.sendMessage(ChatColor.RED + getUsage());
                return;
            }
            if (category == null) {
                MessageUtils.tell(sender, MessageUtils.NULL_CATEGORY, null, null);
                return;
            }
            if (category.containsProductName(name)) {
                MessageUtils.tell(sender, MessageUtils.EXISTING_PRODUCT, "{name}", name);
                return;
            }
            ItemStack item = player.getItemInHand();
            if (item == null) {
                player.sendMessage("null item");
                return;
            }
            double price = Double.parseDouble(args[4]);
            Product product = new Product(name, item, price);
            category.addProduct(product);
            MessageUtils.tell(sender, MessageUtils.NEW_PRODUCT, "{name}", name);
        }

        if (args[1].equalsIgnoreCase("remove")) {
            if (args.length != 4) {
                player.sendMessage(ChatColor.RED + getUsage());
                return;
            }
            if (category == null) {
                MessageUtils.tell(sender, MessageUtils.NULL_CATEGORY, null, null);
                return;
            }
            if (!category.containsProductName(name)) {
                MessageUtils.tell(sender, MessageUtils.NULL_PRODUCT, "{name}", name);
                return;
            }
            Product product = Product.getProductByName(name, category);
            category.removeProduct(product);
            MessageUtils.tell(sender, MessageUtils.REMOVED_PRODUCT, "{name}", name);
        }

    }
}
