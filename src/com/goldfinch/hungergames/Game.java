package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Game {

    private static Arena arena;
    private DeathEvent deathEvent;
    private static int time;

    public Game(Arena arena) { this.arena = arena; }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "Победит последний выживший! Удачи!");
        System.out.println("Game/start/setLiveState");
        for (Player player : arena.getPlayers()) {
            player.sendTitle(ChatColor.GREEN + "Игра началась!", ChatColor.WHITE + "Убейте всех противников, для этого собирайте вещи в сундуках", 1, 5, 10);
            arena.createGameScoreboard(player);
            DeathEvent.alivePlayers.add(player);
            DeathEvent.playersKills.put(player, 0);
            runGameTimer(player);
        }
    }

    public static void runGameTimer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                player.getScoreboard().getTeam("timeteam").setSuffix(String.valueOf(time));
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
       System.out.println("Game/runGameTimer");
    }

}
