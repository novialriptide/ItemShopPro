package me.novial.itemshoppro.shops;

import me.novial.itemshoppro.Main;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

public class ShopManager {
    public ArrayList<Shop> shops = new ArrayList<>();

    public ShopManager() {

    }

    public Shop findShopFromSign(Sign sign) {
        for (Shop shop : Main.shopManager.shops) {
            if (shop.sign == sign) {
                return shop;
            }
        }
        return null;
    }

    public void loadShopsFromYml(YamlConfiguration ymlFile) {

    }
}
