package com.moojm.premiumshop.gui;

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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

interface ShopInventoryInterface {
    void work(InventoryClickEvent event, ItemStack item, Player player, Inventory inv);
}

public enum ShopInventoryType implements ShopInventoryInterface {
    CATEGORY {
        @Override
        public void work(InventoryClickEvent event, ItemStack item, Player player, Inventory inv) {
            Category category = Category.getCategoryByName(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
            if (category == null) {
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
            selectCategory(player, category);
            return;
        }
    },
    PRODUCT {
        @Override
        public void work(InventoryClickEvent event, ItemStack item, Player player, Inventory inv) {
            Category category = Category.getCategoryFromProductItem(item);
            Profile profile = Profile.getByPlayer(player);
            if (category == null) {
                handleError(player, event);
                return;
            }
            Product product = Product.getProductByItem(item, category);

            if (profile.hasPurchased(product)) {
                event.setCancelled(true);
                return;
            }

            if (product == null) {
                handleError(player, event);
                return;
            }

            if (!playerCanAfford(product, profile)) {
                MessageUtils.tell(player, MessageUtils.NOT_ENOUGH_GOLD, null, null);
                event.setCancelled(true);
                return;
            }

            selectProduct(player, product, category);
            event.setCancelled(true);
        }
    },
    PURCHASE {
        @Override
        public void work(InventoryClickEvent event, ItemStack item, Player player, Inventory inv) {
            if (doesCancel(item)) {
                event.setCancelled(true);
                player.closeInventory();
                return;
            }

            if (!doesPurchase(item)) {
                event.setCancelled(true);
                return;
            }
            ItemStack purchaseItem = inv.getItem(13);
            Category purchaseCategory = Category.getCategoryFromProductItem(purchaseItem);
            Product product = Product.getProductByItem(purchaseItem, purchaseCategory);
            if (product == null) {
                MessageUtils.tell(player, MessageUtils.ERROR, null, null);
                return;
            }

            purchase(player, product, event);
        }
    };

    private static void selectCategory(Player player, Category category) {
        ProductInventory productInventory = new ProductInventory(category, player);
        player.openInventory(productInventory.getInventory());
    }

    private static void selectProduct(Player player, Product product, Category category) {
        PurchaseInventory purchaseInventory = new PurchaseInventory(product, category);
        player.openInventory(purchaseInventory.getInventory());
    }

    private static boolean playerCanAfford(Product product, Profile profile) {
        return profile.getGold() >= product.getPrice();
    }

    private static boolean doesPurchase(ItemStack item) {
        return item.getType() == Material.EMERALD_BLOCK;
    }

    private static boolean doesCancel(ItemStack item) {
        return item.getType() == Material.REDSTONE_BLOCK;
    }

    private static void executeCommand(Player player, Product product) {
        String command = Utils.replaceCommandPlaceholders(player, product.getCommand());
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } catch (CommandException e) {
            MessageUtils.tell(player, MessageUtils.ERROR, null, null);
        }

    }

    private static boolean purchase(Player player, Product product, InventoryClickEvent event) {
        Profile profile = Profile.getByPlayer(player);
        if (profile == null) {
            MessageUtils.tell(player, MessageUtils.ERROR, null, null);
            event.setCancelled(true);
            player.closeInventory();
            return false;
        }

        if (!playerCanAfford(product, profile)) {
            MessageUtils.tell(player, MessageUtils.NOT_ENOUGH_GOLD, null, null);
            event.setCancelled(true);
            player.closeInventory();
            return false;
        }

        withdrawGold(player, product.getPrice());
        executeCommand(player, product);
        profile.addPurchase(product);
        MessageUtils.tellPurchase(player, product);
        event.setCancelled(true);
        player.closeInventory();
        return true;
    }

    private static void withdrawGold(Player player, double amount) {
        Profile profile = Profile.getByPlayer(player);
        profile.removeGold(amount);
    }

    private static void handleError(Player player, InventoryClickEvent event) {
        MessageUtils.tell(player, MessageUtils.ERROR, null, null);
        event.setCancelled(true);
    }

    private static boolean isCategoryInventory(Inventory inv) {
        return inv.getName().equals(Utils.toColor(Utils.getMessage("category-shop-name")));
    }

    private static boolean isProductInventory(Inventory inv) {
        return inv.getName().equals(Utils.toColor(Utils.getMessage("product-shop-name")));
    }

    private static boolean isPurchaseInventory(Inventory inv) {
        return inv.getName().equals(Utils.toColor(Utils.getMessage("purchase-shop-name"))) ;
    }


    public static ShopInventoryType getShopInventoryType(Inventory inv) {
        if (isCategoryInventory(inv)) {
            return CATEGORY;
        }
        if (isProductInventory(inv)) {
            return PRODUCT;
        }
        if (isPurchaseInventory(inv)) {
            return PURCHASE;
        }
        return null;
    }
}
