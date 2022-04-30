package me.novial.itemshoppro.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QueueCmdShopCreate {
    public Player player;
    public ItemStack product;
    public ItemStack currency;

    public QueueCmdShopCreate(Player player, ItemStack product, ItemStack currency) {
        this.player = player;
        this.product = product;
        this.currency = currency;
    }
}
