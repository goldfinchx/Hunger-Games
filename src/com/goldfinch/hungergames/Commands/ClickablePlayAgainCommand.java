package com.goldfinch.hungergames.Commands;


import com.goldfinch.hungergames.Core.Manager;
import com.goldfinch.hungergames.Game.GameStates;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClickablePlayAgainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        for (int id = 0; id <= Manager.getArenas().size(); id++) {
            if (Manager.getArena(id).getState() == GameStates.COUNTDOWN || Manager.getArena(id).getState() == GameStates.RECRUITING) {
                Manager.getArena(player).removePlayer(player);
                Manager.getArena(id).addPlayer(player);
                return true;
            }

            player.sendMessage(ChatColor.RED + "Сейчас нет доступных арен!");
            player.sendMessage(ChatColor.WHITE + "Пожалуйста, подожди!");
        }

        return false;
    }
}
