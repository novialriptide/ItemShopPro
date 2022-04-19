package me.novial.itemshoppro;

import me.novial.itemshoppro.commands.CommandItemShopPro;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("itemshoppro").setExecutor((new CommandItemShopPro()));
    }
}
