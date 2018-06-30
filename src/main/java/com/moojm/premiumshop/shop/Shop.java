package com.moojm.premiumshop.shop;

import com.moojm.premiumshop.PremiumShop;
import com.moojm.premiumshop.utils.Utils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Shop {

    private int id;
    private Location location;

    public Shop(int id, Location location) {
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void save() {
        double[] coords = Utils.parseLocation(location);
        PremiumShop.getInstance().getConfig().set("shops." + id + ".location" + ".x", coords[0]);
        PremiumShop.getInstance().getConfig().set("shops." + id + ".location" + ".y", coords[1]);
        PremiumShop.getInstance().getConfig().set("shops." + id + ".location" + ".z", coords[2]);
        PremiumShop.getInstance().getConfig().set("shops." + id + ".location" + ".world", location.getWorld().getName());
        PremiumShop.getInstance().saveConfig();
    }

    public void create() {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "merchant");
        npc.spawn(location);
    }

}
