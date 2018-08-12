package com.moojm.premiumshop.command.gold;

import com.moojm.premiumshop.command.PremiumCommandExecutor;
import com.moojm.premiumshop.command.shop.ShopCategoryCommand;
import com.moojm.premiumshop.command.shop.ShopCmdCommand;
import com.moojm.premiumshop.command.shop.ShopCreateCommand;
import com.moojm.premiumshop.command.shop.ShopProductCommand;
import com.moojm.premiumshop.profile.Profile;
import com.moojm.premiumshop.utils.MessageUtils;
import com.moojm.premiumshop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GoldCommandHandler implements CommandExecutor {

    private HashMap<String, PremiumCommandExecutor> commands = new HashMap<String, PremiumCommandExecutor>();

    public GoldCommandHandler() {
        commands.put("set", new GoldSetCommand());
        commands.put("add", new GoldAddCommand());
        commands.put("remove", new GoldRemoveCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gold")) {

            if (args.length == 0) {
                sendOwnGoldBalance(sender);
                return true;
            }

            String name = args[0].toLowerCase();
            Player target = Bukkit.getPlayer(name);
            if (sender.hasPermission("premiumshop.gold.others") && target != null) {
                sendOthersGoldBalance(sender, target);
                return true;
            }

            if (name.equalsIgnoreCase("help")) {
                MessageUtils.tellList(sender, MessageUtils.HELP);
                return true;
            }

            if (!commands.containsKey(name)) {
                return true;
            }

            final PremiumCommandExecutor command = commands.get(name);

            if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
                sender.sendMessage(MessageUtils.NO_PERMISSION);
                return false;
            }

            if (!command.isBoth()) {
                if (command.isConsole() && sender instanceof Player) {
                    sender.sendMessage(MessageUtils.ONLY_CONSOLE);
                    return false;
                }

                if (command.isPlayer() && sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(MessageUtils.ONLY_PLAYER);
                    return false;
                }
            }

            if (command.getLength() > args.length) {
                sender.sendMessage(ChatColor.RED + command.getUsage());
                return false;
            }

            command.execute(sender, args);
        }
        return false;
    }

    private void sendOwnGoldBalance(CommandSender sender) {
        if (!(sender instanceof Player)) {
            MessageUtils.tell(sender, MessageUtils.ONLY_PLAYER, null, null);
            return;
        }
        Player player = (Player) sender;
        Profile profile = Profile.getByPlayer(player);
        if (profile == null) {
            MessageUtils.tell(player, MessageUtils.ERROR, null, null);
            return;
        }

        String gold = String.valueOf(profile.getGold());
        MessageUtils.tell(player, MessageUtils.GOLD_BALANCE, "{gold}", gold);
        return;
    }

    private void sendOthersGoldBalance(CommandSender sender, Player target) {
        if (target == null) {
            MessageUtils.tell(sender, Utils.toColor("Player not found"), null, null);
            return;
        }
        Profile profile = Profile.getByPlayer(target);
        if (profile == null) {
            MessageUtils.tell(target, MessageUtils.ERROR, null, null);
            return;
        }
        String gold = String.valueOf(profile.getGold());
        MessageUtils.tell(sender, MessageUtils.GOLD_BALANCE, "{gold}", gold);
        return;
    }
}
