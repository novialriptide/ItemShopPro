package me.novial.itemshoppro;

import me.novial.itemshoppro.commands.CommandItemShopPro;
import me.novial.itemshoppro.events.CreateShopSign;
import me.novial.itemshoppro.events.PlayerInteractShopSign;
import me.novial.itemshoppro.shops.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static ShopManager shopManager = new ShopManager();

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("Loading commands...");
        this.getCommand("itemshoppro").setExecutor((new CommandItemShopPro()));

        this.getServer().getConsoleSender().sendMessage("Loading event listeners...");
        this.getServer().getPluginManager().registerEvents(new CreateShopSign(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractShopSign(), this);
    }
}
