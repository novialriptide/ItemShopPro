package me.novial.itemshoppro;

import javafx.util.Pair;
import me.novial.itemshoppro.commands.CommandItemShopPro;
import me.novial.itemshoppro.listeners.CreateShopSign;
import me.novial.itemshoppro.listeners.DeleteShopSign;
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

    public static File configFile;
    public static FileConfiguration configConfig;

    public static File langFile;
    public static FileConfiguration langConfig;

    @Override
    public void onEnable() {
        ConsoleCommandSender commandSender = getServer().getConsoleSender();
        PluginManager pluginManager = getServer().getPluginManager();

        commandSender.sendMessage("Loading commands...");
        getCommand("itemshoppro").setExecutor((new CommandItemShopPro()));

        commandSender.sendMessage("Loading event listeners...");
        pluginManager.registerEvents(new CreateShopSign(), this);
        pluginManager.registerEvents(new PlayerInteractShopSign(), this);
        pluginManager.registerEvents(new DeleteShopSign(), this);
        pluginManager.registerEvents(new LockChest(), this);

        this.getServer().getConsoleSender().sendMessage("Creating/loading files...");
        reloadFiles();
        shopManager.loadShopsFromYml((YamlConfiguration) shopsConfig);
        commandSender.sendMessage(String.valueOf(shopManager.shops.size()) + " shops loaded!");
    }

    private void reloadFiles() {
        Object[] configData = reloadFile("config.yml");
        configFile = (File) configData[0];
        configConfig = (FileConfiguration) configData[1];

        Object[] shopsData = reloadFile("shops.yml");
        shopsFile = (File) shopsData[0];
        shopsConfig = (FileConfiguration) shopsData[1];

        Object[] langData = reloadFile("langs/{0}.yml".format((String) configConfig.get("lang")));
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
