package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class HungerGamesCommand implements CommandExecutor {
    private Arena arena;
    private DeathEvent deathEvent;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (sender instanceof Player) {
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {

                player.sendMessage(ChatColor.GOLD + "Доступные для игры арены: ");
                for (Arena arena : Manager.getArenas()) {
                    player.sendMessage(ChatColor.GRAY + "- " + arena.getID());
                }

            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                if (Manager.isPlaying(player)) {
                    Manager.getArena(player).removePlayer(player);

                    player.sendTitle(ChatColor.RED + "ВЫ ВЫШЛИ С АРЕНЫ.", "", 20 * 1, 20 * 5, 20 * 3);

                    int playersAmount = (arena.players.size() - 1);
                    for (Player players : deathEvent.alivePlayers) {
                        players.sendMessage(ChatColor.RED + "(" + ChatColor.DARK_GRAY + (playersAmount - 1) + ChatColor.RED + "/" + ChatColor.DARK_GRAY + Config.getMaxPlayersAmount() + ChatColor.RED + ") "
                                + ChatColor.RED + "Игрок " + ChatColor.DARK_GRAY + player.getName() + ChatColor.RED + " вышел с арены.");
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "Ты не находишься на арене, чтобы её покидать!");
                }

            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                if (!Manager.isPlaying(player)) {
                    try {
                        int id = Integer.parseInt(args[1]);

                        if (id >= 0 && id <= (Config.getArenasAmount()) - 1) {
                            if (Manager.isRecruiting(id)) {
                                Manager.getArena(id).addPlayer(player);

                                player.sendTitle(ChatColor.GREEN + "ВЫ ЗАШЛИ НА АРЕНУ.", "", 20 * 1, 20 * 5, 20 * 3);

                                int playersAmount = (deathEvent.alivePlayers.size() - 1);
                                for (Player players : deathEvent.alivePlayers) {
                                    players.sendMessage(ChatColor.GREEN + "(" + ChatColor.GOLD + (playersAmount + 1) + ChatColor.GREEN + "/" + ChatColor.GOLD + Config.getMaxPlayersAmount() + ChatColor.GREEN + ") "
                                            + ChatColor.GREEN + "Игрок " + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " зашёл на арену.");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Ты уже находишься на другой арене!");
                                player.sendMessage(ChatColor.WHITE + "Используй " + ChatColor.GRAY + "/hg leave" + ChatColor.WHITE + ", чтобы выйти с этой арены.");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Арены с таким номером не существует!");
                            player.sendMessage(ChatColor.WHITE + "Используй " + ChatColor.GRAY + "/hg list" + ChatColor.WHITE + ", чтобы посмотреть все доступные арены.");
                        }
                    } catch (NumberFormatException x) {
                        player.sendMessage(ChatColor.RED + "Арены с таким номером не существует!");
                        player.sendMessage(ChatColor.WHITE + "Используй " + ChatColor.GRAY + "/hg list" + ChatColor.WHITE + ", чтобы посмотреть все доступные арены.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Ты уже находишься в игре!");
                }


            } else if ((args.length == 1 && args[0].equalsIgnoreCase("help")) || args.length == 0) {
                player.sendMessage(ChatColor.GOLD + "Все доступные команды мини-режима Hunger Games:");
                player.sendMessage(ChatColor.GRAY + "/hg list" + ChatColor.WHITE + " - список всех доступных арен");
                player.sendMessage(ChatColor.GRAY + "/hg join [Номер]" + ChatColor.WHITE + " - войти на выбранную арену");
                player.sendMessage(ChatColor.GRAY + "/hg leave" + ChatColor.WHITE + " - выйти с арены");
                player.sendMessage(ChatColor.GRAY + "/hg help" + ChatColor.WHITE + " - посмотреть список всех доступных команд");
                player.sendMessage(arena.players.size() + "");
                player.sendMessage(Manager.getArena(0).getState().toString());
                player.sendMessage(Manager.getArena(1).getState().toString());
                player.sendMessage(Manager.getArena(2).getState().toString());
            } else {
                player.sendMessage(ChatColor.RED + "Неизвестная команда! Используй ");
                player.sendMessage(ChatColor.WHITE + "Используй " + ChatColor.GRAY + "/hg help" + ChatColor.WHITE + ", чтобы посмотреть все доступные команды.");
            }

        } else {
            System.out.println("Ты не можешь использовать эту команду в консоли!");
        }
        return false;
    }
}



