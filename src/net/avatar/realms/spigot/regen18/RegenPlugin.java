package net.avatar.realms.spigot.regen18;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class RegenPlugin extends JavaPlugin {

    private RegenManager manager;
    private int taskId = -1;

    @Override
    public void onEnable() {
        disableRegenInWorlds();
        manager = new RegenManager();

        // Every 1/4 second, check for all players to regen
        taskId = getServer().getScheduler().scheduleSyncRepeatingTask(this, manager, 20, 5);
        // Listen for player disconnections
        getServer().getPluginManager().registerEvents(manager, this);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTask(taskId);
        enableRegenInWorlds();
    }

    private void disableRegenInWorlds() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("naturalRegeneration", "false");
        }
    }

    private void enableRegenInWorlds() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("naturalRegeneration", "true");
        }
    }
}
