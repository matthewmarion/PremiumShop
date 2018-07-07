package com.moojm.premiumshop.gui;

import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopInventory {

    private Inventory inv;

    public ShopInventory(Player player) {
        final String shopName = Utils.toColor(Utils.getMessage("shop-name"));
        inv = Bukkit.createInventory(null, 27, shopName);
        addCategories();
    }

    private void addCategories() {
        int index = 0;
        for (Category category : Category.getCategories()) {
            inv.setItem(10 + index, category.getItem());
            index++;
        }
    }

    public Inventory getInventory() {
        return inv;
    }

}
