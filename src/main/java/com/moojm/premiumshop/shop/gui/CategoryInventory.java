package com.moojm.premiumshop.shop.gui;

import com.moojm.premiumshop.shop.Category;

public class CategoryInventory extends BaseShopInventory {

    private static final String SHOP_NODE = "category-shop-name";

    public CategoryInventory() {
        super(SHOP_NODE);
        addCategories();
    }

    private void addCategories() {
        int index = 0;
        for (Category category : Category.getCategories()) {
            this.getInventory().setItem(10 + index, category.getItem());
            index++;
        }
    }
}
