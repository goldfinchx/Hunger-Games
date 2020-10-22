package com.goldfinch.hungergames.Game;

import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Core.Manager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    private Main main;
    private Arena arena;
    public QuitListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onExit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (Manager.isPlaying(player)) {
            Manager.getArena(player).removePlayer(player);
        }
    }
}
