package me.novial.itemshoppro;

import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

public class Queue {
    public Sign itemShopSign;
    public Chest chest;

    public Queue(Sign itemShopSign, Chest chest) {
        this.itemShopSign = itemShopSign;
        this.chest = chest;
    }
}