package me.novial.itemshoppro.commands.itemShopProSubCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandInfo {
    public boolean subCommandInfo(CommandSender sender, Command command) {
        Player player;
        if (sender instanceof Player) {
            Player player = (Player) sender;
        }

        player.sendMessage("Plugin by Novial");
    }
}
