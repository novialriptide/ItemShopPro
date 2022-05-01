package me.novial.itemshoppro.objects;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class QueueCmdShopCreate {
    public OfflinePlayer player;
    public ItemStack product;
    public ItemStack currency;

    public QueueCmdShopCreate(OfflinePlayer player, ItemStack product, ItemStack currency) {
        this.player = player;
        this.product = product;
        this.currency = currency;
    }
}
