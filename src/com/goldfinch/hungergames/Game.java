package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Game extends BukkitRunnable {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "game start message");
    }

    @Override
    public void run() {

    }
}
