package me.novial.itemshoppro;

import me.novial.itemshoppro.commands.CommandItemShopPro;
import me.novial.itemshoppro.listeners.CreateShopSign;
import me.novial.itemshoppro.listeners.LockChest;
import me.novial.itemshoppro.listeners.PlayerInteractShopSign;
import me.novial.itemshoppro.objects.ShopManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    public static ShopManager shopManager = new ShopManager();

    public static File shopsFile;
    public static FileConfiguration shopsConfig;

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("Creating/loading files...");
        createFiles();
        shopManager.loadShopsFromYml();

        ConsoleCommandSender commandSender = getServer().getConsoleSender();
        PluginManager pluginManager = getServer().getPluginManager();

        commandSender.sendMessage("Loading commands...");
        getCommand("itemshoppro").setExecutor((new CommandItemShopPro()));

        commandSender.sendMessage("Loading event listeners...");
        pluginManager.registerEvents(new CreateShopSign(), this);
        pluginManager.registerEvents(new PlayerInteractShopSign(), this);
        pluginManager.registerEvents(new LockChest(), this);
    }

    private void createFiles() {
        shopsFile = new File(getDataFolder(), "shops.yml");
        if (!shopsFile.exists()) {
            shopsFile.getParentFile().mkdirs();
            saveResource("shops.yml", false);
        }

        shopsConfig = new YamlConfiguration();
        try {
            shopsConfig.load(shopsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
