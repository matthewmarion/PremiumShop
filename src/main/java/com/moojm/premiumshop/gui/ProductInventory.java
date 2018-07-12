package com.moojm.premiumshop.gui;

import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;

public class ProductInventory extends ShopInventory {

    private static final String SHOP_NODE = "product-shop-name";

    public ProductInventory(Category category) {
        super(SHOP_NODE);
        addProducts(category);
    }

    private void addProducts(Category category) {
        int index = 0;
        for (Product product : category.getProducts()) {
            this.getInventory().setItem(10 + index, product.getItem());
            index++;
        }
    }
}
