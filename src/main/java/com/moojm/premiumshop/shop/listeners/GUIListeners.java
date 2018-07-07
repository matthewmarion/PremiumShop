package com.moojm.premiumshop.shop.listeners;

import com.moojm.premiumshop.gui.ProductInventory;
import com.moojm.premiumshop.gui.ShopInventory;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIListeners implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        if (!isShopInventory(inv)) {
            return;
        }

        if (isMovedOutInventory(event)) {
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR || itemIsBorder(item)) {
            event.setCancelled(true);
            return;
        }

        Category category = Category.getCategoryByName(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
        if (category == null) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(true);
        player.closeInventory();

        ProductInventory productInventory = new ProductInventory(player, category);
        player.openInventory(productInventory.getInventory());
    }

    private boolean isMovedOutInventory(InventoryClickEvent event) {
        return event.getRawSlot() >= event.getInventory().getSize();
    }

    private boolean itemIsBorder(ItemStack item) {
        return item.getType() == Material.STAINED_GLASS_PANE;
    }

    private boolean isShopInventory(Inventory inv) {
        return inv.getName().equals(Utils.toColor(Utils.getMessage("shop-name")));
    }
}
