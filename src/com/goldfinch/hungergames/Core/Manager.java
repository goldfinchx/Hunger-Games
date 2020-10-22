package com.goldfinch.hungergames.Core;

import com.goldfinch.hungergames.Game.Arena;
import com.goldfinch.hungergames.Game.GameStates;
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
        return getArena(id).getState() == GameStates.RECRUITING;
    }
}