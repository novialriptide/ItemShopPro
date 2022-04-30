package me.novial.itemshoppro.objects;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

public class QueueSignShopCreate {
    public Sign itemShopSign;
    public Chest chest;

    public QueueSignShopCreate(Sign itemShopSign, Chest chest) {
        this.itemShopSign = itemShopSign;
        this.chest = chest;
    }
}