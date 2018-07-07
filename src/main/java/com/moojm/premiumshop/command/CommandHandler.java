package com.moojm.premiumshop.command;

import com.moojm.premiumshop.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor {

    private HashMap<String, ShopCommandExecutor> commands = new HashMap<String, ShopCommandExecutor>();

    public CommandHandler() {
        commands.put("create", new ShopCreateCommand());
        commands.put("category", new ShopCategoryCommand());
        commands.put("product", new ShopProductCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pshop")) {

            if (args.length == 0) {
                MessageUtils.tellList(sender, MessageUtils.HELP);
                return true;
            }
            String name = args[0].toLowerCase();

            if (!commands.containsKey(name)) {
                return true;
            }

            final ShopCommandExecutor command = commands.get(name);

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
}
