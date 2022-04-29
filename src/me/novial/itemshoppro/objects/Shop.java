package me.novial.itemshoppro.objects;

import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class Shop {
    public UUID uuid;
    public Player owner;
    public ArrayList<Player> blockedPlayers;
    public ArrayList<Player> playersWithPermission;

    public ItemStack product;
    public ItemStack currency;
    public Chest chest;
    public Sign sign;
    public World world;

    public Shop(
            Player owner,
            ItemStack product,
            ItemStack currency,
            Chest chest,
            Sign sign
    ) {
        this.owner = owner;
        this.product = product;
        this.currency = currency;
        this.chest = chest;
        this.sign = sign;
        this.world = chest.getWorld();
        this.blockedPlayers = new ArrayList<>();
        this.playersWithPermission = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }

    /** Getters **/
    public int getProductQuantity() {
        return this.product.getAmount();
    }

    public int getCurrencyQuantity() {
        return this.currency.getAmount();
    }

    /** Purchases an item from the Shop. Returns a boolean whether the transaction has been successful or not. **/
    public boolean purchaseItem(Player player) {
        Inventory chestInventory = this.chest.getInventory();
        Inventory playerInventory = player.getInventory();

        if (chestInventory.containsAtLeast(product, 1) && playerInventory.containsAtLeast(currency, 1)) {
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
        Inventory inventory = this.chest.getBlockInventory();
        return inventory.containsAtLeast(this.product, 1);
    }
}
