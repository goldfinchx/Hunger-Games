package com.goldfinch.hungergames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public static ArrayList<Player> players;
    private Location spawn;
    private GameStates state;
    private Countdown countdown;
    private Game game;
    private DeathEvent deathEvent;

    public Arena (int id) {
        this.id = id;
        players = new ArrayList<>();
        spawn = Config.getArenaSpawn(id);
        state = GameStates.RECRUITING;
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void start() { game.start(); System.out.println("Arena/Start");}

    public void reset() {
        for (Player player : players) {
            player.teleport(Config.getLobbySpawn());

            Bukkit.getScheduler().cancelTask(seconds);
            new BukkitRunnable() {
                @Override
                public void run() { removePlayer(player); System.out.println("Arena/runnable/removePlayer");}
            }.runTaskLater(Main.getInstance(), 20*10);

            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            System.out.println("Arena/reset");
        }

        state = GameStates.RECRUITING;
        players.clear();
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void sendMessage (String message) {
        for (Player player : players) {
            player.sendMessage(message);
            System.out.println("Arena/sendMessage");
        }
    }

    public void addPlayer (Player player) {
        players.add(player);
        DeathEvent.alivePlayers.add(player);
        player.teleport(spawn);

        if (players.size() >= Config.getRequiredPlayers()) { countdown.begin(); System.out.println("Arena/addPlayer=CountdownBegins");}
        System.out.println("Arena/addPlayer");
    }

    public void removePlayer (Player player) {
        players.remove(player);
        player.teleport(Config.getLobbySpawn());
        DeathEvent.alivePlayers.remove(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        if (players.size() <= Config.getRequiredPlayers() && state.equals(GameStates.COUNTDOWN)) { reset(); System.out.println("Arena/removePlayer/reset");}

        if (players.size() == 0 && state.equals(GameStates.LIVE)) { reset();  System.out.println("Arena/removePlayer/reset2"); }

        System.out.println("Arena/removePlayer");
    }

    public void createGameScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hungergames", "dummy");
        obj.setDisplayName(ChatColor.BLUE.toString() + ChatColor.BOLD + "Hunger Games");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team time = board.registerNewTeam("timeteam");
        time.addEntry(ChatColor.RED + "" + ChatColor.YELLOW);
        time.setPrefix("Время " + ChatColor.DARK_GRAY + "» ");
        time.setSuffix(String.valueOf(seconds));

        obj.getScore(ChatColor.RED + "" + ChatColor.YELLOW).setScore(1);

        player.setScoreboard(board);
        System.out.println("Arena/createGameScoreboard");
    }


    public int getID() { return id; }
    public List<Player> getPlayers() { return players; }
    public GameStates getState() { return state; }
    public void setState(GameStates state) { this.state = state; }
    public Game getGame() { return game; }
}

