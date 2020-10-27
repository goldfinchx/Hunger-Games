package com.goldfinch.hungergames.Game;

import com.goldfinch.hungergames.Core.Config;
import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Core.Manager;
import com.goldfinch.hungergames.Game.Events.Events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private int id;
    public int seconds;
    public  ArrayList<Player> players;
    public ArrayList<Player> alivePlayers;
    private Location spawn;
    private GameStates state;
    private Countdown countdown;
    private Events events;
    private Game game;
    private DeathListener deathListener;

    public Arena (int id) {
        this.id = id;
        players = new ArrayList<>();
        alivePlayers = new ArrayList<>();
        spawn = Config.getArenaSpawn(id);
        state = GameStates.RECRUITING;
        countdown = new Countdown(this);
        events = new Events(this);
        game = new Game(this);

    }

    public void start() { game.start(); }

    public void reset() {
        for (Player player : players) {
            Bukkit.getScheduler().cancelTask(seconds);
            game.cancelGameTimer();

            player.sendMessage(" ");
            player.sendMessage(ChatColor.GOLD + "          " + "Игра завершена!");
            player.sendMessage(" ");
            player.sendMessage(ChatColor.GRAY + "               " + "Победил");
            player.sendMessage(ChatColor.WHITE + "              " +  deathListener.winner.getName());
            player.sendMessage(" ");
            player.sendMessage(ChatColor.GRAY + "             " + "Игра длилась");
            player.sendMessage(ChatColor.WHITE + "                 " +  Manager.getArena(id).getGame().timeString);
            player.sendMessage(" ");
            player.sendMessage(ChatColor.GRAY + "       " + "Больше всего убийств: ");
            player.sendMessage(ChatColor.WHITE + "             " + deathListener.killer1name + ChatColor.GRAY + " (" + ChatColor.WHITE + deathListener.killer1kills + ChatColor.GRAY + ")");
            player.sendMessage(" ");

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(Config.getLobbySpawn());
                    alivePlayers.remove(player);

                    player.setGameMode(GameMode.SURVIVAL);
                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    cancel();
                }
            }.runTaskLater(Main.getInstance(), 20*10);
        }

        state = GameStates.RECRUITING;
        players.clear();
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void sendMessage (String message) {
        for (Player player : players) { player.sendMessage(message); }
    }

    public void addPlayer (Player player) {
        players.add(player);
        player.teleport(spawn);
        alivePlayers.add(player);
        player.sendTitle(ChatColor.GREEN + "ТЫ ЗАШЁЛ НА АРЕНУ.", "", 20 * 1, 20 * 5, 20 * 3);

        for (Player players : players) {
            players.sendMessage(ChatColor.GREEN + "(" + ChatColor.GOLD + Manager.getArena(id).getPlayers().size() + ChatColor.GREEN + "/" + ChatColor.GOLD + Config.getMaxPlayersAmount() + ChatColor.GREEN + ") "
                    + ChatColor.GREEN + "Игрок " + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " зашёл на арену.");
        }

        if (players.size() == Config.getRequiredPlayers()) { countdown.begin(); }

    }

    public void removePlayer (Player player) {
        players.remove(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Config.getLobbySpawn());
        alivePlayers.remove(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        if (players.size() <= Config.getRequiredPlayers() && state.equals(GameStates.COUNTDOWN)) { reset(); }

        if (players.size() == 0 && state.equals(GameStates.LIVE)) { reset(); }
    }

    public void createGameScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hungergames", "dummy");
        obj.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Голодные игры");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team alive = board.registerNewTeam("aliveteam");
        alive.addEntry(ChatColor.BLUE + "" + ChatColor.WHITE);
        alive.setPrefix("");
        alive.setSuffix(alivePlayers.size() + " игроков");

        Team time = board.registerNewTeam("timeteam");
        time.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        time.setPrefix(ChatColor.GRAY + "");
        time.setSuffix(String.valueOf(seconds));

        obj.getScore("    ").setScore(11);
        obj.getScore(ChatColor.YELLOW + "Живых игроков").setScore(10);
        obj.getScore(ChatColor.BLUE + "" + ChatColor.WHITE).setScore(9);
        obj.getScore("   ").setScore(8);
        obj.getScore(ChatColor.YELLOW + "Ближайшее событие").setScore(7);
        obj.getScore(ChatColor.WHITE + "Обновление сундуков").setScore(6); // берём стринг событие
        obj.getScore("  ").setScore(5);
        obj.getScore(ChatColor.YELLOW + "Время").setScore(4);
        obj.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(3);
        obj.getScore(" ").setScore(2);
        obj.getScore(ChatColor.WHITE + "      www.server.ru").setScore(1);

        player.setScoreboard(board);
    }


    public int getID() { return id; }
    public List<Player> getPlayers() { return players; }
    public GameStates getState() { return state; }
    public void setState(GameStates state) { this.state = state; }
    public Game getGame() { return game; }
}

