package me.novial.itemshoppro.listeners;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractShopSign implements Listener {
  @EventHandler
  public void onSignRightClick(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();
    Action action = event.getAction();

    /** Check if the block actually exists. * */
    if (block == null) {
      return;
    }

    BlockState blockState = block.getState();
    Player player = event.getPlayer();
    Sign sign;

    /** Check if block is a sign. * */
    if (blockState instanceof Sign) {
      sign = (Sign) blockState;
    } else {
      return;
    }

    /** Check if sign is in database. * */
    Shop shop = Main.shopManager.findShopFromSign(sign);
    if (shop == null) {
      return;
    }

    /** Check if the player is blocked. * */
    if (shop.blockedPlayers.contains(player)) {
      String ownerName = shop.owner.getName();
      player.sendMessage(Main.configMessager.getMessage("shop-blocked"));
      return;
    }

    if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
      if (!shop.inStock()) {
        player.sendMessage(Main.configMessager.getMessage("out-of-stock", shop));
        return;
      }

      boolean success = shop.purchaseItem(player);
      if (success) {
        player.sendMessage(Main.configMessager.getMessage("transaction-complete", shop));
      } else {
        player.sendMessage(Main.configMessager.getMessage("not-enough", shop));
      }
    } else if (action.equals(Action.LEFT_CLICK_BLOCK)) {
      player.sendMessage(Main.configMessager.getMessage("shop-data", shop));
    }
  }
}
