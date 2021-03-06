package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements ConfigurationSerializable {

    private String name;
    private double price;
    private ItemStack item;
    private String command;
    private boolean canRepurchase;

    public Product(String name, ItemStack item, double price, boolean canRepurchase) {
        this.name = name;
        this.item = item;
        this.price = price;
        this.canRepurchase = canRepurchase;
    }

    public Product(String name, ItemStack item, double price, boolean canRepurchase, String command) {
        this(name, item, price, canRepurchase);
        this.command = command;
    }

    public static Product getProductByName(String name, Category category) {
        for (Product product : category.getProducts()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public static Product getProductByItem(ItemStack item, Category category) {
        for (Product product : category.getProducts()) {
            String itemName = item.getItemMeta().getDisplayName();
            String productName = product.getItem().getItemMeta().getDisplayName();
            if (itemName.equals(productName)) {
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

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean canRepurchase() {
        return canRepurchase;
    }

    public static Product load(String name, String category) {
        ItemStack item = ConfigManager.getInventoryConfig().getItemStack(category + ".products." + name + ".item");
        double price = ConfigManager.getInventoryConfig().getDouble(category + ".products." + name + ".price");
        boolean canRepurchase = ConfigManager.getInventoryConfig().getBoolean(category + ".products." + name + ".can-repurchase");
        String command = ConfigManager.getInventoryConfig().getString(category + ".products." + name + ".command");
        return new Product(name, item, price, canRepurchase, command);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("price", price);
        map.put("item", item);
        map.put("can-repurchase", canRepurchase);
        map.put("command", command);
        return map;
    }
}
