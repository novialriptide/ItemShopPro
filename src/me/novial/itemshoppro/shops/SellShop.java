package me.novial.itemshoppro.shops;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SellShop extends Shop {

    public SellShop(Player owner, ItemStack product, ItemStack currency, Chest inventoryBlock, Sign sign) {
        super(owner, product, currency, inventoryBlock, sign);
    }

    /** Sells an item to the Shop. **/
    public void sellItem(Player player) {
        List<Player> blockedPlayers = new ArrayList<>(Arrays.asList(this.blockedPlayers));
        if (blockedPlayers.contains(player)) {
            return;
        }

        Inventory chestInventory = this.inventoryBlock.getInventory();
        Inventory playerInventory = player.getInventory();

        if (playerInventory.contains(product) && chestInventory.contains(currency)) {
            playerInventory.removeItem(product);
            chestInventory.addItem(product);

            chestInventory.removeItem(currency);
            playerInventory.addItem(currency);
        }

        player.sendMessage("Successful transaction");
    }

    /** Returns a boolean whether the shop is in stock or not. **/
    public boolean inStock() {
        Inventory inventory = this.inventoryBlock.getBlockInventory();
        return inventory.contains(this.currency);
    }
}
