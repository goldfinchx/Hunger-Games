package com.goldfinch.hungergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Game {

    private DeathEvent deathEvent;
    private static Arena arena;

    public Game(Arena arena) { this.arena = arena; }

    public void start() { // игра все равно применяет на игроков скорборд
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "Победит последний выживший! Удачи!");
        for (Player player : Bukkit.getOnlinePlayers()) {
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
                arena.seconds++;
                player.getScoreboard().getTeam("timeteam").setSuffix(String.valueOf(arena.seconds));
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

}
