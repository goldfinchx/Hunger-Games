package com.goldfinch.hungergames.Game.Events;

import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Game.Arena;
import com.goldfinch.hungergames.Game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Events {

    private Arena arena;
    private Main main;
    private EventToxicRain eventToxicRain;
    public Events(Arena arena) { this.arena = arena; }

    public void runRandomEvent(Game game) {
        switch (main.getRandomInteger(0, 2)) {
            case 0:
            case 1:
            case 2:
                Bukkit.getWorld("world").setStorm(true);
                for (Player player : arena.getPlayers()) {
                    eventToxicRain.activateToxicRain(game);
                    player.sendMessage("AAAAAAAAAA");
                }
                break;

            default:
                break;
        }
    }

    public void runRefillingChests() {

    }

}