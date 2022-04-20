package me.novial.itemshoppro.events;

import me.novial.itemshoppro.Queue;
import me.novial.itemshoppro.shops.Shop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CreateShopSign {
    public HashMap<Player, Queue> shopCreationQueue = new HashMap<>();

    /** Create ItemShopPro Chest Shop Event. (Part 1) **/
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block placedBlock = event.getBlockPlaced();
        Block placedAgainst = event.getBlockAgainst();

        if (placedBlock instanceof Sign && placedAgainst instanceof Chest) {
            shopCreationQueue.put(player, new Queue((Sign) placedBlock, (Chest) placedAgainst));
        }
    }

    /** Create ItemShopPro Chest Shop Event. (Part 2) **/
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        String[] lines = event.getLines();
        /** How the program reads this.
         * Line 0 contains prefix
         * Line 1 contains currency quantity and item
         * Line 2 contains product quantity and item
         * Line 3 should contain nothing
         *
         * Here's an Example
         *  - [ItemShopProB]
         *  - 1 diamond
         *  - 64 log
         *
         * Here's an example of the output
         *  - USERNAME
         *  - Purchase 64
         *  - log for
         *  - for 1 diamond
         */

        if (lines[0] == "[ItemShopPro]" && shopCreationQueue.keySet().contains(player)) {
            Queue queue = shopCreationQueue.get(player);
            shopCreationQueue.remove(player);

            Block block = event.getBlock();
            Sign sign = (Sign) block;

            String[] line1 = lines[1].split(" ");
            String[] line2 = lines[2].split(" ");

            int productQuantity = Integer.parseInt(line1[0]);
            Material productMaterial = Material.matchMaterial(line1[1]);

            ItemStack product = new ItemStack(productMaterial);
            int currencyQuantity = Integer.parseInt(line2[0]);

            Material currencyMaterial = Material.matchMaterial(line2[1]);
            ItemStack currency = new ItemStack(currencyMaterial);

            Shop shop = new Shop(player, product, productQuantity, currency, currencyQuantity, queue.chest);
        }
    }
}
