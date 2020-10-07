package com.goldfinch.hungergames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (sender instanceof Player) {
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {

                player.sendMessage(ChatColor.GREEN + "Доступные арены:");
                for (Arena arena : Manager.getArenas()) {
                    player.sendMessage(ChatColor.GREEN + "- " + arena.getID());
                }

            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                if (Manager.isPlaying(player)) {
                    Manager.getArena(player).removePlayer(player);

                    player.sendMessage(ChatColor.RED + "Вы покинули арену.");
                } else { player.sendMessage(ChatColor.RED + "Вы не находитесь в игре!"); }



                // проблема в джоин!!!!!!!!!!!!!!!!!!!

            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {

                try {
                    int id = Integer.parseInt(args[1]);

                    if (id >= 0 && id <= (Config.getArenasAmount()) - 1) {
                        if (Manager.isRecruiting(id)) {
                            Manager.getArena(id).addPlayer(player);

                            player.sendMessage(ChatColor.GREEN + "Вы зашли на арену " + id);
                        } else { player.sendMessage(ChatColor.RED + "Вы не можете зайти на эту арену!"); }
                    } else {
                        player.sendMessage(ChatColor.RED + "Арены с таким номером не существует! Попробуйте: ");
                        player.sendMessage(ChatColor.RED + "- /arena list");
                    }
                } catch (NumberFormatException x) {
                    player.sendMessage(ChatColor.RED + "Арены с таким номером не существует! Попробуйте: ");
                    player.sendMessage(ChatColor.RED + "- /arena list");
                }

            } else {
                player.sendMessage(ChatColor.RED + "Неизвестная команда! Используйте эти команды:");
                player.sendMessage(ChatColor.RED + "- /arena list");
                player.sendMessage(ChatColor.RED + "- /arena join [id]");
                player.sendMessage(ChatColor.RED + "- /arena leave");
            }

        } else { System.out.println("Вы не можете использовать эту команду в консоли!"); }
        return false;
    }
}
