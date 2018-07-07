package com.moojm.premiumshop.shop.listeners;

import com.moojm.premiumshop.gui.ShopInventory;
import com.moojm.premiumshop.shop.Shop;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListeners implements Listener {

    @EventHandler
    public void on(NPCRightClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();
        if (!npc.getName().equals(Shop.getNPCName())) {
            return;
        }
        ShopInventory shopInventory = new ShopInventory(player);
        player.openInventory(shopInventory.getInventory());
    }
}
