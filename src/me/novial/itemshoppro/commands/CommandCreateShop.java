package me.novial.itemshoppro.commands;

import java.util.ArrayList;
import java.util.List;
import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.listeners.CmdCreateShopSign;
import me.novial.itemshoppro.objects.QueueCmdShopCreate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandCreateShop implements TabExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(Main.configMessager.getMessage("must-be-player"));
      return true;
    }

    if (label.equalsIgnoreCase("createshop")) {
      if (args.length != 4) {
        sender.sendMessage(Main.configMessager.getMessage("not-enough-arguments"));
        return true;
      }

      Player player = (Player) sender;
      int productQuantity = Integer.parseInt(args[0]);
      ItemStack product = new ItemStack(Material.getMaterial(args[1]), productQuantity);
      int currencyQuantity = Integer.parseInt(args[2]);
      ItemStack currency = new ItemStack(Material.getMaterial(args[3]), currencyQuantity);

      CmdCreateShopSign.shopCreationQueue.put(
          player,
          new QueueCmdShopCreate(Bukkit.getOfflinePlayer(player.getUniqueId()), product, currency));
      player.sendMessage(Main.configMessager.getMessage("cmd-create-sign"));
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String label, String[] args) {
    List<String> list = new ArrayList<>();

    if (!(sender instanceof Player)) {
      return null;
    }

    if (command.getName().equals("createshop")) {
      if (args.length == 2 || args.length == 4) {
        list.addAll(Main.materials);
      }
    }

    return list;
  }
}
