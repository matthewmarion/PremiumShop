package com.moojm.premiumshop.config;

import com.moojm.premiumshop.PremiumShop;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static File configf;
    public static File messagesf;
    public static File shopf;
    private static FileConfiguration config;
    private static FileConfiguration messages;
    private static FileConfiguration shop;

    public void loadConfig() {
        configf = new File(PremiumShop.getInstance().getDataFolder(), "config.yml");
        createNewFile(configf, "config.yml");

        messagesf = new File(PremiumShop.getInstance().getDataFolder(), "messages.yml");
        createNewFile(messagesf, "messages.yml");

        shopf = new File(PremiumShop.getInstance().getDataFolder(), "shop.yml");
        createNewFile(shopf, "shop.yml");

        config = YamlConfiguration.loadConfiguration(configf);
        messages = YamlConfiguration.loadConfiguration(messagesf);
        shop = YamlConfiguration.loadConfiguration(shopf);
    }

    private void createNewFile(File file, String fileName) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            PremiumShop.getInstance().saveResource(fileName, false);
        }
    }

    private void loadFileIntoConfiguration(File file, FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void save(File file, FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfig() { return config; }
    public static FileConfiguration getMessagesConfig() { return messages; }
    public static FileConfiguration getShopConfig() { return shop; }

}
