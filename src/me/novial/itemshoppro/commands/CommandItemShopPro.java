package me.novial.itemshoppro.commands;

import me.novial.itemshoppro.commands.itemShopProSubCommands.SubCommandNoArgs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandItemShopPro implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (label.equalsIgnoreCase("itemshoppro")) {
      if (args.length == 0) {
        SubCommandNoArgs.run(sender, command);
      }
    }

    return true;
  }
}
