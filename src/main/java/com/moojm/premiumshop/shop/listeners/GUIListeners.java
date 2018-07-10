package com.moojm.premiumshop.shop.listeners;

import com.moojm.premiumshop.gui.ProductInventory;
import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.MessageUtils;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GUIListeners implements Listener {

    private HashMap<UUID, Product> pendingTransactions = new HashMap<>();

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        if (!isCategoryInventory(inv) && !isProductInventory(inv)) {
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (!isValidClick(event, item)) {
            return;
        }

        if (isCategoryInventory(inv)) {
            Category category = Category.getCategoryByName(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
            if (category == null) {
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
            selectCategory(player, category);
            return;
        }

        if (isProductInventory(inv)) {
            Category category = Category.getCategoryFromItem(item);
            if (category == null) {
                handleError(player, event);
                return;
            }
            Product product = Product.getProductByItem(item, category);
            if (product == null) {
                handleError(player, event);
                return;
            }

            if (!transactionIsPending(player)) {
                addToPendingTransaction(player, product);
                event.setCancelled(true);
                return;
            }

            purchase(player, product);
            event.setCancelled(true);
        }

    }

    private boolean playerCanAfford(Product product, Profile profile) {
        return profile.getGold() >= product.getPrice();
    }

    private void executeCommand(Player player, Product product) {
        String command = Utils.replaceCommandPlaceholders(player, product.getCommand());
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } catch (CommandException e) {
            MessageUtils.tell(player, MessageUtils.ERROR, null, null);
        }

    }

    private void addToPendingTransaction(Player player, Product product) {
        pendingTransactions.put(player.getUniqueId(), product);
    }

    private boolean transactionIsPending(Player player) {
        return pendingTransactions.containsKey(player.getUniqueId());
    }

    private void confirmTransaction(Player player, Product product) {
        if (!transactionIsPending(player)) {
            return;
        }
        pendingTransactions.remove(player.getUniqueId());
    }

    private boolean purchase(Player player, Product product) {
        Profile profile = Profile.getByPlayer(player);
        if (profile == null) {
            MessageUtils.tell(player, MessageUtils.ERROR, null, null);
            return false;
        }

        if (!playerCanAfford(product, profile)) {
            MessageUtils.tell(player, MessageUtils.NOT_ENOUGH_GOLD, null, null);
            return false;
        }

        MessageUtils.tellPurchase(player, product);
        executeCommand(player, product);
        return true;
    }
    private void handleError(Player player, InventoryClickEvent event) {
        MessageUtils.tell(player, MessageUtils.ERROR, null, null);
        event.setCancelled(true);
    }

    private void selectCategory(Player player, Category category) {
        ProductInventory productInventory = new ProductInventory(player, category);
        player.openInventory(productInventory.getInventory());
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

    private boolean isCategoryInventory(Inventory inv) {
        return inv.getName().equals(Utils.toColor(Utils.getMessage("category-shop-name")));
    }

    private boolean isProductInventory(Inventory inv) {
        return inv.getName().equals(Utils.toColor(Utils.getMessage("product-shop-name")));
    }
}
