package com.moojm.premiumshop;

import com.moojm.premiumshop.command.ShopCommandHandler;
import com.moojm.premiumshop.command.gold.GoldCommand;
import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.profile.ProfileListeners;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.listeners.GUIListeners;
import com.moojm.premiumshop.shop.listeners.NPCListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PremiumShop extends JavaPlugin {

    private static PremiumShop instance;

    public void onEnable() {
        instance = this;
        ConfigManager configManager = new ConfigManager();
        configManager.loadConfig();
        loadShopInventory();
        registerCommands();
        registerListeners();
    }

    public void onDisable() {
        instance = null;
        saveShopInventory();
    }

    private void loadShopInventory() {
        Category.load();
    }

    private void saveShopInventory() {
        Category.save();
    }

    private void registerCommands() {
        getCommand("pshop").setExecutor(new ShopCommandHandler());
        getCommand("gold").setExecutor(new GoldCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new NPCListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ProfileListeners(), this);
    }

    public static PremiumShop getInstance() {
        return instance;
    }
}
