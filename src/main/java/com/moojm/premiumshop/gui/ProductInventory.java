package com.moojm.premiumshop.gui;

import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ProductInventory {

    private Inventory inv;
    private final ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11);

    public ProductInventory(Player player, Category category) {
        final String shopName = Utils.toColor(Utils.getMessage("shop-name"));
        inv = Bukkit.createInventory(null, 27, shopName);
        createBorder();
        addProducts(category);
    }

    private void createBorder() {
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, border);
        }
    }

    private void addProducts(Category category) {
        int index = 0;
        for (Product product : category.getProducts()) {
            inv.setItem(10 + index, product.getItem());
            index++;
        }
    }

    public Inventory getInventory() {
        return inv;
    }
}
