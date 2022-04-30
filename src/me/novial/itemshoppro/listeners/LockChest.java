package me.novial.itemshoppro.listeners;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LockChest implements Listener {
    @EventHandler
    public void onChestRightClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        Action action = event.getAction();
        Chest chest;

        if (action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (block == null) {
            return;
        }

        BlockState blockState = block.getState();

        /** Check if block is a chest. **/
        if (blockState instanceof Chest) {
            chest = (Chest) blockState;
        }
        else {
            return;
        }

        /** Check if the chest is actually an ItemShopPro chest. **/
        Shop shop = Main.shopManager.findShopFromChest(chest);
        if (shop == null) {
            return;
        }

        if (!(player.getUniqueId().equals(shop.owner.getUniqueId()))) {
            player.sendMessage(Main.messager.getMessage("shop-access-denied-open", shop));
            event.setCancelled(true);
            return;
        }
    }
}
