package com.goldfinch.hungergames.Game;

import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Core.Manager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class DeathListener implements Listener {

    private Main main;
    public Arena arena;
    public static Player winner;
    public static String killer1name;
    public static int killer1kills;
    public static HashMap<Player, Integer> playersKills = new HashMap<>();

    public DeathListener(Main main) {
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

            TextComponent playAgain = new TextComponent("[ИГРАТЬ ЗАНОВО] ");
            playAgain.setColor(ChatColor.GREEN);
            playAgain.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dnn3n32osso3x"));

            TextComponent leaveArena = new TextComponent("[ВЕРНУТЬСЯ В ЛОББИ]");
            leaveArena.setColor(ChatColor.RED);
            leaveArena.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/oo3inznejtn"));

            playAgain.addExtra(" ");
            playAgain.addExtra(leaveArena);
            death.spigot().sendMessage(playAgain);

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

                for (Map.Entry<Player, Integer> entry : playersKills.entrySet()) {
                    if (entry.getValue() >= 0) {
                        killer1kills = entry.getValue();
                        killer1name = entry.getKey().getName();
                    }
                }
                Manager.getArena(killer).reset();
            }
        }
    }
}
