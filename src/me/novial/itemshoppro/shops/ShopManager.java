package me.novial.itemshoppro.shops;

import me.novial.itemshoppro.Main;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

public class ShopManager {
    public ArrayList<Shop> shops;

    public ShopManager() {
        this.shops = new ArrayList<Shop>();
    }

    public Shop findShopFromSign(Sign sign) {
        for (Shop shop : Main.shopManager.shops) {
            if (shop.sign == sign) {
                return shop;
            }
        }
        return null;
    }

    public Shop findShopFromChest(Chest chest) {
        for (Shop shop : Main.shopManager.shops) {
            if (shop.inventoryBlock == chest) {
                return shop;
            }
        }
        return null;
    }

    public void loadShopsFromYml(YamlConfiguration ymlFile) {

    }
}
