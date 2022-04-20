package me.novial.itemshoppro.commands.itemShopProSubCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandInfo {
    public static void subCommandInfo(CommandSender sender, Command command) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
        }

        sender.sendMessage("ItemShopPro by Novial.");
    }
}
