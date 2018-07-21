package com.moojm.premiumshop.shop.listeners;

import com.moojm.premiumshop.shop.gui.ShopInventoryType;
import com.moojm.premiumshop.profile.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopGUIListener implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Profile profile = Profile.getByPlayer(player);
        Inventory inv = event.getInventory();
        ShopInventoryType invType = ShopInventoryType.getShopInventoryType(inv);
        if (invType == null) {
            return;
        }
        ItemStack item = event.getCurrentItem();
        if (!isValidClick(event, item)) {
            event.setCancelled(true);
            return;
        }
        invType.work(event, item, player, inv);
    }

    private boolean isValidClick(InventoryClickEvent event, ItemStack item) {
        if (isMovedOutInventory(event)) {
            event.setCancelled(true);
            return false;
        }

        if (item == null || item.getType() == Material.AIR || itemIsBorder(item)) {
            event.setCancelled(true);
            return false;
        }
        return true;
    }

    private boolean isMovedOutInventory(InventoryClickEvent event) {
        return event.getRawSlot() >= event.getInventory().getSize();
    }

    private boolean itemIsBorder(ItemStack item) {
        return item.getType() == Material.STAINED_GLASS_PANE;
    }
}
