package me.novial.itemshoppro.listeners;

import me.novial.itemshoppro.Main;
import me.novial.itemshoppro.objects.QueueCmdShopCreate;
import me.novial.itemshoppro.objects.Shop;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
import java.util.HashMap;

public class CmdCreateShopSign implements Listener {
    public static HashMap<Player, QueueCmdShopCreate> shopCreationQueue = new HashMap<>();

    private void removePlayerFromQueue(Player player) {
        shopCreationQueue.remove(player);
        player.sendMessage(Main.messager.getMessage("exit-cmd-shop-create"));
    }

    @EventHandler
    public void onSignRightClick(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (!(shopCreationQueue.keySet().contains(player) && action.equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            removePlayerFromQueue(player);
            return;
        }

        BlockState blockState = block.getState();
        QueueCmdShopCreate queue = shopCreationQueue.get(player);

        if (!(blockState instanceof Sign)) {
            removePlayerFromQueue(player);
            return;
        }
        Sign sign = (Sign) event.getClickedBlock().getState();

        /** Check if sign exists on that location. **/
        if (Main.shopManager.findShopFromSign(sign) != null) {
            player.sendMessage(Main.messager.getMessage("shop-already-exists"));
            removePlayerFromQueue(player);
            return;
        }

        BlockFace blockFace = ((Directional) block.getBlockData()).getFacing();
        Block blockBehindSign = null;
        switch (blockFace) {
            case WEST:
                blockBehindSign = event.getClickedBlock().getRelative(BlockFace.EAST);
                break;
            case EAST:
                blockBehindSign = event.getClickedBlock().getRelative(BlockFace.WEST);
                break;
            case NORTH:
                blockBehindSign = event.getClickedBlock().getRelative(BlockFace.SOUTH);
                break;
            case SOUTH:
                blockBehindSign = event.getClickedBlock().getRelative(BlockFace.NORTH);
                break;
            default:
                break;
        }

        if (!(blockBehindSign.getState() instanceof Chest)) {
            removePlayerFromQueue(player);
            return;
        }


        Shop shop = new Shop(queue.player, queue.product, queue.currency, (Chest) blockBehindSign.getState(), sign);
        Main.shopManager.registerShop(shop);

        player.sendMessage(Main.messager.getMessage("shop-created", shop));
        removePlayerFromQueue(player);
    }
}
