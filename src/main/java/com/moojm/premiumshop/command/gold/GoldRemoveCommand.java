package com.moojm.premiumshop.command.gold;

import com.moojm.premiumshop.command.PremiumCommandExecutor;
import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.utils.MessageUtils;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoldRemoveCommand extends PremiumCommandExecutor {

    public GoldRemoveCommand() {
        setSubCommand("remove");
        setPermission("pshop.admin");
        setUsage("/gold remove <player> <amount>");
        setBoth();
        setLength(3);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            MessageUtils.tell(sender, ChatColor.RED + getUsage(), null, null);
            return;
        }

        Profile profile = Profile.getByPlayer(target);
        String sAmount = args[2];
        double amount = Integer.valueOf(sAmount);
        profile.removeGold(amount);
        MessageUtils.tell(target, MessageUtils.REMOVE_GOLD, "{amount}", sAmount);
        MessageUtils.tell(sender, Utils.toColor("&aSUCCESS &r&7Removed &a" + sAmount + "&7 from " + target.getName() + "s balance."), null, null);
    }
}
