package me.novial.itemshoppro.shops;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BuyShop extends Shop {

    public BuyShop(Player owner, ItemStack product, ItemStack currency, Chest inventoryBlock, Sign sign) {
        super(owner, product, currency, inventoryBlock, sign);
    }

    /** Purchases an item from the Shop. **/
    public void purchaseItem(Player player) {
        List<Player> blockedPlayers = new ArrayList<>(Arrays.asList(this.blockedPlayers));
        if (blockedPlayers.contains(player)) {
            return;
        }

        Inventory chestInventory = this.inventoryBlock.getInventory();
        Inventory playerInventory = player.getInventory();

        if (chestInventory.contains(currency) && playerInventory.contains(product)) {
            chestInventory.removeItem(product);
            playerInventory.addItem(product);

            playerInventory.removeItem(currency);
            chestInventory.addItem(currency);
        }

        player.sendMessage("Successful transaction");
    }

    /** Returns a boolean whether the shop is in stock or not. **/
    public boolean inStock() {
        Inventory inventory = this.inventoryBlock.getBlockInventory();
        return inventory.contains(this.product);
    }
}
