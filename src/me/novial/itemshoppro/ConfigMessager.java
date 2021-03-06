package me.novial.itemshoppro;

import java.util.HashMap;
import me.novial.itemshoppro.objects.Shop;
import me.novial.itemshoppro.objects.ShopManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigMessager {
  private final FileConfiguration config;

  public ConfigMessager(FileConfiguration config) {
    this.config = config;
  }

  public String getMessage(String messageID) {
    String prefix = (String) this.config.get("prefix");
    String message = (String) this.config.get(messageID);
    return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
  }

  public String getMessage(String messageID, Shop shop) {
    String prefix = (String) this.config.get("prefix");
    String message = (String) this.config.get(messageID);

    HashMap<String, String> variables = new HashMap<>();
    variables.put("%PRODUCT_QUANTITY%", Integer.toString(shop.getProductQuantity()));
    variables.put("%PRODUCT_NAME%", shop.product.getType().name());
    variables.put("%CURRENCY_QUANTITY%", Integer.toString(shop.getCurrencyQuantity()));
    variables.put("%CURRENCY_NAME%", shop.currency.getType().name());
    variables.put("%OWNER_NAME%", shop.owner.getName());

    for (String variable : variables.keySet()) {
      message = message.replaceAll(variable, variables.get(variable));
    }

    return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
  }

  public String getMessage(String messageID, ShopManager shopManager) {
    String prefix = (String) this.config.get("prefix");
    String message = (String) this.config.get(messageID);

    HashMap<String, String> variables = new HashMap<>();
    variables.put("%GLOBAL_SHOPS_QUANTITY%", Integer.toString(shopManager.shops.size()));

    for (String variable : variables.keySet()) {
      message = message.replaceAll(variable, variables.get(variable));
    }

    return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
  }
}
