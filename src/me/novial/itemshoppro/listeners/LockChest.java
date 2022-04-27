package me.novial.itemshoppro.listeners;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.shops.Shop;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LockChest implements Listener {

    @EventHandler
    public void onChestRightClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        Chest chest;

        /** Check if block is a chest. **/
        if (block instanceof Chest) {
            chest = (Chest) block;
        }
        else {
            return;
        }

        /** Check if the chest is actually an ItemShopPro chest. **/
        Shop shop = Main.shopManager.findShopFromChest(chest);
        if (shop == null) {
            return;
        }

        if (player != shop.owner) {
            player.sendMessage("This shop is owned by {0}".format(shop.owner.getDisplayName()));
            player.closeInventory();
            return;
        }
    }

    @EventHandler
    public void onChestExplode() {}
}
