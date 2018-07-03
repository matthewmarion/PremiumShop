package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Product implements ConfigurationSerializable {

    private String name;
    private double price;
    private ItemStack item;

    public Product(String name, ItemStack item, double price) {
        this.name = name;
        this.item = item;
        this.price = price;
    }

    public static Product getProductByName(String name, Category category) {
        for (Product product : category.getProducts()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public static Product load(String name, String category) {
        ItemStack item = ConfigManager.getInventoryConfig().getItemStack(category + ".products." + name + ".item");
        double price = ConfigManager.getInventoryConfig().getDouble(category + ".products." + name + ".price");
        return new Product(name, item, price);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("price", Double.toString(price));
        map.put("item", item.serialize());
        return map;
    }
}
