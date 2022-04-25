package me.novial.itemshoppro.shops;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop {
    public Player owner;
    public Player[] blockedPlayers;
    public Player[] playersWithPermission;

    public ItemStack product;
    public ItemStack currency;
    public Chest inventoryBlock;
    public Sign sign;

    public Shop(
            Player owner,
            ItemStack product,
            ItemStack currency,
            Chest inventoryBlock,
            Sign sign
    ) {
        this.owner = owner;
        this.product = product;
        this.currency = currency;
        this.inventoryBlock = inventoryBlock;
        this.sign = sign;
    }

    /** Getters **/
    public int getProductQuantity() {
        return this.product.getAmount();
    }

    public int getCurrencyQuantity() {
        return this.currency.getAmount();
    }

    /** Gives the player a read-only view of the chest's inventory. **/
    public void seeInventory(Player player) {
        player.openInventory(this.inventoryBlock.getInventory());
    }
}
