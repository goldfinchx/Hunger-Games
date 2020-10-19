package com.goldfinch.hungergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Game {

    private Arena arena;
    private DeathEvent deathEvent;
    private HashMap<Player, Integer> time;
    private Main main;
    public String timeString;

    public Game(Arena arena) {
        this.arena = arena;
        time = new HashMap<>();
    }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "Победит последний выживший! Удачи!");
        System.out.println("Game/start/setLiveState " + arena.getID());

        int i = 0;

        for (Player player : arena.getPlayers()) {
            player.sendTitle(ChatColor.GREEN + "Игра началась!", ChatColor.WHITE + "Убейте всех противников, для этого собирайте вещи в сундуках", 20*1, 20*5, 20*2);
            time.put(player, 0);
            DeathEvent.playersKills.put(player, 0);
            runGameTimer(player);
            System.out.println(player + " ");
            arena.createGameScoreboard(player);
        }
    }

    private int counter;
    int totalTime;

    public void runGameTimer(Player player) {
        counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {
            public void run() {
                totalTime = time.get(player);

                int minutes = (totalTime%3600)/60;
                int seconds = totalTime%60;
                timeString = String.format("%02d:%02d", minutes, seconds);

                time.put(player, (totalTime + 1));

                player.getScoreboard().getTeam("timeteam").setSuffix(timeString);
            }
        }, 0L, 20L);

    }

    public void cancelGameTimer(Player player) {
        Bukkit.getScheduler().cancelTask(counter);
    }


}
