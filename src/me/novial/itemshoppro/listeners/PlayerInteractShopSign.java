package me.novial.itemshoppro.listeners;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractShopSign implements Listener {

    @EventHandler
    public void onSignRightClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        /** Check if the block actually exists. **/
        if (block == null) {
            return;
        }

        BlockState blockState = block.getState();
        Player player = event.getPlayer();
        Sign sign;

        /** Check if block is a sign. **/
        if (blockState instanceof Sign) {
            sign = (Sign) blockState;
        }
        else {
            return;
        }

        /** Check if sign is in database. **/
        Shop shop = Main.shopManager.findShopFromSign(sign);
        if (shop == null) {
            return;
        }

        /** Check if the player is blocked. **/
        if (shop.blockedPlayers.contains(player)) {
            String ownerName = shop.owner.getDisplayName();
            player.sendMessage(Main.messager.getMessage("shop-blocked"));
            return;
        }

        /**
         * Check if the store is out of stock,
         * if player doesn't have enough funds,
         * or there's nothing wrong.
         **/
        boolean success = false;
        String shopType = null;
        shopType = "purchased";

        if (!shop.inStock()) {
            player.sendMessage(Main.messager.getMessage("out-of-stock"));
            return;
        }

        success = shop.purchaseItem(player);

        if (success) {
            player.sendMessage(Main.messager.getMessage("transaction-complete"));
        }
        else {
            player.sendMessage(Main.messager.getMessage("not-enough"));
        }
    }
}
