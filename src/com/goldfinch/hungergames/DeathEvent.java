package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathEvent implements Listener {

    private Main main;
    public Arena arena;
    public static ArrayList<Player> alivePlayers;
    public static Player winner;
    public static String killer1name;
    public static int killer1kills;
    public static HashMap<Player, Integer> playersKills = new HashMap<>();

    public DeathEvent(Main main) {
        this.main = main;
        alivePlayers = new ArrayList<>();
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (arena.getState().equals(GameStates.LIVE)) {
          //  if (e.getEntity().getType().equals(EntityType.PLAYER)) {
          //      if (e.getEntity().getKiller().getType().equals(EntityType.PLAYER)) {
                    e.getEntity().getKiller().sendMessage("fgfgf");

                    Player death = (Player) e.getEntity();
                    Player killer = e.getEntity().getKiller();

                        death.setGameMode(GameMode.SPECTATOR);
                        death.sendTitle(ChatColor.RED + "Вы умерли", ChatColor.WHITE + "Чтобы начать новую игру, напишите /arena leave ", 1, 5, 2);
                        alivePlayers.remove(death);

                        Location location;
                        location = killer.getLocation();
                        int kills = playersKills.get(killer);
                        killer.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 0.5F);
                        playersKills.put(killer, kills++);

                        // main.data.getConfig().set("players." + killer.getUniqueID().toString() + ".kills", kills++)

                        if (alivePlayers.size() == 1) {
                            winner = alivePlayers.get(0);
                            // main.data.getConfig().set("players." + winner.getUniqueID().toString() + ".wins", wins++)

                            for (Map.Entry<Player, Integer> entry : playersKills.entrySet()) {
                                if (entry.getValue() >= 0) {
                                    killer1kills = entry.getValue();
                                    killer1name = entry.getKey().getName();
                                }
                            }
                            arena.reset();
                        }

              //  } else { e.getEntity().getKiller().sendMessage("ты убил и ты игрок"); }
           // } else { e.getEntity().sendMessage("тебя убили и ты игрок"); }
        }
    }
}
