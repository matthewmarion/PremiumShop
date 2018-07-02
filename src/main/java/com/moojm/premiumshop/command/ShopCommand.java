package com.moojm.premiumshop.command;

import com.moojm.premiumshop.shop.Shop;
import com.moojm.premiumshop.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand extends ShopCommandExecutor {

    public ShopCommand() {
        setSubCommand("create");
        setPermission("pshop.admin");
        setUsage("/pshop create");
        setPlayer(true);
        setLength(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Location location = player.getLocation();

        if (location == null) {
            return;
        }

        Shop shop = new Shop(location);
        shop.save();
        shop.create();
        MessageUtils.tell(sender, MessageUtils.CREATE_SHOP, "{id}", Integer.toString(shop.getId()));
    }

}
