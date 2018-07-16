package com.moojm.premiumshop.shop.listeners;

import com.moojm.premiumshop.gui.CategoryInventory;
import com.moojm.premiumshop.shop.Shop;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListeners implements Listener {

    @EventHandler
    public void on(NPCRightClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();
        String name = ChatColor.stripColor(npc.getName());
        if (!ChatColor.stripColor(npc.getName()).equals(Shop.getNPCName())) {
            return;
        }
        CategoryInventory categoryInventory = new CategoryInventory();
        player.openInventory(categoryInventory.getInventory());
    }
}
