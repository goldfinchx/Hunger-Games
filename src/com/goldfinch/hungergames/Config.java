package com.goldfinch.hungergames;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Config {

    private static Main main;
    public static int random;

    public Config(Main main) {
        this.main = main;

        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    public static int getRequiredPlayers() { return main.getConfig().getInt("required-players"); }

    public static int getMaxPlayersAmount() {return main.getConfig().getInt("max-players-amount"); }

    public static int getCountdownSeconds() { return main.getConfig().getInt("countdown-seconds"); }

    public static Location getLobbySpawn() {
        return new Location(Bukkit.getWorld("world"),
                main.getConfig().getDouble("lobby.x"),
                main.getConfig().getDouble("lobby.y"),
                main.getConfig().getDouble("lobby.z"),
                main.getConfig().getInt("lobby.yaw"),
                main.getConfig().getInt("lobby.pitch"));
    }

    public static Location getArenaSpawn(int id) {
        return new Location(Bukkit.getWorld(main.getConfig().getString("arenas." + id + ".world")),
                main.getConfig().getDouble("arenas." + id + ".x"),
                main.getConfig().getDouble("arenas." + id + ".y"),
                main.getConfig().getDouble("arenas." + id + ".z"),
                main.getConfig().getInt("arenas." + id + ".yaw"),
                main.getConfig().getInt("arenas." + id + ".pitch"));
    }

    public static int getArenasAmount() { return main.getConfig().getConfigurationSection("arenas").getKeys(false).size(); }
}
