package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Game {

    private Arena arena;
    private DeathEvent deathEvent;
    private HashMap<Player, Integer> time;

    public Game(Arena arena) {
        this.arena = arena;
        time = new HashMap<>();
    }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "Победит последний выживший! Удачи!");
        System.out.println("Game/start/setLiveState " + arena.getID());
        for (Player player : arena.getPlayers()) {
            player.sendTitle(ChatColor.GREEN + "Игра началась!", ChatColor.WHITE + "Убейте всех противников, для этого собирайте вещи в сундуках", 1, 5, 10);
            arena.createGameScoreboard(player);
            DeathEvent.alivePlayers.add(player);
            time.put(player, 0);
            DeathEvent.playersKills.put(player, 0);
            runGameTimer(player);
            System.out.println(player + " ");
        }
    }

    public void runGameTimer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {

                time.put(player, (time.get(player) + 1));
                player.getScoreboard().getTeam("timeteam").setSuffix(String.valueOf(time.get(player)));
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

}
