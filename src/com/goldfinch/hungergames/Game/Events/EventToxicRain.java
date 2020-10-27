package com.goldfinch.hungergames.Game.Events;

import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Core.Manager;
import com.goldfinch.hungergames.Game.Arena;
import com.goldfinch.hungergames.Game.Game;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class EventToxicRain implements Listener {

    private Main main;
    private Events events;
    int eventTimer;
    public EventToxicRain(Events events) { this.events = events; }

    public void activateToxicRain(Game game) {
        eventTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                EventToxicRain.activateEventOnPlayer(player);
                player.sendMessage(eventTimer + "");
            }

            if (eventTimer == 150 ) {
                Bukkit.getScheduler().cancelTask(eventTimer);
            }

        }, 0L, 40L);
    }


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

    public static void activateEventOnPlayer(Player player) {
        if (!isThereRoof(player)) {
            for (Player players : Manager.getArena(player).getPlayers()) {
                players.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5, 3, false, false));
            }
        }
    }
}
