package me.novial.itemshoppro.listeners;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.BuyShop;
import me.novial.itemshoppro.objects.SellShop;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerInteractShopSign implements Listener {

    @EventHandler
    public void onSignRightClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        Sign sign;

        /** Check if block is a sign. **/
        if (block instanceof Sign) {
            sign = (Sign) block;
        }
        else {
            return;
        }

        /** Check if sign is in database. **/
        Shop shop = Main.shopManager.findShopFromSign(sign);
        if (shop == null) {
            return;
        }

        List<Player> blockedPlayers = new ArrayList<>(Arrays.asList(shop.blockedPlayers));
        if (blockedPlayers.contains(player)) {
            String ownerName = shop.owner.getDisplayName();
            player.sendMessage("You are blocked from any activity with all shops owned by {}.".format(ownerName));
            return;
        }

        boolean success = false;
        String shopType = null;
        if (shop instanceof BuyShop) {
            BuyShop buyShop = (BuyShop) shop;
            success = buyShop.purchaseItem(player);
            shopType = "purchased";
        }

        else if (shop instanceof SellShop) {
            SellShop sellShop = (SellShop) shop;
            success = sellShop.sellItem(player);
            shopType = "sold";
        }

        if (success) {
            player.sendMessage(String.format(
                    "You have %s %d %s for %d %s.",
                    shopType,
                    shop.getProductQuantity(),
                    shop.product.toString(),
                    shop.getCurrencyQuantity(),
                    shop.currency.toString()
            ));
        }

        else {
            player.sendMessage("Not enough funds.");
        }
    }
}
