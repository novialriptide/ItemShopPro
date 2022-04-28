package me.novial.itemshoppro.listeners;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.Queue;
import me.novial.itemshoppro.objects.BuyShop;
import me.novial.itemshoppro.objects.SellShop;
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

import java.io.IOException;
import java.util.HashMap;

public class CreateShopSign implements Listener {
    public HashMap<Player, Queue> shopCreationQueue = new HashMap<>();

    /** Create ItemShopPro Chest Shop Event. (Part 1) **/
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block placedBlock = event.getBlockPlaced();
        Block placedAgainst = event.getBlockAgainst();

        BlockState placedBlockState = placedBlock.getState();
        BlockState placedAgainstState = placedAgainst.getState();

        if (placedBlockState instanceof Sign && placedAgainstState instanceof Chest) {
            shopCreationQueue.put(player, new Queue((Sign) placedBlockState, (Chest) placedAgainstState));
        }
    }

    /** Create ItemShopPro Chest Shop Event. (Part 2) **/
    @EventHandler
    public void onSignChange(SignChangeEvent event) throws IOException {
        Player player = event.getPlayer();
        String[] lines = event.getLines();
        /* How the program reads this.
         * Line 0 contains prefix
         * Line 1 contains currency quantity and item
         * Line 2 contains product quantity and item
         * Line 3 should contain nothing
         *
         * Here's an Example
         *  - [ItemShopProB]
         *  - C: 1 diamond
         *  - B: 64 log
         */
        if (shopCreationQueue.keySet().contains(player)) {
            Queue queue = shopCreationQueue.get(player);
            Sign sign = (Sign) event.getBlock().getState();

            if (lines[0].equals("[ItemShopPro]") && sign.equals(queue.itemShopSign)) {
                String line1Prefix = lines[1].substring(0, 3);
                String line2Prefix = lines[2].substring(0, 3);
                String[] line1 = lines[1].substring(3).split(" ");
                String[] line2 = lines[2].substring(3).split(" ");

                int productQuantity;
                try {
                    productQuantity = Integer.parseInt(line1[0]);
                }
                catch (NumberFormatException exception) {
                    player.sendMessage(line1[0]);
                    player.sendMessage("Invalid product quantity.");
                    return;
                }
                Material productMaterial = Material.getMaterial(line1[1], false);
                ItemStack product = new ItemStack(productMaterial, productQuantity);

                int currencyQuantity;
                try {
                    currencyQuantity = Integer.parseInt(line2[0]);
                }
                catch (NumberFormatException exception) {
                    player.sendMessage("Invalid currency quantity.");
                    return;
                }

                Material currencyMaterial = Material.getMaterial(line2[1], false);
                ItemStack currency = new ItemStack(currencyMaterial, currencyQuantity);

                Shop shop = null;
                if (line2Prefix.equals("B: ")) {
                    shop = new BuyShop(player, product, currency, queue.chest, sign);
                }
                else if (line2Prefix.equals("S: ")) {
                    shop = new SellShop(player, product, currency, queue.chest, sign);
                }
                else {
                    player.sendMessage("Incorrect shop type.");
                    return;
                }

                Main.shopManager.shops.add(shop);

                String parent = player.getUniqueId() + ".";

                /** Add Shop to shops.yml file. **/
                Main.shopsConfig.set(parent + "world", shop.world.getUID().toString());

                Main.shopsConfig.set(parent + "chestX", shop.chest.getX());
                Main.shopsConfig.set(parent + "chestY", shop.chest.getY());
                Main.shopsConfig.set(parent + "chestZ", shop.chest.getZ());

                Main.shopsConfig.set(parent + "signX", shop.sign.getX());
                Main.shopsConfig.set(parent + "signY", shop.sign.getY());
                Main.shopsConfig.set(parent + "signZ", shop.sign.getZ());

                Main.shopsConfig.set(parent + "currency", shop.currency.getType().name());
                Main.shopsConfig.set(parent + "currencyQuantity", shop.getCurrencyQuantity());

                Main.shopsConfig.set(parent + "product", shop.product.getType().name());
                Main.shopsConfig.set(parent + "productQuantity", shop.getProductQuantity());

                Main.shopsConfig.save(Main.shopsFile);

                player.sendMessage("Created shop.");
            }
        }
        shopCreationQueue.remove(player);
    }
}
