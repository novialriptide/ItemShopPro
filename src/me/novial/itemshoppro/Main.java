package me.novial.itemshoppro;

import me.novial.itemshoppro.commands.CommandCreateShop;
import me.novial.itemshoppro.commands.CommandItemShopPro;
import me.novial.itemshoppro.listeners.*;
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
    public static ConfigMessager messager;

    public static File shopsFile;
    public static FileConfiguration shopsConfig;

    public static File configFile;
    public static FileConfiguration configConfig;

    public static File langFile;
    public static FileConfiguration langConfig;

    @Override
    public void onEnable() {
        ConsoleCommandSender commandSender = getServer().getConsoleSender();
        PluginManager pluginManager = getServer().getPluginManager();

        commandSender.sendMessage("[ItemShopPro] Loading commands...");
        getCommand("itemshoppro").setExecutor((new CommandItemShopPro()));
        getCommand("createshop").setExecutor((new CommandCreateShop()));
        commandSender.sendMessage("[ItemShopPro] Loaded commands");

        commandSender.sendMessage("[ItemShopPro] Loading event listeners...");
        pluginManager.registerEvents(new CreateShopSign(), this);
        pluginManager.registerEvents(new PlayerInteractShopSign(), this);
        pluginManager.registerEvents(new CmdCreateShopSign(), this);
        pluginManager.registerEvents(new DeleteShopSign(), this);
        pluginManager.registerEvents(new LockChest(), this);
        commandSender.sendMessage("[ItemShopPro] Loaded event listeners");

        commandSender.sendMessage("[ItemShopPro] Creating/loading files...");
        reloadFiles();
        shopManager.loadShopsFromYml((YamlConfiguration) shopsConfig);
        commandSender.sendMessage("[ItemShopPro] " + String.valueOf(shopManager.shops.size()) + " shops loaded!");

        commandSender.sendMessage("[ItemShopPro] Loading custom messages...");
        messager = new ConfigMessager(langConfig);
        commandSender.sendMessage("[ItemShopPro] Loaded custom messages");
    }

    private void reloadFiles() {
        Object[] configData = reloadFile("config.yml");
        configFile = (File) configData[0];
        configConfig = (FileConfiguration) configData[1];

        Object[] shopsData = reloadFile("shops.yml");
        shopsFile = (File) shopsData[0];
        shopsConfig = (FileConfiguration) shopsData[1];

        Object[] langData = reloadFile("messages.yml");
        langFile = (File) langData[0];
        langConfig = (FileConfiguration) langData[1];
    }

    private Object[] reloadFile(String fileName) {
        File file = new File(getDataFolder(), fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource(fileName, false);
        }

        FileConfiguration fileConfig = new YamlConfiguration();
        try {
            fileConfig.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return new Object[]{file, fileConfig};
    }
}
