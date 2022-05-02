package me.novial.itemshoppro.objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import me.novial.itemshoppro.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

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

    int charLimit = 12;
    String newLine1 = "B: " + shop.getProductQuantity() + " " + shop.product.getType().name();
    if (newLine1.length() > charLimit) {
      shop.sign.setLine(1, newLine1.substring(0, charLimit));
    } else {
      shop.sign.setLine(1, newLine1);
    }
    String newLine2 = "C: " + shop.getCurrencyQuantity() + " " + shop.currency.getType().name();
    if (newLine2.length() > charLimit) {
      shop.sign.setLine(2, newLine2.substring(0, charLimit));
    } else {
      shop.sign.setLine(2, (newLine2));
    }
    shop.sign.setLine(3, "");
    shop.sign.update(true);

    String parent = "shops." + shop.uuid.toString() + ".";

    /** Add Shop to shops.yml file. * */
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

  public void loadShopsFromYml(YamlConfiguration ymlConfig, File ymlFile) throws IOException {
    ConfigurationSection shopSection = ymlConfig.getConfigurationSection("shops");

    if (shopSection == null) {
      Bukkit.getConsoleSender().sendMessage("Shops is empty.");
      return;
    }

    Set<String> shops = shopSection.getKeys(false);

    for (String shopUUID : shops) {
      String parent = "shops." + shopUUID + ".";

      String playerUUID = (String) ymlConfig.get(parent + "owner");
      OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));

      UUID worldUUID = UUID.fromString((String) ymlConfig.get(parent + "world"));
      World world = Bukkit.getWorld(worldUUID);

      int chestX = (int) ymlConfig.get(parent + "chestX");
      int chestY = (int) ymlConfig.get(parent + "chestY");
      int chestZ = (int) ymlConfig.get(parent + "chestZ");
      Chest chest;
      try {
        chest = (Chest) world.getBlockAt(chestX, chestY, chestZ).getState();
      } catch (ClassCastException exception) {
        Bukkit.getConsoleSender()
            .sendMessage("[ItemShopPro] Deleting 1 shop (failed to load chest)");
        ymlConfig.set("shops." + shopUUID, null);
        continue;
      }

      int signX = (int) ymlConfig.get(parent + "signX");
      int signY = (int) ymlConfig.get(parent + "signY");
      int signZ = (int) ymlConfig.get(parent + "signZ");
      Sign sign;
      try {
        sign = (Sign) world.getBlockAt(signX, signY, signZ).getState();
      } catch (ClassCastException exception) {
        Bukkit.getConsoleSender()
            .sendMessage("[ItemShopPro] Deleting 1 shop (failed to load sign)");
        ymlConfig.set("shops." + shopUUID, null);
        continue;
      }

      int currencyQuantity = (int) ymlConfig.get(parent + "currencyQuantity");
      String currencyName = (String) ymlConfig.get(parent + "currency");
      Material currencyMaterial = Material.getMaterial(currencyName, false);
      ItemStack currency = new ItemStack(currencyMaterial, currencyQuantity);

      int productQuantity = (int) ymlConfig.get(parent + "productQuantity");
      String productName = (String) ymlConfig.get(parent + "product");
      Material productMaterial = Material.getMaterial(productName, false);
      ItemStack product = new ItemStack(productMaterial, productQuantity);

      Shop shop = new Shop(player, product, currency, chest, sign);
      Main.shopManager.shops.add(shop);
    }

    ymlConfig.save(ymlFile);
  }
}
