package me.novial.itemshoppro.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandItemShopPro implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
        }

        if (label.equalsIgnoreCase("itemshoppro")) {
            if (args[0].equalsIgnoreCase("info") && args.length == 1) {
                // call func outside file here
            }
        }

        return true;
    }
}
