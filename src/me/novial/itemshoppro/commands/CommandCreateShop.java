package me.novial.itemshoppro.commands;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.listeners.CmdCreateShopSign;
import me.novial.itemshoppro.objects.QueueCmdShopCreate;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandCreateShop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.messager.getMessage("must-be-player"));
            return true;
        }

        if (label.equalsIgnoreCase("createshop")) {
            if (args.length != 4) {
                sender.sendMessage(Main.messager.getMessage("not-enough-arguments"));
                return true;
            }

            Player player = (Player) sender;
            int productQuantity = Integer.parseInt(args[0]);
            ItemStack product = new ItemStack(Material.getMaterial(args[1]), productQuantity);
            int currencyQuantity = Integer.parseInt(args[2]);
            ItemStack currency = new ItemStack(Material.getMaterial(args[3]), currencyQuantity);

            CmdCreateShopSign.shopCreationQueue.put(player, new QueueCmdShopCreate(player, product, currency));
            player.sendMessage(Main.messager.getMessage("cmd-create-sign"));
        }

        return true;
    }
}
