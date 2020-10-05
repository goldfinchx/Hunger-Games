package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Arena arena;
    public static int seconds;

    public Countdown(Arena arena) {
        this.arena = arena;
        this.seconds = Config.getCountdownSeconds();
    }

    public void begin() {
        this.runTaskTimer(Main.getInstance(), 0, 20);
    }

    @Override
    public void run() {

        if (seconds == 30 ) {
            arena.start();
            return;
        }

        if (seconds == 30 || seconds >= 20) {
            if (seconds == 29) {
                arena.sendMessage(ChatColor.GREEN + "Игра начинается! Удачи! ");
            } else {
                arena.sendMessage(ChatColor.GREEN + "Игра начнётся через " + seconds + " секунд!");
            }
        }

        if (arena.getPlayers().size() < Config.getRequiredPlayers()) {
            cancel();
            arena.setState(GameStates.RECRUITING);
            arena.sendMessage(ChatColor.RED + "Недостаточно игроков для начала! Отсчет до начала игры остановлен.");
            return;
        }

        seconds++;
    }
}
