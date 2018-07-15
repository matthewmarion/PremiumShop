package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.config.ConfigManager;
import com.moojm.premiumshop.utils.Utils;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.ai.NPCHolder;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Shop {

    private int id;
    private Location location;
    private final static String NPC_NAME = "&6&lGold Exchange";
    private final String skinName = "Disrupts";

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

    private void changeSkin(NPC npc, String skinName) {
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc.getId());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + skinName);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Utils.toColor(NPC_NAME));
        npc.spawn(location);
        changeSkin(npc, skinName);
    }

    public static String getNPCName() {
        return NPC_NAME;
    }

}