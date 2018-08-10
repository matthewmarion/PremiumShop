package com.moojm.premiumshop;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
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
import org.bukkit.ChatColor;
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
        loadGoldPlaceholder();
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

    private void loadGoldPlaceholder() {
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            // The plugin is enabled
            PlaceholderAPI.registerPlaceholder(this, "premiumshopgold",
                    new PlaceholderReplacer() {

                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
                            Player player = event.getPlayer();
                            Profile profile = Profile.getByPlayer(player);
                            return ChatColor.YELLOW + String.valueOf(profile.getGold());
                        }

                    });
        }
    }

    public static PremiumShop getInstance() {
        return instance;
    }
}
