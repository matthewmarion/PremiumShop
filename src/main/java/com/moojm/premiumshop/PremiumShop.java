package com.moojm.premiumshop;

import org.bukkit.plugin.java.JavaPlugin;

public class PremiumShop extends JavaPlugin {

    private static PremiumShop instance;

    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    public void onDisable() {
        instance = null;
    }

    public static PremiumShop getInstance() {
        return instance;
    }
}
