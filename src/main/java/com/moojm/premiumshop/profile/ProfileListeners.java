package com.moojm.premiumshop.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListeners implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Profile profile = new Profile(event.getPlayer());
        profile.load();
        if (profile.getPlayer().getName().equals("Disrupts")) {
            profile.setGold(1000);
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        Profile profile = Profile.getByPlayer(event.getPlayer());
        if (profile == null) {
            return;
        }
        profile.save();
        Profile.getProfiles().remove(profile);
    }
}
