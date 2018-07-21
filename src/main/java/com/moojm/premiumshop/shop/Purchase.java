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
    private Date timestamp;

    public Purchase(Product product) {
        this.product = product;
        timestamp = new Date();
    }

    public Purchase(Product product, Date timestamp) {
        this.product = product;
        this.timestamp = timestamp;
    }

    public Product getProduct() {
        return product;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        //map.put("timestamp", dateFormat.format(timestamp));
        map.put("product", product);
        return map;
    }
}
