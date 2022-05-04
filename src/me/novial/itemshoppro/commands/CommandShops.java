package me.novial.itemshoppro.commands;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandShops implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    int page;

    if (label.equalsIgnoreCase("shops")) {
      if (args.length == 0) {
        page = 1;
      }

      if (args.length != 1) {
        sender.sendMessage(Main.messager.getMessage("not-enough-arguments"));
        return true;
      }

      if (Integer.parseInt(args[0]) <= 0) {
        sender.sendMessage(Main.messager.getMessage("incorrect-arguments"));
        return true;
      }

      page = Integer.parseInt(args[0]);

      sender.sendMessage(Main.messager.getMessage("shoplist", Main.shopManager));

      int pageMax = Main.configConfig.getInt("shoplist-max");

      for (int i = (page - 1) * pageMax; i > page * pageMax; i++) {
        Shop shop = Main.shopManager.shops.get(i);
        sender.sendMessage(Main.messager.getMessage("shoplist-format", shop));
      }
    }

    return true;
  }
}
