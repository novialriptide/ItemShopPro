package me.novial.itemshoppro.objects;

import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Shop {
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
    }

    /** Getters **/
    public int getProductQuantity() {
        return this.product.getAmount();
    }

    public int getCurrencyQuantity() {
        return this.currency.getAmount();
    }
}
