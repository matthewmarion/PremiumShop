package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private static List<Category> categories = new ArrayList<>();

    private String name;
    private List<Product> products;

    public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        categories.add(this);
    }

    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
        categories.add(this);
    }

    public static Category getCategoryByName(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public static boolean categoryExists(Category category) {
        return categories.contains(category);
    }

    public boolean containsProductName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void save() {
        for (Category category : categories) {
            System.out.println(category.getName());
            for (Product product : category.getProducts()) {
                ConfigManager.getInventoryConfig().set(category.getName() + ".products." + product.getName(), product.serialize());
            }
        }
        ConfigManager.save(ConfigManager.inventoryf, ConfigManager.getInventoryConfig());
    }

    public static void load() {
        for (String key : ConfigManager.getInventoryConfig().getKeys(false)) {
            ConfigurationSection categorySection = ConfigManager.getInventoryConfig().getConfigurationSection(key + ".products");
            List<Product> products = new ArrayList<>();
            for (String productName : categorySection.getKeys(false)) {
                products.add(Product.load(productName, key));
            }
            Category category = new Category(key, products);
        }
    }

    public static void remove(Category category) {
        categories.remove(category);
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
