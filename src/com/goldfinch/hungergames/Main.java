package com.goldfinch.hungergames;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public int getRandomInteger(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    @Override
    public void onEnable() {
        Main.instance = this;

        Bukkit.getPluginCommand("hg").setExecutor(new HungerGamesCommand());
        Bukkit.getPluginManager().registerEvents(new QuitEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(this), this);

        new Config(this);
        new Manager();
    }

    public static Main getInstance() { return instance; }
}
