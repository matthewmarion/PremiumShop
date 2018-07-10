package com.moojm.premiumshop.profile;

import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.shop.Product;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Profile {

    private static Set<Profile> profiles = new HashSet<>();

    private UUID uuid;
    private Player player;
    private double gold;

    public Profile(UUID uuid, Player player) {
        this.uuid = uuid;
        this.player = player;

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
    }

    public void save() {
        ConfigManager.getProfilesConfig().set(uuid + ".gold", gold);
        ConfigManager.save(ConfigManager.profilesf, ConfigManager.getProfilesConfig());
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

    public static Set<Profile> getProfiles() {
        return profiles;
    }
}
