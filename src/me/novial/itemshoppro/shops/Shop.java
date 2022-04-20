package me.novial.itemshoppro.shops;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Shop {
    public UUID ownerUUID;
    public UUID[] blockedPlayers;
    public UUID[] playersWithPermission;

    public ItemStack product;
    public int productQuantity;
    public ItemStack currency;
    public int currencyQuantity;
    public Chest inventoryBlock;

    public Shop(UUID ownerUUID, ItemStack product, int productQuantity, ItemStack currency, int currencyQuantity, Chest inventoryBlock) {
        this.ownerUUID = ownerUUID;
        this.product = product;
        this.currency = currency;
        this.inventoryBlock = inventoryBlock;
        this.productQuantity = productQuantity;
        this.currencyQuantity = currencyQuantity;
    }

    /** Gives the player a read-only view of the chest's inventory. **/
    public void seeInventory(Player player) {
        player.openInventory(this.inventoryBlock.getInventory());
    }
}
