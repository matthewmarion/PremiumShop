package com.moojm.premiumshop.gui;

import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ProductInventory extends ShopInventory {

    private static final String SHOP_NODE = "product-shop-name";
    private Player player;

    public ProductInventory(Category category, Player player) {
        super(SHOP_NODE);
        this.player = player;
        addProducts(category);
    }

    private void addProducts(Category category) {
        int index = 0;
        Profile profile = Profile.getByPlayer(player);
        for (Product product : category.getProducts()) {
            ItemStack item = product.getItem();
            if (profile.hasPurchased(product)) {
                item = addPurchasedLore(product);
            }
            this.getInventory().setItem(10 + index, item);
            System.out.println(product.getItem().getItemMeta().getLore());
            index++;
        }
    }

    private ItemStack addPurchasedLore(Product product) {
        ItemStack item = product.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        String purchasedLine = "&a&lPURCHASED";
        System.out.println(lore.get(0));
        boolean alreadyPurchased = lore.get(0).equals(purchasedLine);
        if (!lore.get(0).equals(purchasedLine)) {
            lore.add(0, Utils.toColor("&a&lPURCHASED"));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
