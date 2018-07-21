package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.shop.Product;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Purchase implements ConfigurationSerializable {

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private Product product;
    private String timestamp;

    public Purchase(Product product) {
        this.product = product;
        Date date = new Date();
        timestamp = dateFormat.format(date);
    }

    public Purchase(Product product, String timestamp) {
        this.product = product;
        this.timestamp = dateFormat.format(timestamp);
    }

    public Product getProduct() {
        return product;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("product", product);
        return map;
    }
}
