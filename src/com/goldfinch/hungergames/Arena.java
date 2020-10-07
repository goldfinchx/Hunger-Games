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
import java.util.UUID;

public class Arena {

    private int id;
    public int seconds;
    private ArrayList<UUID> players;
    private Location spawn;
    private GameStates state;
    private Countdown countdown;
    private Game game;

    public Arena (int id) {
        this.id = id;
        players = new ArrayList<>();
        spawn = Config.getArenaSpawn(id);
        state = GameStates.RECRUITING;
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void start() { game.start(); }

    public void reset() {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).teleport(Config.getLobbySpawn());

            Bukkit.getScheduler().cancelTask(seconds);
            new BukkitRunnable() {
                @Override
                public void run() { removePlayer(Bukkit.getPlayer(uuid)); }
            }.runTaskLater(Main.getInstance(), 20*10);

            Bukkit.getPlayer(uuid).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            Bukkit.getPlayer(uuid).sendTitle(ChatColor.GREEN + "Игра завершена!", ChatColor.WHITE + "Победу одержал " +  DeathEvent.winner, 1, 5, 2);
            Bukkit.getPlayer(uuid).sendMessage("   * * * * *");
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + "   Игра завершена!");
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + "   Победил: ");
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.LIGHT_PURPLE + "   " + (DeathEvent.winner).toString());
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + "   Больше всего убийств: ");
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.LIGHT_PURPLE + "   " + DeathEvent.killer1name + "  " + DeathEvent.killer1kills + "убийств");
            Bukkit.getPlayer(uuid).sendMessage("   * * * * *");
        }

        state = GameStates.RECRUITING;
        players.clear();
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void sendMessage (String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void addPlayer (Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);
        Game.alivePlayers.add(player);

        if (players.size() >= Config.getRequiredPlayers()) { countdown.begin(); }
    }

    public void removePlayer (Player player) {
        players.remove(player.getUniqueId());
        player.teleport(Config.getLobbySpawn());
        Game.alivePlayers.remove(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        if (players.size() <= Config.getRequiredPlayers() && state.equals(GameStates.COUNTDOWN))
            reset();

        if (players.size() == 0 && state.equals(GameStates.LIVE))
            reset();

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
    }


    public int getID() { return id; }
    public List<UUID> getPlayers() { return players; }
    public GameStates getState() { return state; }
    public void setState(GameStates state) { this.state = state; }
    public Game getGame() { return game; }
}

