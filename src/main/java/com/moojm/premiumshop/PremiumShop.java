package com.moojm.premiumshop;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.moojm.premiumshop.command.gold.GoldCommandHandler;
import com.moojm.premiumshop.command.shop.ShopCommandHandler;
import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.profile.ProfileListeners;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.shop.Purchase;
import com.moojm.premiumshop.shop.listeners.ShopGUIListener;
import com.moojm.premiumshop.shop.listeners.NPCListeners;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PremiumShop extends JavaPlugin {

    private static PremiumShop instance;

    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(Product.class);
        ConfigurationSerialization.registerClass(Purchase.class);
        ConfigManager configManager = new ConfigManager();
        configManager.loadConfig();
        loadShopInventory();
        registerCommands();
        registerListeners();
        registerPlaceholders();
    }

    public void onDisable() {
        instance = null;
        saveShopInventory();
        Profile.saveAll();
    }

    private void loadShopInventory() {
        Category.load();
    }

    private void saveShopInventory() {
        Category.save();
    }

    private void registerCommands() {
        getCommand("pshop").setExecutor(new ShopCommandHandler());
        getCommand("gold").setExecutor(new GoldCommandHandler());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new NPCListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ShopGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProfileListeners(), this);
    }

    private boolean placeholderAPIEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI");
    }

    private void registerPlaceholders() {
        if (placeholderAPIEnabled()) {
            PlaceholderAPI.registerPlaceholder(PremiumShop.getInstance(), "premiumshop_gold",
                    event -> {
                        System.out.println("Testing this????");
                        Player player = event.getPlayer();
                        Profile profile = Profile.getByPlayer(player);
                        if (profile == null) {
                            return "";
                        }
                        String gold = Utils.convertMoney(profile.getGold());
                        return gold;
                    });
        }
    }

    public static PremiumShop getInstance() {
        return instance;
    }
}
