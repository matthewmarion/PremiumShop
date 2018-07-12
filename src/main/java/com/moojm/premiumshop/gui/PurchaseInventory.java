package com.moojm.premiumshop.gui;

import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PurchaseInventory extends ShopInventory {

    private static final String SHOP_NODE = "purchase-shop-name";
    private Product product;
    private Category category;

    public PurchaseInventory(Product product, Category category) {
        super(SHOP_NODE);
        this.product = product;
        this.category = category;
        createLayout();
    }

    private void createLayout() {
        int middle = 13;
        this.getInventory().setItem(middle, product.getItem());
        this.getInventory().setItem(12, getPurchaseItem());
        this.getInventory().setItem(14, getCancelItem());
    }

    private ItemStack getPurchaseItem() {
        String displayName = Utils.toColor("&a&lPURCHASE");
        List<String> lore = new ArrayList<>();
        lore.add(Utils.toColor("&7Items from the &6Gold Exchange &7are non-refundable."));
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getCancelItem() {
        String displayName = Utils.toColor("&c&lCANCEL");
        List<String> lore = new ArrayList<>();
        lore.add(Utils.toColor("&7Cancel purchase and exit shop."));
        ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
