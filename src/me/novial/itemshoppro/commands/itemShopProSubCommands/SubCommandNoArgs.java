package me.novial.itemshoppro.commands.itemShopProSubCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SubCommandNoArgs {
    public static void run(CommandSender sender, Command command) {
        sender.sendMessage("ItemShopPro by Novial.");
    }
}
