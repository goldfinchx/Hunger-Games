package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class DeathEvent implements Listener {

    private Main main;
    public Arena arena;
    public static Player winner;
    public static String killer1name;
    public static int killer1kills;
    public static HashMap<Player, Integer> playersKills = new HashMap<>();

    public DeathEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Player death = (Player) e.getEntity();
        Player killer = e.getEntity().getKiller();

        if (Manager.getArena(death).getState().equals(GameStates.LIVE)) {

            death.setGameMode(GameMode.SPECTATOR);
            death.sendTitle(ChatColor.RED + "Вы умерли", ChatColor.WHITE + "Чтобы начать новую игру, напишите /arena leave ", 1, 5, 2);
            Game.alivePlayers.remove(death);

            Location location;
            location = killer.getLocation();
            int kills = playersKills.get(killer);
            killer.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 0.5F);
            playersKills.put(killer, kills++);

            // main.data.getConfig().set("players." + killer.getUniqueID().toString() + ".kills", kills++)

            if (Game.alivePlayers.size() == 1) {
                winner = Game.alivePlayers.get(0);
                // main.data.getConfig().set("players." + winner.getUniqueID().toString() + ".wins", wins++)

                for (Map.Entry<Player, Integer> entry : playersKills.entrySet()) {
                    if (entry.getValue() >= 0) {
                        killer1kills = entry.getValue();
                        killer1name = entry.getKey().getName();
                    }
                }
                arena.reset();

            }

        }
    }
}
