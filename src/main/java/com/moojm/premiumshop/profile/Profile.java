package com.moojm.premiumshop.profile;

import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.shop.Purchase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Profile {

    private static Set<Profile> profiles = new HashSet<>();

    private UUID uuid;
    private Player player;
    private double gold;
    private List<Purchase> purchases;

    public Profile(UUID uuid, Player player) {
        this.uuid = uuid;
        this.player = player;
        this.purchases = new ArrayList<>();

        profiles.add(this);
    }

    public Profile(Player player) {
        this(player.getUniqueId(), player);
    }

    public static Profile getByUUID(UUID uuid) {
        for (Profile profile : profiles) {
            if (profile.getUUID().equals(uuid)) {
                return profile;
            }
        }
        return null;
    }

    public static Profile getByPlayer(Player player) {
        return getByUUID(player.getUniqueId());
    }

    public void load() {
        this.gold = ConfigManager.getProfilesConfig().getDouble(uuid + ".gold");
        ConfigurationSection purchaseSection = ConfigManager.getProfilesConfig().getConfigurationSection(uuid + ".purchases");
        if (purchaseSection == null) {
            return;
        }
        for (String productName : purchaseSection.getKeys(false)) {
            String path = uuid + ".purchases." + productName;
            Product product = loadProduct(productName, path);
            String timestamp = ConfigManager.getProfilesConfig().getString(path + ".timestamp");
            purchases.add(new Purchase(product, timestamp));
        }
    }

    private Product loadProduct(String productName, String path) {
        ItemStack item = ConfigManager.getProfilesConfig().getItemStack(path + ".item");
        double price = ConfigManager.getProfilesConfig().getDouble(path + ".price");
        String command = ConfigManager.getProfilesConfig().getString(path + ".command");
        return new Product(productName, item, price, command);
    }

    public void save() {
        ConfigManager.getProfilesConfig().set(uuid + ".gold", gold);
        for (Purchase purchase : purchases) {
            ConfigManager.getProfilesConfig().set(uuid + ".purchases." + purchase.getProduct().getName(), purchase.serialize());
        }
        ConfigManager.save(ConfigManager.profilesf, ConfigManager.getProfilesConfig());
    }

    public static void saveAll() {
        Set<Profile> allProfiles = profiles;
        for (Profile profile : profiles) {
            profile.save();
        }
        profiles.removeAll(allProfiles);
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public double getGold() {
        return gold;
    }

    public void addGold(double gold) {
        this.gold += gold;
    }

    public void removeGold(double gold) {
        this.gold -= gold;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasPurchased(Product product) {
        for (Purchase purchase : purchases) {
            if (purchase.getProduct().getName().equals(product.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public static Set<Profile> getProfiles() {
        return profiles;
    }
}
