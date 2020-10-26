package com.goldfinch.hungergames.Game.Chests;

import com.goldfinch.hungergames.Core.Config;
import com.goldfinch.hungergames.Core.Main;
import com.goldfinch.hungergames.Core.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ChestListener implements Listener {

    private Main main;
    public HashMap<Location, Boolean> openedChests = new HashMap<>();

    public ChestListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (Manager.isPlaying(player)) {
            if (e.getClickedBlock().getType().equals(Material.CHEST) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Block block = e.getClickedBlock();
                Location location = new Location(Bukkit.getWorld(block.getWorld().getName()), block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());

                if (!openedChests.containsKey(location)) {
                    openedChests.put(location, true);

                    int permittedAmountItems = main.getRandomInteger((main.getConfig().getInt("min-items-amount")), (main.getConfig().getInt("max-items-amount")));
                    Chest chest = (Chest) block.getState();
                    Inventory inventory = chest.getBlockInventory();

                    for (int amount = 0; amount <= permittedAmountItems; amount++) {
                        int slot = main.getRandomInteger(0, 26);
                        ItemStack item = Config.getRandomItem();
                        item.setAmount(Config.getAmount());

                        if (Config.getChance() > main.getRandomInteger(0, 100)) {
                            if (inventory.getItem(slot) == null) {
                                inventory.setItem(slot, item);

                            } else {
                                slot++;
                            }
                        }
                    }
                }
            }
        }
    }
}



