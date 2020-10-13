package com.goldfinch.hungergames;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static ArrayList<Arena> arenas;

    public Manager() {
        arenas = new ArrayList<>();

        for (int i = 0; i <= (Config.getArenasAmount() - 1); i++) {
            arenas.add(new Arena(i));
        }
    }

    public static List<Arena> getArenas() { return arenas; }

    public static boolean isPlaying(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player))
                return true;
        }
        return false;
    }

    public static Arena getArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player))
                return arena;

        }
        return null;
    }

    public static Arena getArena(int id) {
        for (Arena arena : arenas) {
            if (arena.getID() == id)
                return arena;

        }
        return null;
    }

    public static boolean isRecruiting(int id) {
        if (getArena(id).getState() == GameStates.RECRUITING) {
            return true;
        } else { return false; }
    }
}