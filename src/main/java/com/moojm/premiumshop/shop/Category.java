package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private static List<Category> categories = new ArrayList<>();

    private String name;
    private ItemStack item;
    private List<Product> products;

    public Category(String name, ItemStack item) {
        this.name = name;
        this.item = item;
        this.products = new ArrayList<>();
        categories.add(this);
    }

    public Category(String name, ItemStack item, List<Product> products) {
        this.name = name;
        this.item = item;
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
            ConfigManager.getInventoryConfig().set(category.getName() + ".item", category.getItem().serialize());
            for (Product product : category.getProducts()) {
                ConfigManager.getInventoryConfig().set(category.getName() + ".products." + product.getName(), product.serialize());
            }
        }
        ConfigManager.save(ConfigManager.inventoryf, ConfigManager.getInventoryConfig());
    }

    public static void load() {
        for (String key : ConfigManager.getInventoryConfig().getKeys(false)) {
            ItemStack item = ConfigManager.getInventoryConfig().getItemStack(key + ".item");
            ConfigurationSection categorySection = ConfigManager.getInventoryConfig().getConfigurationSection(key + ".products");
            List<Product> products = new ArrayList<>();
            for (String productName : categorySection.getKeys(false)) {
                products.add(Product.load(productName, key));
            }
            Category category = new Category(key, item, products);
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

    public ItemStack getItem() {
        return item;
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
