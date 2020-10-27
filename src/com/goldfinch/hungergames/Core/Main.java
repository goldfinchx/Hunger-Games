package com.goldfinch.hungergames.Core;

import com.goldfinch.hungergames.Commands.ClickableLeaveArenaCommand;
import com.goldfinch.hungergames.Commands.ClickablePlayAgainCommand;
import com.goldfinch.hungergames.Commands.HungerGamesCommand;
import com.goldfinch.hungergames.Game.Chests.ChestListener;
import com.goldfinch.hungergames.Game.DeathListener;
import com.goldfinch.hungergames.Game.QuitListener;
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
        new Config(this);
        new Manager();

        Bukkit.getPluginManager().registerEvents(new QuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChestListener(this), this);

        Bukkit.getPluginCommand("hg").setExecutor(new HungerGamesCommand());
        Bukkit.getPluginCommand("dnn3n32osso3x").setExecutor(new ClickablePlayAgainCommand());
        Bukkit.getPluginCommand("oo3inznejtn").setExecutor(new ClickableLeaveArenaCommand());

        Main.instance = this;
    }

    public static Main getInstance() { return instance; }
}
