package net.avatar.realms.spigot.regen18;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nokorbis on 19/03/2016.
 */
public class RegenManager implements Runnable, Listener{

    private Map<UUID, Long> lastRegens;

    public RegenManager() {
        lastRegens = new HashMap<UUID, Long>();
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (lastRegens.containsKey(player.getUniqueId())) {
                if (now < lastRegens.get(player.getUniqueId())) {
                    continue;
                }
            }
            if (!player.isDead() && player.getFoodLevel() >= 18 && player.getHealth() < player.getMaxHealth()) {
                double newHealth = player.getHealth() + 1;
                if (newHealth > 20.0) {
                    newHealth = 20.0;
                }
                else if (newHealth < 0.0) {
                    newHealth = 0.0;
                }
                player.setHealth(newHealth);
                //Do not regen this player for the next 4 seconds
                lastRegens.put(player.getUniqueId(), now + 4000);
            }
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (lastRegens.containsKey(event.getPlayer().getUniqueId())) {
            lastRegens.remove(event.getPlayer().getUniqueId());
        }
    }
}
