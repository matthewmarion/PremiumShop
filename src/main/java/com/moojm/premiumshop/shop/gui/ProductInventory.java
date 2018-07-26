package com.moojm.premiumshop.shop.gui;

import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ProductInventory {

    private Inventory inv;
    private static final String SHOP_NODE = "product-shop-name";
    private Player player;
    private final String purchaseLine = "&a&lPURCHASED";

    public ProductInventory(Category category, Player player) {
        this.player = player;
        createInventory();
        addProducts(category);
    }

    private void createInventory() {
        inv = Bukkit.createInventory(null, 27, Utils.toColor(Utils.getMessage(SHOP_NODE)));
    }

    private void addProducts(Category category) {
        Profile profile = Profile.getByPlayer(player);
        for (Product product : category.getProducts()) {
            ItemStack item = product.getItem().clone();
            if (profile.hasPurchased(product) && !product.canRepurchase()) {
                item = addPurchaseLoreIfNotAlready(item);
                inv.addItem(item);
                continue;
            }
            inv.addItem(item);
        }
    }

    private ItemStack addPurchaseLoreIfNotAlready(ItemStack item) {
        ItemStack purchaseItem = item;
        ItemMeta meta = purchaseItem.getItemMeta();
        List<String> lore = meta.getLore();
        if (containsPurchaseLine(lore)) {
            return purchaseItem;
        }
        lore.add(0, Utils.toColor(purchaseLine));
        meta.setLore(lore);
        purchaseItem.setItemMeta(meta);
        return purchaseItem;
    }

    private boolean containsPurchaseLine(List<String> lore) {
        return lore.get(0).contains("PURCHASE");
    }

    public Inventory getInventory() {
        return inv;
    }

}
