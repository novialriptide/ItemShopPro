package me.novial.itemshoppro;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Shop {
    public UUID _ownerUUID;
    public UUID[] _blockedPlayers;
    public UUID[] _playersWithPermission;

    public ItemStack _product;
    public ItemStack _currency;
    public Chest _inventoryBlock;

    public Shop(UUID ownerUUID, ItemStack product, ItemStack currency, Chest inventoryBlock) {
        _ownerUUID = ownerUUID;
        _product = product;
        _currency = currency;
        _inventoryBlock = inventoryBlock;
    }

    /** Gives the player a read-only view of the chest's inventory. **/
    public void seeInventory(Player player) {
        player.openInventory(_inventoryBlock.getInventory());
    }

    /** Returns a boolean whether the shop is in stock or not. **/
    public boolean inStock() {
        Inventory inventory = _inventoryBlock.getBlockInventory();
        return inventory.contains(_product);
    }
}
