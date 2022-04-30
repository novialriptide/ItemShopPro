package me.novial.itemshoppro.objects;

import me.novial.itemshoppro.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

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

    public void registerShop(Shop shop) throws IOException {
        Main.shopManager.shops.add(shop);

        shop.sign.setLine(0, "[ItemShopPro]");
        shop.sign.setLine(1, ("B: " + shop.getProductQuantity() + " " + shop.product.getType().name()).substring(0, 16));
        shop.sign.setLine(2, ("C: " + shop.getCurrencyQuantity() + " " + shop.currency.getType().name()).substring(0, 16));
        shop.sign.setLine(3, "");
        shop.sign.update(true);

        String parent = "shops." + shop.uuid.toString() + ".";

        /** Add Shop to shops.yml file. **/
        Main.shopsConfig.set(parent + "owner", shop.owner.getUniqueId().toString());
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
    }

    public void loadShopsFromYml(YamlConfiguration ymlFile) {
        ConfigurationSection shopSection = ymlFile.getConfigurationSection("shops");

        if (shopSection == null) {
            Bukkit.getConsoleSender().sendMessage("Shops is empty.");
            return;
        }

        Set<String> shops = shopSection.getKeys(false);

        for (String shopUUID : shops) {
            String parent = "shops." + shopUUID + ".";

            String playerUUID = (String) ymlFile.get(parent + "owner");
            Player player = Bukkit.getPlayer(UUID.fromString(playerUUID));

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
