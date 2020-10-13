package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Arena arena;
    private int seconds;

    public Countdown(Arena arena) {
        this.arena = arena;
        this.seconds = Config.getCountdownSeconds();
    }

    public void begin() {
        arena.setState(GameStates.COUNTDOWN);
        System.out.println("Countdown/Begin/CountdownSet");
        this.runTaskTimer(Main.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds == 0) {
            cancel();
            arena.start();
            System.out.println("Countdown/0");
            return;
        }

        if (seconds % 30 == 0 || seconds <= 10) {
            if (seconds == 1) {
                arena.sendMessage(ChatColor.GREEN + "Игра начинается! Удачи! ");
            } else {
                arena.sendMessage(ChatColor.GREEN + "Игра начнётся через " + seconds + " секунд!");
            }
        }

        if (arena.getPlayers().size() < Config.getRequiredPlayers()) {

            cancel();
            arena.setState(GameStates.RECRUITING);
            System.out.println("Countdown/cancel/RecruitingSet");
            arena.sendMessage(ChatColor.RED + "Недостаточно игроков для начала! Отсчет до начала игры остановлен.");
            return;
        }

        seconds--;
    }
}
