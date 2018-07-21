package com.moojm.premiumshop.utils;

import com.moojm.premiumshop.profile.Profile;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class GoldPlaceholder extends PlaceholderExpansion {

    private final String identifier = "gold";

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getPlugin() {
        return null;
    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (!identifier.equals(identifier)) {
            return "";
        }
        Profile profile = Profile.getByPlayer(player);
        return String.valueOf(profile.getGold());
    }
}
