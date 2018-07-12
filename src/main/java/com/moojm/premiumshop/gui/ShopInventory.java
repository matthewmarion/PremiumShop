package com.moojm.premiumshop.gui;

import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ShopInventory {

    private Inventory inv;
    private final ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);

    public ShopInventory(String shopNode) {
        final String shopName = Utils.toColor(Utils.getMessage(shopNode));
        inv = Bukkit.createInventory(null, 27, shopName);
        createBorder();
    }

    private void createBorder() {
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, border);
        }
    }

    public Inventory getInventory() {
        return inv;
    }


}
