package me.novial.itemshoppro;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Shop {
    public UUID ownerUUID;
    public UUID[] blockedPlayers;
    public UUID[] playersWithPermission;

    public ItemStack product;
    public ItemStack currency;
}
