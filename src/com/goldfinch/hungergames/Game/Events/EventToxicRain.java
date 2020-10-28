package com.goldfinch.hungergames.Game.Events;

import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Core.Manager;
import com.goldfinch.hungergames.Game.Game;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class EventToxicRain implements Listener {

    private Main main;
    private Game game;
    public EventToxicRain(Game game) { this.game = game; }

    public static boolean isThereRoof(Player player) {
        HashMap<Material, Integer> blocksAbove = new HashMap<>();
        Block block = player.getLocation().getBlock();
        Material material = block.getType();
        Location blockLocation = block.getLocation();
        int x = blockLocation.getBlockY() + 30;
        int z = blockLocation.getBlockY()+2;

        for (int y = z ; x > y; y++) {
            blockLocation.setY(y - 1);
            material = blockLocation.getBlock().getType();
            blocksAbove.put(material, y);

            if (material != Material.AIR) { return true; }
        }
        return false;
    }

    public void activateEventOnPlayer(Player player) {
        if (!isThereRoof(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 3, false, false));
        }
    }
}

