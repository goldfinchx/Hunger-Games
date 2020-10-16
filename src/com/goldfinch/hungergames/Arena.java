package com.goldfinch.hungergames;

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
    private Game game;
    private DeathEvent deathEvent;

    public Arena (int id) {
        this.id = id;
        players = new ArrayList<>();
        alivePlayers = new ArrayList<>();
        spawn = Config.getArenaSpawn(id);
        state = GameStates.RECRUITING;
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void start() { game.start(); System.out.println("Arena/Start " + id);}

    public void reset() {
        for (Player player : players) {
            player.sendMessage(deathEvent.winner.getName());
            player.sendMessage(deathEvent.killer1name + " убил " + deathEvent.killer1kills);
            Bukkit.getScheduler().cancelTask(seconds);

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(Config.getLobbySpawn());
                    alivePlayers.remove(player);
                    game.cancelGameTimer(player);
                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

                }
            }.runTaskLater(Main.getInstance(), 20*10);
        }

        state = GameStates.RECRUITING;
        players.clear();
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void sendMessage (String message) {
        for (Player player : players) {
            player.sendMessage(message);
            System.out.println("Arena/sendMessage " + id);
        }
    }

    public void addPlayer (Player player) {
        players.add(player);
        player.teleport(spawn);
        alivePlayers.add(player);

        if (players.size() >= Config.getRequiredPlayers()) { countdown.begin(); System.out.println("Arena/addPlayer=CountdownBegins " + id);}
        System.out.println("Arena/addPlayer " + id);
    }

    public void removePlayer (Player player) {
        players.remove(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Config.getLobbySpawn());
        alivePlayers.remove(player);
        game.cancelGameTimer(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        if (players.size() <= Config.getRequiredPlayers() && state.equals(GameStates.COUNTDOWN)) { reset(); System.out.println("Arena/removePlayer/reset " + id);}

        if (players.size() == 0 && state.equals(GameStates.LIVE)) { reset();  System.out.println("Arena/removePlayer/reset2 " + id); }

        System.out.println("Arena/removePlayer " + id);
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
        System.out.println("Arena/createGameScoreboard " + id);
    }


    public int getID() { return id; }
    public List<Player> getPlayers() { return players; }
    public GameStates getState() { return state; }
    public void setState(GameStates state) { this.state = state; }
    public Game getGame() { return game; }
}

