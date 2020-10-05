package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena) {
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameStates.LIVE);
        arena.sendMessage(ChatColor.GOLD + "fdgdfgdfg");

        for (UUID uuid : arena.getPlayers()) { points.put(uuid, 0); }
    }

    public void addPoint(Player player) {
        int p = points.get(player.getUniqueId()) + 1;

        if (p == 5) {
            arena.sendMessage(ChatColor.RED + "Игра завершена!");
            arena.sendMessage(ChatColor.RED + "Победителем становится " + player.getName());

            arena.reset();
            return;
        }

        points.replace(player.getUniqueId(), p);
    }
}
