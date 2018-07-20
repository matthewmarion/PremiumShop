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
    private List<String> purchases;

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
        for (String key : ConfigManager.getProfilesConfig().getKeys(false)) {
            List<String> purchaseStringList = ConfigManager.getProfilesConfig().getStringList(uuid + ".purchases");
            if (purchaseStringList == null || purchaseStringList.size() == 0) {
                break;
            }
            for (String purchase : purchaseStringList) {
                purchases.add(purchase);
            }

        }
    }

    public void save() {
        ConfigManager.getProfilesConfig().set(uuid + ".gold", gold);
        for (String product : purchases) {
            ConfigManager.getProfilesConfig().set(uuid + ".purchases", purchases);
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
        for (String purchase : purchases) {
            if (purchase.equals(product.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addPurchase(Product product) {
        purchases.add(product.getName());
    }

    public void setPurchases(List<String> purchases) {
        this.purchases = purchases;
    }

    public static Set<Profile> getProfiles() {
        return profiles;
    }
}
