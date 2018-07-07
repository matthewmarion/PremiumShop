package com.moojm.premiumshop.command;

import com.moojm.premiumshop.shop.Category;
import com.moojm.premiumshop.shop.Product;
import com.moojm.premiumshop.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class ShopCmdCommand extends ShopCommandExecutor {

    public ShopCmdCommand() {
        setSubCommand("cmd");
        setPermission("pshop.admin");
        setUsage("/pshop cmd <product> <category> <args>");
        setBoth();
        setLength(4);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Category category = Category.getCategoryByName(args[2]);
        if (category == null) {
            MessageUtils.tell(sender, MessageUtils.NULL_CATEGORY, null, null);
            return;
        }
        Product product = Product.getProductByName(args[1], category);
        if (product == null) {
            MessageUtils.tell(sender, MessageUtils.NULL_PRODUCT, null, null);
            return;
        }

        StringBuilder cmdBuilder = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            cmdBuilder.append(args[i] + " ");
        }
        String cmd = cmdBuilder.toString().trim();
        product.setCommand(cmd);
        MessageUtils.tell(sender, MessageUtils.CREATE_CMD, "{cmd}", cmd);
    }
}
