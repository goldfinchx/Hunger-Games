package com.goldfinch.hungergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Game {

    private Arena arena;
    private DeathEvent deathEvent;
    private HashMap<Game, Integer> time;
    private Main main;
    int counter;
    int totalTime;
    public String timeString;

    public Game(Arena arena) {
        this.arena = arena;
        time = new HashMap<>();
    }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "Победит последний выживший! Удачи!");
        time.put(this, 0);
        runGameTimer(this);

        int i = 0;

        for (Player player : arena.getPlayers()) {
            player.sendTitle(ChatColor.GREEN + "Игра началась!", ChatColor.WHITE + "Убейте всех противников, для этого собирайте вещи в сундуках", 20*1, 20*5, 20*2);
            DeathEvent.playersKills.put(player, 0);
            arena.createGameScoreboard(player);
        }
    }

    public void runGameTimer(Game game) {
        counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), () -> {

            totalTime = time.get(game);
            time.put(game, (totalTime + 1));

            int minutes = (totalTime%3600)/60;
            int seconds = totalTime%60;

            timeString = String.format("%02d:%02d", minutes, seconds);


            for (Player player : Manager.getArena(arena.getID()).getPlayers()) {
                player.getScoreboard().getTeam("timeteam").setSuffix(timeString);
            }

        }, 0L, 20L);

    }

    public void cancelGameTimer() { Bukkit.getScheduler().cancelTask(counter); }
}
