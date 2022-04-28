package me.novial.itemshoppro.objects;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuyShop extends Shop {

    public BuyShop(Player owner, ItemStack product, ItemStack currency, Chest inventoryBlock, Sign sign) {
        super(owner, product, currency, inventoryBlock, sign);
    }

    /** Purchases an item from the Shop. Returns a boolean whether the transaction has been successful or not. **/
    public boolean purchaseItem(Player player) {
        Inventory chestInventory = this.chest.getInventory();
        Inventory playerInventory = player.getInventory();

        if (chestInventory.contains(currency) && playerInventory.contains(product)) {
            chestInventory.removeItem(product);
            playerInventory.addItem(product);

            playerInventory.removeItem(currency);
            chestInventory.addItem(currency);
            return true;
        }

        return false;
    }

    /** Returns a boolean whether the shop is in stock or not. **/
    public boolean inStock() {
        Inventory inventory = this.chest.getInventory();
        return inventory.contains(this.product);
    }
}
