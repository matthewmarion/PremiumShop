package com.moojm.premiumshop.command.shop;

import com.moojm.premiumshop.command.PremiumCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ShopReportCommand extends PremiumCommandExecutor {

    public ShopReportCommand() {
        setSubCommand("report");
        setPermission("pshop.admin");
        setUsage("/pshop report <player>");
        setBoth();
        setLength(2);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        UUID playerUUID = UUID.fromString(args[1]);
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
    }
}
