package me.novial.itemshoppro;

import me.novial.itemshoppro.commands.CommandItemShopPro;
import me.novial.itemshoppro.shops.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static ShopManager shopManager = new ShopManager();

    @Override
    public void onEnable() {
        this.getCommand("itemshoppro").setExecutor((new CommandItemShopPro()));
    }
}
