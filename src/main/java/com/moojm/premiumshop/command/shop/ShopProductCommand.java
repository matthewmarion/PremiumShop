package com.moojm.premiumshop.command.shop;

import com.moojm.premiumshop.command.PremiumCommandExecutor;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.MessageUtils;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopProductCommand extends PremiumCommandExecutor {

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

        if (args.length < 4) {
            player.sendMessage(ChatColor.RED + getUsage());
            return;
        }

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
                MessageUtils.tell(sender, "&cItem cannot be null", null, null);
                return;
            }
            double price = Double.parseDouble(args[4]);
            item = addInfoToLore(item, price, category.getName());
            Product product = new Product(name, item, price, categoryName);
            category.addProduct(product);
            MessageUtils.tell(sender, MessageUtils.NEW_PRODUCT, "{name}", name);
            return;
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

    private ItemStack addInfoToLore(ItemStack item, double price, String categoryName) {
        ItemStack product = item.clone();
        ItemMeta meta = product.getItemMeta();
        List<String> lore = meta.getLore();
        String priceLore = Utils.toColor("&6&l" + String.valueOf(price) + " GOLD");
        if (lore != null) {
            lore.add(0, priceLore);
            lore.add(1, categoryName);
            meta.setLore(lore);
            product.setItemMeta(meta);
            return product;
        }

        lore = new ArrayList<>();
        lore.add(0, priceLore);
        lore.add(1, categoryName);
        meta.setLore(lore);
        product.setItemMeta(meta);
        return product;
    }
}
