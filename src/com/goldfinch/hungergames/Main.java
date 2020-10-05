package com.goldfinch.hungergames;

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

        new Config(this);
    }

    public static Main getInstance() { return instance; }
}
