package me.novial.itemshoppro.shops;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BuyShop extends Shop {
    public BuyShop(UUID ownerUUID, ItemStack product, ItemStack currency, Chest inventoryBlock) {
        super(ownerUUID, product, currency, inventoryBlock);
    }

    public void purchaseItem(Player player) {
        List<UUID> blockedPlayers = new ArrayList<>(Arrays.asList(this.blockedPlayers));
        if (blockedPlayers.contains(player.getUniqueId())) {
            return;
        }
    }
}
