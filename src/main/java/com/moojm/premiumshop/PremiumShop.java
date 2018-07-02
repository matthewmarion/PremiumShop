package com.moojm.premiumshop;

import com.moojm.premiumshop.command.CommandHandler;
import com.moojm.premiumshop.command.ShopCommandExecutor;
import com.moojm.premiumshop.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PremiumShop extends JavaPlugin {

    private static PremiumShop instance;

    public void onEnable() {
        instance = this;
        ConfigManager configManager = new ConfigManager();
        configManager.loadConfig();
        registerCommands();
    }

    public void onDisable() {
        instance = null;
    }

    private void registerCommands() {
        getCommand("pshop").setExecutor(new CommandHandler());
    }

    public static PremiumShop getInstance() {
        return instance;
    }
}
