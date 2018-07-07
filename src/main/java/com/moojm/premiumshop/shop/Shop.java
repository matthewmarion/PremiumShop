package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.utils.Utils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Shop {

    private int id;
    private Location location;
    private static String NPC = "Cowboy";

    public Shop(Location location) {
        this.id = getNextId();
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int getNextId() {
        int id = 0;
        for (String key : ConfigManager.getShopConfig().getKeys(false)) {
            int intId = Integer.parseInt(key);
            id = intId + 1;
        }
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void save() {
        double[] coords = Utils.parseLocation(location);
        ConfigManager.getShopConfig().set(id + ".location" + ".x", coords[0]);
        ConfigManager.getShopConfig().set(id + ".location" + ".y", coords[1]);
        ConfigManager.getShopConfig().set(id + ".location" + ".z", coords[2]);
        ConfigManager.getShopConfig().set(id + ".location" + ".world", location.getWorld().getName());
        ConfigManager.save(ConfigManager.shopf, ConfigManager.getShopConfig());
    }

    public void create() {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, NPC);
        npc.spawn(location);
    }

    public static String getNPCName() {
        return NPC;
    }

}
