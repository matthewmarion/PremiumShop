package com.moojm.premiumshop;

import com.moojm.premiumshop.command.CommandHandler;
import com.moojm.premiumshop.command.ShopCommandExecutor;
import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.shop.Category;
import org.bukkit.plugin.java.JavaPlugin;

public class PremiumShop extends JavaPlugin {

    private static PremiumShop instance;

    public void onEnable() {
        instance = this;
        ConfigManager configManager = new ConfigManager();
        configManager.loadConfig();
        loadShopInventory();
        registerCommands();
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
        getCommand("pshop").setExecutor(new CommandHandler());
    }

    public static PremiumShop getInstance() {
        return instance;
    }
}
