package com.moojm.premiumshop.command;

import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageUtils.tell(sender, MessageUtils.ONLY_PLAYER, null, null);
            return true;
        }

        Player player = (Player) sender;
        Profile profile = Profile.getByPlayer(player);
        if (profile == null) {
            MessageUtils.tell(player, MessageUtils.ERROR, null, null);
            return true;
        }

        String gold = String.valueOf(profile.getGold());
        MessageUtils.tell(player, MessageUtils.GOLD_BALANCE, "{gold}", gold);
        return false;
    }
}
