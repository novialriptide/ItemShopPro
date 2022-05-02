package me.novial.itemshoppro.listeners;

import java.io.IOException;
import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DeleteShopSign implements Listener {
  @EventHandler
  public void onBlockDestroy(BlockBreakEvent event) throws IOException {
    Player player = event.getPlayer();
    Block block = event.getBlock();
    if (block == null) {
      return;
    }

    BlockState blockState = block.getState();
    Shop shop;

    if (blockState instanceof Chest) {
      shop = Main.shopManager.findShopFromChest((Chest) blockState);
    } else if (blockState instanceof Sign) {
      shop = Main.shopManager.findShopFromSign((Sign) blockState);
    } else {
      return;
    }

    if (shop == null) {
      return;
    }

    if (!(player.getUniqueId().equals(shop.owner.getUniqueId()))) {
      player.sendMessage(Main.messager.getMessage("shop-access-denied-destroy", shop));
      event.setCancelled(true);
      return;
    }

    Main.shopsConfig.set(String.format("shops." + shop.uuid.toString()), null);
    Main.shopsConfig.save(Main.shopsFile);
    Main.shopManager.shops.remove(shop);

    player.sendMessage(Main.messager.getMessage("shop-deleted"));
  }
}
