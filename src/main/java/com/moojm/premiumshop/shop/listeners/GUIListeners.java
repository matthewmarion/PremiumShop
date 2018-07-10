package com.moojm.premiumshop.shop.listeners;

import com.moojm.premiumshop.gui.ProductInventory;
import com.moojm.premiumshop.gui.ShopInventory;
import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.MessageUtils;
import com.moojm.premiumshop.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandException;
import org.bukkit.command.ConsoleCommandSender;
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
            System.out.println(command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } catch (CommandException e) {
            MessageUtils.tell(player, MessageUtils.ERROR, null, null);
        }

    }

    private boolean purchase(Player player, Product product) {
        Profile profile = Profile.getByPlayer(player);
        if (profile == null) {
            System.out.println("Profile null");
            return false;
        }

        if (!playerCanAfford(product, profile)) {
            MessageUtils.tell(player, "&cNot enough gold.", null, null);
            return false;
        }

        MessageUtils.tell(player, "&a&lPURCHASED", null, null);
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
