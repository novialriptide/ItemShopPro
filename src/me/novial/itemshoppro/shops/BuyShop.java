package me.novial.itemshoppro.shops;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    }

    /** Returns a boolean whether the shop is in stock or not. **/
    public boolean inStock() {
        Inventory inventory = this.inventoryBlock.getBlockInventory();
        return inventory.contains(this.product, this.productQuantity);
    }
}
