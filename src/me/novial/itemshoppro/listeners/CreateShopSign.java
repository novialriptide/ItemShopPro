package me.novial.itemshoppro.listeners;

import java.io.IOException;
import java.util.HashMap;
import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.QueueSignShopCreate;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class CreateShopSign implements Listener {
  public HashMap<Player, QueueSignShopCreate> shopCreationQueue = new HashMap<>();

  /** Create ItemShopPro Chest Shop Event. (Part 1) */
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    Block placedBlock = event.getBlockPlaced();
    Block placedAgainst = event.getBlockAgainst();

    BlockState placedBlockState = placedBlock.getState();
    BlockState placedAgainstState = placedAgainst.getState();

    if (placedBlockState instanceof Sign && placedAgainstState instanceof Chest) {
      shopCreationQueue.put(
          player, new QueueSignShopCreate((Sign) placedBlockState, (Chest) placedAgainstState));
    }
  }

  /** Create ItemShopPro Chest Shop Event. (Part 2) */
  @EventHandler
  public void onSignChange(SignChangeEvent event) throws IOException {
    Player player = event.getPlayer();
    String[] lines = event.getLines();

    if (shopCreationQueue.containsKey(player)) {
      QueueSignShopCreate queue = shopCreationQueue.get(player);
      Sign sign = (Sign) event.getBlock().getState();

      if (lines[0].equals("[ItemShopPro]") && sign.equals(queue.itemShopSign)) {
        if (lines[1].equals("") || lines[2].equals("")) {
          return;
        }

        String line1Prefix = lines[1].substring(0, 3);
        String line2Prefix = lines[2].substring(0, 3);
        String[] line1 = lines[1].substring(3).split(" ");
        String[] line2 = lines[2].substring(3).split(" ");

        int productQuantity;
        try {
          productQuantity = Integer.parseInt(line1[0]);
        } catch (NumberFormatException exception) {
          player.sendMessage(line1[0]);
          player.sendMessage(Main.configMessager.getMessage("invalid-product-quantity"));
          return;
        }
        Material productMaterial = Material.getMaterial(line1[1], false);
        if (productMaterial == null) {
          player.sendMessage(Main.configMessager.getMessage("invalid-product"));
          return;
        }
        ItemStack product = new ItemStack(productMaterial, productQuantity);

        int currencyQuantity;
        try {
          currencyQuantity = Integer.parseInt(line2[0]);
        } catch (NumberFormatException exception) {
          player.sendMessage(Main.configMessager.getMessage("invalid-currency-quantity"));
          return;
        }

        Material currencyMaterial = Material.getMaterial(line2[1], false);
        if (currencyMaterial == null) {
          player.sendMessage(Main.configMessager.getMessage("invalid-currency"));
          return;
        }
        ItemStack currency = new ItemStack(currencyMaterial, currencyQuantity);

        Shop shop = new Shop(player, product, currency, queue.chest, sign);
        Main.shopManager.registerShop(shop);

        player.sendMessage(Main.configMessager.getMessage("shop-created", shop));
      }
    }
    shopCreationQueue.remove(player);
  }
}
