package me.novial.itemshoppro.objects;

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
    public Chest chest;
    public Sign sign;

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
    }

    /** Getters **/
    public int getProductQuantity() {
        return this.product.getAmount();
    }

    public int getCurrencyQuantity() {
        return this.currency.getAmount();
    }
}