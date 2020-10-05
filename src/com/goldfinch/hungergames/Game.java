package com.goldfinch.hungergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private static Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "game start message");
        for (Player player : Bukkit.getOnlinePlayers()) {
            arena.createGameScoreboard(player);
            runGameTimer(player);
        }
    }

   public static void runGameTimer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                arena.seconds++;
                player.getScoreboard().getTeam("timeteam").setSuffix(String.valueOf(arena.seconds));
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

}
