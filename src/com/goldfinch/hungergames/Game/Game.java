package com.goldfinch.hungergames.Game;

import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Core.Manager;
import com.goldfinch.hungergames.Game.Events.EventToxicRain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Game {

    private Arena arena;
    private DeathListener deathListener;
    private HashMap<Game, Integer> time;
    private Main main;
    private EventToxicRain eventToxicRain;
    int counter;
    int totalTime;
    int gameTimerInt;
    int toxicInt;
    int eventTimer;
    public String timeString;

    public Game(Arena arena) {
        this.arena = arena;
        time = new HashMap<>();
        eventToxicRain = new EventToxicRain(this);
    }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "Победит последний выживший! Удачи!");
        time.put(this, 0);
        runGameTimer(this);

        int i = 0;

        for (Player player : arena.getPlayers()) {
            player.sendTitle(ChatColor.GREEN + "Игра началась!", ChatColor.WHITE + "Убейте всех противников, для этого собирайте вещи в сундуках", 20*1, 20*5, 20*2);
            DeathListener.playersKills.put(player, 0);
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
                player.sendMessage(gameTimerInt + "");
                player.getScoreboard().getTeam("timeteam").setSuffix(timeString);
            }

            gameTimerInt++;
            switch (gameTimerInt) {
                case 30:
                    activateRandomEvent();
                    break;

                case 900:

                    break;
                case 1200:

                    break;
                default:

                    break;
            }


        }, 0L, 20L);
    }

    public void cancelGameTimer() { Bukkit.getScheduler().cancelTask(counter); }

    public void activateRandomEvent() {
        int number = main.getRandomInteger(0, 1);

        switch (number) {
            case 0:
            case 1:
                Bukkit.getWorld("world").setStorm(true);
                activateToxicRain();
                break;

            default:
                break;
        }
    }

    public void activateToxicRain() {
        eventTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), () -> {
            for (Player player : arena.getPlayers()) { eventToxicRain.activateEventOnPlayer(player); }

            toxicInt++;
            if (toxicInt == 100) { Bukkit.getScheduler().cancelTask(eventTimer); }
        }, 0L, 60L);
    }
}
