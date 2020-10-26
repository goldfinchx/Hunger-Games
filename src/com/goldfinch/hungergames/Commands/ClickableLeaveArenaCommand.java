package com.goldfinch.hungergames.Commands;

import com.goldfinch.hungergames.Core.Manager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClickableLeaveArenaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (Manager.isPlaying(player)) {
            Manager.getArena(player).removePlayer(player);

        } else { player.sendMessage(ChatColor.RED + "Ты не находишься в игре, чтобы из неё выходить!"); }

        return false;
    }

}
