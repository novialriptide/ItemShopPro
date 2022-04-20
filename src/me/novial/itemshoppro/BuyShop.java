package me.novial.itemshoppro;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BuyShop extends Shop {
    public BuyShop(UUID ownerUUID, ItemStack product, ItemStack currency, Chest inventoryBlock) {
        super(ownerUUID, product, currency, inventoryBlock);
    }

    public void purchaseItem(Player player) {

    }
}
