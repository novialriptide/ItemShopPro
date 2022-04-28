package me.novial.itemshoppro.objects;

import me.novial.itemshoppro.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

public class ShopManager {
    public ArrayList<Shop> shops;

    public ShopManager() {
        this.shops = new ArrayList<Shop>();
    }

    public Shop findShopFromSign(Sign sign) {
        for (Shop shop : Main.shopManager.shops) {
            if (shop.sign.equals(sign)) {
                return shop;
            }
        }
        return null;
    }

    public Shop findShopFromChest(Chest chest) {
        for (Shop shop : Main.shopManager.shops) {
            if (shop.chest.equals(chest)) {
                return shop;
            }
        }
        return null;
    }

    public void loadShopsFromYml(YamlConfiguration ymlFile) {
        Set<String> players = ymlFile.getKeys(true);

        for (String stringUUID : players) {
            Player player = Bukkit.getPlayer(UUID.fromString(stringUUID));
            String parent = stringUUID + ".";

            UUID worldUUID = UUID.fromString((String) ymlFile.get(parent + "world"));
            World world = Bukkit.getWorld(worldUUID);

            int chestX = (int) ymlFile.get(parent + "chestX");
            int chestY = (int) ymlFile.get(parent + "chestY");
            int chestZ = (int) ymlFile.get(parent + "chestZ");
            Chest chest = (Chest) world.getBlockAt(chestX, chestY, chestZ).getState();

            int signX = (int) ymlFile.get(parent + "signX");
            int signY = (int) ymlFile.get(parent + "signY");
            int signZ = (int) ymlFile.get(parent + "signZ");
            Sign sign = (Sign) world.getBlockAt(signX, signY, signZ).getState();

            int currencyQuantity = (int) ymlFile.get(parent + "currencyQuantity");
            String currencyName = (String) ymlFile.get(parent + "currency");
            Material currencyMaterial = Material.getMaterial(currencyName, false);
            ItemStack currency = new ItemStack(currencyMaterial, currencyQuantity);

            int productQuantity = (int) ymlFile.get(parent + "productQuantity");
            String productName = (String) ymlFile.get(parent + "product");
            Material productMaterial = Material.getMaterial(productName, false);
            ItemStack product = new ItemStack(productMaterial, productQuantity);

            Shop shop = new Shop(player, product, currency, chest, sign);
            Main.shopManager.shops.add(shop);
        }
    }
}
