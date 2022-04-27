package me.novial.itemshoppro;

import me.novial.itemshoppro.commands.CommandItemShopPro;
import me.novial.itemshoppro.listeners.CreateShopSign;
import me.novial.itemshoppro.listeners.LockChest;
import me.novial.itemshoppro.listeners.PlayerInteractShopSign;
import me.novial.itemshoppro.objects.ShopManager;
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
        this.getServer().getPluginManager().registerEvents(new LockChest(), this);
    }
}
