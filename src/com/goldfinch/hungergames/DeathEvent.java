package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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
    public void onDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player death = e.getEntity();

        if (Manager.getArena(death).getState() == GameStates.LIVE) {
            death.spigot().respawn();
            death.setGameMode(GameMode.SPECTATOR);
            death.sendTitle(ChatColor.RED + "Тебя убили!", ChatColor.WHITE + "Чтобы начать новую игру, нажми на [ИГРАТЬ ЗАНОВО] в чате ", 20 * 1, 20 * 5, 20 * 5);
            death.sendMessage(" ");
            death.sendMessage(ChatColor.GOLD + "Хочешь начать новую игру?");
            death.sendMessage(ChatColor.GREEN + "[ИГРАТЬ ЗАНОВО] " + ChatColor.RED + "[ВЕРНУТЬСЯ В ЛОББИ]");
            death.sendMessage(" ");

            Manager.getArena(death).alivePlayers.remove(death);

            for (Player player : Manager.getArena(killer).getPlayers()) {
                e.setDeathMessage((ChatColor.RED + death.getName() + " был убит " + killer.getName()));
                player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 0.4F, 0.1F);

                if (Manager.getArena(killer).alivePlayers.size() == 2 || Manager.getArena(killer).alivePlayers.size() == 3 || Manager.getArena(killer).alivePlayers.size() == 4) { player.getScoreboard().getTeam("aliveteam").setSuffix(Manager.getArena(killer).alivePlayers.size() + " игрока"); }
                else if (Manager.getArena(killer).alivePlayers.size() == 1) { player.getScoreboard().getTeam("aliveteam").setSuffix(Manager.getArena(killer).alivePlayers.size() + " игрок"); }
                else { player.getScoreboard().getTeam("aliveteam").setSuffix(Manager.getArena(killer).alivePlayers.size() + " игроков"); }

            }

            int kills = playersKills.get(killer);
            playersKills.put(killer, kills + 1);

            if (Manager.getArena(killer).alivePlayers.size() == 1) {
                winner = Manager.getArena(killer).alivePlayers.get(0);
                System.out.println(ChatColor.BLUE + "RRRRRRRRRRRRRRR");
                // main.data.getConfig().set("players." + winner.getUniqueID().toString() + ".wins", wins++)


                for (Map.Entry<Player, Integer> entry : playersKills.entrySet()) {
                    if (entry.getValue() >= 0) {
                        killer1kills = entry.getValue();
                        killer1name = entry.getKey().getName();
                        System.out.println(ChatColor.DARK_PURPLE + "353543534534534");
                    }
                }
                Manager.getArena(killer).reset();
            }

        }
    }
}
