package me.novial.itemshoppro;


import me.novial.itemshoppro.objects.Shop;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class ConfigMessager {
    private FileConfiguration config;

    public ConfigMessager(FileConfiguration config) {
        this.config = config;
    }

    public String getMessage(String messageID) {
        String message = (String) this.config.get(messageID);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getMessage(String messageID, Shop shop) {
        String message = (String) this.config.get(messageID);

        HashMap<String, String> variables = new HashMap<>();
        variables.put("{PRODUCT_QUANTITY}", Integer.toString(shop.getProductQuantity()));
        variables.put("{PRODUCT_NAME}", shop.product.getType().name());
        variables.put("{CURRENCY_QUANTITY}", Integer.toString(shop.getCurrencyQuantity()));
        variables.put("{CURRENCY_NAME}", shop.currency.getType().name());
        variables.put("{OWNER_NAME}", shop.owner.getDisplayName());

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
