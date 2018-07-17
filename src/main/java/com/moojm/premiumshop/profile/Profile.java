package com.moojm.premiumshop.profile;

import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Profile {

    private static Set<Profile> profiles = new HashSet<>();

    private UUID uuid;
    private Player player;
    private double gold;
    private Set<Product> purchases;

    public Profile(UUID uuid, Player player) {
        this.uuid = uuid;
        this.player = player;
        this.purchases = new HashSet<>();

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
        for (String key : ConfigManager.getProfilesConfig().getKeys(false)) {
            ConfigurationSection purchasesSection = ConfigManager.getProfilesConfig().getConfigurationSection(key + ".purchases");
            if (purchasesSection == null) {
                break;
            }
            if (purchasesSection.getKeys(false).size() != 0) {
                for (String productName : purchasesSection.getKeys(false)) {
                    ItemStack item = ConfigManager.getProfilesConfig().getItemStack(key + ".purchases." + productName + ".item");
                    Category category = Category.getCategoryFromProductItem(item);
                    purchases.add(Product.load(productName, category.getName()));
                }
            }
        }
    }

    public void save() {
        ConfigManager.getProfilesConfig().set(uuid + ".gold", gold);
        for (Product product : purchases) {
            ConfigManager.getProfilesConfig().set(uuid + ".purchases." + product.getName(), product.serialize());
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
        for (Product purchase : purchases) {
            String purchaseName = purchase.getName();
            String productName = product.getName();
            if (purchaseName.equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public void addPurchase(Product product) {
        purchases.add(product);
    }

    public void setPurchases(Set<Product> purchases) {
        this.purchases = purchases;
    }

    public static Set<Profile> getProfiles() {
        return profiles;
    }
}
